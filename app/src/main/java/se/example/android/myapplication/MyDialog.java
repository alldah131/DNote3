package se.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MyDialog extends DialogFragment implements View.OnClickListener{
    Button cancel, discard, save;
    MainActivity main = new MainActivity();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        cancel = (Button) view.findViewById(R.id.cancel);
        discard = (Button) view.findViewById(R.id.discard);
        save = (Button) view.findViewById(R.id.save);
        cancel.setOnClickListener(this);
        discard.setOnClickListener(this);
        save.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        if(view.getId()==R.id.cancel) {
            dismiss();
            Toast.makeText(getActivity(), "Cancel was clicked", Toast.LENGTH_SHORT).show();
        }

        if (view.getId()==R.id.discard) {
            dismiss();
            startActivity(intent);
            Toast.makeText(getActivity(), "Discard was clicked", Toast.LENGTH_SHORT).show();
        }

        if(view.getId()==R.id.save){
            CheckOutMemo detail = new CheckOutMemo();
            detail.onSaveClick(view);


        }
    }




}

