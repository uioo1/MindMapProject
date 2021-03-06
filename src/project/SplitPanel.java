package project;

import java.awt.*;

import java.awt.color.*;

import java.awt.List;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.*;


public class SplitPanel extends JFrame{
	MenuBar menubar = new MenuBar();	
	ToolBar toolbar = new ToolBar();
	public static JPanel panel_Mid;	//가운데 마인드맵패널 정의
	JPanel panel_Left_Background;	//textarea 와 적용butoon을 가지고있는 패널
	JPanel panel_Right;	//오른쪽 속성 패널 정의
	JPanel panel_Background;
	public static ArrayList<JLabel> jLabel_nodes = new ArrayList<JLabel>();
	public static ArrayList<Node> node_for_Labels = new ArrayList<Node>();
	public static JTextArea myDrawPanel;	//textarea 정의
	JTextField node_textfield = new JTextField();
	JTextField node_xfield = new JTextField();
	JTextField node_yfield = new JTextField();
	JTextField node_widfield = new JTextField();
	JTextField node_heifield = new JTextField();
	JTextField node_colorfield = new JTextField();

	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	Tree myTree = new Tree();
	JLabel before_click_label = null;
	JLabel resize_label[] = new JLabel[8];
	JLabel before_resize_label[] = new JLabel[8];
	Graphics g_line;
	boolean isReversed = false;
	boolean isDragged = false;
	
	public SplitPanel() {
        split.setDividerLocation( 300 );
        split2.setDividerLocation( 800 );
        
        /////////////////가운데 마인드맵 패널
        
        
        
        panel_Mid = new JPanel();
        panel_Mid.setBackground(new Color(163, 202, 241));
        panel_Mid.setLayout(null);
        panel_Mid.addMouseListener(new MindMapPanelMouseListener());
        split2.setBackground(new Color(100, 100, 100));
        
        
        /////////////////왼쪽 텍스트 패널+다 담는 패널
        panel_Left_Background = new JPanel();
        myDrawPanel = new JTextArea(30,25);
        JButton textbutton = new JButton("적용");       
		
		panel_Left_Background.setBackground(new Color(255, 255, 255));
		panel_Left_Background.setLayout(new BorderLayout(5,5));
		panel_Left_Background.add(new JScrollPane( myDrawPanel ), BorderLayout.CENTER );
		panel_Left_Background.add(textbutton, BorderLayout.SOUTH );
	    textbutton.addActionListener(TextPanelActionListener);	//Action 리스너 달기

				
        ////////////////오른쪽 설정 패널
        panel_Right = new JPanel();
        panel_Right.setBackground(new Color(174, 246, 160)); 
        panel_Right.setPreferredSize( new Dimension( 350, 350 ) );
        panel_Right.setSize(200,600);
    	GridLayout gridAttPane = new GridLayout(8,2,0,30);
    	JLabel attribute_label = new JLabel("속성");
    	attribute_label.setOpaque(true);
    	attribute_label.setHorizontalAlignment(SwingConstants.CENTER);
    	attribute_label.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_Right.add(attribute_label);
    	panel_Right.add(new JLabel(""));
    	
		panel_Right.add(new JLabel("  TEXT : "));		
		node_textfield.setEditable(false);
		panel_Right.add(node_textfield);
		
		panel_Right.add(new JLabel("  X : "));		
		panel_Right.add(node_xfield);
		
		panel_Right.add(new JLabel("  Y : "));		
		panel_Right.add(node_yfield);
		
		panel_Right.add(new JLabel("  W : "));		
		panel_Right.add(node_widfield);
		
		panel_Right.add(new JLabel("  H : "));		
		panel_Right.add(node_heifield);
		
		panel_Right.add(new JLabel("  COLOR : "));		
		panel_Right.add(node_colorfield);
		
		JButton button_attr = new JButton("변경");
		panel_Right.add(button_attr);		
		button_attr.addActionListener(ChangeNodeListener);
		
		panel_Right.setLayout(gridAttPane);
		
		//틀에 스플릿 추가
		split.setLeftComponent(panel_Left_Background);
		split.setRightComponent(split2);
		//스플릿 하나위에 미드와 라이트 추가
		split2.setLeftComponent(panel_Mid);		
		split2.setRightComponent(panel_Right);
		
	}
	
	public static String[] splitByLength(String str, int splitLength) {
		if (str == null) {
			return null;
		}

		int strLen = str.length();
		int arrayLength = Math.abs(strLen / splitLength) + 1;

		String[] strArray = null;
		if (arrayLength == 0) {
			return new String[] { str };
		} else {
			strArray = new String[arrayLength];
		}

		String temp = "";
		for (int i = 0; i < arrayLength; i++) {
			if (str.length() > splitLength) {
				strArray[i] = str.substring(0, splitLength);
				temp = str.substring(splitLength, str.length());
			} else {
				strArray[i] = str.substring(0, str.length());
			}
			str = temp;
		}
		return strArray;
	}
	
	public JSplitPane splitpane_create() {        
        return split;
	}
	
	public void draw_Tree(Node root, int i, JPanel mid_panel) {
		JLabel label = new JLabel(root.getNodeData());
		int check = 0, node_x = i*60, node_y = i*60, node_wid = 60, node_hei = 40;
		Node check_node = root;
		
		label.setSize(node_wid, node_hei);
		label.setOpaque(true);
	
		int random_r, random_g, random_b;
		random_r = (int)(Math.random() * 256);
		random_g = (int)(Math.random() * 256);
		random_b = (int)(Math.random() * 256);
		Color random_color = new Color(random_r, random_g, random_b);
		label.setBackground(random_color);
		root.setNodeColor(random_color);

		label.setBorder(new LineBorder(new Color(82, 130, 184), 2));
		label.setLocation(node_x, node_y);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_Mid.add(label);	//Label 마인드맵에 추가
		jLabel_nodes.add(label);
		node_for_Labels.add(root);
		root.setNodex(node_x);
		root.setNodey(node_y);
		root.setNodewid(node_wid);
		root.setNodehei(node_hei);
		
		NodeMouseListener nodeMouse = new NodeMouseListener(label, mid_panel);
		label.addMouseListener(nodeMouse);
		label.addMouseMotionListener(nodeMouse);
       
		// 자식 노드가 존재한다면
	    if(root.getLeftChild() != null)
	    	draw_Tree(root.getLeftChild(), i + 1, panel_Mid);
	         
	    // 형제 노드가 존재한다면
	    if(root.getRightSibling() != null)
	    	draw_Tree(root.getRightSibling(), i + 1, panel_Mid);
	    	
	   panel_Mid.repaint();
	}
	
