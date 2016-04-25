package se.example.android.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CheckOutMemo extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    private int position;
    EditText editableTitle;
    EditText editableContent;
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        editableTitle = (EditText) findViewById(R.id.editHeader);
        editableContent = (EditText) findViewById(R.id.editBodyText);
        //Setting text for my editableTitle and EditableContent.
        editableTitle.setText(intent.getStringExtra("header"));
        editableContent.setText(intent.getStringExtra("bodyText"));
        checkIfUserChangedOrWroteAnyText();
        //Declaring keyword and default position.
        position = intent.getIntExtra("position", 0);

        openDB();
    }


    private void openDB(){
        myDb = new DBAdapter(this);
        myDb.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    public void closeDB(){
        myDb.close();
    }



    public void onSaveClick(View view) {
        // Retrieving the text in my editableContent and editableTitle.
        String editableContentString = editableContent.getText().toString();
        String editableTitleString = editableTitle.getText().toString();
            if(TextUtils.isEmpty(editableContentString) && TextUtils.isEmpty(editableTitleString)) {
                finish();
                Toast.makeText(CheckOutMemo.this, "No content to save, note discarded", Toast.LENGTH_SHORT).show();
            }
             else {
                if ((TextUtils.isEmpty(editableTitleString))) {
                    editableTitleString.equals(editableContentString);
                    Intent intent = new Intent();
                    intent.putExtra("header", editableContent.getText().toString());
                    intent.putExtra("position", position);

                    //Sending userInput back to MainActivity.
                    setResult(Activity.RESULT_OK, intent);
                    finish();
//                    long newId = myDb.insertRow(editableTitleString, editableContentString);
//                    Cursor cursor = myDb.getAllRows();
//                    displayRecordSet(cursor);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("header", editableTitle.getText().toString());
                    intent.putExtra("bodyText", editableContent.getText().toString());
                    intent.putExtra("position", position);

                    //Sending userInput back to MainActivity.
                    setResult(Activity.RESULT_OK, intent);
                    finish();
//                    long newId = myDb.insertRow(editableTitleString, editableContentString);
//                    Cursor cursor = myDb.getAllRows();
//                    displayRecordSet(cursor);
                }

            }

    }

//    public void displayRecordSet(Cursor cursor){
//        String message = "Hi!";
//        if(cursor.moveToFirst()){
////            int id = cursor.getInt(0);
////            String title = cursor.getString(1);
////            String context = cursor.getString(2);
//            String id = getIntent().getStringExtra("position");
//            String header = getIntent().getStringExtra("header");
//            String bodyText = getIntent().getStringExtra("bodyText");
//
//            message += "id" + id +
//                        ", header=" + header +
//                        ", bodyText" + bodyText+
//                        "\n";
//
//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.custom_Row_linearLayout);
//            View view = getLayoutInflater().inflate(R.layout.custom_row,null);
//            TextView customAdapterHeader = (TextView) view.findViewById(R.id.header);
//            TextView customAdapterBodyText = (TextView) view.findViewById(R.id.bodyText);
//
//            customAdapterHeader.setText(header);
//            customAdapterBodyText.setText(bodyText);
//            linearLayout.addView(view);
//
//        }
//    }

    public void openDialogFragment() {
        Button button = (Button) findViewById(R.id.bigCancelButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CheckOutMemo.this);
                alertDialogBuilder.setTitle("Save memo before exit?");
                alertDialogBuilder.setMessage("Save memo before exit?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onSaveClick(v);
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialogBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                Toast.makeText(CheckOutMemo.this, "WORKED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkIfUserChangedOrWroteAnyText() {
        editableContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(CheckOutMemo.this, "hasFocus!", Toast.LENGTH_SHORT).show();
                    editableContent.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        openDialogFragment();
                        }
                    });

                }
            }
        });



        editableTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(CheckOutMemo.this, "Title hasFocus!", Toast.LENGTH_SHORT).show();
                    editableTitle.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            openDialogFragment();
                        }
                    });

                }
            }
        });

    }
    
    public void onCancelClick(View view){
        finish();
        Toast.makeText(CheckOutMemo.this, "Cancel was clicked without any changes made!!!", Toast.LENGTH_SHORT).show();
    }
}

