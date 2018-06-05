package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ContentPaneEx extends JFrame{
	JFrame myframe;
	
	
	public ContentPaneEx() {
		myframe = new JFrame();
		this.setTitle("메뉴바 만들기 예제");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addJMenu();
	   // panel();
		toolBar();
		split();
		this.setBackground(Color.darkGray);
		this.setLocationRelativeTo(null);
	    this.setSize(800, 500);
	    this.setVisible(true);
	}
	
	void addJMenu() {
	        JMenuBar menuBar = new JMenuBar();
	        JMenu f = new JMenu("File");
	        JMenu e = new JMenu("edit");
	        
	        f.add(new JMenuItem("New"));
	        f.add(new JMenuItem("Open"));
	        f.addSeparator();//분리선 삽입
	        f.add(new JMenuItem("Save"));
	        f.add(new JMenuItem("SaveAs"));
	        
	        e.add(new JMenuItem("close"));
	        e.add(new JMenuItem("apply"));
	        e.add(new JMenuItem("change"));
	        
	        menuBar.add(f);
			menuBar.add(e);
			this.setJMenuBar(menuBar);
	}
	
	void toolBar() {
		  JToolBar tool = new JToolBar("Kitae Menu");
	        tool.setBackground(Color.gray);
	        tool.add(new JButton("새로 만들기"));
	        tool.addSeparator();
	        tool.add(new JButton("열기"));
	        tool.addSeparator();
	        tool.add(new JButton("저장"));
	       /* JButton btn3=new JButton(new ImageIcon("save.jpg"));//저장 이미지인데 내꺼에 이미지가 없는지 안나타남
	        tool.add(btn3);*/
	        tool.addSeparator();
	        tool.add(new JButton("다른 이름으로 저장"));
	        tool.addSeparator();
	        tool.add(new JButton("닫기"));
	        tool.addSeparator();
	        tool.add(new JButton("적용"));
	        tool.addSeparator();
	        tool.add(new JButton("변경"));
	        tool.addSeparator();
	       // tool.add(new JTextField("text field"));
	        JComboBox combo=new JComboBox();
	        combo.addItem("click");
	        combo.addItem("ㅗ");
	        combo.addItem("ㅗㅗ");
	        combo.addItem("ㅗㅗㅗ");
	        tool.add(combo);
	        
	        this.add(tool, BorderLayout.NORTH);
	        this.setLocationRelativeTo(null);
	}
	
	void panel() {
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		JTextArea txtLog = new JTextArea();
		JPanel panelA = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel2_1 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel3_1 = new JPanel();
		
		panel2.setBackground(Color.BLUE);
		panel2_1.setBackground(Color.BLUE); 
		panel3.setBackground(Color.GREEN);
		
		panel3_1.setBackground(Color.GREEN);
		panel3_1.add(new JTextField("Attribute Pane"), BorderLayout.CENTER);
		panel3.add(panel3_1, BorderLayout.NORTH);
		GridLayout gridAttPane = new GridLayout(7,2,0,30);
		panel3.setLayout(gridAttPane);
		panel3.add(new JLabel(" TEXT: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" X: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" Y: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" W: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" H: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" COLOR: "));
		panel3.add(new JTextField(""));
		
		
		
		GridLayout gridback = new GridLayout(1,4,0,0);
		panelA.setLayout(gridback);
		panelA.add(txtLog);
		panelA.add(panel2);
		panelA.add(panel2_1);
		panelA.add(panel3);
		
		this.add(panelA);
	}
	void split(){
        JSplitPane split = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        split.setDividerLocation( 200 );
        this.add( split );

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.BLUE);
        panel1.setLayout( new BorderLayout() );
        panel1.add( new JLabel( "CENTER panel" ), BorderLayout.CENTER );

		JTextArea myDrawPanel = new JTextArea();
        myDrawPanel.setPreferredSize( new Dimension( 200, 200 ) );
        myDrawPanel.add( new JLabel( "WEST panel!" ) );
        panel1.add( new JScrollPane( myDrawPanel ), BorderLayout.WEST );

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GREEN); 
        panel3.setPreferredSize( new Dimension( 200, 200 ) );
    	GridLayout gridAttPane = new GridLayout(7,2,0,30);
    	panel3.add( new JLabel( "EAST Panel!" ) );
    	panel3.add( new JLabel( "" ) );
		panel3.setLayout(gridAttPane);
		panel3.add(new JLabel(" TEXT: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" X: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" Y: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" W: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" H: "));
		panel3.add(new JTextField(""));
		panel3.add(new JLabel(" COLOR: "));
		panel3.add(new JTextField(""));
		panel1.add( new JScrollPane( panel3 ), BorderLayout.EAST );
        
        split.setTopComponent( panel1 );

        setVisible( true );

	}
}
