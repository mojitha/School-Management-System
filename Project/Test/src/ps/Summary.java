package ps;

import java.util.Scanner;

public class Summary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList lL = new LinkedList();
        
        double min = 0.0;
        double q1 = 0.0;
        double q2 = 0.0;
        double q3 = 0.0;
        double max = 0.0;
        double range = 0.0;
        double iqr = 0.0;
        double lowerBound = 0.0;
        double upperBound = 0.0;
        
        insertItems(lL, sc);
        lL.sortList();
        
        min = lL.findMin();
        max = lL.findMax();
        range = max - min;
        q1 = lL.findQuartileOne();
        q2 = lL.findQuartileTwo();
        q3 = lL.findQuartileThree();
        iqr = q3 - q1;
        
        System.out.println("Min    : "+min);
        System.out.println("Max    : "+max);
        System.out.println("Range  : "+range);
        System.out.println("Q1     : "+q1);
        System.out.println("Q2     : "+q2);
        System.out.println("Q3     : "+q3);
        System.out.println("IQR    : "+iqr);
        
        
        //lL.displayList();
    }
    
    static void insertItems(LinkedList lL ,Scanner sc) {
        double num = 0.0;
        int n = 0;
        
        System.out.println("Enter number of items ");
        n = sc.nextInt();
            
        System.out.println("-----Enter numbers-----");

        for(int i=1;i<=n;i++) {
            System.out.println("Enter number "+i);
            num = sc.nextDouble();
            
            lL.insertFirst(num);
        }
    }
}


class NumberClass {
    public double num;
    public NumberClass next;
    
    public NumberClass(double num) {
        this.num = num;
        next = null;
    }

    public void displayNumber() {
        System.out.println(" "+num);
    }
    
}


class LinkedList {
    private NumberClass first;

    public LinkedList() {
        first = null;
    }
    
    public NumberClass getFirst() {
        return first;
    }
	
    public boolean isEmpty() {
        return first == null;
    }
	
    public void insertFirst(double num) {
        NumberClass newNumber = new NumberClass(num);

        if(isEmpty())
            first = newNumber;
        else {
            newNumber.next = first;
            first = newNumber;
        }
    }
	
    public NumberClass deleteFirst() {
        if(isEmpty()) {
            System.out.println("No Values Present");
            return null;
        }
        else {
            NumberClass temp = first;
            first = temp.next;
            return temp;
        }
    }
    
    public double findMin() {
        NumberClass currNumber = first;
        double minimum = first.num;
        
        while(currNumber != null) {
            if(currNumber.num <= minimum) 
                minimum = currNumber.num;
            
            currNumber = currNumber.next;
        }
        
        return minimum;
    }
    
    public double findMax() {
        NumberClass currNumber = first;
        double maximum = first.num;
        
        while(currNumber != null) {
            if(currNumber.num >= maximum) 
                maximum = currNumber.num;
            
            currNumber = currNumber.next;
        }
        
        return maximum;
    }
    
    public void sortList() {
        NumberClass currNumber = first;

        while(currNumber != null) {
            

            currNumber = currNumber.next;
        }
    }
	
    public double findMean() {
        double total = 0;
        int count = 0;
        double mean = 0;
		
        NumberClass currNumber = first;
		
        while(currNumber != null) {
            total += (double) currNumber.num;
            count++;

            currNumber = currNumber.next;
        }

        mean = total / count;

        return mean;
    }
	
    public void displayList() {
        NumberClass currNumber = first;

        while(currNumber != null) {
            currNumber.displayNumber();

            currNumber = currNumber.next;
        }
    }
	
    public int find(double num) {
        NumberClass currNumber = first;
        int count = 0;

        while(currNumber != null) {
            if(num == currNumber.num)
                count++;

            currNumber = currNumber.next;
        }

        return count;
    }
	
    public double getMode() {
        double modeTemp = 0.0;
        double mode = 0.0;
        int modeCount = 0;
        int[] modeArray = new int[2];
        //modeArray[mode];
        
        if(isEmpty()) {
            System.out.println("No values entered");
            return -99;
        }
        else {
            NumberClass currNumber = first;

            while(currNumber != null) {
                    modeTemp = currNumber.num;
                    modeCount = find(mode);

//                    if(modeArray[])
//                        modeArray[0] = mode;
//                        modeArray[1] = modeCount;
            }
        }
		
            return mode;
    }

    public double findQuartileOne() {
        NumberClass currNumber = first;
        int countA = 0;
        double q1 = 0.0;
        double place = 0.0;
        int decPlace = 0;
        double placeDifference = 0.0;
        int placeA = 0;
        int placeB = 0;
        int countB = 0;
        double numberA = 0.0;
        double numberB = 0.0;
        
        while(currNumber != null) {
            countA++;
            currNumber = currNumber.next;
        }
        
        place = countA / 4;
        decPlace = (int) place;
        placeDifference = place - decPlace;
        placeA = decPlace;
        placeB = decPlace + 1;
        
        System.out.println(place+" "+placeA+" "+placeB);
        
        while(currNumber != null) {
            countB++;
            
            if(countB == placeA)
                numberA = currNumber.num;
            
            if(countB == placeB)
                numberB = currNumber.num;
            
            currNumber = currNumber.next;
        }
        
        q1 = numberA + (numberB - numberA) * placeDifference;
        
        return q1;
    }

    public double findQuartileTwo() {
        NumberClass currNumber = first;
        int countA = 0;
        double q2 = 0.0;
        double place = 0.0;
        int decPlace = 0;
        double placeDifference = 0.0;
        int placeA = 0;
        int placeB = 0;
        int countB = 0;
        double numberA = 0.0;
        double numberB = 0.0;
        
        while(currNumber != null) {
            countA++;
            currNumber = currNumber.next;
        }
        
        place = 2 * (countA / 4);
        decPlace = (int) place;
        placeDifference = place - decPlace;
        placeA = decPlace;
        placeB = decPlace + 1;
        
        while(currNumber != null) {
            countB++;
            
            if(countB == placeA)
                numberA = currNumber.num;
            
            if(countB == placeB)
                numberB = currNumber.num;
            
            currNumber = currNumber.next;
        }
        
        q2 = numberA + (numberB - numberA) * placeDifference;
        
        return q2;
    }

    public double findQuartileThree() {
        NumberClass currNumber = first;
        int countA = 0;
        double q3 = 0.0;
        double place = 0.0;
        int decPlace = 0;
        double placeDifference = 0.0;
        int placeA = 0;
        int placeB = 0;
        int countB = 0;
        double numberA = 0.0;
        double numberB = 0.0;
        
        while(currNumber != null) {
            countA++;
            currNumber = currNumber.next;
        }
        
        place = 3 * (countA / 4);
        decPlace = (int) place;
        placeDifference = place - decPlace;
        placeA = decPlace;
        placeB = decPlace + 1;
        
        while(currNumber != null) {
            countB++;
            
            if(countB == placeA)
                numberA = currNumber.num;
            
            if(countB == placeB)
                numberB = currNumber.num;
            
            currNumber = currNumber.next;
        }
        
        q3 = numberA + (numberB - numberA) * placeDifference;
        
        return q3;
    }
	
	
	
	
}