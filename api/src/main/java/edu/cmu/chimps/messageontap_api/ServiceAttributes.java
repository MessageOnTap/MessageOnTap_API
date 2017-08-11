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


@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue"})
public class ServiceAttributes {
    public static class Graph {
        public static class Person {
            public static final String TYPE = "person_type";
            public static final String NAME = "person_name";
            public static final String NUMBER = "phone_number";
            public static final String WORK_PHONE_NUMBER = "person_work_phone_number";
            public static final String HOME_PHONE_NUMBER = "person_home_phone_number";
            public static final String EMAIL_ADDRESS = "person_email_address";
            public static final String CONTACT_ID = "person_contact_id";
            public static final String NODE_ID = "person_node_id";
        }

        public static class Image {
            public static final String TYPE = "image_type";
            public static final String URI = "image_uri";
            public static final String TAKEN_TIME = "image_taken_time";
            public static final String NODE_ID = "image_node_id";
        }

        public static class Place {
            public static final String TYPE = "place_type";
            public static final String NAME = "place_name";
            public static final String GPS_COORDINATE = "place_gps_coordinate";
            public static final String STREET_ADDRESS = "place_street_address";
            public static final String PLACE_CATEGORY = "place_category";
            public static final String NODE_ID = "place_node_id";
        }

        public static class Event {
            public static final String TYPE = "event_type";
            public static final String NAME = "event_name";
            public static final String START_TIME = "event_start_time";
            public static final String END_TIME = "event_end_time";
            public static final String EVENT_CATEGORY = "event_category";
            public static final String CALENDAR_ID = "event_calendar_id";
            public static final String TIME = "event_time";
            public static final String NODE_ID = "event_node_id";
        }

        public static class BrowserSearch {
            public static final String TYPE = "browser_search_type";
            public static final String TITLE = "browser_search_title";
            public static final String NODE_ID = "browser_search_node_id";
        }

        public static class BrowserVisit {
            public static final String TYPE = "browser_visit_type";
            public static final String TITLE = "browser_visit_title";
            public static final String URL = "browser_visit_url";
            public static final String NODE_ID = "browser_visit_node_id";
        }

        public static class Flight {
            public static final String TYPE = "flight_type";
            public static final String ARRIVAL_AIRPORT = "flight_arrival_airport";
            public static final String ARRIVAL_TIME = "flight_arrival_time";
            public static final String DEPARTURE_AIRPORT = "flight_departure_airport";
            public static final String DEPARTURE_TIME = "flight_departure_time";
            public static final String FLIGHT_NUMBER = "flight_number";
            public static final String FLIGHT_SELLER = "flight_seller";
            public static final String NODE_ID = "fight_node_id";
        }

        public static class Ticket {
            public static final String TYPE = "ticket_type";
            public static final String NUMBER = "ticket_number";
            public static final String SEAT = "ticket_seat";
            public static final String OWNER = "ticket_owner";
            public static final String DESCRIPTION = "ticket_description";
            public static final String NODE_ID = "ticket_node_id";
        }

        public static class Order {
            public static final String TYPE = "order_type";
            public static final String NUMBER = "order_number";
            public static final String SELLER = "order_seller";
            public static final String NODE_ID = "order_node_id";
        }

        public static class Document {
            public static final String TYPE = "document_type";
            public static final String ID = "document_id";
            public static final String TITLE = "document_title";
            public static final String CREATED_TIME = "document_created_time";
            public static final String MODIFIED_TIME = "document_modified_time";
            public static final String DESCRIPTION = "document_description";
            public static final String SIZE = "document_size";
            public static final String NODE_ID = "document_node_id";
        }

        public static final String SYNTAX_TREE = "syntax_tree";
        public static final String CARD_LIST = "card_list";

    }

