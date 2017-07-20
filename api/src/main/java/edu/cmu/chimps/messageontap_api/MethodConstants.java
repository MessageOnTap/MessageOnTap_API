package edu.cmu.chimps.messageontap_api;

import android.content.ComponentName;

public class MethodConstants {

    public static final String UI_UPDATE = "ui_update";
    public static final String UI_SHOW = "ui_show";
    public static final String GRAPH_RETRIEVAL = "graph_retrieval";
    public static final String GRAPH_CREATE = "graph_create";
    public static final String GRAPH_UPDATE = "graph_update";
    public static final String GRAPH_DELETE = "graph_delete";
    public static final String RESPOND_EXTENSION = "respond_plugin";
    public static final String SEND_MESSAGE = "send_message";
    public static final ComponentName SAMPLE_PLUGIN = new ComponentName("edu.cmu.chimps.messageontap_sample_plugin", "edu.cmu.chimps.messageontap_sample_plugin.SamplePlugin");
}
