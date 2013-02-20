package info.angrynerds.wafflecode.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {
	
	private String title, owner;
	
	private HashMap<Integer, Doc> docs;
	
	public Project(String title, String owner) {
		docs = new HashMap<Integer, Doc>();
		this.title = title;
		this.owner = owner;
	}
	
	public void addDoc() {
		Doc myDoc = new Doc();
		docs.put(myDoc.getID(), myDoc);
	}
	
	public Doc getDoc(int i) {
		if(docs.get(i) != null) {
			return docs.get(i);
		}
		else {
			return new Doc();
		}
	}
	
	public String getTitle() {
		return title;
		
	}
	
	public void setTitle(String t) {
		title = t;
	}
}
