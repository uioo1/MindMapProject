package project;

import java.awt.*;
import javax.swing.*;

public class SplitPanel {
	JSplitPane split = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
	public SplitPanel() {
        split.setDividerLocation( 200 );
        
        /////////////////가운데 마인드맵 패널
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(163, 202, 241));
        panel1.setLayout( new BorderLayout() );
        panel1.add( new JLabel( "CENTER panel" ), BorderLayout.CENTER );

        /////////////////왼쪽 텍스트 패널
        JPanel panel2_1 = new JPanel();
        
        JButton textbutton = new JButton("적용");
		JTextArea myDrawPanel = new JTextArea(30,25);
		panel2_1.setBackground(new Color(255, 255, 255));
		panel2_1.setLayout(new BorderLayout(5,5));
		panel2_1.add( new JScrollPane( myDrawPanel ), BorderLayout.CENTER );
		panel2_1.add( textbutton, BorderLayout.SOUTH );
		panel1.add(panel2_1, BorderLayout.WEST);
		
        ////////////////오른쪽 설정 패널
        JPanel panel3 = new JPanel();
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



