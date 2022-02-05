package com.result;

import com.string.Strings;

public class Result {
    private String userID,studentID,name,subjectID,examID,result;
    private static Result resultSingleton;
    
    public Result() {
        super();
    }
    
    public Result(String userID, String studentID, String name, String subjectID, String examID, String result) {
        this.userID = userID;
        this.studentID = studentID;
        this.name = name;
        this.subjectID = subjectID;
        this.examID = examID;
        this.result = result;
    }
    
    public static Result getInstance() {
        if(resultSingleton == null) {
            synchronized(Result.class) {
                resultSingleton = new Result();
                System.out.println(Strings.created_resultSingleton);
            }
        }
        
        return resultSingleton;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    
}