	public void recoloring_tree() {
		Node temp_Node, myNode;
		int before_count = 0, count;
		
		for(int i = 0; i < node_for_Labels.size(); i++) {
			temp_Node = node_for_Labels.get(i);
			myNode = node_for_Labels.get(i);
			count = 0;
			while(true) {
				if(temp_Node.getParent() != null) {
					temp_Node = temp_Node.getParent();
					count++;
				}
				else
					break;				
			}
			
			if(count > before_count) {
				before_count = count;
				Color color = node_for_Labels.get(i).getNodecolor();
				while(myNode.getRightSibling() != null) {
					myNode = myNode.getRightSibling();
					myNode.setNodeColor(color);
					//myNode.myLabel.setBackground(color);
					jLabel_nodes.get(node_for_Labels.indexOf(myNode)).setBackground(color);
				}
			}
		}
		
	}
	
	public void relocating_tree(Node this_node) {
		Node now_Node = this_node;
		Node temp_Node = null;
		Node parent_count_Node = null;
		int sibling_count = 0;
		double distance = 200;
		
		if(now_Node == myTree.root) {
			jLabel_nodes.get(node_for_Labels.indexOf(now_Node)).setLocation(panel_Mid.getWidth()/ 2 - jLabel_nodes.get(0).getWidth()/2, panel_Mid.getHeight()/2 - jLabel_nodes.get(0).getHeight()/2);
			now_Node.setNodex(panel_Mid.getWidth()/2 - jLabel_nodes.get(0).getWidth()/2);
			now_Node.setNodey(panel_Mid.getHeight()/2 - jLabel_nodes.get(0).getHeight()/2);
		}
		
		while(true) {
			while(now_Node.getLeftChild() != null) {
				sibling_count++;
				temp_Node = now_Node.getLeftChild();
			
				while(temp_Node.getRightSibling() != null) {
					sibling_count++;
					temp_Node = temp_Node.getRightSibling();
				}
				temp_Node = now_Node.getLeftChild();			 
				
				int parent_count = 0;
				parent_count_Node = temp_Node;
				while(parent_count_Node.getParent() != null) {
					parent_count_Node = parent_count_Node.getParent();
					parent_count++;
				}
				
				if(distance- 50*parent_count > 0) {
					distance = distance - 50*parent_count;
				}
				else
					distance = 25;
				
				for(int i = 0; i < sibling_count; i++) {
					double radius = 2 * Math.PI / sibling_count;
					temp_Node.setNodex(now_Node.getNodex() + (int)(distance*Math.sin(radius * i)));
					temp_Node.setNodey(now_Node.getNodey() + (int)(distance*Math.cos(radius * i)));
					jLabel_nodes.get(node_for_Labels.indexOf(temp_Node)).setLocation(temp_Node.getNodex(), temp_Node.getNodey());
					temp_Node = temp_Node.getRightSibling();
				}
				temp_Node = now_Node.getLeftChild();
				distance = 200;
			
				/////여기까지는 루트의 자식들 다 출력
				
				
				sibling_count = 0;
				now_Node = now_Node.getLeftChild();
			}
		
			temp_Node = now_Node;
			while(true) {				
				if(temp_Node.getLeftChild() != null) {	//자식이 있다면
					now_Node = temp_Node;
					break;
				}
				else if(temp_Node.getRightSibling() != null) {	//형제가 있다면
					temp_Node= temp_Node.getRightSibling();
					continue;
				}
				else if(temp_Node.getParent() != null) {	//자식이랑 형제는 없고 부모만 있다면
					while(true) {
						if(temp_Node.getParent().getRightSibling() != null) {	//부모의 오른쪽 형제가 있을때
							temp_Node = temp_Node.getParent().getRightSibling();
							break;
						}
						else {													//부모의 오른쪽 형제가 없을때
							if(temp_Node.getParent().getParent() != null) {	//부모의 부모는 있을 때
								temp_Node = temp_Node.getParent();
							}
							else {											//부모의 부모가 없을때(부모가 root일때)
								temp_Node = null;
								break;
							}
						}
					}
				}

				if(temp_Node == null)
					break;
			}
			
			if(temp_Node == null)
				break;
		}
	}
	
	class NodeMouseListener extends Frame implements MouseListener, MouseMotionListener {
		JLabel label;
		JPanel mid_panel;
		Node myNode;
		int before_x, before_y;
		int before_wid, before_hei, before_label_x, before_label_y;
		boolean isNorth = false, isWest = false, isSouth = false, isEast = false, isNW = false, isNE = false, isSW = false, isSE = false;
		 
		public NodeMouseListener(JLabel label, JPanel panel) {
			this.label = label;
			mid_panel = panel;
			int i = jLabel_nodes.indexOf(label);
			myNode = node_for_Labels.get(i);
		}
		
		public void false_compass() {
			isNorth = false; isWest = false; isSouth = false; isEast = false; isNW = false; isNE = false; isSW = false; isSE = false;
		}
		
		public boolean is_false_compass() {
			if(isNorth == false && isWest == false && isSouth == false && isEast == false && isNW == false && isNE == false && isSW == false && isSE == false)
				return true;
			else
				return false;
		}
		
