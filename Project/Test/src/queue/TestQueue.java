package queue;

public class TestQueue {
    public static void main(String[] args) {
        QueueX qx1 = new QueueX(5);
        qx1.insert(10.1);
        qx1.insert(11.2);
        qx1.insert(12.3);
        qx1.insert(13.4);
        qx1.insert(14.5);
        qx1.insert(15.6);
        
        System.out.println("front is "+qx1.peekFront());
        
        while(!qx1.isEmpty())
            System.out.println(qx1.remove());
        
        System.out.println(qx1.peekFront());
        
        System.out.println(qx1.isEmpty());
    }
}

class QueueX {
    private int front;
    private int rear;
    private int maxSize;
    private double[] queueArray;
    private int nItems;
    
    public QueueX(int S) {
        maxSize = S;
        queueArray = new double[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    
    public void insert(double x) {
        if(rear == maxSize-1)
            System.out.println("Cannot enter anymore values");
        else {
            rear++;
            queueArray[rear] = x;
            nItems++;
        }
    }
    
    public double remove() {
        if(isEmpty()) {
            System.out.println("No items available to remove");
            return -99;
        }
        else {
            double temp = queueArray[front];
            front++;
            nItems--;
            return temp;
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
        if(nItems == 0) {
            System.out.println("No values available");
            return -99;
        }
        else
            return queueArray[front];
    }
}
