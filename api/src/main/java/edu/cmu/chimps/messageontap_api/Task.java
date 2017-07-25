package edu.cmu.chimps.messageontap_api;

import java.util.HashMap;

/**
 * Created by adamyi on 25/07/2017.
 */

public class Task {
    private TaskData data;
    private int status = 0; //0:running; 1:done; 2:failed
    private long timestamp[];

    Task(TaskData data) {
        timestamp = new long[3];
        timestamp[0] = System.currentTimeMillis();
    }

    public TaskData getTaskData() {
        return data;
    }

    public void setTaskData(TaskData data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public long getTimeStamp(int i) {
        if (i == -1)
            i = status;
        return timestamp[i];
    }

    public void updateStatus(int i) {
        if (status == i)
            return;
        status = i;
        timestamp[i] = System.currentTimeMillis();
    }

    public void prepareSendResponse(HashMap<String, Object> params) {
        String json = DataUtils.hashMapToString(params);
        data = data.content(json);
        this.updateStatus(1);
    }

}
