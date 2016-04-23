package se.example.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
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
        editableTitle.setText(intent.getStringExtra("header"));
        editableContent.setText(intent.getStringExtra("bodyText"));
        checkIfUserChangedOrWroteAnyText();
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
        String editableContentString = editableContent.getText().toString();
        String editableTitleString = editableTitle.getText().toString();
            if(TextUtils.isEmpty(editableContentString) && TextUtils.isEmpty(editableTitleString)) {
                finish();
                Toast.makeText(CheckOutMemo.this, "No content to save, note discarded", Toast.LENGTH_SHORT).show();

            }
             else{
            if((TextUtils.isEmpty(editableTitleString))) {
                editableTitleString.equals(editableContentString);
                Intent intent = new Intent();
                intent.putExtra("header", editableContent.getText().toString());
                intent.putExtra("position", position);


                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else{
                Intent intent = new Intent();
                intent.putExtra("header", editableTitle.getText().toString());
                intent.putExtra("bodyText", editableContent.getText().toString());
                intent.putExtra("position", position);


                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        }

    }

    public void openDialogFragment() {
        DialogFragment dialog = new MyDialog();
        dialog.show(this.getSupportFragmentManager(), "MyDialog");
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
                            Button button = (Button) findViewById(R.id.bigCancelButton);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openDialogFragment();
                                    Toast.makeText(CheckOutMemo.this, "WORKED", Toast.LENGTH_SHORT).show();
                                }
                            });
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

