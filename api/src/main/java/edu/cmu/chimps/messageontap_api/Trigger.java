package edu.cmu.chimps.messageontap_api;

import android.content.ComponentName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This is the data type for pluggings to set up their triggers at message perception
 */

public class Trigger {
    private ArrayList<String> mTriggerKeyList;
    ComponentName mComponentName;
    public Trigger(ComponentName componentName){
        mTriggerKeyList = new ArrayList<>();
        mComponentName = componentName;
    }

    public Trigger(ComponentName componentName,ArrayList<String> keyList){
        mTriggerKeyList = keyList;
        mComponentName = componentName;
    }

    public boolean activateTrigger(Set<String> set){
        for (String mKey : mTriggerKeyList){
            if(!set.contains(mKey)){
                return false;
            }
        }
        return true;
    }

    public ComponentName getComponentName(){
        return mComponentName;
    }

    public String getJson(){
        return DataUtils.SimpleObjectToJson(this,Globals.TYPE_TRIGGER);
    }

    public String getTypeKey(){
        return Globals.TYPE_TRIGGER;
    }
}
