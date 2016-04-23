package se.example.android.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    CustomAdapter customAdapter;
    ListView listView;
    Intent intent;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.myListView);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Memo memo = customAdapter.getItem(position);
            intent = new Intent(getApplicationContext(), CheckOutMemo.class);
            intent.putExtra("header", memo.header);
            intent.putExtra("bodyText", memo.bodyText);
            intent.putExtra("position", position);

            startActivityForResult(intent, CheckOutMemo.EDIT_REQUEST_CODE);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm Delete");
        alertDialogBuilder.setMessage("Sure you want to delete?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                customAdapter.delete(position);
                customAdapter.notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }

    public void addNewNote(View view){
        Intent intent = new Intent(getApplicationContext(), CheckOutMemo.class);
        startActivityForResult(intent, CheckOutMemo.ADD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode == CheckOutMemo.ADD_REQUEST_CODE){
            String header = data.getStringExtra("header");
            String bodyText = data.getStringExtra("bodyText");

            Memo memo = new Memo(header, bodyText);
            customAdapter.add(memo);
            customAdapter.notifyDataSetChanged();
        }
        else if(requestCode == CheckOutMemo.EDIT_REQUEST_CODE){

            int position = data.getIntExtra("position", 0);
            Memo memo = customAdapter.getItem(position);
            memo.header = data.getStringExtra("header");
            memo.bodyText = data.getStringExtra("bodyText");
            customAdapter.notifyDataSetChanged();
        }

    }




}
