package com.example.answer.view.answerQuestionList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.answer.R;

import java.util.List;

public class QuestionListAdapter extends ArrayAdapter<Question> {
    private int resourceId;
    public QuestionListAdapter(Context context, int textViewResourceId, List<Question>objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Question question = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.title = (TextView)view.findViewById(R.id.title);
            viewHolder.state = (TextView)view.findViewById(R.id.state);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.title.setText(question.getTitle());
        viewHolder.state.setText(question.getState());
        return view;
    }
    class ViewHolder{
        TextView title;
        TextView state;

    }
}
