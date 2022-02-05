package model;

class IntStack {
    private int top,maxSize;
    private int intArray[];
    
    public IntStack(int s) {
        maxSize = s;
        intArray = new int[maxSize];
        top = -1;
    }
    
    public void push(int x) {
        if(top == maxSize - 1) {
            System.out.println("Cannot enter any more values");
        }
        else {
            top++;
            intArray[top] = x;
        }
    }
    
    public int pop() {
        if(top == -1) {
            System.out.println("Cannot return from empty");
            return -99;
        }
        else {
            int temp = intArray[top];
            top--;
            return temp;
        }
    }
    
    public int peek() {
        if(top == -1) {
            System.out.println("Cannot return from empty");
            return -99;
        }
        else {
            return intArray[top];
        }
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    public boolean isFull() {
        return top == maxSize - 1;
    }
}


