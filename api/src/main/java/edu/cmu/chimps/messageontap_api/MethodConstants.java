package edu.cmu.chimps.messageontap_api;

public class MethodConstants {

    public static final String TASK_RESPONSE = "respond";

    /**
     * types of tasks
     */
    public static final String PMS_TYPE = "PMS";
    public static final String GRAPH_TYPE = "graph";
    public static final String UI_TYPE = "ui";
    public static final String ACTION_TYPE = "action";

    /**
     * methods of PMS tasks
     */
    public static final String PMS_METHOD_END_SESSION = "session_end";
    public static final String PMS_METHOD_STATUS_QUERY = "status_query";
    public static final String PMS_METHOD_STATUS_REPLY = "status_reply";
    public static final String PMS_METHOD_RESPONSE_REFETCH = "response_refetch";

    /**
     * methods of graph tasks
     */
    public static final String GRAPH_METHOD_RETRIEVE = "retrieve";
    public static final String GRAPH_METHOD_CREATE = "create";
    public static final String GRAPH_METHOD_UPDATE = "update";
    public static final String GRAPH_METHOD_DELETE = "delete";

    /**
     * methods of ui tasks
     */
    public static final String UI_METHOD_SHOW_BUBBLE = "show_bubble";
    public static final String UI_METHOD_SHOW_DRAWER = "show_drawer";
    public static final String UI_METHOD_LOAD_WEBVIEW = "load_webview";

    /**
     * methods of action tasks
     */
    public final static String ACTION_METHOD_ALARM_NEW = "alarm_new";
    public final static String ACTION_METHOD_PHONE_CALL = "phone_call";
    public final static String ACTION_METHOD_CALENDAR_NEW = "calendar_new";
    public final static String ACTION_METHOD_CALENDAR_SEARCH = "calendar_search";
    public final static String ACTION_METHOD_EMAIL_SEND = "email_send";
    public final static String ACTION_METHOD_MAP_SEARCH_G = "map_search_g";
    public final static String ACTION_METHOD_NOTIFICATION_NEW = "notification_new";
    public final static String ACTION_METHOD_SETTEXT = "settext";
    public final static String ACTION_METHOD_AUTO_SHARE = "auto_share";

}
