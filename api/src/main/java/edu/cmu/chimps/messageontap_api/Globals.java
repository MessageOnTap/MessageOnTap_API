/*
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

//TODO Change the Globals which could be downloaded from online server from PKG
@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue"})
public class Globals {

    // These are the datatypes that would be used in the query service.
    public final static String KEY_NODE_TYPE = "node_type";
    public final static String KEY_MAP = "map";
    public final static String KEY_GRAPH_COMPONENT_TYPE = "graph_component_type";
    public final static String KEY_NODE = "node";
    public final static String KEY_RELATIONSHIP = "relationship";


    public final static String KEY_DATASTORE_DATA_TYPE = "datastore_data_type";
    public final static String KEY_DATASTORE_DATA = "datastore_data";

    public final static String KEY_CARD_LIST = "card_list";

    // The action type class and vert text type action
    public final static String KEY_ACTION_ORIGIN = "action_origin";
    public final static String KEY_ACTION_PKG_RETRIEVAL = "action_pkg_retrival";
    public final static String KEY_ACTION_CREATE_CONTACT = "action_create_contact";
    public final static String KEY_ACTION_UPDATE_CONTACT = "action_update_contact";
    public final static String KEY_ACTION_CREATE_GRAPH_NODE = "action_create_graph_node";
    public final static String KEY_ACTION_UPDATE_GRAPH_NODE = "action_update_graph_node";
    public final static String KEY_ACTION_MESSAGE_PERCEPTION = "action_message_perception";
    public final static String KEY_ACTION_UI = "action_ui";

    public final static String KEY_ACTION = "action";
    public final static String KEY_QUERY_SUBJECT = "query_subject";
    public final static String KEY_QUERY_INFO = "query_info";
    public final static String KEY_QUERY_ID = "query_id";
    public final static String KEY_ATTRIBUTE_LIST = "attribute_list";
    //An action Type for representing doing nothing
    public final static String ACTION_UPDATE = "update";
    public final static String ACTION_SHOW = "show";
    public final static String ACTION_RETRIEVE = "retrieve";
    public final static String ACTION_ADD_EXACT = "add_exact";
    public final static String ACTION_END = "end";
    public final static String ACTION_CREATE = "create";

    //Object Type
    public final static String TYPE_TRIGGER = "trigger";
    public final static String TYPE_TAG = "tag";
    public final static String TYPE_NODE = "node";
    public final static String TYPE_PARSE_TREE = "parse_tree";
    public final static String TYPE_TAG_SET = "tag_set";
    public final static String TYPE_TRIGGER_SET = "trigger_set";
    public final static String TYPE_CARD_LIST = "card_list";
    public final static String TYPE_TAG_ARRAY = "tag_array";


}
