package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;

public class SplitPanel {
	JPanel panel_Mid;	//��� ���ε���г� ����
	JPanel panel_Left_Background;	//textarea �� ����butoon�� �������ִ� �г�
	JTextArea myDrawPanel;	//textarea ����
	JPanel panel_Right;	//������ �Ӽ� �г� ����
	JPanel panel_Background;
	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	
	public SplitPanel() {
        split.setDividerLocation( 300 );
        split2.setDividerLocation( 800 );
        /////////////////��� ���ε�� �г�
        panel_Mid = new JPanel();
        panel_Mid.setBackground(new Color(163, 202, 241));
        panel_Mid.setLayout(null);
        split2.setBackground(new Color(100, 100, 100));
        
        /////////////////////////��� �ǳڿ� �ִ°� �������� �ڵ�
        
        /*for(int i = 0; i < 10 ; i++) {
			JLabel label = new JLabel(Integer.toString(i));
			label.setSize(20, 10);
			label.setLocation(i*15, i*15);
			label.setSize(10,10);
			label.setBackground(new Color(100, 100, 100));
			panel_Mid.add(label);
        }*/
        
        
        /////////////////���� �ؽ�Ʈ �г�+�� ��� �г�
        panel_Left_Background = new JPanel();
        myDrawPanel = new JTextArea(30,25);
        JButton textbutton = new JButton("����");       
		
		panel_Left_Background.setBackground(new Color(255, 255, 255));
		panel_Left_Background.setLayout(new BorderLayout(5,5));
		panel_Left_Background.add( new JScrollPane( myDrawPanel ), BorderLayout.CENTER );
		panel_Left_Background.add( textbutton, BorderLayout.SOUTH );
		
		ActionListener TextPanelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tree myTree = new Tree();
				myTree.getTextPanel(myDrawPanel.getText());	//tree�� textPanel���� �Ѱ��ֱ�
				draw_Tree(myTree.root, 0, panel_Mid);
				panel_Mid.repaint();
			}
		};
	    textbutton.addActionListener(TextPanelActionListener);	//Action ������ �ޱ�

				
        ////////////////������ ���� �г�
        panel_Right = new JPanel();
        panel_Right.setBackground(new Color(174, 246, 160)); 
        panel_Right.setPreferredSize( new Dimension( 350, 350 ) );
        panel_Right.setSize(200,600);
    	GridLayout gridAttPane = new GridLayout(7,2,0,30);
    	panel_Right.add( new JLabel( "EAST Panel!" ) );
    	panel_Right.add( new JLabel( "" ) );
		panel_Right.setLayout(gridAttPane);
		panel_Right.add(new JLabel(" TEXT: "));
		panel_Right.add(new JTextField(""));
		panel_Right.add(new JLabel(" X: "));
		panel_Right.add(new JTextField(""));
		panel_Right.add(new JLabel(" Y: "));
		panel_Right.add(new JTextField(""));
		panel_Right.add(new JLabel(" W: "));
		panel_Right.add(new JTextField(""));
		panel_Right.add(new JLabel(" H: "));
		panel_Right.add(new JTextField(""));
		panel_Right.add(new JLabel(" COLOR: "));
		panel_Right.add(new JTextField(""));
		
		
		//Ʋ�� ���ø� �߰�
		split.setLeftComponent(panel_Left_Background);
		split.setRightComponent(split2);
		//���ø� �ϳ����� �̵�� ����Ʈ �߰�
		split2.setLeftComponent(panel_Mid);		
		split2.setRightComponent(panel_Right);
		
		
	}
	
	public JSplitPane splitpane_create() {        
        return split;
	}
	
	public void draw_Tree(Node root, int i, JPanel mid_panel) {
		JLabel label = new JLabel(root.getNodeData());
		NodeMouseListener nodeMouse = new NodeMouseListener(label, mid_panel);
		label.addMouseListener(nodeMouse);
		label.setSize(60, 40);
		label.setOpaque(true);
		label.setBorder(new LineBorder(new Color(82, 130, 184), 3, true));
		label.setLocation(i*60, i*60);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(new Color(239, 187, 77));
		
		panel_Mid.add(label);
       
		// �ڽ� ��尡 �����Ѵٸ�
	    if(root.getLeftChild() != null)
	    	draw_Tree(root.getLeftChild(), i + 1, panel_Mid);
	         
	    // ���� ��尡 �����Ѵٸ�
	    if(root.getRightSibling() != null)
	    	draw_Tree(root.getRightSibling(), i + 1, panel_Mid);
	    	
	   panel_Mid.repaint();
	}
	
	 class NodeMouseListener implements MouseListener, MouseMotionListener {
		 JLabel label;
		 JPanel mid_panel;
		 
		 public NodeMouseListener(JLabel label, JPanel panel) {
			 this.label = label;
			 mid_panel = panel;
			 System.out.println("�߰�����");
		 }
		 
		 public void mouseDragged(MouseEvent e) {
			 int x = e.getX();
			 int y = e.getY();
			 System.out.println("x: "+ x + " y: " + y);
			 label.setLocation(x, y);
			 mid_panel.repaint();
		 }

		public void mouseMoved(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			 int x = e.getX();
			 int y = e.getY();
			 int label_x = label.getX();
			 int label_y = label.getY();
			 System.out.println("���콺 �� x: "+ x + " y: " + y);
			 label.setLocation(label_x + x - label.getWidth()/2, label_y + y - label.getHeight()/2);
			 mid_panel.repaint();
		}
	 }
}
