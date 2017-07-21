package edu.cmu.chimps.messageontap_api;


public class EntityAttributes {

    public static class Person {
        public static final String TYPE_KEY = "person_type_key";
        public static final String PERSON_NAME = "person_name";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String WORKPHONE_NUMBER = "person_workphone_number";
        public static final String HOMEPHONE_NUMBER = "person_homephone_number";
        public static final String EMAIL_ADDRESS = "email_address";
        public static final String CONTACT_ID = "contact_id";
        public static final String NODE_ID = "person_node_id";
    }

    public static class Image {
        public static final String TYPE_KEY = "image_type_key";
        public static final String IMAGE_URI = "image_uri";
        public static final String TAKEN_TIME = "image_taken_time";
//        public static final String IMAGE_TIME = "image_image_time";
    }

    public static class Place {
        public static final String TYPE_KEY = "place_type_key";
        public static final String PLACE_NAME = "place_name";
        public static final String GPS_COORDINATE = "place_gps_coordinate";
        public static final String STREET_ADDRESS = "place_street_address";
        public static final String PLACE_TYPE = "place_type";
        public static final String NODE_ID = "person_node_id";

    }

    public static class Event {
        public static final String TYPE_KEY = "event_type_key";
        public static final String EVENT_NAME = "event_name";
        public static final String START_TIME = "event_start_time";
        public static final String END_TIME = "event_end_time";
        public static final String EVENT_TYPE = "event_type_name";
        public static final String CALENDAR_ID = "calendar_id";
        public static final String EVENT_TIME = "event_time";
        public static final String NODE_ID = "person_node_id";
    }

    public static class BrowserSearch {
        public static final String TYPE_KEY = "browser_search_type_key";
        public static final String BROWSER_SEARCH_TITLE = "browser_search_title";
    }

    public static class BrowserVisit {
        public static final String TYPE_KEY = "browser_visit_type_key";
        public static final String BROWSER_VISIT_TITLE = "browser_visit_title";
        public static final String BROWSER_VISIT_URL = "browser_visit_url";
    }

    public static class Flight {
        public static final String TYPE_KEY = "flight_type_key";
        public static final String ARRIVAL_AIRPORT = "arrival_airport";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String DEPARTURE_AIRPORT = "departure_airport";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String FLIGHT_NUMBER = "flight_number";
        public static final String FLIGHT_SELLER = "flight_seller";
    }

    public static class Ticket {
        public static final String TYPE_KEY = "ticket_type_key";
        public static final String TICKET_NUMBER = "ticket_number";
        public static final String TICKET_SEAT = "ticket_seat";
        public static final String TICKET_OWNER = "ticket_owner";
        public static final String TICKET_DESCRIPTION = "ticket_description";
    }

    public static class Order {
        public static final String TYPE_KEY = "order_type_key";
        public static final String ORDER_NUMBER = "order_number";
        public static final String ORDER_SELLER = "order_seller";
    }

    public static class Document {
        public static final String TYPE_KEY = "drive_document_type_key";
        public static final String DOCUMENT_ID = "document_id";
        public static final String DOCUMENT_TITLE = "document_title";
        public static final String DOCUMENT_CREATED_TIME = "document_created_time";
        public static final String DOCUMENT_MODIFIED_TIME = "document_modified_time";
        public static final String DOCUMENT_DESCRIPTION = "document_description";
        public static final String DOCUMENT_SIZE = "document_size";
    }

}
