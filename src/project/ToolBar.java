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
	     JButton newbtn = new JButton("���� �����");
	     tool.add(newbtn);
	     newbtn.setToolTipText("���ε� �� ������ ���� �����մϴ�");
	     tool.addSeparator();
	     JButton openbtn = new JButton("����");
	     tool.add(openbtn);
	     openbtn.setToolTipText("���ε� �� ������ �ҷ��ɴϴ�");
	     tool.addSeparator();
	     JButton savebtn = new JButton("����");
	     tool.add(savebtn);
	     savebtn.setToolTipText("�۾��� ������ �����մϴ�");
	     /*JButton btn3=new JButton(new ImageIcon("save.jpg"));//���� �̹����ε� ������ �̹����� ������ �ȳ�Ÿ��
	     tool.add(btn3);*/
	     tool.addSeparator();
	     JButton save_otherbtn = new JButton("�ٸ� �̸����� ����");
	     tool.add(save_otherbtn);
	     save_otherbtn.setToolTipText("�۾��� ������ ���ο� ���Ϸ� �����մϴ�");
	     tool.addSeparator();
	     JButton closebtn = new JButton("�ݱ�");
	     tool.add(closebtn);
	     closebtn.setToolTipText("���α׷��� �����մϴ�");
	     tool.addSeparator();
	     JButton applybtn = new JButton("����");
	     tool.add(applybtn);
	     applybtn.setToolTipText("�ؽ�Ʈ ���� ������ ���ε� �ʿ� �����մϴ�");
	     tool.addSeparator();
	     JButton changebtn = new JButton("����");
	     changebtn.setToolTipText("�Ӽ� ���� ������ ���ε� �ʿ� �����մϴ�");
	     tool.add(changebtn);
	     tool.addSeparator();

	     JComboBox<String> combo = new JComboBox<String>();
	     combo.addItem("click");
	     combo.addItem("��");
	     combo.addItem("�Ǥ�");
	     combo.addItem("�ǤǤ�");
	     tool.add(combo);
	     
	     return tool;
	}
}
