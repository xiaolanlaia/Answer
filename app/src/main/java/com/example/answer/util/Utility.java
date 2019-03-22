package com.example.answer.util;


import com.example.answer.view.answerQuestion.Answer;
import com.example.answer.view.answerQuestionList.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;


public class Utility {


    /**
     * 将返回的数据解析成Question实体类
     * @param questionListResponse
     * @return
     */
    public static List<Question> handleQuestionListResponse(String questionListResponse){
        try{
            Gson gson = new Gson();
            List<Question>questionList = gson.fromJson(questionListResponse,new TypeToken<List<Question>>(){}.getType());
            return questionList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public static List<Answer> handleMainActivityResponse(String mainActivityResponse){
        try{
            Gson gson = new Gson();
            List<Answer>answerList = gson.fromJson(mainActivityResponse,new TypeToken<List<Answer>>(){}.getType());
            return answerList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
