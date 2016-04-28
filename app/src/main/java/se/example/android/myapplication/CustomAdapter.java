package se.example.android.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

     ArrayList<Memo> memos = new ArrayList<>();

    public void add(Memo memo){
        this.memos.add(memo);
    }

    public void delete(int position){
        memos.remove(position);
    }

    @Override
    public Memo getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    class MyViewHolder {
        public TextView header, bodyText;

        public MyViewHolder(View view){
            header = (TextView) view.findViewById(R.id.header);
            bodyText = (TextView) view.findViewById(R.id.bodyText);
        }
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder viewHolder;

        if(null == convertView){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);
            viewHolder = new MyViewHolder(convertView);
            viewHolder.header.setTag(position);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        Memo memo = getItem(position);
        viewHolder.header.setText(memo.header);
        viewHolder.bodyText.setText(memo.bodyText);
        return convertView;
    }
}