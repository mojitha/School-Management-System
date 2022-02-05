package com.subject;

import com.string.Strings;

public class Subject {
    private String subjectID,subject,grade,teacher,teacherID;
    private static Subject subjectSingleton;
    
    public Subject() {
        super();
    }

    public Subject(String subjectID, String subject, String grade, String teacher, String teacherID) {
        this.subjectID = subjectID;
        this.subject = subject;
        this.grade = grade;
        this.teacher = teacher;
        this.teacherID = teacherID;
    }

    public static Subject getInstance() {
        if(subjectSingleton == null) {
            synchronized(Subject.class) {
                subjectSingleton = new Subject();
                System.out.println(Strings.created_subjectSingleton);
            }
        }
        
        return subjectSingleton;
    }
    
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }
    
    
}
