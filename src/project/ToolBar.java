package project;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

public class ToolBar {
	public ToolBar() {
		
	}
	
	public JToolBar toolBar_create() {
		 JToolBar tool = new JToolBar("Kitae Menu");
		 
	     tool.setBackground(Color.gray);
	     tool.add(new JButton("���� �����"));
	     tool.addSeparator();
	     tool.add(new JButton("����"));
	     tool.addSeparator();
	     tool.add(new JButton("����"));
	     /* JButton btn3=new JButton(new ImageIcon("save.jpg"));//���� �̹����ε� ������ �̹����� ������ �ȳ�Ÿ��
	     tool.add(btn3);*/
	     tool.addSeparator();
	     tool.add(new JButton("�ٸ� �̸����� ����"));
	     tool.addSeparator();
	     tool.add(new JButton("�ݱ�"));
	     tool.addSeparator();
	     tool.add(new JButton("����"));
	     tool.addSeparator();
	     tool.add(new JButton("����"));
	     tool.addSeparator();
	     // tool.add(new JTextField("text field"));
	     JComboBox<String> combo = new JComboBox<String>();
	     combo.addItem("click");
	     combo.addItem("��");
	     combo.addItem("�Ǥ�");
	     combo.addItem("�ǤǤ�");
	     tool.add(combo);
	     
	     return tool;
	}
}
