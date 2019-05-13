package com.example.answer.randomExam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.answer.R;


import java.util.List;

public class RandomExamAdapter extends ArrayAdapter<RandomExam> {
    private int resourceId;
    public RandomExamAdapter(Context context, int textViewResourceId, List<RandomExam>objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RandomExam randomExam = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder= new ViewHolder();

            viewHolder.random_exam_id = (TextView)view.findViewById(R.id.random_exam_id);
            viewHolder.submit_time = (TextView)view.findViewById(R.id.submit_time);
            viewHolder.mark = (TextView)view.findViewById(R.id.mark);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.random_exam_id.setText(String.valueOf(randomExam.getExamID()));

        viewHolder.submit_time.setText(randomExam.getSubmitTime());
        viewHolder.mark.setText(String.valueOf(randomExam.getMark()));

        return view;
    }
    class ViewHolder{
        TextView random_exam_id;
        TextView submit_time;
        TextView mark;

    }
}
