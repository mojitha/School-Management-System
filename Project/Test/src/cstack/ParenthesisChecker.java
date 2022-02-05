package cstack;

import java.util.Scanner;

public class ParenthesisChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Please enter the sequence : ");
        String valueS = sc.nextLine();
        char[] valueC = valueS.toCharArray();
        
        int valueSize = valueC.length;
        CharStack cs1 = new CharStack(valueSize);
        
        for(char x:valueC)
            cs1.push(x);
        
        checkDepth(cs1, valueC);
    }
    
    public static void checkDepth(CharStack cs,char[] ca) {
        char x = '0';
        CharStack csmy = new CharStack(ca.length);
        int countL = 0,countR = 0,max = 0;
        while(!cs.isEmpty()) {
            x = cs.pop();
            if(x == ')')
                countR++;
            else if(x == '(') {
                countL++;
            }
        }
        System.out.println("count"+countL);
        if(countL == countR) {
            max = countR;
            System.out.println("maximum depth of nested parentheses : "+max);
        }
        else
            System.out.println("maximum depth of nested parentheses : "+-1);
    }
}
