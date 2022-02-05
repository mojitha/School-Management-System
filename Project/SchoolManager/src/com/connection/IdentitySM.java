package com.connection;

import com.book.BookDao;
import com.exam.ExamDao;
import com.item.ItemDao;
import com.staff.StaffDao;
import com.string.Strings;
import com.student.StudentDao;
import com.subject.SubjectDao;

public class IdentitySM {
    public static String getID(String tableName) {
        try {
            String prevID = "",oneSubID = "",newSubID = "",newID = "";
            char letter = '0';
            int newOneSubID;

            switch (tableName) {
                case Strings.Item:
                    letter = 'I';
                    prevID = ItemDao.returnID();
                    break;
                case Strings.Book:
                    letter = 'B';
                    prevID = BookDao.returnID();
                    break;
                case Strings.ItemCategories:
                    letter = 'C';
                    prevID = ItemDao.returnCategoryID();
                    break;
                case Strings.Student:
                    letter = 'U';
                    prevID = StudentDao.returnID();
                    break;
                case Strings.Staff:
                    letter = 'U';
                    prevID = StaffDao.returnID();
                    break;
                case Strings.Exam:
                    letter = 'E';
                    prevID = ExamDao.returnID();
                    break;
                case Strings.Subject:
                    letter = 'S';
                    prevID = SubjectDao.returnID();
                    break;
            }
            
            if(prevID != null) {
                oneSubID = 1 + prevID.substring(1);
                newOneSubID = Integer.parseInt(oneSubID)+1;
                newSubID = Integer.toString(newOneSubID);
                newID = letter+newSubID.substring(1);
                System.out.println(newID);
            }
            else
                return letter+"000000001";
            
            return newID;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return null;
    }
}
