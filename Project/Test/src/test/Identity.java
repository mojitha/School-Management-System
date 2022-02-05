package test;

public class Identity {
    public static void main(String[] args) {
        String prevID,oneSubID,newSubID,newID;
        int newOneSubID;
        
        prevID = "I000000007";
        
        oneSubID = 1 + prevID.substring(1);
        newOneSubID = Integer.parseInt(oneSubID)+1;
        newSubID = Integer.toString(newOneSubID);
        newID = "I"+newSubID.substring(1);
        System.out.println(newID);
    }
}

