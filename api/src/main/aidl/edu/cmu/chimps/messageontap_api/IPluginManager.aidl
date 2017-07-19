// IPluginManagerCallback.aidl
package edu.cmu.chimps.messageontap_api;

import edu.cmu.chimps.messageontap_api.MessageData;

interface IPluginManager {

    void sendResponse(in MessageData data);

}