		public void mouseDragged(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int label_x = label.getX();
			int label_y = label.getY();
			
			if(isEast == true) {
				//System.out.println(before_wid + x-before_x + " " + before_hei);
				if(before_wid - before_x >= x) {
					x = -(before_wid - before_x) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid + x-before_x, before_hei);
				node_xfield.setText(Integer.toString(label_x));			
				node_yfield.setText(Integer.toString(label_y));		
				node_widfield.setText(Integer.toString(before_wid + x-before_x));
				node_heifield.setText(Integer.toString(before_hei));
				myNode.setNodex(label_x);
				myNode.setNodey(label_y);
				myNode.setNodewid(before_wid + x-before_x);
				myNode.setNodehei(before_hei);
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isSouth == true) {
				if(before_hei -before_y >= y) {
					y = -(before_hei -before_y) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid, before_hei + y-before_y);
				node_xfield.setText(Integer.toString(label_x));			
				node_yfield.setText(Integer.toString(label_y));		
				node_widfield.setText(Integer.toString(before_wid));
				node_heifield.setText(Integer.toString(before_hei + y-before_y));
				myNode.setNodex(label_x);
				myNode.setNodey(label_y);
				myNode.setNodewid(before_wid);
				myNode.setNodehei(before_hei + y-before_y);
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isWest == true) {
				//System.out.println("before_x: " + before_x + " x: " + x + "차이 : " + (before_x - x));
				//System.out.println(before_wid +before_x - x + " " + before_hei);
				/*if(before_wid + before_x <= x) {
					x = -(before_wid + before_x) + 15;
				}*/
				label.setBounds(label.getX(), label.getY(), before_wid + before_x-x, before_hei);
				label.setLocation(before_label_x - (before_x-x), before_label_y);
				node_xfield.setText(Integer.toString(before_label_x - (before_x-x)));			
				node_yfield.setText(Integer.toString(label_y));		
				node_widfield.setText(Integer.toString(before_wid + (before_x-x)));
				node_heifield.setText(Integer.toString(before_hei));
				myNode.setNodex(before_label_x - (before_x-x));
				myNode.setNodey(label_y);
				myNode.setNodewid(before_wid + (before_x-x));
				myNode.setNodehei(before_hei);
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isSE == true) {
				if(before_wid - before_x >= x) {
					x = -(before_wid - before_x) + 15;
				}
				if(before_hei -before_y >= y) {
					y = -(before_hei -before_y) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid + x-before_x, before_hei + y-before_y);
				node_xfield.setText(Integer.toString(label_x));			
				node_yfield.setText(Integer.toString(label_y));		
				node_widfield.setText(Integer.toString(before_wid + x-before_x));
				node_heifield.setText(Integer.toString(before_hei + y-before_y));
				myNode.setNodex(label_x);
				myNode.setNodey(label_y);
				myNode.setNodewid(before_wid + x-before_x);
				myNode.setNodehei(before_hei + y-before_y);
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isSW == true) {
				if(before_hei -before_y >= y) {
					y = -(before_hei -before_y) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid + before_x-x,before_hei + y-before_y);
				label.setLocation(before_label_x - (before_x-x), label_y);
				node_xfield.setText(Integer.toString(before_label_x - (before_x-x)));			
				node_yfield.setText(Integer.toString(label_y));		
				node_widfield.setText(Integer.toString(before_wid + before_x-x));
				node_heifield.setText(Integer.toString(before_hei + y-before_y));
				myNode.setNodex(before_label_x - (before_x-x));
				myNode.setNodey(label_y);
				myNode.setNodewid(before_wid + before_x-x);
				myNode.setNodehei(before_hei + y-before_y);
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isNorth == true) {
				if(before_hei + before_y <= y) {
					y = -(before_hei + before_y) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid, before_hei + (before_y-y));
				label.setLocation(label_x, before_label_y - (before_y-y));
				node_xfield.setText(Integer.toString(label_x));			
				node_yfield.setText(Integer.toString(before_label_y - (before_y-y)));		
				node_widfield.setText(Integer.toString(before_wid));
				node_heifield.setText(Integer.toString(before_hei + (before_y-y)));
				myNode.setNodex(label_x);
				myNode.setNodey(before_label_y - (before_y-y));
				myNode.setNodewid(before_wid);
				myNode.setNodehei(before_hei + (before_y-y));
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isNE == true) {
				if(before_wid - before_x >= x) {
					x = -(before_wid - before_x) + 15;
				}
				if(before_hei + before_y <= y) {
					y = -(before_hei + before_y) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid + x-before_x, before_hei + (before_y-y));
				label.setLocation(label_x, before_label_y - (before_y-y));
				node_xfield.setText(Integer.toString(label_x));			
				node_yfield.setText(Integer.toString(before_label_y - (before_y-y)));		
				node_widfield.setText(Integer.toString(before_wid + x-before_x));
				node_heifield.setText(Integer.toString(before_hei + (before_y-y)));
				myNode.setNodex(label_x);
				myNode.setNodey(before_label_y - (before_y-y));
				myNode.setNodewid(before_wid + x-before_x);
				myNode.setNodehei(before_hei + (before_y-y));
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else if(isNW == true) {
				if(before_hei + before_y <= y) {
					y = -(before_hei + before_y) + 15;
				}
				label.setBounds(label.getX(), label.getY(), before_wid + before_x-x, before_hei + (before_y-y));
				label.setLocation(before_label_x - (before_x-x), before_label_y - (before_y-y));
				node_xfield.setText(Integer.toString(before_label_x - (before_x-x)));			
				node_yfield.setText(Integer.toString(before_label_y - (before_y-y)));		
				node_widfield.setText(Integer.toString(before_wid + before_x-x));
				node_heifield.setText(Integer.toString(before_hei + (before_y-y)));
				myNode.setNodex(before_label_x - (before_x-x));
				myNode.setNodey(before_label_y - (before_y-y));
				myNode.setNodewid(before_wid + before_x-x);
				myNode.setNodehei(before_hei + (before_y-y));
				removing_now_resize_label();
				locating_resize_label(label_x, label_y);
			}
			else {
				myNode.setNodex(label_x + x - before_x);
				myNode.setNodey(label_y + y - before_y);
				node_xfield.setText(Integer.toString(label_x + x - before_x));			
				node_yfield.setText(Integer.toString(label_y + y - before_y));			
				label.setLocation(label_x + x - before_x, label_y + y - before_y);
			
				if(before_click_label == null) {	//맨처음
					removing_now_resize_label();
					locating_resize_label(label_x + x - before_x, label_y + y - before_y);
					isReversed = true;
				}
				else if(isReversed == false) {
					if(before_click_label == label) {
						removing_now_resize_label();
						locating_resize_label(label_x + x - before_x, label_y + y - before_y);
						isReversed = true;
					}
					else {
						removing_now_resize_label();
						locating_resize_label(label_x + x - before_x, label_y + y - before_y);
						isReversed = true;
					}
				}
				else if(isReversed == true) {
					if(before_click_label == label) {
						removing_now_resize_label();
						locating_resize_label(label_x + x - before_x, label_y + y - before_y);
						isReversed = true;
					}
					else {
						removing_now_resize_label();
						locating_resize_label(label_x + x - before_x, label_y + y - before_y);
						isReversed = true;
					}
				}
			}
			
			isDragged = true;
		}

		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int setArea = 5;
			int node_wid = myNode.getNodewid();
			before_wid = myNode.getNodewid();
			int node_hei = myNode.getNodehei();
			before_hei = myNode.getNodehei();
			before_label_x = myNode.getNodex();
			before_label_y = myNode.getNodey();
			
			if(x >= 0 && x <= setArea && y >= 0 && y <= setArea) {
				label.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
				false_compass();
				isNW = true;
			}
			else if(x <= node_wid && x >= node_wid-setArea && y >= 0 && y <= setArea) {
				label.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
				false_compass();
				isNE = true;
			}
			else if(x >= 0 && x <= setArea && y <= node_hei && y >= node_hei-setArea) {
				label.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
				false_compass();
				isSW = true;
			}
			else if(x <= node_wid && x >= node_wid-setArea && y <= node_hei && y >= node_hei-setArea) {
				label.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
				false_compass();
				isSE = true;
			}
			else if(x >= 0 && x <= setArea) {
				label.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				false_compass();
				isWest = true;
			}
			else if(x <= node_wid && x >= node_wid-setArea) {
				label.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				false_compass();
				isEast = true;
			}
			else if(y >= 0 && y <= setArea) {
				label.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				false_compass();
				isNorth = true;
			}
			else if(y <= node_hei && y >= node_hei-setArea) {
				label.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				false_compass();
				isSouth = true;				
			}
			else {
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				false_compass();
			}
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {		
			label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			before_x = e.getX();
			before_y = e.getY();
			int red = myNode.getNodecolor().getRed();
			int green = myNode.getNodecolor().getGreen();
			int blue = myNode.getNodecolor().getBlue();
			String redVal, greenVal, blueVal;
			
			
			if(red < 16)
				redVal = 0 + Long.toHexString(red);
			else
				redVal = Long.toHexString(red);
			
			if(green < 16)
				greenVal = 0 + Long.toHexString(green);
			else
				greenVal = Long.toHexString(green);
			
			if(blue < 16)
				blueVal = 0 + Long.toHexString(blue);
			else
				blueVal = Long.toHexString(blue);
				
			node_textfield.setText(myNode.getNodeData());
			node_widfield.setText(Integer.toString(myNode.getNodewid()));
			node_heifield.setText(Integer.toString(myNode.getNodehei()));
			node_colorfield.setText(redVal + greenVal + blueVal);			
			
			locating_resize_label(myNode.getNodex(), myNode.getNodey());
			
			if(before_click_label == null) {	//맨처음
				//resize_box[0] = new Rectangle(50, 50, 100, 100);
				reversing_node();
			}
			else if(isReversed == false) {
				if(before_click_label == label) {
					reversing_node();
				}
				else {
					reversing_node();
				}
			}
			else if(isReversed == true) {
				if(before_click_label == label) {
					removing_before_resize_label();
				}
				else {
					node_back_again();
					removing_before_resize_label();
					reversing_node();
				}
			}			
			

		}

		public void mouseReleased(MouseEvent e) {
			int now_x = e.getX();
			int now_y = e.getY();
			int label_x = label.getX();
			int label_y = label.getY();
			int real_x = label_x + (now_x - before_x);
			if(real_x < 0)
				real_x = 0;
			int real_y = label_y + (now_y - before_y);
			if(real_y < 0)
				real_y = 0;
			
			if(is_false_compass() == false) {
				removing_before_resize_label();
				removing_now_resize_label();
				locating_resize_label(label.getX(), label.getY());
				
				before_wid = label.getWidth();
				before_hei = label.getHeight();
			}
			else {
				myNode.setNodex(real_x);
				myNode.setNodey(label_y + (now_y - before_y));
				node_xfield.setText(Integer.toString(real_x));			
				node_yfield.setText(Integer.toString(real_y));
			
			label.setLocation(real_x, real_y);
			
			if(before_click_label == null) {	//맨처음
				isReversed = true;
			}
			else if(isReversed == false) {
				if(before_click_label == label) {
					if(isDragged == false) {
						isReversed = true;
					}
					else {
						removing_now_resize_label();
						locating_resize_label(real_x, real_y);
						isReversed = true;
					}
				}
				else {
					if(isDragged == false) {
						isReversed = true;
					}
					else {
						removing_now_resize_label();
						locating_resize_label(real_x, real_y);
							isReversed = true;
						}
					}
				}
				else if(isReversed == true) {
					if(before_click_label == label) {
						if(isDragged == false) {
							reversing_node();
							removing_now_resize_label();
							isReversed = false;
						}
						else {
							removing_now_resize_label();
							locating_resize_label(real_x, real_y);
							isReversed = true;
						}
					}
					else {
						if(isDragged == false) {
							isReversed = true;
						}
						else {
							removing_now_resize_label();
							locating_resize_label(real_x, real_y);
							isReversed = true;
						}					
					}
				}
			}
			
			mid_panel.repaint();
			
			for(int i = 0; i < resize_label.length; i++) {
				before_resize_label[i] = resize_label[i];
			}			
			before_click_label = label;
			
			isDragged = false;
		}
		
		public void reversing_node() {
			label.setBackground(new Color(255 - label.getBackground().getRed(), 255 - label.getBackground().getGreen(), 255 - label.getBackground().getBlue()));
			label.setForeground(new Color(255 - label.getForeground().getRed(), 255 - label.getForeground().getGreen(), 255 - label.getForeground().getBlue()));
		}
		
		public void node_back_again() {
			//Node temp_node = node_for_Labels.get(jLabel_nodes.indexOf(before_click_label));
			before_click_label.setBackground(new Color(255 - before_click_label.getBackground().getRed(), 255 - before_click_label.getBackground().getGreen(), 255 - before_click_label.getBackground().getBlue()));
			before_click_label.setForeground(new Color(255 - before_click_label.getForeground().getRed(), 255 - before_click_label.getForeground().getGreen(), 255 - before_click_label.getForeground().getBlue()));
			//temp_node.setNodeColor(new Color(255 - temp_node.getNodecolor().getRed(), 255 - temp_node.getNodecolor().getGreen(), 255 - temp_node.getNodecolor().getBlue()));
		}
		
		public void locating_resize_label(int real_x, int real_y) {
			int x = real_x;
			int y = real_y;
			int wid = myNode.getNodewid();
			int hei = myNode.getNodehei();
			
			for(int i = 0; i < resize_label.length; i++) {
				resize_label[i] = new JLabel("");
				resize_label[i].setOpaque(true);
				resize_label[i].setBorder(new LineBorder(new Color(155, 157, 159), 1));
				resize_label[i].setSize(8, 8);
				resize_label[i].setBackground(Color.WHITE);
			}
			resize_label[0].setLocation(x - 4, y - 4);
			mid_panel.add(resize_label[0]);
			resize_label[1].setLocation(x + wid/2 - 4, y - 4);
			mid_panel.add(resize_label[1]);
			resize_label[2].setLocation(x + wid - 4, y - 4);
			mid_panel.add(resize_label[2]);
			resize_label[3].setLocation(x - 4, y + hei/2 - 4);
			mid_panel.add(resize_label[3]);
			resize_label[4].setLocation(x + wid - 4, y + hei/2 - 4);
			mid_panel.add(resize_label[4]);
			resize_label[5].setLocation(x - 4, y + hei - 4);
			mid_panel.add(resize_label[5]);
			resize_label[6].setLocation(x + wid/2 - 4, y + hei - 4);
			mid_panel.add(resize_label[6]);
			resize_label[7].setLocation(x + wid - 4, y + hei - 4);
			mid_panel.add(resize_label[7]);
			
			mid_panel.repaint();
		}
		
		public void removing_before_resize_label() {
			if(before_resize_label[0] != null) {
				for(int i = 0; i < before_resize_label.length; i++) {
					mid_panel.remove(before_resize_label[i]);
				}
			}
		}
		
		public void removing_now_resize_label() {
			if(resize_label[0] != null) {
				for(int i = 0; i < resize_label.length; i++) {
					mid_panel.remove(resize_label[i]);
				}
			}
		}
		
	}
	
