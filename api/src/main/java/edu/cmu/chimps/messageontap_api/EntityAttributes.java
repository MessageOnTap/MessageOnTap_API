package edu.cmu.chimps.messageontap_api;


public class EntityAttributes {
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
//        public static final String IMAGE_TIME = "image_image_time";
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
    }

    public static final String CURRENT_MESSAGE_CONTACT_NAME = "current_message_contact_name";

    public static final String CURRENT_MESSAGE_RELATIVE_POSITION = "current_message_relative_position";
    public static final String CURRENT_MESSAGE_EMBEDDED_TIME = "current_message_embedded_time";
    public static final String CURRENT_MESSAGE_TEXT_BOX_ACCESSIBILITY_NODE_INFO = "current_message_text_box_accessibility_node_info";

}
