package project;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ContentPaneEx extends JFrame{
	JFrame myframe;
	
	
	public ContentPaneEx() {
		myframe = new JFrame();
		this.setTitle("�޴��� ����� ����");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBar().menubar_create());	//Menubar ����

        this.add(new ToolBar().toolBar_create(), BorderLayout.NORTH);	//toolbar ����
        this.setLocationRelativeTo(null);	//toolbar()�� ���� �ִ� �ڵ��ε� ���ϴ°���?
        
        SplitPanel split = new SplitPanel();
        this.add(split.splitpane_create());
        
		this.setBackground(Color.darkGray);
		this.setLocationRelativeTo(null);
	    this.setSize(1600, 900);
	    this.setVisible(true);
	}
	
}
