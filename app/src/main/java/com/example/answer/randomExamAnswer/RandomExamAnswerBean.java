package com.example.answer.randomExamAnswer;

import com.example.answer.createRandomExam.CreateRandomExamBean;

import java.util.List;

public class RandomExamAnswerBean {
        public int code;
        public String message;
        public List<RandomExamAnswerBean.RandomAnswerValueBean> value;

        public static class RandomAnswerValueBean{
            public long id;
            public long randonexamID;
            public int questionIndex;
            public int questionID;
            public String title;
            public String description;
            public String answer1;
            public String answer2;
            public String answer3;
            public String answer4;
            public int choice1;
            public int choice2;
            public int choice3;
            public int choice4;
            public int userchoice;
            public int correctChoice;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getRandonexamID() {
                return randonexamID;
            }

            public void setRandonexamID(long randonexamID) {
                this.randonexamID = randonexamID;
            }

            public int getQuestionIndex() {
                return questionIndex;
            }

            public void setQuestionIndex(int questionIndex) {
                this.questionIndex = questionIndex;
            }

            public int getQuestionID() {
                return questionID;
            }

            public void setQuestionID(int questionID) {
                this.questionID = questionID;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAnswer1() {
                return answer1;
            }

            public void setAnswer1(String answer1) {
                this.answer1 = answer1;
            }

            public String getAnswer2() {
                return answer2;
            }

            public void setAnswer2(String answer2) {
                this.answer2 = answer2;
            }

            public String getAnswer3() {
                return answer3;
            }

            public void setAnswer3(String answer3) {
                this.answer3 = answer3;
            }

            public String getAnswer4() {
                return answer4;
            }

            public void setAnswer4(String answer4) {
                this.answer4 = answer4;
            }

            public int getChoice1() {
                return choice1;
            }

            public void setChoice1(int choice1) {
                this.choice1 = choice1;
            }

            public int getChoice2() {
                return choice2;
            }

            public void setChoice2(int choice2) {
                this.choice2 = choice2;
            }

            public int getChoice3() {
                return choice3;
            }

            public void setChoice3(int choice3) {
                this.choice3 = choice3;
            }

            public int getChoice4() {
                return choice4;
            }

            public void setChoice4(int choice4) {
                this.choice4 = choice4;
            }

            public int getUserchoice() {
                return userchoice;
            }

            public void setUserchoice(int userchoice) {
                this.userchoice = userchoice;
            }

            public int getCorrectChoice() {
                return correctChoice;
            }

            public void setCorrectChoice(int correctChoice) {
                this.correctChoice = correctChoice;
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    public List<RandomAnswerValueBean> getValue() {
        return value;
    }

    public void setValue(List<RandomAnswerValueBean> value) {
        this.value = value;
    }
}
