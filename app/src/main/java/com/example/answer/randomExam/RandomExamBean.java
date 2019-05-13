package com.example.answer.randomExam;

import java.util.List;

public class RandomExamBean {

	public int code;
	public String message;
	public List<RandomExamValue>value;
	public class RandomExamValue{
		public long id;
		public int examID;
		public int userID;
		public long submitTime;
		public int mark;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getExamID() {
			return examID;
		}

		public void setExamID(int examID) {
			this.examID = examID;
		}

		public int getUserID() {
			return userID;
		}

		public void setUserID(int userID) {
			this.userID = userID;
		}

		public long getSubmitTime() {
			return submitTime;
		}

		public void setSubmitTime(long submitTime) {
			this.submitTime = submitTime;
		}

		public int getMark() {
			return mark;
		}

		public void setMark(int mark) {
			this.mark = mark;
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

	public List<RandomExamValue> getValue() {
		return value;
	}

	public void setValue(List<RandomExamValue> value) {
		this.value = value;
	}
}
