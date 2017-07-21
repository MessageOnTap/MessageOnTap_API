package edu.cmu.chimps.messageontap_api;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


public abstract class MessageOnTapPlugin extends Service {
    /**
     * The {@link Intent} action representing a MessageOnTap plugin. This service should
     * declare an <code>&lt;intent-filter&gt;</code> for this action in order to register with
     * DashClock.
     */
    public static final String ACTION_EXTENSION = "edu.cmu.chimps.messageontap_prototype.Plugin";

    protected IPluginManager mManager;

    private IBinder mBinder = new IPlugin.Stub() {

        @Override
        public void onMessageReceived(MessageData data) throws RemoteException {
//            Log.e("extension","Receive message");
            analyzeMessage(data);
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

    protected abstract PluginData iPluginData();

    protected abstract void analyzeMessage(MessageData data);
}
