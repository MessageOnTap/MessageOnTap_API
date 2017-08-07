/**
 * Copyright 2017 CHIMPS Lab, Carnegie Mellon University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

import java.util.HashMap;


public abstract class MessageOnTapPlugin extends Service {

    private static final String TAG = "Plugin_API";

    protected IPluginManager mManager;
    private LongSparseArray<Session> sessionList = new LongSparseArray<>();

    private IBinder mBinder = new IPlugin.Stub() {

        /**
         * This is called when a task is received.
         * @param data The data of the task
         * @throws RemoteException
         */
        @Override
        public void onTaskReceived(TaskData data) throws RemoteException {
//            Log.e("extension","Receive message");
            Log.e(TAG, "got message data: " + data);
            long sid = data.sid(),
                    tid = data.tid();
            String type = data.type();
            if (sid != -100) { // Eastern eggs are always fun. Aren't they?
                if (TextUtils.equals(type, MethodConstants.PMS_TYPE)) {
                    handlePMSTask(tid, sid, data.method());
                } else {
                    Task task = new Task(data);
                    try {
                        HashMap<String, Object> content = JSONUtils.toMap(data.content());
                        if (tid == 0) {
                            sessionList.put(sid, new Session(task));
                            initNewSession(sid, content);
                        } else {
                            Session session = sessionList.get(sid);
                            //session.newTask(task);
                            session.updateTaskResponse(task);
                            newTaskResponded(sid, tid, content);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Exception caught while running plugin code:");
                        e.printStackTrace();
                        Session session = sessionList.get(sid);
                        session.failTask(tid);
                    }
                }
            } else {
                Log.e(TAG, "Hello Developer! Test plugin communication up! Isn't it a nice day? Hooray lol");
            }
        }

        /**
         * This is called when PMS asks the plugin for PluginData.
         * @return the PluginData to be sent to PMS.
         * @throws RemoteException
         */
        @Override
        public PluginData getPluginData() throws RemoteException {
            return iPluginData();
        }

        /**
         * This is called when PMS asks to register itself.
         * @param manager the PluginManager of PMS
         * @throws RemoteException
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
         * @throws RemoteException
         */
        @Override
        public void unregisterManager(IPluginManager manager) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            mManager = null;
            //plugins.remove(packageName);
            /*boolean success = mListenerList.unregister(listener);
            if (success) {
                Log.d("plugin", "Unregistration succeed.");
            } else {
                Log.d("plugin", "Not found, cannot unregister.");
            }*/
        }

        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /*protected void sendSessionResponse(long sid, String type, String method, HashMap<String, Object> params) {
        Session session = sessionList.get(sid);
        Task task = session.getTask(new Long(0));
        task.prepareSendResponse(params);
        session.updateTaskResponse(0);
        try {
            mManager.sendResponse(task.getTaskData());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * A function to end an active session
     *
     * @param sid the ID of the session to be ended
     */
    protected void endSession(long sid) {
        Session session = sessionList.get(sid);
        session.updateTaskResponse(0);
        Task task = session.getTask(0);
        try {
            mManager.sendResponse(task.getTaskData().type(MethodConstants.PMS_TYPE).method(MethodConstants.PMS_METHOD_END_SESSION).content("{}"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiate a new session.
     */
    protected void createSession() {
        try {
            mManager.sendResponse(new TaskData().type(MethodConstants.PMS_TYPE).method(MethodConstants.PMS_METHOD_NEW_SESSION).content("{}"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
        TaskData data = new TaskData().content(json).type(type).method(method);
        Task task = new Task(data);
        task = session.newTask(task);
        try {
            mManager.sendResponse(task.getTaskData());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
                try {
                    mManager.sendResponse(new TaskData().sid(sid).tid(tid).type(MethodConstants.PMS_TYPE).method(MethodConstants.PMS_METHOD_STATUS_REPLY).content("{\"result\": " + sessionList.get(sid).getTask(tid).getStatus() + "}"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case MethodConstants.PMS_METHOD_RESPONSE_REFETCH:
                Session session = sessionList.get(sid);
                Task task = session.getTask(tid);
                if (task.getStatus() == 1) {
                    try {
                        mManager.sendResponse(task.getTaskData());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    // TODO:retry
                }
                break;
            default:
                Log.e(TAG, "Unknown PMS task received.");
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
     * @throws Exception
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
     * @throws Exception
     */
    protected abstract void newTaskResponded(long sid, long tid, HashMap<String, Object> data) throws Exception;
}
