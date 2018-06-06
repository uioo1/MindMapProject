package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplitPanel {
	JSplitPane split = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
	JPanel panel1;	//��� ���ε���г� ����
	JPanel panel2_1;	//��� ��� ���� ū �г� ����
	JTextArea myDrawPanel;	//��� ���ε���г� ����
	JPanel panel3;	//������ �Ӽ� �г� ����
	
	public SplitPanel() {
        split.setDividerLocation( 200 );
        
        /////////////////��� ���ε�� �г�
        panel1 = new JPanel();
        panel1.setBackground(new Color(163, 202, 241));
        panel1.setLayout( new BorderLayout() );
        panel1.add( new JLabel( "CENTER panel" ), BorderLayout.CENTER );
        //panel1.add( new JLabel( "����" ), BorderLayout.SOUTH);        

        /////////////////���� �ؽ�Ʈ �г�+ �� ��� �г�
        panel2_1 = new JPanel();
        myDrawPanel = new JTextArea(30,25);
        JButton textbutton = new JButton("����");       
		
		panel2_1.setBackground(new Color(255, 255, 255));
		panel2_1.setLayout(new BorderLayout(5,5));
		panel2_1.add( new JScrollPane( myDrawPanel ), BorderLayout.CENTER );
		panel2_1.add( textbutton, BorderLayout.SOUTH );
		panel1.add(panel2_1, BorderLayout.WEST);
		
		ActionListener TextPanelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Tree().getTextPanel(myDrawPanel.getText());	//tree�� textPanel���� �Ѱ��ֱ�
			}
		};
	    textbutton.addActionListener(TextPanelActionListener);	//Action ������ �ޱ�
	 
				
        ////////////////������ ���� �г�
        panel3 = new JPanel();
        panel3.setBackground(new Color(174, 246, 160)); 
        panel3.setPreferredSize( new Dimension( 350, 350 ) );
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
		
		split.setTopComponent(panel1);
	}
	
	public JSplitPane splitpane_create() {        
        return split;
	}
		
}
