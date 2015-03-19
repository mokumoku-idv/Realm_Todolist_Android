package com.example.g_2015.realmsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.g_2015.realmsample.model.Task;

import java.util.Date;

import io.realm.Realm;

/**
 * Created by g-2015 on 2015/03/17.
 */
public class TaskEditActivity extends ActionBarActivity {

    Task task;

    EditText taskEdit;

    Realm realm;

    Realm realm_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        taskEdit = (EditText) findViewById(R.id.task_edit);
        realm = Realm.getInstance(this);
        setupUi();
    }

    private void setupUi() {
        String strTask = getIntent().getStringExtra("task");
        int strId = getIntent().getExtras().getInt("task_id");
        taskEdit.setText(strTask);
        task = realm.where(Task.class).equalTo("Id", strId).findFirst();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            String newText = taskEdit.getText().toString();
            updateTodo(task.getId(), newText, task.getisChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTodo(int id, String text, boolean isChecked) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        long date = new Date().getTime();


        Task newtask = realm.where(Task.class)
                .equalTo("Id", id)
                .findFirst();
        realm.beginTransaction();
        newtask.setTask(text);
        newtask.setLastUpdated(date);
        newtask.setisChecked(isChecked);
        realm.commitTransaction();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
