package project;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import project.SplitPanel.*;

public class MenuBar extends SplitPanel{	
	public MenuBar() {
		 
	}
	
	public JMenuBar menubar_create() {
		JMenuBar menuBar = new JMenuBar();
        JMenu f = new JMenu("File");
        JMenu e = new JMenu("edit");
        
        JMenuItem JMenu_New = new JMenuItem("New"); 
        JMenuItem JMenu_Open = new JMenuItem("Open"); 
        JMenuItem JMenu_Save = new JMenuItem("Save"); 
        JMenuItem JMenu_SaveAs = new JMenuItem("SaveAs"); 
        JMenuItem JMenu_Close = new JMenuItem("Close"); 
        JMenuItem JMenu_Apply = new JMenuItem("Apply"); 
        JMenuItem JMenu_Change = new JMenuItem("Change"); 

        JMenu_Open.addActionListener(new OpenActionListener());
        JMenu_SaveAs.addActionListener(new SaveAsActionListener());
        JMenu_Save.addActionListener(new SaveActionListener());
        JMenu_Close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                System.exit(0);//dispose는 창하나만 닫기이고 exit는 모든 창닫기인데 dispose같은 경우는jframe을 상속하애되서 일단 exit으로 함
                }
            });
        f.add(JMenu_New);
        f.add(JMenu_Open);
        f.addSeparator();//분리선 삽입
        f.add(JMenu_Save);
        f.add(JMenu_SaveAs);
        
        e.add(JMenu_Close);
        e.add(JMenu_Apply);
        e.add(JMenu_Change);
        
        menuBar.add(f);
		menuBar.add(e);
		return menuBar;
	}
	
	public class OpenActionListener implements ActionListener {//열기 창만드는 코드
		private JFileChooser chooser;
		JSONParser parser = new JSONParser();
		public OpenActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {//json파일을 불러오는 코드
			FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
			chooser.setFileFilter(filter);
			int ret = chooser.showOpenDialog(null);
			String pathName = chooser.getSelectedFile().getPath();//클릭한것 경로저장
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath = chooser.getSelectedFile().getPath();
			try {
				Object obj = parser.parse(new FileReader(pathName));
				JSONObject jsonObject = (JSONObject) obj;
				String textarea = (String) jsonObject.get("textarea");//textarea글자로 변환
				actionOpen actionopen = new actionOpen();
				actionopen.actionOpen(textarea);
			}
			catch (FileNotFoundException e3) {
				e3.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			} catch (ParseException e3) {
				e3.printStackTrace();
			}
		}
	}
	public class actionOpen{//파일에서 불러온 내용 실행하기 - 근데 마우스로 클릭했을때 오른쪽에 안뜸
		actionOpen(){}
		public void actionOpen(String textarea){
			for(JLabel label : jLabel_nodes) {
				label.setText("");
				label.setOpaque(false);
				label.setSize(0, 0);
			}
			myDrawPanel.setText(textarea);
			myDrawPanel.revalidate();
			myDrawPanel.repaint();
			jLabel_nodes.clear();
			node_for_Labels.clear();
			myTree.getTextPanel(SplitPanel.myDrawPanel.getText());	//tree에 textPanel내용 넘겨주기
			draw_Tree(myTree.root, 0, panel_Mid);
			recoloring_tree();
			panel_Mid.repaint();
			
			isNotFirst = true;
		}
	}
	