    public static class PMS {
        public static final String TRIGGER_SOURCE = "trigger";
        public static final String QUERY_ID = "query_id";
        public static final String METHOD = "method";
        public static final String PARAMETERS = "params";
        public static final String PARSE_TREE = "tree";
        public static final String CURRENT_MESSAGE_CONTACT_NAME = "contact_name";
        public static final String CURRENT_MESSAGE_RELATIVE_POSITION = "relative_position";
        public static final String CURRENT_MESSAGE_EMBEDDED_TIME = "embedded_time";
        public static final String CURRENT_MESSAGE_TEXT_BOX_ACCESSIBILITY_NODE_INFO = "text_box_node";
	public static final String TAG_PERSON = "tag_person";
	public static final String TAG_LOCATION = "tag_location";
	public static final String TAG_EVENT = "tag_event";
	public static final String TAG_PHONE_NUMBER = "tag_phone_number";
	public static final String TAG_EMAIL_ADDRESS = "tag_email_address";
	public static final String TAG_STREET_ADDRESS = "tag_street_address";
	public static final String TAG_WEB_URL = "tag_web_url";
	
    }

    public class Action {
        //TODO: move things that plugins do NOT use back
        public final static String SKIP_UI = "skip_ui";

        public final static String ALARM_EXTRA_HOUR = "alarm_extra_hour";
        public final static String ALARM_EXTRA_MINUTE = "alarm_extra_minute";
        public final static String ALARM_EXTRA_LABEL = "alarm_extra_label";

        public final static String PHONE_EXTRA_TEL = "phone_extra_tel";

        public final static String ACTION_CALENDAR_SEARCH = "action_calendar_search";

        public final static String CAL_EXTRA_TITLE = "calendar_extra_title";
        public final static String CAL_EXTRA_NOTE = "calendar_extra_note";
        public final static String CAL_EXTRA_TIME_START = "calendar_extra_time_start";
        public final static String CAL_EXTRA_TIME_END = "calendar_extra_time_end";
        public final static String CAL_EXTRA_IS_ALLDAY = "calendar_extra_is_allday";
        public final static String CAL_EXTRA_LOCATION = "calendar_extra_location";
        public final static String CAL_EXTRA_INVITEE = "calendar_extra_invitee";

        public final static String EMAIL_EXTRA_SUBJECT = "email_extra_subject";
        public final static String EMAIL_EXTRA_BODY = "email_extra_body";
        public final static String EMAIL_EXTRA_TO = "email_extra_to";
        public final static String EMAIL_EXTRA_CC = "email_extra_cc";
        public final static String EMAIL_EXTRA_BCC = "email_extra_bcc";
        public final static String EMAIL_EXTRA_FILE = "email_extra_file";

        public final static String MAP_SEARCH_EXTRA_QUERY = "map_search_extra_query";

        public final static String SET_TEXT_EXTRA_NODE_INFO = "set_text_extra_nodeinfo";
        public final static String SET_TEXT_EXTRA_MESSAGE = "set_text_extra_message";
        public final static String SET_TEXT_EXTRA_IS_REPLACED = "set_text_extra_is_replaced";

        public final static String SHARE_EXTRA_MESSAGE = "share_extra_message";
        public final static String SHARE_EXTRA_APP = "share_extra_app";
        public final static String SHARE_EXTRA_REFERENCE_LIST = "share_extra_reference_list";
        public final static String SHARE_EXTRA_TOAST = "share_extra_toast";

        public static final String RESULT = "result";
        public static final String RESULT_EXTRA = "result_extra";

        public final static String SHARE_RESULT_LIST_NOTFOUND = "share_result_list_notfound";
        public final static String SHARE_RESULT_LIST_DUPLICATE = "share_result_list_duplicate";
        public final static String SHARE_RESULT_LIST_SELECTED = "share_result_list_selected";
    }

    public class UI {
        public final static String BUBBLE_FIRST_LINE = "first_line";
        public final static String BUBBLE_SECOND_LINE = "second_line";
        public final static String ICON_TYPE_STRING = "icon_type";
        public final static String STATUS = "bubble_status";
        //TODO
    }
}
