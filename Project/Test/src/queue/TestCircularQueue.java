package queue;

public class TestCircularQueue {
    public static void main(String[] args) {
        CircularQueue cQ1 = new CircularQueue(3);
        cQ1.insert(12);
        cQ1.insert(21);
        cQ1.insert(53);
        cQ1.insert(32);
        
        System.out.println(cQ1.isEmpty());
        System.out.println(cQ1.isFull());
        
        for(int i=0;i<3;i++) {
            System.out.println(cQ1.remove());
        }
    }
}

class CircularQueue {
    private int front;
    private int rear;
    private double[] queueArray;
    private int maxSize;
    private int nItems;
            
    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queueArray = new double[maxSize];
        this.front = 0;
        this.rear = -1;
        this.nItems = 0;
    }
    
    public void insert(double x) {
        if(nItems == maxSize)
            System.out.println("Cannot enter anymore values");
        else {
            if(rear == maxSize-1) {
                rear = -1;
            }
            
            rear++;
            queueArray[rear] = x;
            nItems++;
        }
    }
    
    public double remove() {
        if(isEmpty()) {
            System.out.println("No values entered");
            return -99;
        }
        else {
            double x = queueArray[front];
            front++;
            if(front == maxSize)
                front = 0;
            nItems--;
            return x;
        }
    }
    
    public boolean isEmpty() {
        if(nItems == 0) 
            return true;
        else
            return false;
    }
    
    public boolean isFull() {
        if(rear == maxSize-1)
            return true;
        else
            return false;
    }
    
    public double peekFront() {
        if(isEmpty()) {
            System.out.println("No values entered");
            return -99;
        }
        else {
            double x = queueArray[front];
            return x;
        }
    }
}
