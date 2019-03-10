package com.example.answer.util;


import com.example.answer.db.Question;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



public class Utility {


    /**
     * 将返回的数据解析成Question实体类
     * @param response
     * @return
     */
    public static void handleQuestionListResponse(String response){


        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(response).getAsJsonArray();

        Gson gson = new Gson();
        for (JsonElement element : jsonArray){
            Question question = gson.fromJson(element,Question.class);
            Question question1 = new Question();
            question1.setTitle(question.getTitle());
            question1.setState(question.getState());
            question1.save();
        }

    }
}
