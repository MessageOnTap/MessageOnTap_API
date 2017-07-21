package edu.cmu.chimps.messageontap_api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataUtils {
    public static String hashMapToString(HashMap<String, Object> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

    public static HashMap<String, Object> jsonToMap(JSONObject json) throws JSONException {
        HashMap<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static String SimpleObjectToJson(Object object, String typeKey) {
        Gson gson = new Gson();
        switch (typeKey) {
            case Globals.TYPE_TRIGGER:
                Type type = new TypeToken<List<Trigger>>() {
                }.getType();
                return gson.toJson(object, type);
            default:
                return "";
        }
    }

    public static Object JsonToSimpleObject(String json, String typeKey) {
        Gson gson = new Gson();
        switch (typeKey) {
            case Globals.TYPE_TRIGGER:
                Type type = new TypeToken<List<Trigger>>() {
                }.getType();
                return gson.fromJson(json, type);
            default:
                return "";
        }
    }
}
