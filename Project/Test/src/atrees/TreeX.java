package atrees;

public class TreeX {
    public static void main(String[] args) {
        TreeX trX = new TreeX();
        
        trX.insertNode(1, 1.1);
        trX.insertNode(3, 1.3);
        trX.insertNode(2, 1.2);
        
        trX.find(3).displayNode();
    }
    
    private Node root;
    
    public TreeX() {
        root = null;
    }
    
    public void insertNode(int id, double dd) {
        Node newNode = new Node(id,dd);
        if(root == null)
            root = newNode;
        else {
            Node current = root;
            Node parent;
            while(true) {
                parent = current;
                if(id < current.iData) {
                    current = current.leftChild;
                    if(current == null) {
                        parent.leftChild = newNode;
                        return;
                    }
                }
                else if(id >= current.iData) {
                    current = current.rightChild;
                    if(current == null) {
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }
    
    public boolean deleteNode(int id) {
        return false;
    }
    
    public Node find(int key) {
        Node current = root;
        while(current.iData != key) {
            if(key<current.iData)
                current = current.leftChild;
            else if(key>=current.iData)
                current = current.rightChild;
            if(current == null)
                return null;
        }
        
        return current;
    }
    
    public void inOrder(Node localRoot) {
        if(localRoot != null) {
            inOrder(localRoot.leftChild);
            localRoot.displayNode();
            inOrder(localRoot.rightChild);
        }
    }
    
    public void preOrder(Node localRoot) {
        if(localRoot != null) {
            localRoot.displayNode();
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }
    
    public void postOrder(Node localRoot) {
        if(localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            localRoot.displayNode();
        }
    }
}

class Node {
    public int iData;
    public double dData;
    public Node leftChild;
    public Node rightChild;
    
    public Node(int iData, double dData) {
        this.iData = iData;
        this.dData = dData;
        this.leftChild = null;
        this.rightChild = null;
    }
    
    public void displayNode() {
        System.out.println("iData : "+iData);
        System.out.println("dData : "+dData);
    }
}
