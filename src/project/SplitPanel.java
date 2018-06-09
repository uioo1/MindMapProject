package project;

import java.awt.*;
import java.awt.List;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class SplitPanel {
	JPanel panel_Mid;	//가운데 마인드맵패널 정의
	JPanel panel_Left_Background;	//textarea 와 적용butoon을 가지고있는 패널
	JPanel panel_Right;	//오른쪽 속성 패널 정의
	JPanel panel_Background;
	//JLabel jLabel_nodes[] = new JLabel[100];	//JLabel이랑 Node랑 이어주는거
	public static ArrayList<JLabel> jLabel_nodes = new ArrayList<JLabel>();
	//Node node_for_Labels[] = new Node[100];
	public static ArrayList<Node> node_for_Labels = new ArrayList<Node>();
	JTextArea myDrawPanel;	//textarea 정의
	JTextField node_textfield = new JTextField();
	JTextField node_xfield = new JTextField();
	JTextField node_yfield = new JTextField();
	JTextField node_widfield = new JTextField();
	JTextField node_heifield = new JTextField();
	JTextField node_colorfield = new JTextField();

	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	Tree myTree = new Tree();
	boolean isNotFirst = false;
	
	public SplitPanel() {
        split.setDividerLocation( 300 );
        split2.setDividerLocation( 800 );
        
        /////////////////가운데 마인드맵 패널
        
        panel_Mid = new JPanel();
        panel_Mid.setBackground(new Color(163, 202, 241));
        panel_Mid.setLayout(null);
        split2.setBackground(new Color(100, 100, 100));
        
        
        /////////////////왼쪽 텍스트 패널+다 담는 패널
        panel_Left_Background = new JPanel();
        myDrawPanel = new JTextArea(30,25);
        JButton textbutton = new JButton("적용");       
		
		panel_Left_Background.setBackground(new Color(255, 255, 255));
		panel_Left_Background.setLayout(new BorderLayout(5,5));
		panel_Left_Background.add( new JScrollPane( myDrawPanel ), BorderLayout.CENTER );
		panel_Left_Background.add( textbutton, BorderLayout.SOUTH );
		
		ActionListener TextPanelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myTree = new Tree();
				if(isNotFirst) {				
					for(JLabel label : jLabel_nodes) {
						label.setText("");
						label.setOpaque(false);
						label.setSize(0, 0);
					}
					myDrawPanel.revalidate();
					myDrawPanel.repaint();
				}
				jLabel_nodes.clear();
				node_for_Labels.clear();
				myTree.getTextPanel(myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
				draw_Tree(myTree.root, 0, panel_Mid);
				recoloring_tree();
				panel_Mid.repaint();
				
				isNotFirst = true;
			}
		};
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
		//panel_Right.add(node_colorfield);
		panel_Right.setLayout(gridAttPane);
		
		/*ActionListener AttrButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myTree.getTextPanel(myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
				make_JLabelArray(myTree.node_count);
				draw_Tree(myTree.root, 0, panel_Mid);
				panel_Mid.repaint();
			}
		};
	    textbutton.addActionListener(AttrButtonActionListener);	//Action 리스*///만드는중
		
		
		//틀에 스플릿 추가
		split.setLeftComponent(panel_Left_Background);
		split.setRightComponent(split2);
		//스플릿 하나위에 미드와 라이트 추가
		split2.setLeftComponent(panel_Mid);		
		split2.setRightComponent(panel_Right);
		
		
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
	
	public void relocating_tree() {
		Node temp_Node, myNode;
		int before_count = 0, count;
	}
	
	class NodeMouseListener implements MouseListener, MouseMotionListener {
		JLabel label;
		JPanel mid_panel;
		Node myNode;
		int before_x, before_y;
		 
		public NodeMouseListener(JLabel label, JPanel panel) {
			this.label = label;
			mid_panel = panel;
			int i = jLabel_nodes.indexOf(label);
			myNode = node_for_Labels.get(i);
		}
		 
		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
			int now_x = e.getX();
			int now_y = e.getY();
			System.out.println("마우스 무빙 x: "+ now_x + " y: " + now_y);
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			before_x = e.getX();
			before_y = e.getY();
			node_textfield.setText(myNode.getNodeData());
			node_xfield.setText(Integer.toString(myNode.getNodex()));
			node_yfield.setText(Integer.toString(myNode.getNodey()));
			node_widfield.setText(Integer.toString(myNode.getNodewid()));
			node_heifield.setText(Integer.toString(myNode.getNodehei()));
			node_colorfield.setText("0X" + Long.toHexString(myNode.getNodecolor().getRed()) + 
					Long.toHexString(myNode.getNodecolor().getGreen()) + 
					Long.toHexString(myNode.getNodecolor().getBlue()));
		}

		public void mouseReleased(MouseEvent e) {
			int now_x = e.getX();
			int now_y = e.getY();
			int label_x = label.getX();
			myNode.setNodex(label_x);
			node_xfield.setText(Integer.toString(myNode.getNodex()));
			
			int label_y = label.getY();
			myNode.setNodey(label_y);
			node_yfield.setText(Integer.toString(myNode.getNodey()));
			
			label.setLocation(label_x + (now_x - before_x), label_y + (now_y - before_y));
			mid_panel.repaint();
		}
		
		public ArrayList getJLabelList() {
			return jLabel_nodes;
		}
		
		public ArrayList getNodeList() {
			return node_for_Labels;
		}
		
	}
}
