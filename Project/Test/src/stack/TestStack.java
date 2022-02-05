package stack;

public class TestStack {
    public static void main(String[] args) {
        StackX st1 = new StackX(3);
        st1.push(12.9);
        st1.push(42.8);
        st1.push(54.2);
        
        st1.push(34.7);
        
        System.out.println("Peek "+st1.peek());
        
        while(!st1.isEmpty()) {
            System.out.println(st1.pop());
        }
        
        System.out.println(st1.isEmpty());
    }
}

class StackX {
    private int top;
    private double[] stackArray;
    private int maxSize;
    
    public StackX(int s) {
        maxSize = s;
        stackArray = new double[maxSize];
        top = -1;
    }
    
    public void push(double id) {
        if(top == maxSize-1)
            System.out.println("Cannot enter any more values");
        else {
            top++;
            stackArray[top] = id;
        }
    }
    
    public double pop() {
        if(top == -1) {
            System.out.println("No values available");
            return -99;
        }
        else {
            double temp = stackArray[top];
            top--;
            return temp;
        }
    }
    
    public boolean isEmpty() {
        if(top == -1)
            return true;
        else
            return false;
    }
    
    public boolean isFull() {
        if(top == maxSize-1)
            return true;
        else
            return false;
    }
    
    public double peek() {
        if(top == -1) {
            System.out.println("No values available");
            return -99;
        }
        else
            return stackArray[top];
    }
}