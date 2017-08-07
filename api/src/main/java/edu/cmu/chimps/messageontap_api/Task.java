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

import java.util.HashMap;

/**
 * Created by adamyi on 25/07/2017.
 */

@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue"})
public class Task {
    public static final int STATUS_NOT_EXIST = -1;
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_DONE = 1;
    public static final int STATUS_FAILED = 2;

    private TaskData data;
    private int status = STATUS_RUNNING;
    private long timestamp[];

    public Task(TaskData data) {
        this.data = data;
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
        this.prepareSendResponse(JSONUtils.hashMapToString(params));
    }

    public void prepareSendResponse(String json) {
        data = data.content(json);
        this.updateStatus(STATUS_DONE);
    }

    public String idString() {
        return data.idString();
    }

}
