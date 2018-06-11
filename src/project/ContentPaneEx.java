package project;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ContentPaneEx extends JFrame{
	JFrame myframe;
	
	
	public ContentPaneEx() {
		myframe = new JFrame();
		SplitPanel splitpanel = new SplitPanel();
		this.setTitle("메뉴바 만들기 예제");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(splitpanel.menubar.menubar_create());	//Menubar 생성

        this.add(splitpanel.toolbar.toolBar_create(), BorderLayout.NORTH);	//toolbar 생성
        this.setLocationRelativeTo(null);	//toolbar()에 같이 있던 코드인데 뭐하는거지?
        
        this.add(splitpanel.splitpane_create());
        
		this.setBackground(Color.darkGray);
	    this.setSize(1600, 900);
	    this.setVisible(true);
	}
	
	public void repaintFrame() {
		myframe.repaint();
	}
	
}
