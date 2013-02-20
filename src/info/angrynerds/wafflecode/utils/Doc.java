package info.angrynerds.wafflecode.utils;

import javax.swing.text.Document;

import info.angrynerds.wafflecode.mvc.WaffleController;
import info.angrynerds.wafflecode.mvc.WaffleView;
import info.angrynerds.wafflecode.network.WaffleClient;

public class Doc /*implements Document*/ {
	private static int numOfDocs;
	private final int docID;
	private String content;
	private static WaffleClient client;
	
	static {
		numOfDocs = 0;
	}
	
	public Doc() {
		numOfDocs++;
		docID = numOfDocs;
		content = new String(WaffleView.HTML_TOP + WaffleView.HTML_BOTTOM);
	}
	
	public void insert(int start, String what) {
		String newContent = content;
		if(start >= 0 && start < content.length()) {
			newContent = content.substring(0, start) + what + content.substring(start);
		}
		else if(start < 0) {
			newContent = what + content;
		}
		else if(start >= content.length()) {
			newContent = content + what;
		}
		content = newContent;
		//client - pushTheEditToEveryoneElse()
		((WaffleView)(WaffleController.getView())).inform(content);
	}
	
	public void delete(int start, int length) {
		String newContent = content.substring(0, start-1) + content.substring(start + length);
		content = newContent;
		//client - pushTheEditToEveryoneElse
		((WaffleView)WaffleController.getView()).inform(content);
	}
	
	public String contents() {
		return content;
	}
	
	public void setContent(String cont) {
		content = cont;
	}
	
	public void setClient(WaffleClient client) {
		this.client = client;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return docID;
	}

	public void informOfInsert(int start, String what) {
		String newContent = content;
		if(start >= 0 && start < content.length()) {
			newContent = content.substring(0, start) + what + content.substring(start);
		}
		else if(start < 0) {
			newContent = what + content;
		}
		else if(start >= content.length()) {
			newContent = content + what;
		}
		content = newContent;
		
		//Push to other people.
	}
	
	public void informOfDelete(int start, int length) {
		String newContent = content.substring(0, start-1) + content.substring(start + length);
		content = newContent;
		//client - pushTheEditToEveryoneElse
	}
}
