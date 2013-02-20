package info.angrynerds.wafflecode.mvc;

import info.angrynerds.wafflecode.network.*;
import info.angrynerds.wafflecode.utils.Doc;
import info.angrynerds.wafflecode.utils.Project;

import javax.swing.JOptionPane;

public class WaffleController implements Controller {
	private View view;
	private WaffleClient client;
	private static WaffleController me;
	public static String SERVER_IP = "192.168.0.101";
	
	public void runApplication() {
		view = new WaffleView(this);
		
		client = new WaffleClient(JOptionPane.showInputDialog("What is your username?", "Enter username here"));
		view.setVisible(true);
		
		Project example = new Project("My First Project", getUsername());
		example.addDoc();
		((WaffleView)view).openProject(example);
		((WaffleView)view).openDoc(example.getDoc(0));
		
		me = this;
	}
	
	public String getUsername() {
		return client.getUsername();
	}

	public WaffleClient getClient() {
		return client;
	}
	
	private View view() {
		return view;
	}
	
	public static View getView() {
		return me.view();
	}
	
	public void createProject(String title) {
		new Project(title, getUsername());
	}
	
	public void setIP(String ip) {
		SERVER_IP = ip;
	}
}