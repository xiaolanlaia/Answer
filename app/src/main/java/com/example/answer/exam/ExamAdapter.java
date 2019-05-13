package com.example.answer.exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.answer.R;

import java.util.List;

public class ExamAdapter extends ArrayAdapter<ExamBean> {
    private int resourceId;
    public ExamAdapter(Context context, int textViewResourceId, List<ExamBean>objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ExamBean examBean = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.title = (TextView)view.findViewById(R.id.title);
            viewHolder.start_time = (TextView)view.findViewById(R.id.start_time);
            viewHolder.end_time = (TextView)view.findViewById(R.id.end_time);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.title.setText(examBean.getTitle());
        viewHolder.start_time.setText(examBean.getStartTime());
        viewHolder.end_time.setText(examBean.getEndTime());
        return view;
    }
    class ViewHolder{
        TextView title;
        TextView start_time;
        TextView end_time;

    }
}
