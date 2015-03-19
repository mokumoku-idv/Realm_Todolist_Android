package com.example.g_2015.realmsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

import com.example.g_2015.realmsample.model.Task;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    Button todoAddButton;
    ListView todoListView;
    EditText todoAddTask;
    TaskAdapter taskadapter;

    public Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoAddButton = (Button) findViewById(R.id.addbtn);
        todoListView = (ListView) findViewById(R.id.listView);
        realm = Realm.getInstance(this);

        loadTodoList();
    }

    private void loadTodoList() {
        RealmResults<Task> results = realm.where(Task.class)
                                          .findAll();

        taskadapter = new TaskAdapter(this, new TaskAdapter.OnItemClickListener() {
            public void onItemClick(Task task) {
                openTodoEdit(task);
            }
        });

        taskadapter.setResults(results);

        todoListView.setAdapter(taskadapter);

    }

    private void openTodoEdit(Task task) {
        Intent intent = new Intent(this, TaskEditActivity.class);
        intent.putExtra("task", task.getTask());
        intent.putExtra("task_id", task.getId());
        startActivity(intent);
    }

    public void addTaskList(View view) {
        todoAddTask = (EditText) findViewById(R.id.addTask);

        if( todoAddTask == null) {
            return;
        }
        long date = new Date().getTime();

        Random rnd = new Random();
        int ran = rnd.nextInt(1000);

        realm.beginTransaction();
        Task task = realm.createObject(Task.class); // Create a new object
        task.setId(ran);
        task.setTask(todoAddTask.getText().toString());
        task.setCreated(date);
        task.setLastUpdated(date);
        task.setisChecked(false);
        realm.commitTransaction();

        todoAddTask.setText("");
        loadTodoList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; thiafs adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteCheckedTodo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteCheckedTodo() {
        final List<Task> checkedTodoList = taskadapter.getCheckedTodoList();
        realm.beginTransaction();
        for( Task task : checkedTodoList) {
            realm.where(Task.class)
                 .equalTo("Id", task.getId())
                 .findAll()
                 .clear();
        }
        realm.commitTransaction();

        loadTodoList();
    }

}
