// IPluginManagerCallback.aidl
package edu.cmu.chimps.messageontap_api;

import edu.cmu.chimps.messageontap_api.TaskData;

interface IPluginManager {

    void sendResponse(in TaskData data);

}
