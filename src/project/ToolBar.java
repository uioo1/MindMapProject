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
	     JButton newbtn = new JButton("새로 만들기");
	     tool.add(newbtn);
	     newbtn.setToolTipText("마인드 맵 파일을 새로 생성합니다");
	     tool.addSeparator();
	     JButton openbtn = new JButton("열기");
	     tool.add(openbtn);
	     openbtn.setToolTipText("마인드 맵 파일을 불러옵니다");
	     tool.addSeparator();
	     JButton savebtn = new JButton("저장");
	     tool.add(savebtn);
	     savebtn.setToolTipText("작업한 내용을 저장합니다");
	     /*JButton btn3=new JButton(new ImageIcon("save.jpg"));//저장 이미지인데 내꺼에 이미지가 없는지 안나타남
	     tool.add(btn3);*/
	     tool.addSeparator();
	     JButton save_otherbtn = new JButton("다른 이름으로 저장");
	     tool.add(save_otherbtn);
	     save_otherbtn.setToolTipText("작업한 내용을 새로운 파일로 저장합니다");
	     tool.addSeparator();
	     JButton closebtn = new JButton("닫기");
	     tool.add(closebtn);
	     closebtn.setToolTipText("프로그램을 종료합니다");
	     tool.addSeparator();
	     JButton applybtn = new JButton("적용");
	     tool.add(applybtn);
	     applybtn.setToolTipText("텍스트 편집 내용을 마인드 맵에 적용합니다");
	     tool.addSeparator();
	     JButton changebtn = new JButton("변경");
	     changebtn.setToolTipText("속성 변경 내용을 마인드 맵에 적용합니다");
	     tool.add(changebtn);
	     tool.addSeparator();

	     JComboBox<String> combo = new JComboBox<String>();
	     combo.addItem("click");
	     combo.addItem("ㅗ");
	     combo.addItem("ㅗㅗ");
	     combo.addItem("ㅗㅗㅗ");
	     tool.add(combo);
	     
	     return tool;
	}
}
