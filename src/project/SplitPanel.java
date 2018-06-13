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

import jdk.jfr.Unsigned;

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
	boolean isNotFirst = false;
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
		root.setmyLabel(label);
		root.setIndex(i);
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
					myNode.myLabel.setBackground(color);
				}
			}
		}
		
	}
	
	public void relocating_tree(Node this_node) {
		Node now_Node = this_node;
		Node temp_Node = null;
		int sibling_count = 0;
		double distance = 150;
		
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

				for(int i = 0; i < sibling_count; i++) {
					double radius = 2 * Math.PI / sibling_count;
					System.out.println(distance * (int)Math.sin(radius * i) + " "+  distance * (int)Math.cos(radius * i));
					//System.out.println(i + "번째 지금은 " + temp_Node.getNodeData() + " " +now_Node.getNodex() + " " + now_Node.getNodey());
					temp_Node.setNodex(now_Node.getNodex() + (int)(distance*Math.sin(radius * i)));
					temp_Node.setNodey(now_Node.getNodey() + (int)(distance*Math.cos(radius * i)));
					jLabel_nodes.get(node_for_Labels.indexOf(temp_Node)).setLocation(temp_Node.getNodex(), temp_Node.getNodey());
					temp_Node = temp_Node.getRightSibling();
				}
				temp_Node = now_Node.getLeftChild();
			
				/////여기까지는 루트의 자식들 다 출력
				if(distance-20 > 0)
					distance = distance - 20;
				sibling_count = 0;
				now_Node = now_Node.getLeftChild();
			}
		
			temp_Node = now_Node;
			while(true) {				
				System.out.println(temp_Node.getNodeData());
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
	
	class NodeMouseListener implements MouseListener, MouseMotionListener {
		JLabel label;
		JPanel mid_panel;
		Node myNode;
		int before_x, before_y;
		boolean isMoved = false;
		 
		public NodeMouseListener(JLabel label, JPanel panel) {
			this.label = label;
			mid_panel = panel;
			int i = jLabel_nodes.indexOf(label);
			myNode = node_for_Labels.get(i);
		}
		 
		public void mouseDragged(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int label_x = label.getX();
			int label_y = label.getY();
			
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
			
			isDragged = true;
		}

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int node_wid = myNode.getNodewid();
			int node_hei = myNode.getNodehei();
			
			if(isReversed) {
				if((x >= 0 && x <= 10) || (y >= 0 && y <= 10)) {
					label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
				else {
					label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}
			else {
				
			}
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
			painting_resize_box();
			
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
			myNode.setNodex(label_x + (now_x - before_x));
			myNode.setNodey(label_y + (now_y - before_y));
			node_xfield.setText(Integer.toString(label_x + (now_x - before_x)));			
			node_yfield.setText(Integer.toString(label_y + (now_y - before_y)));
			
			label.setLocation(label_x + (now_x - before_x), label_y + (now_y - before_y));
			
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
						locating_resize_label(label_x + (now_x - before_x), label_y + (now_y - before_y));
						isReversed = true;
					}
				}
				else {
					if(isDragged == false) {
						isReversed = true;
					}
					else {
						removing_now_resize_label();
						locating_resize_label(label_x + (now_x - before_x), label_y + (now_y - before_y));
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
						locating_resize_label(label_x + (now_x - before_x), label_y + (now_y - before_y));
						isReversed = true;
					}
				}
				else {
					if(isDragged == false) {
						isReversed = true;
					}
					else {
						removing_now_resize_label();
						locating_resize_label(label_x + (now_x - before_x), label_y + (now_y - before_y));
						isReversed = true;
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
	
	public void painting_resize_box() {	//클래스 내 함수임 MouseListener의 함수가 아님
		Graphics g = panel_Mid.getGraphics();
		panel_Mid.paint(g);
		g.drawRect(50, 50, 6, 6);
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
			if(isNotFirst) {
				panel_Mid.removeAll();
				panel_Mid.revalidate();
				panel_Mid.repaint();
			}
			jLabel_nodes.clear();
			node_for_Labels.clear();
			myTree.getTextPanel(myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
			draw_Tree(myTree.root, 0, panel_Mid);
			recoloring_tree();
			relocating_tree(myTree.root);
			panel_Mid.repaint();
			before_click_label = null;
			isNotFirst = true;
		}
	};
	
	ActionListener ChangeNodeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(before_click_label != null && isReversed == true) {
				Node myNode = node_for_Labels.get(jLabel_nodes.indexOf(before_click_label));
				String [] colors = splitByLength(node_colorfield.getText(), 2);
				int x = Integer.parseInt(node_xfield.getText()); 
				int y = Integer.parseInt(node_yfield.getText());
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

	        JMenu_Open.addActionListener(new OpenActionListener());
	        JMenu_SaveAs.addActionListener(new SaveAsActionListener());
	        JMenu_Save.addActionListener(new SaveActionListener());
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
		     combo.addItem("ㅗ");
		     combo.addItem("ㅗㅗ");
		     combo.addItem("ㅗㅗㅗ");
		     tool.add(combo);
		     
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
			String pathName = chooser.getSelectedFile().getPath();//클릭한것 경로저장
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			try {
				JSONParser parser = new JSONParser(); 
				JSONArray array = (JSONArray) parser.parse(new FileReader(pathName));
				actionOpen(array);
			}
			catch (FileNotFoundException e3) {
				e3.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			} catch (ParseException e3) {
				e3.printStackTrace();
			}
		}
		
		public void actionOpen(JSONArray array){//파일에서 불러온 내용 실행하기 - 근데 마우스로 클릭했을때 오른쪽에 안뜸
			if(isNotFirst) {
	               panel_Mid.removeAll();
	               panel_Mid.revalidate();
	               panel_Mid.repaint();
	            }
	            jLabel_nodes.clear();
	            node_for_Labels.clear();
	            Node root= null;
			for(int i = 0; i<array.size(); i++) {
				JSONObject obj = (JSONObject)array.get(i);
		    	Node new_node = new Node((String)obj.get("NodeData"));
				/*new_node.setmyLabel((JLabel)obj.get("MyData"));//데이터 넣는 코드 자꾸 널포인터 에러뜸
				new_node.setIndex((int)obj.get("intdex"));
				new_node.setNodex((int)obj.get("x"));
				new_node.setNodey((int)obj.get("y"));
				new_node.setNodewid((int)obj.get("w"));
				new_node.setNodehei((int)obj.get("h"));
				new_node.setNodeColor((Color)obj.get("Color"));
				new_node.setParent((Node)obj.get("parent"));
				new_node.setLeftChild((Node)obj.get("LChil"));
				new_node.setRightSibling((Node)obj.get("RSbling"));*/
				System.out.println((String)obj.get("NodeData"));
				System.out.println((JLabel)obj.get("MyData"));
				System.out.println((int)obj.get("intdex"));
				System.out.println((int)obj.get("x"));
				System.out.println((int)obj.get("y"));
				System.out.println((int)obj.get("h"));
				System.out.println((int)obj.get("w"));
				System.out.println((Color)obj.get("Color"));
				System.out.println(((Node)obj.get("parent")));
				System.out.println((Node)obj.get("LChil"));
				System.out.println((Node)obj.get("RSbling"));

				if(i ==0) myTree.root = new_node;
				}
			draw_tree2(root, 0, panel_Mid);
		}
		public void draw_tree2(Node root, int i, JPanel mid_panel) { 
			JLabel label = new JLabel(root.getNodeData());
			//int check = 0, node_x = i*60, node_y = i*60, node_wid = 60, node_hei = 40;
			Node check_node = root;
			//label.setSize(node_wid, node_hei);
			label.setOpaque(true);
		
			int random_r, random_g, random_b;
			random_r = (int)(Math.random() * 256);
			random_g = (int)(Math.random() * 256);
			random_b = (int)(Math.random() * 256);
			Color random_color = new Color(random_r, random_g, random_b);
			label.setBackground(random_color);
			//root.setNodeColor(random_color);
			label.setBorder(new LineBorder(new Color(82, 130, 184), 2));
			label.setLocation(root.getNodex(), root.getNodey());
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panel_Mid.add(label);	//Label 마인드맵에 추가
			jLabel_nodes.add(label);	
			node_for_Labels.add(root);
				/*root.setmyLabel(label);
				root.setIndex(i);
				root.setNodex(node_x);
				root.setNodey(node_y);
				root.setNodewid(node_wid);
				root.setNodehei(node_hei);
				 */
			NodeMouseListener nodeMouse = new NodeMouseListener(label, panel_Mid);
			label.addMouseListener(nodeMouse);
       
				// 자식 노드가 존재한다면
			if(root.getLeftChild() != null)
				draw_Tree(root.getLeftChild(), i + 1, panel_Mid);
	         
				// 형제 노드가 존재한다면
			if(root.getRightSibling() != null)
				draw_Tree(root.getRightSibling(), i + 1, panel_Mid);
			panel_Mid.repaint();
		}
    }

	class SaveActionListener implements ActionListener{//save-메뉴바 만들기 예제로 c드라이브에 생성됨
		private JFileChooser chooser;
		public SaveActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			 JSONArray list = new JSONArray();
			    //obj.put("textarea", SplitPanel.myDrawPanel.getText());//textarea내용 자체 저장코드
			    int i = 0;
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
				    	String numStr5 = String.valueOf(label.getIndex());
				    	obj.put("index", numStr5);
				    	String numStr7 = String.valueOf(label.getLeftChild());
				    	obj.put("LChil", numStr7);
				    	String numStr8 = String.valueOf(label.getmyLabel());
				    	obj.put("MyLabel", numStr8);
				    	String numStr9 = String.valueOf(label.getNodecolor());
				    	obj.put("Color", numStr9);
				    	String numStr10 = String.valueOf(label.getNodeData());
				    	obj.put("NodeData", numStr10);
				    	String numStr11 = String.valueOf(label.getParent());
				    	obj.put("Parent", numStr11);
				    	String numStr12 = String.valueOf(label.getRightSibling());
				    	obj.put("RSbling", numStr12);
				    	list.add(obj);
			      }
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
			    JSONArray list = new JSONArray();
			    //obj.put("textarea", SplitPanel.myDrawPanel.getText());//textarea내용 자체 저장코드
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
			    	String numStr5 = String.valueOf(label.getIndex());
			    	obj.put("index", numStr5);
			    	String numStr7 = String.valueOf(label.getLeftChild());
			    	obj.put("LChil", numStr7);
			    	String numStr8 = String.valueOf(label.getmyLabel());
			    	obj.put("MyLabel", numStr8);
			    	String numStr9 = String.valueOf(label.getNodecolor());
			    	obj.put("Color", numStr9);
			    	String numStr10 = String.valueOf(label.getNodeData());
			    	obj.put("NodeData", numStr10);
			    	String numStr11 = String.valueOf(label.getParent());
			    	obj.put("Parent", numStr11);
			    	String numStr12 = String.valueOf(label.getRightSibling());
			    	obj.put("RSbling", numStr12);
			    	list.add(obj+"i");
			      }
		    /*
		    for(JLabel label : SplitPanel.jLabel_nodes) {
		    	String numStr13 = String.valueOf(label.);
		    	obj.add();
		    	String numStr13 = String.valueOf(label.);
		    }*/
		    //String what = SplitPanel.myDrawPanel.getText();
		     try {
		      FileWriter file = new FileWriter(fileToSave+".json");//이름 저장
		      	file.write(list.toJSONString());
		   		file.flush();
		  		file.close();
		  		System.out.println(list);
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
