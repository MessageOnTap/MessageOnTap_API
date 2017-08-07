/*
  Copyright 2017 CHIMPS Lab, Carnegie Mellon University

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package edu.cmu.chimps.messageontap_api;

import android.text.TextUtils;
import android.util.LongSparseArray;
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
import java.util.Set;

@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue"})
public class JSONUtils {
    
    public final static String TYPE_TRIGGER = "trigger";
    public final static String TYPE_TAG = "tag";
    public final static String TYPE_NODE = "node";
    public final static String TYPE_PARSE_TREE = "parse_tree";
    public final static String TYPE_TAG_SET = "tag_set";
    public final static String TYPE_TRIGGER_SET = "trigger_set";
    public final static String TYPE_CARD_LIST = "card_list";
    public final static String TYPE_TAG_ARRAY = "tag_array";

    /**
     * Generate a new Gson instance with customized type adapters.
     *
     * @return A Gson instance
     */
    public static Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(ParseTree.Node.class, new JsonDeserializer<ParseTree.Node>() {
                    /**
                     * Deserialize a node JSON to Node class object.
                     * @author Adam Yi &lt;xuan@yiad.am&gt;
                     */
                    @Override
                    public ParseTree.Node deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject nodeObj = json.getAsJsonObject();
                        Gson gson = new Gson();
                        ParseTree.Node node = gson.fromJson(json, ParseTree.Node.class);
                        node.setChildrenIds((HashSet) gson.fromJson(nodeObj.get("mChildrenIds"), new TypeToken<HashSet<Integer>>() {
                        }.getType()));
                        Set<Object> oldTagSet = (HashSet) gson.fromJson(nodeObj.get("mTagSet"), new TypeToken<HashSet<Object>>() {
                        }.getType());
                        Set<Object> newTagSet = new HashSet<>();
                        for (Object obj : oldTagSet) {
                            if (obj instanceof Double)
                                newTagSet.add((long) (double) obj);
                            else
                                newTagSet.add(obj);
                        }

                        node.setTagList(newTagSet);
                        return node;
                    }
                })
                .registerTypeAdapter(ParseTree.class, new JsonDeserializer<ParseTree>() {
                    @Override
                    /**
                     * Deserialize a parse tree JSON to ParseTree class object.
                     * @author: Adam Yi &lt;xuan@yiad.am&gt;
                     */
                    public ParseTree deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject treeObj = json.getAsJsonObject();
                        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<SparseArray<ParseTree.Node>>() {
                                }.getType(),
                                new SparseArrayTypeAdapter<ParseTree.Node>(TYPE_NODE))
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
                }).registerTypeAdapter(Tag.class, new JsonDeserializer<Tag>() {
                    @Override
                    /**
                     * Deserialize a tag JSON to Tag class object.
                     * @author: Adam Yi &lt;xuan@yiad.am&gt;
                     */
                    public Tag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject tagObj = json.getAsJsonObject();
                        Gson gson = new Gson();
                        Tag tag = gson.fromJson(json, Tag.class);
                        tag.setKeywordList((HashSet) gson.fromJson(tagObj.get("mRegularExpressions"), new TypeToken<HashSet<String>>() {
                        }.getType()));
                        return tag;
                    }
                }).registerTypeAdapter(Trigger.class, new JsonDeserializer<Trigger>() {
                    @Override
                    /**
                     * Deserialize a trigger JSON to Trigger class object.
                     * @author: Adam Yi &lt;xuan@yiad.am&gt;
                     */
                    public Trigger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject triggerObj = json.getAsJsonObject();
                        Gson gson = new Gson();
                        Trigger trigger = gson.fromJson(json, Trigger.class);
                        trigger.setConstraints((HashSet) gson.fromJson(triggerObj.get("mConstraints"), new TypeToken<HashSet<Trigger.Constraint>>() {
                        }.getType()));
                        return trigger;
                    }
                }).registerTypeAdapter(new TypeToken<LongSparseArray<Tag>>() {
                }.getType(), new LongSparseArrayTypeAdapter<Tag>(TYPE_TAG))
                .create();
    }

    /**
     * Convert a hashmap to a JSON string
     *
     * @param map the hash map
     * @return the JSON string
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
        HashMap<String, Object> retMap = new HashMap<>();

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
        HashMap<String, Object> map = new HashMap<>();

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
        List<Object> list = new ArrayList<>();
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
            case TYPE_TAG:
                type = new TypeToken<Tag>() {
                }.getType();
                break;
            case TYPE_TRIGGER:
                type = new TypeToken<Trigger>() {
                }.getType();
                break;
            case TYPE_NODE:
                type = new TypeToken<ParseTree.Node>() {
                }.getType();
                break;
            case TYPE_PARSE_TREE:
                type = new TypeToken<ParseTree>() {
                }.getType();
                break;
            case TYPE_TAG_SET:
                type = new TypeToken<HashSet<Tag>>() {
                }.getType();
                break;
            case TYPE_TAG_ARRAY:
                type = new TypeToken<LongSparseArray<Tag>>() {
                }.getType();
                break;
            case TYPE_TRIGGER_SET:
                type = new TypeToken<HashSet<Trigger>>() {
                }.getType();
                break;
            case TYPE_CARD_LIST:
                type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType();
                break;
            default:
                return "";
        }
        return gson.toJson(object, type);
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
            case TYPE_TAG:
                type = new TypeToken<Tag>() {
                }.getType();
                break;
            case TYPE_TRIGGER:
                type = new TypeToken<Trigger>() {
                }.getType();
                break;
            case TYPE_NODE:
                type = new TypeToken<ParseTree.Node>() {
                }.getType();
                break;
            case TYPE_PARSE_TREE:
                type = new TypeToken<ParseTree>() {
                }.getType();
                break;
            case TYPE_TAG_SET:
                type = new TypeToken<HashSet<Tag>>() {
                }.getType();
                break;
            case TYPE_TAG_ARRAY:
                type = new TypeToken<LongSparseArray<Tag>>() {
                }.getType();
                break;
            case TYPE_TRIGGER_SET:
                type = new TypeToken<HashSet<Trigger>>() {
                }.getType();
                break;
            case TYPE_CARD_LIST:
                type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType();
                break;
            default:
                return null;
        }
        return gson.fromJson(json, type);
    }

    public static HashMap<String, Object> refactorHashMap(HashMap<String, Object> mMap) {
        return refactorHashMap(mMap, 0);
    }

    /**
     * Check all double and float values in a hash map. If they are integers, cast them to
     * the data type that they should be.
     *
     * @param mMap the hash map to be refactored
     * @param mode -1: Integer only Mode; 0: Smart Mode; 1: Long only Mode
     * @return the hash map after refactoring
     * @author Adam Yi &lt;xuan@yiad.am&gt;
     */
    public static HashMap<String, Object> refactorHashMap(HashMap<String, Object> mMap, int mode) {
        for (String key : mMap.keySet()) {
            if (mMap.get(key) != null && (mMap.get(key) instanceof Double || mMap.get(key) instanceof Float)) {
                Double mDouble = (Double) mMap.get(key);
                long mLong = mDouble.longValue();
                if (mDouble == mLong) {
                    if (mode == 1) {
                        mMap.put(key, mLong);
                        continue;
                    }
                    int mInt = (int) mLong;
                    if (mode == -1) {
                        mMap.put(key, mInt);
                        continue;
                    }
                    if (mInt == mLong)
                        mMap.put(key, mInt);
                    else
                        mMap.put(key, mLong);
                }
            }
        }
        return mMap;
    }

    /**
     * Cast a number Object to Long
     *
     * @param num the object to be casted
     * @return the casted Long
     * @throws UnsupportedOperationException when num is not a number
     * @author Adam Yi &lt;xuan@yiad.am&gt;
     */
    public static Long longValue(Object num) throws UnsupportedOperationException {
        if (num instanceof Long)
            return (Long) num;
        if (num instanceof Integer)
            return (long) num;
        if (num instanceof Double)
            return (long) (double) num;
        if (num instanceof Float)
            return (long) (float) num;
        throw new UnsupportedOperationException();
    }

    /**
     * Cast a number Object to Integer
     *
     * @param num the object to be casted
     * @return the casted Integer
     * @throws UnsupportedOperationException when num is not a number
     * @author Adam Yi &lt;xuan@yiad.am&gt;
     */
    public static Integer intValue(Object num) throws UnsupportedOperationException {
        if (num instanceof Integer)
            return (Integer) num;
        if (num instanceof Long)
            return (int) (long) num;
        if (num instanceof Double)
            return (int) (double) num;
        if (num instanceof Float)
            return (int) (float) num;
        throw new UnsupportedOperationException();
    }
}
