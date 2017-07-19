// IPluginManager.aidl
package edu.cmu.chimps.messageontap_api;

import edu.cmu.chimps.messageontap_api.IPluginManager;
import edu.cmu.chimps.messageontap_api.PluginData;
import edu.cmu.chimps.messageontap_api.MessageData;

interface IPlugin {
    void onMessageReceived(in MessageData data);
    PluginData getPluginData();

    void registerManager(IPluginManager cb);
    void unregisterManager(IPluginManager cb);
}
