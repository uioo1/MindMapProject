package project;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {
	public MenuBar() {
		 
	}
	
	public JMenuBar menubar_create() {
		JMenuBar menuBar = new JMenuBar();
        JMenu f = new JMenu("File");
        JMenu e = new JMenu("edit");
        
        f.add(new JMenuItem("New"));
        f.add(new JMenuItem("Open"));
        f.addSeparator();//ºÐ¸®¼± »ðÀÔ
        f.add(new JMenuItem("Save"));
        f.add(new JMenuItem("SaveAs"));
        
        e.add(new JMenuItem("close"));
        e.add(new JMenuItem("apply"));
        e.add(new JMenuItem("change"));
        
        menuBar.add(f);
		menuBar.add(e);
		return menuBar;
	}
}
