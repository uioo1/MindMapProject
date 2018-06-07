package project;

public class Node {
	private String node_data;
	private Node parent;
    private Node leftChild;
    private Node rightSibling;
 
    public Node(String node_data) {
        this.node_data = node_data;
    }
 
    public void setData(String node_data) {
        this.node_data = node_data;
    }
 
    public String getNodeData() {
        return node_data;
    }
    
    public void setParent(Node parent) {
    	this.parent = parent;
    }
    
    public Node getParent() {
    	return parent;
    }       
 
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }  
 
    public Node getLeftChild() {
        return leftChild;
    }
 
    public void setRightSibling(Node rightSibling) {
        this.rightSibling = rightSibling;
    }
 
    public Node getRightSibling() {
        return rightSibling;
    }
}
