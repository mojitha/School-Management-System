package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FindNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IntStack is1 = new IntStack(10);
        is1.push(25);
        is1.push(45);
        is1.push(12);
        is1.push(70);
        is1.push(20);
        is1.push(14);
        is1.push(66);
        is1.push(85);
        is1.push(15);
        is1.push(28);
        
        try {
            int userVal = 0;
            System.out.print("Please enter the number to be searched: ");
            userVal = sc.nextInt();

            if(isAvailable(is1, userVal))
                System.out.println("Number "+userVal+" is in the stack");
            else
                System.out.println("Number "+userVal+" is not in the stack");
        }
        catch(InputMismatchException imme) {
            System.out.println("Please enter a valid number");
        }
    }
    
    public static boolean isAvailable(IntStack is, int val) {
        int intVal = 0;
        while(!is.isEmpty()) {
            intVal = is.pop();
            if(intVal == val)
                return true;
        }
    
        return false;
    }
}
