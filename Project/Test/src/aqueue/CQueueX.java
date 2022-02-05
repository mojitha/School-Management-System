package aqueue;

public class CQueueX {
    public static void main(String[] args) {
        CQueueX q1 = new CQueueX(3);
        q1.insert(2);
        q1.insert(3);
        q1.insert(5);
        
        while(!q1.isEmpty())
            System.out.println(q1.remove());
        
        q1.insert(2);
        q1.insert(3);
        q1.insert(5);
        
        q1.remove();
        
        while(!q1.isEmpty())
            System.out.println(q1.remove());
        
        System.out.println(q1.isEmpty());
    }
    
    private final int maxSize;
    private final double numArray[];
    private int front,rear;
    private int nItems;
    
    public CQueueX(int S) {
        maxSize = S;
        numArray = new double[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    
    public void insert(double X) {
        if(nItems == maxSize)
            System.out.println("Cannot enter anymore");
        else {
            if(rear == maxSize - 1)
                rear = -1;
                
            rear++;
            numArray[rear] = X;
            nItems++;
        }
    }
    
    public double remove() {
        if(nItems == 0) {
            System.out.println("No values present");
            return -99;
        }
        else {
            double temp = numArray[front];
            front++;
            
            if(front == maxSize)
                front = 0;
            
            nItems--;
            return temp;
        }
    }
    
    public double peekFront() {
        if(isEmpty()) {
            System.out.println("No values present");
            return -99;
        }
        else {
            double temp = numArray[front];
            return temp;
        }
    }
    
    public boolean isEmpty() {
        return nItems == 0;
    }
    
    public boolean isFull() {
        return nItems == maxSize;
    }
}
