package project;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.io.File;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MenuBar extends JFrame {	
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
	class OpenActionListener implements ActionListener {
		private JFileChooser chooser;
		public OpenActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON","JSON");
			chooser.setFileFilter(filter);
			int ret = chooser.showOpenDialog(null);
			if(ret != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
				return;
			}
			String filePath = chooser.getSelectedFile().getPath();
			pack(); // 이미지의 크기에 맞추어 프레임 크기 조절
		}
	}
	
	class SaveAsActionListener implements ActionListener {
		private JFileChooser chooser;
		public SaveAsActionListener() {
			chooser = new JFileChooser();
		}
		public void actionPerformed(ActionEvent e) {
			JFrame parentFrame = new JFrame();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON","JSON");
			chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Specify a file to save");   
			 
			int userSelection = chooser.showSaveDialog(parentFrame);
			 
			if (userSelection == chooser.APPROVE_OPTION) {
			    File fileToSave = chooser.getSelectedFile();
			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			}

			JSONObject obj = new JSONObject();
			
		    JSONArray list = new JSONArray();
		    for(Node label : SplitPanel.node_for_Labels) {
		    	System.out.println(label.getNodeData());
		    	list.add(label);
		    }
		    for(JLabel label : SplitPanel.jLabel_nodes) {
		    	list.add(label);
		    }
		    
		    try {
		     
		    	FileWriter file = new FileWriter("c:\\test3.json");
		    	file.write(obj.toJSONString());
		   		file.flush();
		  		file.close();
		    	 
		    } catch (IOException e2) {
		   		e2.printStackTrace();
		   	}
		   	System.out.println(" ");
		}
	}	
}
	
