package com.exam;

import com.string.Strings;

public class Exam {
    private String examID,examDate,grade,subjectID,startTime,endTime,year,term,status;
    private static Exam examSingleton;
    
    public Exam() {
        super();
    }
    
    public static Exam getInstance() {
        if(examSingleton == null) {
            synchronized(Exam.class) {
                examSingleton = new Exam();
                System.out.println(Strings.created_examSingleton);
            }
        }
        
        return examSingleton;
    }
    
    public Exam(String examID, String examDate, String grade, String subjectID, String startTime, String endTime, String year, String term, String status) {
        this.examID = examID;
        this.examDate = examDate;
        this.grade = grade;
        this.subjectID = subjectID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.year = year;
        this.term = term;
        this.status = status;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
