package edu.cmu.chimps.messageontap_api;

import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JSONUtils {
    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(ParseTree.class, new JsonDeserializer<ParseTree>() {
                    @Override
                    /**
                     * Deserialize a parse tree JSON to ParseTree class object.
                     * @author: Adam Yi <xuan@yiad.am>
                     */
                    public ParseTree deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject treeObj = json.getAsJsonObject();
                        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<SparseArray<ParseTree.Node>>() {
                                }.getType(),
                                new SparseArrayTypeAdapter<ParseTree.Node>(ParseTree.Node.class))
                                .create();
                        // Construct a tree (this shouldn't try to parse the sparseArray stuff
                        ParseTree tree = gson.fromJson(json, ParseTree.class);
                        SparseArray<ParseTree.Node> nodeList = gson.fromJson(treeObj.get("mNodeList"),
                                new TypeToken<SparseArray<ParseTree.Node>>() {
                                }.getType());
                        // set the correct node list
                        tree.setNodeList(nodeList);
                        return tree;
                    }
                }).create();
    }

    public static String hashMapToString(HashMap<String, Object> map) {
        return gson().toJson(map);
    }

    public static HashMap<String, Object> jsonToMap(JSONObject json) throws JSONException {
        HashMap<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static HashMap<String, Object> toMap(String text) throws JSONException {
        if (TextUtils.isEmpty(text))
            return new HashMap<>();
        else
            return toMap(new JSONObject(text));
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

    public static String simpleObjectToJson(Object object, String typeKey) {
        Gson gson = gson();
        Type type;
        switch (typeKey) {
            case Globals.TYPE_TRIGGER:
                type = new TypeToken<Trigger>() {
                }.getType();
                return gson.toJson(object, type);
            case Globals.TYPE_PARSETREE:
                type = new TypeToken<ParseTree>() {
                }.getType();
                return gson.toJson(object, type);
            default:
                return "";
        }
    }

    public static Object jsonToSimpleObject(String json, String typeKey) {
        Gson gson = gson();
        switch (typeKey) {
            case Globals.TYPE_TRIGGER:
                Type type = new TypeToken<Trigger>() {
                }.getType();
                return gson.fromJson(json, type);
            case Globals.TYPE_PARSETREE:
                type = new TypeToken<ParseTree>() {
                }.getType();
                return gson.fromJson(json, type);
            default:
                return "";
        }
    }
}
