package com.example.g_2015.realmsample.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by g-2015 on 2015/03/03.
 */
public class Task extends RealmObject {
    private int Id;
    private String Task;
    private long Created;
    private long LastUpdated;
    private boolean isChecked;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        this.Task = task;
    }

    public long getCreated() {
        return Created;
    }

    public void setCreated(long created) {
        this.Created = created;
    }

    public long getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.LastUpdated = lastUpdated;
    }

    public boolean getisChecked() {
        return isChecked;
    }

    public void setisChecked(boolean checked) {
        this.isChecked = checked;
    }
}
