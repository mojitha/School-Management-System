package astack;

public class StackX {
    public static void main(String[] args) {
        StackX st1 = new StackX(3);
        st1.push(1);
        st1.push(1.2);
        st1.push(3.4);
        
        while(!st1.isEmpty())
            System.out.println("No "+st1.pop());
        
        st1.push(1);
        st1.push(1.2);
        st1.push(3.4);
        st1.push(23);
    }
    
    private int top;
    private final double numArray[];
    private final int maxSize;
    
    public StackX(int maxSize) {
        this.maxSize = maxSize;
        numArray = new double[maxSize];
        top = -1;
    }
    
    public void push(double x) {
        if(isFull())
            System.out.println("Cannot enter any more");
        else {
            top++;
            numArray[top] = x;
        }
    }
    
    public double pop() {
        if(isEmpty()) {
            System.out.println("No values present");
            return -99;
        }
        else {
            double temp = numArray[top];
            top--;
            return temp;
        }
    }
    
    public double peek() {
        if(isEmpty()) {
            System.out.println("No values present");
            return -99;
        }
        else {
            double temp = numArray[top];
            return temp;
        }
    }
    
    public boolean isEmpty() {
        return top == -1;
    }
    
    public boolean isFull() {
        return top == maxSize-1;
    }
}
