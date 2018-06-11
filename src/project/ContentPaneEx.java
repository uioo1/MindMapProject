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
		this.setTitle("�޴��� ����� ����");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(splitpanel.menubar.menubar_create());	//Menubar ����

        this.add(splitpanel.toolbar.toolBar_create(), BorderLayout.NORTH);	//toolbar ����
        this.setLocationRelativeTo(null);	//toolbar()�� ���� �ִ� �ڵ��ε� ���ϴ°���?
        
        this.add(splitpanel.splitpane_create());
        
		this.setBackground(Color.darkGray);
	    this.setSize(1600, 900);
	    this.setVisible(true);
	}
	
	public void repaintFrame() {
		myframe.repaint();
	}
	
}
