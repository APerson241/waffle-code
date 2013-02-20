package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.Controller;
import info.angrynerds.wafflecode.mvc.WaffleController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MenuBarListener implements ActionListener {
	private Controller controller;
	
	public MenuBarListener(Controller controller) {
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		System.out.println(command);
		
		if(command.equals("Change Server IP...")) {
			WaffleController.SERVER_IP = JOptionPane.showInputDialog(
					"Type in the New IP Address of the Server.", WaffleController.SERVER_IP);
		}
	}
}
