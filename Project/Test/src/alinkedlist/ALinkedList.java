package alinkedlist;

public class ALinkedList {
    public static void main(String[] args) {
        ALinkedList alL1 = new ALinkedList();
        
        alL1.insertFirst(3);
        alL1.insertFirst(2);
        alL1.insertFirst(1);
        
        alL1.displayList();
        
        while(!alL1.isEmpty())
            alL1.deleteFirst();
        
        System.out.println(alL1.isEmpty());
    }
    
    Link first;
    
    public ALinkedList() {
        first = null;
    }
    
    public void insertFirst(double data) {
        Link newLink = new Link(data);
        newLink.next = first;
        first = newLink;
    }
    
    public Link deleteFirst() {
        if(isEmpty()) {
            System.out.println("List is empty");
            return null;
        }
        else {
            Link temp = first;
            first = temp.next;
            return temp;
        }
    }
    
    public void displayList() {
        Link curr = first;
        while(curr != null) {
            curr.displayLink();
            curr = curr.next;
        }
    }
    
    public boolean isEmpty() {
        return first == null;
    }
}

class Link {
    Link next;
    private final double iData;
    
    public Link(double iData) {
        this.iData = iData;
        this.next = null;
    }
    
    public void displayLink() {
        System.out.println("Link : "+iData);
    }
}
