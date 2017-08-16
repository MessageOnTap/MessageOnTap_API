/*
  Copyright 2017 CHIMPS Lab, Carnegie Mellon University

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package edu.cmu.chimps.messageontap_api;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;

import org.json.JSONException;

import java.util.HashMap;

@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue", "UnusedReturnValue"})
public abstract class MessageOnTapPlugin extends Service {

    private static final String TAG = "Plugin_API";

    private IPluginManager mManager = null;
    private LongSparseArray<Session> sessionList = new LongSparseArray<>();

    private IBinder mBinder = new IPlugin.Stub() {

        /**
         * This is called when a task is received.
         * @param data The data of the task
         * @throws RemoteException when AIDL goes wrong
         */
        @Override
        public void onTaskReceived(TaskData data) throws RemoteException {
//            Log.e("extension","Receive message");
            Log.e(TAG, "got message data: " + data);
            long sid = data.sid(),
                    tid = data.tid();
            String type = data.type();
            HashMap content;
            if (sid != -100) { // Eastern eggs are always fun. Aren't they?
                if (TextUtils.equals(type, MethodConstants.PMS_TYPE)) {

                    //Check whether it's a new session request
                    if (tid == 0 && TextUtils.equals(data.method(), MethodConstants.PMS_METHOD_NEW_SESSION)) {

                        // Try to parse data JSON content
                        try {
                            content = JSONUtils.toMap(data.content());
                        } catch (JSONException e) {
                            Log.e(TAG, "Exception caught while parsing JSON sent by PMS (new session init):");
                            e.printStackTrace();
                            Session session = sessionList.get(sid);
                            session.failTask(tid);
                            return;
                        }

                        // Call developer's function to handle new session
                        try {
                            sessionList.put(sid, new Session(new Task(data)));
                            initNewSession(sid, content);
                        } catch (Exception e) {
                            Log.e(TAG, "Exception caught while running plugin code (new session init):");
                            e.printStackTrace();
                            Session session = sessionList.get(sid);
                            session.failTask(tid);
                        }
                    } else // otherwise, deal with session control related packets
                        handlePMSTask(tid, sid, data.method());

                } else {

                    // Try to parse data JSON content
                    try {
                        content = JSONUtils.toMap(data.content());
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught while parsing JSON sent by PMS (task response, see below for details)");
                        e.printStackTrace();
                        Session session = sessionList.get(sid);
                        session.failTask(tid);
                        return;
                    }

                    // Call developer's function to handle new task response
                    try {
                        Session session = sessionList.get(sid);
                        //session.newTask(task);
                        session.updateTaskResponse(new Task(data));
                        newTaskResponded(sid, tid, content);
                    } catch (Exception e) {
                        Log.e(TAG, "Exception caught while running plugin code (task response, see below for details)");
                        e.printStackTrace();
                        Session session = sessionList.get(sid);
                        session.failTask(tid);
                    }
                }
            } else { // They really are fun.
                Log.e(TAG, "Hello Developer! Test plugin communication up! Isn't it a nice day? Hooray lol");
            }
        }

        /**
         * This is called when PMS asks the plugin for PluginData.
         * @return the PluginData to be sent to PMS.
         * @throws RemoteException when AIDL goes wrong
         */
        @Override
        public PluginData getPluginData() throws RemoteException {
            return iPluginData();
        }

        /**
         * This is called when PMS asks to register itself.
         * @param manager the PluginManager of PMS
         * @throws RemoteException when AIDL goes wrong
         */
        @Override
        public void registerManager(IPluginManager manager) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.e(TAG, "registering manager " + packageName);

            mManager = manager;
            //plugins.put(packageName, listener);
            //mListenerList.register(listener);
        }

        /**
         * This is called when PMS asks to unregister itself.
         * @param manager the PluginManager of PMS
         * @throws RemoteException when AIDL goes wrong
         */
        @Override
        public void unregisterManager(IPluginManager manager) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.e(TAG, "Unregistering manager " + packageName);
            mManager = null;
            //plugins.remove(packageName);
            /*boolean success = mListenerList.unregister(listener);
            if (success) {
                Log.d("plugin", "Unregistration succeed.");
            } else {
                Log.d("plugin", "Not found, cannot unregister.");
            }*/
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Send data packet to PMS with auto-retry function (max retry: 2 times).
     *
     * @param taskData          the data to be sent to PMS
     * @param humanReadableName a human readable name for the data to be sent,
     *                          which is used for log printing.
     * @return boolean success or not
     */
    protected boolean sendData(TaskData taskData, String humanReadableName) {
        return sendData(taskData, humanReadableName, 3);
    }

    /**
     * Send data packet to PMS with auto-retry function (max retry: 2 times).
     *
     * @param taskData          the data to be sent to PMS
     * @param humanReadableName a human readable name for the data to be sent,
     *                          which is used for log printing.
     * @param tryNum            the maximum try times (including initial try, = max retry times + 1)
     * @return success or not
     */
    protected boolean sendData(TaskData taskData, String humanReadableName, int tryNum) {
        //TODO: check mManager status and reconnect if necessary

        if (tryNum < 1) // WTF is the developer trying to do?
            tryNum = 1;
        else if (tryNum > 10) // too much for a packet, isn't it?
            tryNum = 10;

        String errMsg = null;
        boolean fail = true;
        for (int i = tryNum; (i > 0) && fail; --i) {
            try {
                mManager.sendResponse(taskData);
                fail = false;
                Log.e(TAG, "Successfully sent " + humanReadableName + "to PMS");
            } catch (RemoteException e) {
                if (errMsg == null) // just another optimization
                    errMsg = taskData.idString() + " Error sending " + humanReadableName + "to PMS";
                if (i != 1)
                    Log.e(TAG, errMsg + ", retrying (see below for details).");
                else
                    Log.e(TAG, errMsg + ", giving up (see below for details).");
                e.printStackTrace();
            }
        }
        return !fail;
    }


    /**
     * A function to end an active session
     *
     * @param sid the ID of the session to be ended
     */
    protected void endSession(long sid) {
        Session session = sessionList.get(sid);
        session.updateTaskResponse(0);
        Task task = session.getTask(0);
        sendData(task.getTaskData()
                        .type(MethodConstants.PMS_TYPE)
                        .method(MethodConstants.PMS_METHOD_END_SESSION)
                        .content("{}"),
                "session end packet");
    }

    /**
     * Initiate a new session.
     */
    protected void createSession() {
        sendData(new TaskData()
                        .sid(-100)
                        .type(MethodConstants.PMS_TYPE)
                        .method(MethodConstants.PMS_METHOD_NEW_SESSION)
                        .content("{}"),
                "new session request");
    }

    /**
     * Create a new task request.
     *
     * @param sid    the ID of the Session where the task belongs to
     * @param type   the type of the task
     * @param method the method of the task
     * @param params the parameters of the task
     * @return the ID of the task created
     */
    protected long createTask(long sid, String type, String method, HashMap<String, Object> params) {
        Session session = sessionList.get(sid);
        String json = JSONUtils.hashMapToString(params);
        TaskData data = new TaskData().sid(sid).content(json).type(type).method(method);
        Task task = new Task(data);
        task = session.newTask(task);
        sendData(task.getTaskData(), "task creation request");
        return task.getTaskData().tid();
    }

    /**
     * Handle a incoming task, type of which is PMS.
     * Those tasks are used for internal task and session
     * management.
     *
     * @param sid    ID of Session
     * @param tid    ID of Task
     * @param method Method of PMS task
     */
    protected void handlePMSTask(long sid, long tid, String method) {
        Log.e(TAG, "In Handle PMS task");
        switch (method) {
            case MethodConstants.PMS_METHOD_STATUS_QUERY:
                sendData(new TaskData()
                                .sid(sid)
                                .tid(tid)
                                .type(MethodConstants.PMS_TYPE)
                                .method(MethodConstants.PMS_METHOD_STATUS_REPLY)
                                .content("{\"result\": "
                                        + sessionList.get(sid).getTaskStatus(tid)
                                        + "}")
                        , "status reply"
                        , 1);
                break;
            case MethodConstants.PMS_METHOD_RESPONSE_REFETCH:
                Session session = sessionList.get(sid);
                Task task = session.getTask(tid);
                if (task == null) {
                    Log.e(TAG, "Received response refetch request for a task that does not exist... Ignored");
                } else {
                    if (task.getStatus() == 1) {
                        sendData(task.getTaskData(), "task response (resend)", 2);
                    } else {
                        // TODO:retry
                    }
                }
                break;
            case MethodConstants.PMS_METHOD_NEW_SESSION:
                Log.e(TAG, "Received new session request for non-zero task ID... Ignored");
                break;
            default:
                Log.e(TAG, "Unknown PMS task (" + method + ") received.");
        }
    }

    /**
     * This should be overridden by plugin developers.
     * <p>
     * Return the PluginData of the plugin.
     *
     * @return the PluginData of the plugin
     */
    protected abstract PluginData iPluginData();

    /**
     * This should be overridden by plugin developers.
     * <p>
     * This is called when a new session is initiated
     * by the PMS.
     *
     * @param sid  the ID of the session
     * @param data the data of the session
     * @throws Exception errors do happen huh
     */
    protected abstract void initNewSession(long sid, HashMap<String, Object> data) throws Exception;

    /**
     * This should be overridden by plugin developers.
     * <p>
     * This is called when PMS sends a response to a
     * task request that the plugin has sent to the
     * PMS.
     *
     * @param sid  the Session ID of the task
     * @param tid  the Task ID of the task
     * @param data the data of the response
     * @throws Exception errors do happen huh
     */
    protected abstract void newTaskResponded(long sid, long tid, HashMap<String, Object> data) throws Exception;
}
