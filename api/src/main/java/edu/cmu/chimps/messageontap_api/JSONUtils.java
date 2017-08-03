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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class JSONUtils {

    /**
     * Generate a new Gson instance with customized type adapters.
     *
     * @return A Gson instance
     */
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

    /**
     * Convert a hashmap to a JSON string
     *
     * @param map
     * @return
     */
    public static String hashMapToString(HashMap<String, Object> map) {
        return gson().toJson(map);
    }

    /**
     * Convert a JSONObject to a HashMap
     *
     * @param json the JSONObject
     * @return the HashMap converted
     * @throws JSONException
     */
    public static HashMap<String, Object> jsonToMap(JSONObject json) throws JSONException {
        HashMap<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    /**
     * Convert a JSON string to a HashMap
     *
     * @param text the JSON string
     * @return the HashMap converted
     * @throws JSONException
     */
    public static HashMap<String, Object> toMap(String text) throws JSONException {
        if (TextUtils.isEmpty(text))
            return new HashMap<>();
        else
            return toMap(new JSONObject(text));
    }

    /**
     * Convert a JSONObject to a HashMap
     *
     * @param object the JSON Object
     * @return the HashMap converted
     * @throws JSONException
     */
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

    /**
     * Convert a JSONArray to a list
     *
     * @param array the JSON Array
     * @return the List converted
     * @throws JSONException
     */
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

    /**
     * Convert a simple object to JSON string
     *
     * @param object  the object to be converted
     * @param typeKey a string stating the type of the object
     * @return the JSON string
     */
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

    /**
     * Convert a JSON string to a simple object
     *
     * @param json    the JSON string to be converted
     * @param typeKey a string stating the type of the object
     * @return the Object
     */
    public static Object jsonToSimpleObject(String json, String typeKey) {
        Gson gson = gson();
        Type type;
        switch (typeKey) {
            case Globals.TYPE_TRIGGER:
                type = new TypeToken<Trigger>() {
                }.getType();
                break;
            case Globals.TYPE_PARSETREE:
                type = new TypeToken<ParseTree>() {
                }.getType();
                break;
            case Globals.TYPE_TAGSET:
                type = new TypeToken<HashSet<Tag>>() {
                }.getType();
                break;
            case Globals.TYPE_TRIGGERSET:
                type = new TypeToken<HashSet<Trigger>>() {
                }.getType();
                break;
            default:
                return null;
        }
        return gson.fromJson(json, type);
    }
}
