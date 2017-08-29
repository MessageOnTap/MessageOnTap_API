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

    public static class PM {
        public static final String TRIGGER_SOURCE = "trigger";
        public static final String QUERY_ID = "query_id";
        public static final String METHOD = "method";
        public static final String PARAMETERS = "params";
        public static final String PARSE_TREE = "tree";
        public static final String CURRENT_MESSAGE_CONTACT_NAME = "contact_name";
        public static final String CURRENT_MESSAGE_RELATIVE_POSITION = "relative_position";
        public static final String CURRENT_MESSAGE_EMBEDDED_TIME = "embedded_time";
        public static final String CURRENT_MESSAGE_TEXT_BOX_ACCESSIBILITY_NODE_INFO = "text_box_node";
        public static final String TAG_PERSON = "person";
        public static final String TAG_LOCATION = "location";
        public static final String TAG_EVENT = "event";
        public static final String TAG_PHONE_NUMBER = "phone";
        public static final String TAG_EMAIL_ADDRESS = "email";
        public static final String TAG_STREET_ADDRESS = "address";
        public static final String TAG_WEB_URL = "url";
        public static final String API_VERSION = BuildConfig.API_VERSION;

    }

    public class Action {
        //TODO: move things that plugins do NOT use back
        public final static String SKIP_UI = "skip_ui";

        public final static String ALARM_EXTRA_HOUR = "hour";
        public final static String ALARM_EXTRA_MINUTE = "minute";
        public final static String ALARM_EXTRA_LABEL = "label";

        public final static String CONTACT_EXTRA_NAME = "name";
        public final static String CONTACT_EXTRA_PHONE = "phone";
        public final static String CONTACT_EXTRA_EMAIL = "email";

        public final static String PHONE_EXTRA_TEL = "phone_extra_tel";

        public final static String CAL_EXTRA_TITLE = "title";
        public final static String CAL_EXTRA_NOTE = "note";
        public final static String CAL_EXTRA_TIME_START = "time_start";
        public final static String CAL_EXTRA_TIME_END = "time_end";
        public final static String CAL_EXTRA_IS_ALL_DAY = "all_day";
        public final static String CAL_EXTRA_LOCATION = "location";
        public final static String CAL_EXTRA_INVITEE = "invitee";

        public final static String EMAIL_EXTRA_SUBJECT = "subject";
        public final static String EMAIL_EXTRA_BODY = "body";
        public final static String EMAIL_EXTRA_TO = "to";
        public final static String EMAIL_EXTRA_CC = "cc";
        public final static String EMAIL_EXTRA_BCC = "bcc";
        public final static String EMAIL_EXTRA_FILE = "file";

        public final static String MAP_SEARCH_EXTRA_QUERY = "query";

        public final static String SET_TEXT_EXTRA_NODE_INFO = "node_info";
        public final static String SET_TEXT_EXTRA_MESSAGE = "message";
        public final static String SET_TEXT_EXTRA_IS_REPLACED = "is_replaced";

        public final static String SHARE_EXTRA_MESSAGE = "message";
        public final static String SHARE_EXTRA_MESSAGE_TYPE = "type";
        public final static String SHARE_EXTRA_APP = "app";
        public final static String SHARE_EXTRA_REFERENCE_LIST = "reference_list";
        public final static String SHARE_EXTRA_TOAST = "toast";
        public final static String SHARE_EXTRA_MESSAGE_TYPE_TEXT = "text";
        public final static String SHARE_EXTRA_MESSAGE_TYPE_IMAGE = "image";
        public final static String SHARE_EXTRA_MESSAGE_TYPE_DOCUMENT = "document";

        public static final String RESULT_STATUS = "result_status";
        public static final int RESULT_STATUS_MISSING_MANDATORY_DATA = 0;
        public static final int RESULT_STATUS_PERFORMED = 1;
        public static final int RESULT_STATUS_FAILED = 2;
        public static final String RESULT_EXTRA = "result_extra";

        public final static String SHARE_RESULT_LIST_NOT_FOUND = "not_found";
        public final static String SHARE_RESULT_LIST_DUPLICATE = "duplicate";
        public final static String SHARE_RESULT_LIST_SELECTED = "selected";
    }

    public class UI {
        public final static String BUBBLE_FIRST_LINE = "first_line";
        public final static String BUBBLE_SECOND_LINE = "second_line";
        public final static String ICON_TYPE_STRING = "icon_type";
        public final static String STATUS = "bubble_status";
        public final static String HTML_STRING = "html_string";
        public final static String UPDATE_OFFSET = "update_offset";

    }
}
