package com.example.g_2015.realmsample;

import com.example.g_2015.realmsample.model.Task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

/**
 * Created by g-2015 on 2015/03/04.
 */
public final class TaskAdapter extends RealmAdapter<Task, TaskAdapter.ViewHolder> {
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private final static Date DATE = new Date();

    Context context;

    public static class ViewHolder extends RealmAdapter.ViewHolder {
        public final CheckBox checkBox;
        public final TextView createdAt;
        public final TextView todoText;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            createdAt = (TextView) itemView.findViewById(R.id.created_at);
            todoText = (TextView) itemView.findViewById(R.id.todo_text);
        }
    }

    public static interface OnItemClickListener {
        public void onItemClick(Task task);
    }

    private final LayoutInflater inflater;
    private final OnItemClickListener listener;

    public TaskAdapter(Context context, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.task_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Task item = getItem(position);
        DATE.setTime(item.getCreated());
        holder.createdAt.setText(FORMAT.format(DATE));
        holder.todoText.setText(item.getTask());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
        holder.checkBox.setChecked(item.getisChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Realm realm = Realm.getInstance(context);

                long date = new Date().getTime();

                Task newtask = realm.where(Task.class)
                        .equalTo("Id", item.getId())
                        .findFirst();
                realm.beginTransaction();
                newtask.setTask(item.getTask());
                newtask.setLastUpdated(date);
                newtask.setisChecked(isChecked);
                realm.commitTransaction();

            }
        });
    }

    public List<Task> getCheckedTodoList() {
        List<Task> checkedTotoList = new ArrayList<Task>();
        for (int i = 0; i < getCount(); i++) {
            Task task = getItem(i);
            if (task.getisChecked()) {
                checkedTotoList.add(task);
            }
        }
        return checkedTotoList;
    }

}