package project;

import java.awt.Color;
import javax.swing.JLabel;

public class Node {
	private String node_data;
	public JLabel myLabel;
	private int index, node_x, node_y, node_wid, node_hei;
	private Color node_color;
	private Node parent = null;
    private Node leftChild = null;
    private Node rightSibling = null;
 
    public Node(String node_data) {
        this.node_data = node_data;
    }
 
    public void setData(String node_data) {
        this.node_data = node_data;
    }
    
    public String getNodeData() {
        return node_data;
    }
    
    public void setmyLabel(JLabel myLabel) {
    	this.myLabel = myLabel;
    }
    
    public JLabel getmyLabel() {
    	return myLabel;
    }    
    
    public void setIndex(int index) {
    	this.index = index;
    }
    
    public int getIndex() {
    	return index;
    }
    
    public void setNodex(int node_x) {
    	this.node_x = node_x;
    }
    
    public int getNodex() {
    	return node_x;
    }
    
    public void setNodey(int node_y) {
    	this.node_y = node_y;
    }
    
    public int getNodey() {
    	return node_y;
    }
    
    public void setNodewid(int node_wid) {
    	this.node_wid = node_wid;
    }
    
    public int getNodewid() {
    	return node_wid;
    }
    
    public void setNodehei(int node_hei) {
    	this.node_hei = node_hei;
    }
    
    public int getNodehei() {
    	return node_hei;
    }
    
    public void setNodeColor(Color node_color) {
    	this.node_color = node_color;
    }
    
    public Color getNodecolor() {
    	return node_color;
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
