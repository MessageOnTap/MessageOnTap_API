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

    protected IPluginManager mManager;
    private LongSparseArray<Session> sessionList = new LongSparseArray<>();

    private IBinder mBinder = new IPlugin.Stub() {

        @Override
        public void onTaskReceived(TaskData data) throws RemoteException {
//            Log.e("extension","Receive message");
            Log.e("plugin", "got message data: " + data);
            long sid = data.sid(),
                    tid = data.tid();
            String type = data.type();
            if (sid != -100) { // Eastern eggs are always fun. Aren't they?
                if (TextUtils.equals(type, MethodConstants.PMS_TYPE)) {
                    handlePMSTask(tid, sid, data.method());
                } else {
                    Task task = new Task(data);
                    try {
                        HashMap<String, Object> content = DataUtils.toMap(data.content());
                        if (tid == 0) {
                            sessionList.put(sid, new Session(task));
                            initNewSession(sid, content);
                        } else {
                            Session session = sessionList.get(sid);
                            //session.newTask(task);
                            session.updateTaskResponse(task);
                            newTaskResponsed(sid, tid, content);
                        }
                    } catch (Exception e) {
                        Session session = sessionList.get(sid);
                        session.failTask(tid);
                    }
                }
            } else {
                Log.e("plugin", "Hello Developer! Test plugin communication up! Isn't it a nice day? Hooray lol");
            }
        }

        @Override
        public PluginData getPluginData() throws RemoteException {
            return iPluginData();
        }

        @Override
        public void registerManager(IPluginManager manager) throws RemoteException {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.e("plugin", "registering manager " + packageName);

            mManager = manager;
            //plugins.put(packageName, listener);
            //mListenerList.register(listener);
        }

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

    protected void endSession(long sid) {
        Session session = sessionList.get(sid);
        session.updateTaskResponse(0);
        Task task = session.getTask(0);
        try {
            mManager.sendResponse(task.getTaskData().type(MethodConstants.PMS_TYPE).method("sessionEnd").content(""));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    protected long newTaskRequest(long sid, String type, String method, HashMap<String, Object> params) {
        Session session = sessionList.get(sid);
        String json = DataUtils.hashMapToString(params);
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

    protected void handlePMSTask(long sid, long tid, String method) {
        Log.e("plugin", "In Handle PMS task");
        switch (method) {
            case "queryStatus":
                try {
                    mManager.sendResponse(new TaskData().sid(sid).tid(tid).type(MethodConstants.PMS_TYPE).method("statusReply").content("{\"result\": " + sessionList.get(sid).getTask(tid).getStatus() + "}"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "refetchResponse":
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
                Log.e("plugin", "Unknown PMS task received.");
        }
    }

    protected abstract PluginData iPluginData();

    protected abstract void initNewSession(long sid, HashMap<String, Object> data) throws Exception;

    protected abstract void newTaskResponsed(long sid, long tid, HashMap<String, Object> data) throws Exception;
}
