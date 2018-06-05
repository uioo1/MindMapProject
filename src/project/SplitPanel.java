package project;

import java.awt.*;
import javax.swing.*;

public class SplitPanel {
	JSplitPane split = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
	public SplitPanel() {
        split.setDividerLocation( 200 );
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.BLUE);
        panel1.setLayout( new BorderLayout() );
        panel1.add( new JLabel( "CENTER panel" ), BorderLayout.CENTER );

		JTextArea myDrawPanel = new JTextArea();
        myDrawPanel.setPreferredSize( new Dimension( 350, 350 ) );
        myDrawPanel.add( new JLabel( "WEST panel!" ) );
        panel1.add( new JScrollPane( myDrawPanel ), BorderLayout.WEST );

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GREEN); 
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