/*	class OpenActionListener implements ActionListener {//현재 만들어져있는 노드들의 정보를 받아다가 폴더 열기 코드
		private JFileChooser chooser;
		JSONParser parser = new JSONParser();
		public OpenActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
			chooser.setFileFilter(filter);
			int ret = chooser.showOpenDialog(null);
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath = chooser.getSelectedFile().getPath();
			pack(); // 이미지의 크기에 맞추어 프레임 크기 조절
			try {
				Object obj = parser.parse(new FileReader("c:\\test2.json"));
		 
				JSONObject jsonObject = (JSONObject) obj;
				
				String textarea = (String) jsonObject.get("textarea");
				actionOpen actionopen = new actionOpen();
				actionopen.actionOpen(textarea);
			}
				
				
			catch (FileNotFoundException e3) {
				e3.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			} catch (ParseException e3) {
				e3.printStackTrace();
		}
		
	}
}*/
	class SaveActionListener implements ActionListener{//save-메뉴바 만들기 예제로 c드라이브에 생성됨
		private JFileChooser chooser;
		public SaveActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			 JSONObject obj = new JSONObject();
			    JSONArray list = new JSONArray();
			    obj.put("textarea", SplitPanel.myDrawPanel.getText());//textarea내용 자체 저장코드
			try {
				FileWriter file = new FileWriter("c:\\메뉴바 만들기 예제.json");
				file.write(obj.toJSONString());
				file.flush();
				file.close();
		 
			} catch (IOException e2) {
				e2.printStackTrace();
			}

		}
	}
	
	
	class SaveAsActionListener implements ActionListener {//saveas 저장
		private JFileChooser chooser;
		public SaveAsActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			JFrame parentFrame = new JFrame();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("json","json");
			chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Specify a file to save");   

			int userSelection = chooser.showSaveDialog(parentFrame);
			String pathName = chooser.getSelectedFile().getPath();//클릭한것 경로저장
			if (userSelection == chooser.APPROVE_OPTION) {
			    File fileToSave = chooser.getSelectedFile();
			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());//클락한 경로
			    JSONObject obj = new JSONObject();
			    JSONArray list = new JSONArray();
			    obj.put("textarea", SplitPanel.myDrawPanel.getText());//textarea내용 자체 저장코드
			    try {
			    	FileWriter file = new FileWriter(fileToSave+".json");//이름 저장
			    	file.write(obj.toJSONString());
			   		file.flush();
			  		file.close();
			    	  
			    } catch (IOException e2) {
			   		e2.printStackTrace();
			   	}
			}			
		    /*for(Node label : SplitPanel.node_for_Labels) {//라벨 정보들을 저장하는 코드
		    	String numStr1 = String.valueOf(label.getNodex());
		    	obj.put(label+"x", numStr1);
		    	String numStr2 = String.valueOf(label.getNodey());
		    	obj.put(label+"y", numStr2);
		    	String numStr3 = String.valueOf(label.getNodehei());
		    	obj.put(label+"h", numStr3);
		    	String numStr4 = String.valueOf(label.getNodewid());
		    	obj.put(label+"w", numStr4);
		    	String numStr5 = String.valueOf(label.getIndex());
		    	obj.put(label+"index", numStr5);
		    	String numStr7 = String.valueOf(label.getLeftChild());
		    	obj.put(label+"LChil", numStr7);
		    	String numStr8 = String.valueOf(label.getmyLabel());
		    	obj.put(label+"MyLabel", numStr8);
		    	String numStr9 = String.valueOf(label.getNodecolor());
		    	obj.put(label+"Color", numStr9);
		    	String numStr10 = String.valueOf(label.getNodeData());
		    	obj.put(label+"NodeData", numStr10);
		    	String numStr11 = String.valueOf(label.getParent());
		    	obj.put(label+"Parent", numStr11);
		    	String numStr12 = String.valueOf(label.getRightSibling());
		    	obj.put(label+"RSbling", numStr12);
		    	
		    }*/
		    /*
		    for(JLabel label : SplitPanel.jLabel_nodes) {
		    	String numStr13 = String.valueOf(label.);
		    	obj.add();
		    	String numStr13 = String.valueOf(label.);
		    }*/
		    //String what = SplitPanel.myDrawPanel.getText();	   
		}
	}
	class CloseActionListener extends JFrame implements ActionListener{
		private JFileChooser chooser;
		public CloseActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent arg0) {
			dispose();
    	}
	}

}
