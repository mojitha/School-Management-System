package cstack;

public class CharStack {
    private int maxSize,top;
    private char[] charArray;
    
    public CharStack(int s) {
        maxSize = s;
        charArray = new char[maxSize];
        top = -1;
    }
    
    public void push(char x) {
        if(isFull())
            System.out.println("Cannot enter anymore values");
        else {
            top++;
            charArray[top] = x;
        }
    }
    
    public char pop() {
        if(isEmpty()) {
            System.out.println("Array is empty");
            return 'f';
        }
        else {
            char x = charArray[top];
            top--;
            return x;
        }
    }
    
    public char peek() {
        if(isEmpty()) {
            System.out.println("Array is empty");
            return 'f';
        }
        else {
            char x = charArray[top];
            return x;
        }
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    public boolean isFull() {
        return top == maxSize - 1;
    }
}
