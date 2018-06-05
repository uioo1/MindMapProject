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
	     
	     return tool;
	}
}
