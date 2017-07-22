package edu.cmu.chimps.messageontap_api;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This is the data type for plugins to set up their triggers at message perception
 */

public class Trigger {
    private ArrayList<String> mTriggerKeyList;
    private String mPackageName;

    public Trigger() {
        mTriggerKeyList = new ArrayList<>();
    }

    public Trigger(ArrayList<String> keyList) {
        mTriggerKeyList = keyList;
    }

    public boolean activateTrigger(Set<String> set) {
        for (String mKey : mTriggerKeyList) {
            if (!set.contains(mKey)) {
                return false;
            }
        }
        return true;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getJson() {
        return DataUtils.SimpleObjectToJson(this, Globals.TYPE_TRIGGER);
    }

    public String getTypeKey() {
        return Globals.TYPE_TRIGGER;
    }
}
