package com.example.datudayanga.todo_app;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Dbhelper dbhelper ;
    ArrayAdapter<String> arrayAdapter;
    ListView fstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper = new Dbhelper(this);
        fstTask = (ListView)findViewById(R.id.firstTask);

        loadTaskList();

    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbhelper.getTaskList();
        if(arrayAdapter==null){
            arrayAdapter = new ArrayAdapter<String>(this,R.layout.ex,R.id.tasktitle,taskList);
            fstTask.setAdapter(arrayAdapter);
        }else{
            arrayAdapter.clear();
            arrayAdapter.addAll(taskList);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white),PorterDuff.Mode.SRC_IN);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_add_task:
            final EditText takeEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Item")
                        .setMessage("What do yo want to do next ")
                        .setView(takeEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String task = String.valueOf(takeEditText.getText());
                                dbhelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })

                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view){

        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)findViewById(R.id.tasktitle);
        String task = String.valueOf(taskTextView.getText());
        dbhelper.deleteTask(task);
        loadTaskList();
    }


}
