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
import android.util.Log;
import android.view.KeyEvent;
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

                } else {
                    Intent intent = new Intent();
                    intent.putExtra("header", editableTitle.getText().toString());
                    intent.putExtra("bodyText", editableContent.getText().toString());
                    intent.putExtra("position", position);

                    //Sending userInput back to MainActivity.
                    setResult(Activity.RESULT_OK, intent);
                    finish();

                }

            }

    }


    public void cancelButtonClickedAfterEdit() {
        Button button = (Button) findViewById(R.id.bigCancelButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
            openDialogFragment(v);
            }
        });
    }

    //onKeyDown method added, to prevent the user to back get out of the unsaved memo before saving or discarding dialogfragment.



        public void openDialogFragment(final View v){

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
                alertDialogBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }



    public void checkIfUserChangedOrWroteAnyText() {
        editableContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (hasFocus) {

                    editableContent.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        cancelButtonClickedAfterEdit();
                        }
                    });

                }
            }
        });



        editableTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, final boolean hasFocus) {
                if (hasFocus) {

                    editableTitle.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            cancelButtonClickedAfterEdit();
                        }
                    });

                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            editableTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, final boolean hasFocus) {
                    if (hasFocus) {

                        editableTitle.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {


                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                openDialogFragment(v);
                            }
                        });

                    }
                }
            });


            editableContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, final boolean hasFocus) {
                    if (hasFocus) {

                        editableContent.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {


                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                openDialogFragment(v);
                            }
                        });

                    }
                }
            });

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onCancelClick(View view){
        finish();

    }
}

