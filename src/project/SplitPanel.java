package project;

import java.awt.*;

import java.awt.List;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SplitPanel {
	MenuBar menubar = new MenuBar();	
	ToolBar toolbar = new ToolBar();
	public static JPanel panel_Mid;	//가운데 마인드맵패널 정의
	JPanel panel_Left_Background;	//textarea 와 적용butoon을 가지고있는 패널
	JPanel panel_Right;	//오른쪽 속성 패널 정의
	JPanel panel_Background;
	//JLabel jLabel_nodes[] = new JLabel[100];	//JLabel이랑 Node랑 이어주는거
	public static ArrayList<JLabel> jLabel_nodes = new ArrayList<JLabel>();
	//Node node_for_Labels[] = new Node[100];
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
					panel_Mid.removeAll();
					panel_Mid.revalidate();
					panel_Mid.repaint();
				}
				jLabel_nodes.clear();
				node_for_Labels.clear();
				myTree.getTextPanel(myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
				draw_Tree(myTree.root, 0, panel_Mid);
				recoloring_tree();
				relocating_tree();
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
		System.out.println(myDrawPanel.getWidth() - panel_Right.getWidth());
		jLabel_nodes.get(0).setLocation(panel_Mid.getWidth()/ 2 - jLabel_nodes.get(0).getWidth()/2, panel_Mid.getHeight()/2 - jLabel_nodes.get(0).getHeight()/2);
		
		for(int i = 0; i < jLabel_nodes.size(); i++) {
			
		}
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
			System.out.println("강승모 븅신");
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
			before_x = e.getX();
			before_y = e.getY();
			node_textfield.setText(myNode.getNodeData());
			node_xfield.setText(Integer.toString(myNode.getNodex()));
			node_yfield.setText(Integer.toString(myNode.getNodey()));
			node_widfield.setText(Integer.toString(myNode.getNodewid()));
			node_heifield.setText(Integer.toString(myNode.getNodehei()));
			node_colorfield.setText(Long.toHexString(myNode.getNodecolor().getRed()) + 
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
				String filePath = chooser.getSelectedFile().getPath();
				try {
					Object obj = parser.parse(new FileReader(pathName));
					JSONObject jsonObject = (JSONObject) obj;
					String textarea = (String) jsonObject.get("textarea");//textarea글자로 변환
					actionOpen actionopen = new actionOpen();
					actionopen.actionOpen(textarea);
				}
				catch (FileNotFoundException e3) {
					e3.printStackTrace();
				} catch (IOException e3) {
					e3.printStackTrace();
				} catch (ParseException e3) {
					e3.printStackTrace();
				}
			}
		}
		public class actionOpen{//파일에서 불러온 내용 실행하기 - 근데 마우스로 클릭했을때 오른쪽에 안뜸
			actionOpen(){}
			public void actionOpen(String textarea){
				for(JLabel label : jLabel_nodes) {
					label.setText("");
					label.setOpaque(false);
					label.setSize(0, 0);
				}
				myDrawPanel.setText(textarea);
				myDrawPanel.revalidate();
				myDrawPanel.repaint();
				jLabel_nodes.clear();
				node_for_Labels.clear();
				myTree.getTextPanel(SplitPanel.myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
				draw_Tree(myTree.root, 0, panel_Mid);
				recoloring_tree();
				relocating_tree();
				panel_Mid.repaint();
				
				isNotFirst = true;
			}
		}
		
	/*	class OpenActionListener implements ActionListener {//현재 만들어져있는 노드들의 정보를 받아다가 폴더 열기 코드
			private JFileChooser chooser;
			JSONParser parser = new JSONParser();
			public OpenActionListener() {
				chooser = new JFileChooser();
			}
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String filePath = chooser.getSelectedFile().getPath();
				pack(); // 이미지의 크기에 맞추어 프레임 크기 조절
				try {
					Object obj = parser.parse(new FileReader("c:\\test2.json"));
			 
					JSONObject jsonObject = (JSONObject) obj;
					
					String textarea = (String) jsonObject.get("textarea");
					actionOpen actionopen = new actionOpen();
					actionopen.actionOpen(textarea);
				}
					
					
				catch (FileNotFoundException e3) {
					e3.printStackTrace();
				} catch (IOException e3) {
					e3.printStackTrace();
				} catch (ParseException e3) {
					e3.printStackTrace();
			}
			
		}
	}*/
		class SaveActionListener implements ActionListener{//save-메뉴바 만들기 예제로 c드라이브에 생성됨
			private JFileChooser chooser;
			public SaveActionListener() {
				chooser = new JFileChooser();
			}
			public void actionPerformed(ActionEvent e) {
				 JSONObject obj = new JSONObject();
				    JSONArray list = new JSONArray();
				    obj.put("textarea", SplitPanel.myDrawPanel.getText());//textarea내용 자체 저장코드
				try {
					FileWriter file = new FileWriter("C:\\Users\\There\\Desktop\\메뉴바 만들기 예제.json");
					file.write(obj.toJSONString());
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
				    JSONObject obj = new JSONObject();
				    JSONArray list = new JSONArray();
				    obj.put("textarea", SplitPanel.myDrawPanel.getText());//textarea내용 자체 저장코드
				    try {
				    	FileWriter file = new FileWriter(fileToSave+".json");//이름 저장
				    	file.write(obj.toJSONString());
				   		file.flush();
				  		file.close();
				    	  
				    } catch (IOException e2) {
				   		e2.printStackTrace();
				   	}
				}			
			    /*for(Node label : SplitPanel.node_for_Labels) {//라벨 정보들을 저장하는 코드
			    	String numStr1 = String.valueOf(label.getNodex());
			    	obj.put(label+"x", numStr1);
			    	String numStr2 = String.valueOf(label.getNodey());
			    	obj.put(label+"y", numStr2);
			    	String numStr3 = String.valueOf(label.getNodehei());
			    	obj.put(label+"h", numStr3);
			    	String numStr4 = String.valueOf(label.getNodewid());
			    	obj.put(label+"w", numStr4);
			    	String numStr5 = String.valueOf(label.getIndex());
			    	obj.put(label+"index", numStr5);
			    	String numStr7 = String.valueOf(label.getLeftChild());
			    	obj.put(label+"LChil", numStr7);
			    	String numStr8 = String.valueOf(label.getmyLabel());
			    	obj.put(label+"MyLabel", numStr8);
			    	String numStr9 = String.valueOf(label.getNodecolor());
			    	obj.put(label+"Color", numStr9);
			    	String numStr10 = String.valueOf(label.getNodeData());
			    	obj.put(label+"NodeData", numStr10);
			    	String numStr11 = String.valueOf(label.getParent());
			    	obj.put(label+"Parent", numStr11);
			    	String numStr12 = String.valueOf(label.getRightSibling());
			    	obj.put(label+"RSbling", numStr12);
			    	
			    }*/
			    /*
			    for(JLabel label : SplitPanel.jLabel_nodes) {
			    	String numStr13 = String.valueOf(label.);
			    	obj.add();
			    	String numStr13 = String.valueOf(label.);
			    }*/
			    //String what = SplitPanel.myDrawPanel.getText();	   
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
		     applybtn.setToolTipText("텍스트 편집 내용을 마인드 맵에 적용합니다");
		     tool.addSeparator();
		     JButton changebtn = new JButton("변경");
		     changebtn.setToolTipText("속성 변경 내용을 마인드 맵에 적용합니다");
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



}