	public void painting_line() {	//클래스 내 함수임 MouseListener의 함수가 아님
		g_line = panel_Mid.getGraphics();
		panel_Mid.paint(g_line);		
		g_line.drawLine(myTree.root.getNodex(), myTree.root.getNodey(), myTree.root.getLeftChild().getNodex(), myTree.root.getLeftChild().getNodey());
	}
	
	public void locating_resize_label(int real_x, int real_y) {
		Node myNode = node_for_Labels.get(jLabel_nodes.indexOf(before_click_label));
		int x = real_x;
		int y = real_y;
		int wid = myNode.getNodewid();
		int hei = myNode.getNodehei();
		
		for(int i = 0; i < resize_label.length; i++) {
			resize_label[i] = new JLabel("");
			resize_label[i].setOpaque(true);
			resize_label[i].setBorder(new LineBorder(new Color(155, 157, 159), 1));
			resize_label[i].setSize(8, 8);
			resize_label[i].setBackground(Color.WHITE);
		}
		resize_label[0].setLocation(x - 4, y - 4);
		panel_Mid.add(resize_label[0]);
		resize_label[1].setLocation(x + wid/2 - 4, y - 4);
		panel_Mid.add(resize_label[1]);
		resize_label[2].setLocation(x + wid - 4, y - 4);
		panel_Mid.add(resize_label[2]);
		resize_label[3].setLocation(x - 4, y + hei/2 - 4);
		panel_Mid.add(resize_label[3]);
		resize_label[4].setLocation(x + wid - 4, y + hei/2 - 4);
		panel_Mid.add(resize_label[4]);
		resize_label[5].setLocation(x - 4, y + hei - 4);
		panel_Mid.add(resize_label[5]);
		resize_label[6].setLocation(x + wid/2 - 4, y + hei - 4);
		panel_Mid.add(resize_label[6]);
		resize_label[7].setLocation(x + wid - 4, y + hei - 4);
		panel_Mid.add(resize_label[7]);
		
		panel_Mid.repaint();
	}
	
