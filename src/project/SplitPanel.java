package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import java.util.*;

public class SplitPanel {
	JPanel panel_Mid;	//가운데 마인드맵패널 정의
	JPanel panel_Left_Background;	//textarea 와 적용butoon을 가지고있는 패널
	JTextArea myDrawPanel;	//textarea 정의
	JPanel panel_Right;	//오른쪽 속성 패널 정의
	JPanel panel_Background;
	JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	
	public SplitPanel() {
        split.setDividerLocation( 300 );
        split2.setDividerLocation( 800 );
        /////////////////가운데 마인드맵 패널
        panel_Mid = new JPanel();
        panel_Mid.setBackground(new Color(163, 202, 241));
        panel_Mid.setLayout(null);
        split2.setBackground(new Color(100, 100, 100));
        
        /////////////////////////가운데 판넬에 넣는것 실험중인 코드
        /*
        for(int i = 0; i < 10 ; i++) {
			JTextField label = new JTextField(Integer.toString(i));
			label.setSize(20, 10);
			label.setLocation(i*15, i*15);
			label.setSize(10,10);
			label.setBackground(new Color(100, 100, 100));
			panel_Mid.add(label);
        }
        */
        
        /////////////////왼쪽 텍스트 패널+ 다 담는 패널
        panel_Left_Background = new JPanel();
        myDrawPanel = new JTextArea(30,25);
        JButton textbutton = new JButton("적용");       
		
		panel_Left_Background.setBackground(new Color(255, 255, 255));
		panel_Left_Background.setLayout(new BorderLayout(5,5));
		panel_Left_Background.add( new JScrollPane( myDrawPanel ), BorderLayout.CENTER );
		panel_Left_Background.add( textbutton, BorderLayout.SOUTH );
		
		ActionListener TextPanelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Tree().getTextPanel(myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
			}
		};
	    textbutton.addActionListener(TextPanelActionListener);	//Action 리스너 달기
	 
			//ㅋㅋ	
        ////////////////오른쪽 설정 패널
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

}
