package edu.cmu.chimps.messageontap_api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.cmu.chimps.messageontap_api.Task;

/**
 * Created by adamyi on 23/07/2017.
 */

public class Session {
    private String mPackageName; // For PMS ONLY
    private Set<Long> mUncompleted;
    private HashMap<Long, Task> mTasks;

    public Session(String packageName, Task data) { // For PMS
        mPackageName = packageName;
        mUncompleted = new HashSet<>();
        mTasks = new HashMap<>();
        mTasks.put(new Long(0), data);
    }

    public Session(Task data) { // For plugin
        mUncompleted = new HashSet<>();
        mTasks = new HashMap<>();
        mTasks.put(new Long(0), data);
        mUncompleted.add(new Long(0));
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public void newTask(Task task) {
        Long tid = task.getTaskData().tid();
        mTasks.put(tid, task);
        mUncompleted.add(tid);
    }

    public void updateTaskResponse(long tid) {
        mUncompleted.remove(tid);
        mTasks.get(tid).updateStatus(1);
    }

    public Task getTask(long tid) {
        return mTasks.get(tid);
    }

    public void failTask(long tid) {
        mUncompleted.clear();
        mTasks.get(tid).updateStatus(2);
        mTasks.get(0).updateStatus(2);
    }
}