	public void removing_before_resize_label() {
		if(before_resize_label[0] != null) {
			for(int i = 0; i < before_resize_label.length; i++) {
				panel_Mid.remove(before_resize_label[i]);
			}
		}
	}
	
	public void removing_now_resize_label() {
		if(resize_label[0] != null) {
			for(int i = 0; i < resize_label.length; i++) {
				panel_Mid.remove(resize_label[i]);
			}
		}
	}
	
	ActionListener TextPanelActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			myTree = new Tree();
			panel_Mid.removeAll();
			panel_Mid.revalidate();
			panel_Mid.repaint();
			jLabel_nodes.clear();
			node_for_Labels.clear();
			myTree.getTextPanel(myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
			draw_Tree(myTree.root, 0, panel_Mid);
			recoloring_tree();
			relocating_tree(myTree.root);
			panel_Mid.repaint();
			before_click_label = null;
		}
	};
	
	ActionListener ChangeNodeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(before_click_label != null && isReversed == true) {
				Node myNode = node_for_Labels.get(jLabel_nodes.indexOf(before_click_label));
				String [] colors = splitByLength(node_colorfield.getText(), 2);
				int x = Integer.parseInt(node_xfield.getText());
				if(x < 0)
					x = 0;
				int y = Integer.parseInt(node_yfield.getText());
				if(y < 0)
					y = 0;
				int wid = Integer.parseInt(node_widfield.getText());
				int hei = Integer.parseInt(node_heifield.getText());
				
				myNode.setNodex(x);
				myNode.setNodey(y);
				before_click_label.setLocation(x, y);					
				myNode.setNodewid(wid);
				myNode.setNodehei(hei);
				before_click_label.setSize(wid, hei);
				removing_before_resize_label();
				locating_resize_label(x, y);
				myNode.setNodeColor(new Color(Integer.parseInt(colors[0], 16), Integer.parseInt(colors[1], 16), Integer.parseInt(colors[2], 16)));
				before_click_label.setBackground(new Color(255 - Integer.parseInt(colors[0], 16), 255 - Integer.parseInt(colors[1], 16), 255 - Integer.parseInt(colors[2], 16)));
				
				for(int i = 0; i < resize_label.length; i++) {
					before_resize_label[i] = resize_label[i];
				}
			}
			
		}
	};
	
	ActionListener NewcreateListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			myTree = new Tree();
			myDrawPanel.setText("");
			panel_Mid.removeAll();
			panel_Mid.revalidate();
			panel_Mid.repaint();
			jLabel_nodes.clear();
			node_for_Labels.clear();
			panel_Mid.repaint();
			before_click_label = null;	
			isReversed = false;
			isDragged = false;
		}
	};
	
	class MindMapPanelMouseListener implements MouseListener{
		 
		public MindMapPanelMouseListener() {
		}

		public void mouseClicked(MouseEvent arg0) {
		}

		public void mouseEntered(MouseEvent arg0) {	
		}

		public void mouseExited(MouseEvent arg0) {	
		}

		public void mousePressed(MouseEvent arg0) {	
		}

		public void mouseReleased(MouseEvent arg0) {
			if(isReversed == true ) {
				node_back_again();
				removing_before_resize_label();
				panel_Mid.repaint();
				isReversed = false;
			}
		}
		
		public void node_back_again() {
			before_click_label.setBackground(new Color(255 - before_click_label.getBackground().getRed(), 255 - before_click_label.getBackground().getGreen(), 255 - before_click_label.getBackground().getBlue()));
			before_click_label.setForeground(new Color(255 - before_click_label.getForeground().getRed(), 255 - before_click_label.getForeground().getGreen(), 255 - before_click_label.getForeground().getBlue()));
		}
		
	}

	class MenuBar {	
		public MenuBar() {
			 
		}
		
		public JMenuBar menubar_create() {
			JMenuBar menuBar = new JMenuBar();
	        JMenu f = new JMenu("File");
	        JMenu e = new JMenu("edit");
	        
	        JMenuItem JMenu_New = new JMenuItem("New"); 
	        JMenuItem JMenu_Open = new JMenuItem("Open"); 
	        JMenuItem JMenu_Save = new JMenuItem("Save"); 
	        JMenuItem JMenu_SaveAs = new JMenuItem("SaveAs"); 
	        JMenuItem JMenu_Close = new JMenuItem("Close"); 
	        JMenuItem JMenu_Apply = new JMenuItem("Apply"); 
	        JMenuItem JMenu_Change = new JMenuItem("Change"); 

	        JMenu_New.addActionListener(NewcreateListener);
	        JMenu_Open.addActionListener(new OpenActionListener());
	        JMenu_Save.addActionListener(new SaveActionListener());
	        JMenu_SaveAs.addActionListener(new SaveAsActionListener());
	        JMenu_Apply.addActionListener(TextPanelActionListener);
	        JMenu_Change.addActionListener(ChangeNodeListener);
	        JMenu_Close.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent arg0){
	                System.exit(0);//dispose는 창하나만 닫기이고 exit는 모든 창닫기인데 dispose같은 경우는jframe을 상속하애되서 일단 exit으로 함
	                }
	            });
	        f.add(JMenu_New);
	        f.add(JMenu_Open);
	        f.addSeparator();//분리선 삽입
	        f.add(JMenu_Save);
	        f.add(JMenu_SaveAs);
	        
	        e.add(JMenu_Close);
	        e.add(JMenu_Apply);
	        e.add(JMenu_Change);
	        
	        menuBar.add(f);
			menuBar.add(e);
			return menuBar;
		}
	}
	
	class ToolBar {
		public ToolBar() {
			
		}
		
		public JToolBar toolBar_create() {
			 JToolBar tool = new JToolBar("Kitae Menu");
			 
		     tool.setBackground(Color.gray);
		     JButton newbtn = new JButton("새로 만들기");
		     tool.add(newbtn);
		     newbtn.addActionListener(NewcreateListener);
		     newbtn.setToolTipText("마인드 맵 파일을 새로 생성합니다");
		     tool.addSeparator();
		     JButton openbtn = new JButton("열기");
		     tool.add(openbtn);
		     openbtn.setToolTipText("마인드 맵 파일을 불러옵니다");
		     tool.addSeparator();
		     JButton savebtn = new JButton("저장");
		     tool.add(savebtn);
		     savebtn.setToolTipText("작업한 내용을 저장합니다");
		     /*JButton btn3=new JButton(new ImageIcon("save.jpg"));//저장 이미지인데 내꺼에 이미지가 없는지 안나타남
		     tool.add(btn3);*/
		     tool.addSeparator();
		     JButton save_otherbtn = new JButton("다른 이름으로 저장");
		     tool.add(save_otherbtn);
		     save_otherbtn.setToolTipText("작업한 내용을 새로운 파일로 저장합니다");
		     tool.addSeparator();
		     JButton closebtn = new JButton("닫기");
		     tool.add(closebtn);
		     closebtn.setToolTipText("프로그램을 종료합니다");
		     tool.addSeparator();
		     JButton applybtn = new JButton("적용");
		     tool.add(applybtn);
		     applybtn.addActionListener(TextPanelActionListener);	//Action 리스너 달기
		     applybtn.setToolTipText("텍스트 편집 내용을 마인드 맵에 적용합니다");
		     tool.addSeparator();
		     JButton changebtn = new JButton("변경");
		     changebtn.setToolTipText("속성 변경 내용을 마인드 맵에 적용합니다");
		     changebtn.addActionListener(ChangeNodeListener);
		     tool.add(changebtn);
		     tool.addSeparator();
		     
		     JComboBox<String> combo = new JComboBox<String>();
		     combo.addItem("click");
		     combo.addItem("교수님");
		     combo.addItem("너무 잘생겼어요");
		     combo.addItem("조교님도 당근빳다죠");
		     tool.add(combo);
		     openbtn.addActionListener(new OpenActionListener());
		     save_otherbtn.addActionListener(new SaveAsActionListener());
		     savebtn.addActionListener(new SaveActionListener());
		     closebtn.addActionListener(new ActionListener(){
		            public void actionPerformed(ActionEvent arg0){
		                System.exit(0);//dispose는 창하나만 닫기이고 exit는 모든 창닫기인데 dispose같은 경우는jframe을 상속하애되서 일단 exit으로 함
		                }
		            });
		     return tool;
		}
	}


	public class OpenActionListener implements ActionListener {//열기 창만드는 코드
		private JFileChooser chooser;
		JSONParser parser = new JSONParser();
		public OpenActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {//json파일을 불러오는 코드
			FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
			chooser.setFileFilter(filter);
			int ret = chooser.showOpenDialog(null);
			String pathName = chooser.getSelectedFile().getPath();//Ŭ���Ѱ� �������
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			try {
				JSONParser parser = new JSONParser();
				//JSONObject jsonobject = (JSONObject) parser.parse(new FileReader(pathName));
				JSONArray array = (JSONArray) parser.parse(new FileReader(pathName));
				actionOpen(array);
			}
			catch (FileNotFoundException e3){
				e3.printStackTrace();
			} catch (IOException e3){
				e3.printStackTrace();
			} catch (ParseException e3){
				e3.printStackTrace();
			}
		}
		public void actionOpen(JSONArray array){//파일에서 불러온 내용 실행하기 - 근데 마우스로 클릭했을때 오른쪽에 안뜸
	            panel_Mid.removeAll();
	            panel_Mid.revalidate();
	            panel_Mid.repaint();
	            jLabel_nodes.clear();
	            node_for_Labels.clear();
	            System.out.println("array size : " + array.size());
	            String arr[] = new String[array.size()];
	            String temptext = "start";
	            for(int i = 1; i<array.size()-1; i++) {//�������� �ؽ�Ʈ �����̿��� -1
	            	arr[i] = "\n"+i;
	            	temptext = temptext.concat(arr[i]);
	            }
	            myDrawPanel.setText(temptext);
				myTree.getTextPanel(myDrawPanel.getText());

    			panel_Mid.repaint();
    			panel_Mid.removeAll();
				panel_Mid.revalidate();
				int j = 0;
	    			for(Node label : SplitPanel.node_for_Labels) {
	    				System.out.println("아 :"+label.getNodeData() );
	    				JSONObject obj = (JSONObject)array.get(j);
			    		int x = Integer.parseInt((String) obj.get("x"));
			    		int y = Integer.parseInt((String) obj.get("y"));
			   			int w = Integer.parseInt((String) obj.get("w"));
			   			int h = Integer.parseInt((String) obj.get("h"));
			   			String Node_Data = (String)obj.get("NodeData");
		    			// color = (Color)obj.get("Color");
		    			//Node LChil = (Node)obj.get("LChil");
		    			//Node Parent = (Node)obj.get("Parent");
		    			//Node RSbling = (Node)obj.get("RSbling");
	    				label.setNodex(x);
	    				label.setNodey(y);
	    				label.setNodewid(w);
	    				label.setNodehei(h);
	    				label.setData(Node_Data);
	    				//label.setmyLabel(MyLabel);
	    				//label.setNodeColor(color);
	    				//label.setLeftChild(LChil);
	    				//label.setParent(Parent);
	    				//label.setRightSibling(RSbling);
	    				/*if(j == 0) {
	    					myTree.root.setData(Node_Data);
	    					myTree.root.setNodex(x);
	    					myTree.root.setNodey(x);
	    					myTree.root.setNodehei(h);
	    					myTree.root.setNodewid(w);
	    					System.out.println("�ٲ��Ʈ x �� :"+myTree.root.getNodex());
		    				System.out.println("�ٲ��Ʈ ��� �̸� :"+myTree.root.getNodeData());
	    				}*/
	    				j++;
	    			}
	    			int num = array.size();//textarea���� �ֱ�
	    			JSONObject obj = (JSONObject)array.get(num-1);
	    			String paneltext = (String)obj.get("textarea");
	    			System.out.println(paneltext);
	    			myDrawPanel.setText(paneltext);
	    			
	    			draw_Tree2(myTree.root,0, panel_Mid, array);
	            }
	       }
	public void draw_Tree2(Node root, int i ,JPanel mid_panel, JSONArray array) {
		JSONObject obj = (JSONObject)array.get(i);
		JLabel label = new JLabel((String)obj.get("NodeData"));
		String [] colors = splitByLength((String)obj.get("Color"), 2);
		
		
		int x = Integer.parseInt((String) obj.get("x"));
		int y = Integer.parseInt((String) obj.get("y"));
		int w = Integer.parseInt((String) obj.get("w"));
		int h = Integer.parseInt((String) obj.get("h"));
		String Node_Data = (String)obj.get("NodeData");
		String MyLabel = (String)obj.get("MyLabel");

		label.setSize(w, h);/////////�̰Ƕ� ����????
		label.setLocation(x, y);
		label.setName(Node_Data);
		label.setOpaque(true);
		label.setBackground(new Color(Integer.parseInt(colors[0], 16), Integer.parseInt(colors[1], 16), Integer.parseInt(colors[2], 16)));

		label.setBorder(new LineBorder(new Color(82, 130, 184), 2));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_Mid.add(label);	//Label ���ε�ʿ� �߰�
		jLabel_nodes.add(label);
		node_for_Labels.add(root);
		root.setNodewid(w);//myTree.root.getNodewid());
		root.setNodehei(h);//myTree.root.getNodehei());
		root.setNodex(x);//myTree.root.getNodex());
		root.setNodey(y);//myTree.root.getNodey());
		root.setData(Node_Data);
		
		NodeMouseListener nodeMouse = new NodeMouseListener(label, mid_panel);
		label.addMouseListener(nodeMouse);
		label.addMouseMotionListener(nodeMouse);
       
		// �ڽ� ��尡 �����Ѵٸ�
	    if(root.getLeftChild() != null)
	    	draw_Tree2(root.getLeftChild(), i + 1, panel_Mid, array);
	         
	    // ���� ��尡 �����Ѵٸ�
	    if(root.getRightSibling() != null)
	    	draw_Tree2(root.getRightSibling(), i + 1, panel_Mid, array);
	    	
	   panel_Mid.repaint();
	}
	class SaveActionListener implements ActionListener{//save-메뉴바 만들기 예제로 c드라이브에 생성됨
		private JFileChooser chooser;
		public SaveActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			JSONArray list = new JSONArray();
			JSONObject jsonobject = new JSONObject();
			JSONObject paneltext = new JSONObject();
			paneltext.put("textarea", myDrawPanel.getText());//textarea내용 자체 저장코드
			     for(Node label : SplitPanel.node_for_Labels) {//라벨 정보들을 저장하는 코드
					JSONObject obj = new JSONObject();
				    String numStr1 = String.valueOf(label.getNodex());
			    	obj.put("x", numStr1);
			    	String numStr2 = String.valueOf(label.getNodey());
			    	obj.put("y", numStr2);
			    	String numStr3 = String.valueOf(label.getNodehei());
			    	obj.put("h", numStr3);
			    	String numStr4 = String.valueOf(label.getNodewid());
			    	obj.put("w", numStr4);
			    	//String numStr5 = String.valueOf(label.getIndex());
			    	//obj.put("index", numStr5);
			    	String numStr7 = String.valueOf(label.getLeftChild());
			    	obj.put("LChil", numStr7);
			    	//String numStr8 = String.valueOf(label.getmyLabel());
			    	//obj.put("MyLabel", numStr8);
			    	int red, green, blue;
			    	String redVal, greenVal, blueVal;
			    	red = label.getNodecolor().getRed();
			    	if(red < 16)
			    		redVal = String.valueOf(0) + Long.toHexString(red);
			    	else
			    		redVal = Long.toHexString(red);
			    	green = label.getNodecolor().getGreen();
			    	if(green < 16)
			    		greenVal = String.valueOf(0) + Long.toHexString(green);
			    	else
			    		greenVal = Long.toHexString(green);			    	
			    	blue = label.getNodecolor().getBlue();
			    	if(blue < 16)
			    		blueVal = String.valueOf(0) + Long.toHexString(blue);
			    	else
			    		blueVal = Long.toHexString(blue);
			    	String numStr9 = String.valueOf(redVal + greenVal + blueVal);
			    	obj.put("Color", numStr9);
			    	String numStr10 = String.valueOf(label.getNodeData());
			    	obj.put("NodeData", numStr10);
			    	String numStr11 = String.valueOf(label.getParent());
			    	obj.put("Parent", numStr11);
			    	String numStr12 = String.valueOf(label.getRightSibling());
			    	obj.put("RSbling", numStr12);
			    	list.add(obj);
			     }
			     	list.add(paneltext);
			    	jsonobject.put("MyLabel", list);
			try {
				FileWriter file = new FileWriter("C:\\Users\\There\\Desktop\\메뉴바 만들기 예제.json");
				file.write(list.toJSONString());
				file.flush();
				file.close();
		 
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}
	}
	
	
	class SaveAsActionListener implements ActionListener {//saveas 저장
		private JFileChooser chooser;
		public SaveAsActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			JFrame parentFrame = new JFrame();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
			chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Specify a file to save");   

			int userSelection = chooser.showSaveDialog(parentFrame);
			String pathName = chooser.getSelectedFile().getPath();//클릭한것 경로저장
			if (userSelection == chooser.APPROVE_OPTION) {
			    File fileToSave = chooser.getSelectedFile();
			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());//클락한 경로
			    JSONObject jsonobject = new JSONObject();
			    JSONArray list = new JSONArray();
			    JSONObject paneltext = new JSONObject();
				paneltext.put("textarea", myDrawPanel.getText());//textarea내용 자체 저장코드
			     for(Node label : SplitPanel.node_for_Labels) {//라벨 정보들을 저장하는 코드
					JSONObject obj = new JSONObject();
				    String numStr1 = String.valueOf(label.getNodex());
			    	obj.put("x", numStr1);
			    	String numStr2 = String.valueOf(label.getNodey());
			    	obj.put("y", numStr2);
			    	String numStr3 = String.valueOf(label.getNodehei());
			    	obj.put("h", numStr3);
			    	String numStr4 = String.valueOf(label.getNodewid());
			    	obj.put("w", numStr4);
			    	//String numStr5 = String.valueOf(label.getIndex());
			    	//obj.put("index", numStr5);
			    	String numStr7 = String.valueOf(label.getLeftChild());
			    	obj.put("LChil", numStr7);
			    	//String numStr8 = String.valueOf(label.getmyLabel());
			    	//obj.put("MyLabel", numStr8);
			    	int red, green, blue;
			    	String redVal, greenVal, blueVal;
			    	red = label.getNodecolor().getRed();
			    	if(red < 16)
			    		redVal = String.valueOf(0) + Long.toHexString(red);
			    	else
			    		redVal = Long.toHexString(red);
			    	green = label.getNodecolor().getGreen();
			    	if(green < 16)
			    		greenVal = String.valueOf(0) + Long.toHexString(green);
			    	else
			    		greenVal = Long.toHexString(green);			    	
			    	blue = label.getNodecolor().getBlue();
			    	if(blue < 16)
			    		blueVal = String.valueOf(0) + Long.toHexString(blue);
			    	else
			    		blueVal = Long.toHexString(blue);
			    	String numStr9 = String.valueOf(redVal + greenVal + blueVal);
			    	obj.put("Color", numStr9);
			    	String numStr10 = String.valueOf(label.getNodeData());
			    	obj.put("NodeData", numStr10);
			    	String numStr11 = String.valueOf(label.getParent());
			    	obj.put("Parent", numStr11);
			    	String numStr12 = String.valueOf(label.getRightSibling());
			    	obj.put("RSbling", numStr12);
			    	list.add(obj);
			     }
			     	list.add(paneltext);
			    	jsonobject.put("MyLabel", list);

		     try {
		      FileWriter file = new FileWriter(fileToSave+".json");//이름 저장
		      	file.write(list.toJSONString());
		   		file.flush();
		  		file.close();
		  		System.out.println(jsonobject);
			}catch (IOException e2) {
		   		e2.printStackTrace();
		   	}
			 
		}
	}
	}
	class CloseActionListener extends JFrame implements ActionListener{
		private JFileChooser chooser;
		public CloseActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent arg0) {
			dispose();
    	}
	}
}
