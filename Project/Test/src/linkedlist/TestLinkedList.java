package linkedlist;

public class TestLinkedList {
    public static void main(String[] args) {
        LinkedList lL1 = new LinkedList();
        System.out.println("\nIS EMPTY ? "+lL1.isEmpty());
        
        lL1.insertFirst(112);
        lL1.insertFirst(22);
        lL1.insertFirst(133);
        
        System.out.println("\nIS EMPTY NOW? "+lL1.isEmpty());
        
        System.out.println(lL1.deleteFirst());
        
        lL1.displayList();
    }
}

class Link {
    private int iData;
    public Link next;

    public Link(int x) {
        iData = x;
        next = null;
    }
    
    public void displayLink() {
        System.out.println(iData);
    }
}

class LinkedList {
    private Link first;
    
    public LinkedList() {
        first = null;
    }
    
    public void insertFirst(int x) {
        Link newLink = new Link(x);
        newLink.next = first;
        first = newLink;
    }
    
    public Link deleteFirst() {
        if(isEmpty()) {
            System.out.println("Linked List is Empty");
            return null;
        }
        else {
            Link temp = first;
            first = temp.next;
            return temp;
        }
    }
    
    public void displayList() {
        Link current = first;
        while(current != null) {
            current.displayLink();
            current = current.next;
        }
    }
    
    public boolean isEmpty() {
        if(first == null)
            return true;
        else
            return false;
    }
    
    // find()
    // insertMiddle()
    // deleteMiddle()
}