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

import android.util.LongSparseArray;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by adamyi on 23/07/2017.
 */

@SuppressWarnings({"unchecked", "WeakerAccess", "unused", "SameParameterValue"})
public class Session {
    private String mPackageName; // For PMS ONLY
    private Set<Long> mUncompleted;
    private LongSparseArray<Task> mTasks;
    private long lastTID;

    public Session(String packageName, Task data) { // For PMS
        mPackageName = packageName;
        mUncompleted = new HashSet<>();
        mTasks = new LongSparseArray<>();
        mTasks.put(0L, data);
        lastTID = 0;
    }

    public Session(Task data) { // For plugin
        mUncompleted = new HashSet<>();
        mTasks = new LongSparseArray<>();
        mTasks.put(0L, data);
        mUncompleted.add(0L);
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public Set<Long> getUncompletedTaskIDs() {
        return mUncompleted;
    }

    public boolean isTaskCompleted(long tid) {
        return mUncompleted.contains(tid);
    }

    public Task newTask(Task task) {
        long tid = ++lastTID;
        task.setTaskData(task.getTaskData().tid(tid));
        mTasks.put(tid, task);
        mUncompleted.add(tid);
        return task;
    }

    public void updateTaskResponse(long tid) {
        mUncompleted.remove(tid);
        mTasks.get(tid).updateStatus(Task.STATUS_DONE);
    }

    public void updateTaskResponse(Task task) {
        long tid = task.getTaskData().tid();
        task.updateStatus(Task.STATUS_DONE);
        mUncompleted.remove(tid);
        mTasks.put(tid, task);
    }

    public Task getTask(long tid) {
        return mTasks.get(tid);
    }

    public void failTask(long tid) {
        mUncompleted.clear();
        mTasks.get(tid).updateStatus(Task.STATUS_FAILED);
        mTasks.get(0).updateStatus(Task.STATUS_FAILED);
    }
}
