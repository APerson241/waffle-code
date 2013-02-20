package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.WaffleView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JEditorPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AutoCompleter implements KeyListener, CaretListener, DocumentListener {

	private int charLoc;
	private Character prevChar, doublePrevChar;
	private WaffleView view;
	private JEditorPane codeArea;
	private Doc doc;

	public AutoCompleter(WaffleView wv) {
		charLoc = 0;
		view = wv;
		codeArea = wv.getCodeArea();
		doc = wv.getDoc();
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		try {
			// First we find the position of the caret.
			int charLoc = codeArea.getCaretPosition();
			//line = codeArea.getLineOfOffset(charLoc);
			//column = charLoc - codeArea.getLineStartOffset(line);
			
			System.out.print(charLoc + ": [");
			System.out.println(plainText().charAt(charLoc - 2) + "]");
			prevChar = plainText().charAt(charLoc - 2);
			doublePrevChar = plainText().charAt(charLoc - 3); //NOTE TO SELF: FIX THIS EXCEPTION-THING?
		}
		catch(Exception ex) {
			System.out.println("none]");
			prevChar = null; //charLoc = 0
			doublePrevChar = null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER) {
			if(doublePrevChar == '{') {//Line-breaks count as a char.
				System.out.println("Insert a Line Break, an Indent, another line break, and a close curly bracket.");
				if(doc != null) {
					doc.insert(charLoc-1, "}");
				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void changedUpdate(DocumentEvent e) { }

	@Override
	public void insertUpdate(DocumentEvent e) {
		//System.out.println("Insert at " + e.getOffset());
		//doc.informOfInsert(e.getOffset(), "J");
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		//System.out.println("Remove " + e.getLength() + ", starting at " + e.getOffset());
		//doc.informOfDelete(e.getOffset(), e.getLength());
	}

	private String plainText() {
		int lengthOfHTMLTop = codeArea.getText().indexOf("<body>") + "<body>".length();
		return codeArea.getText().replace("<b>", "").replace("</b>", "").substring(
				lengthOfHTMLTop - 2);
		//NOTE: Is there any benefit in removing the tags from the end? (</body, </html>, etc.)
	}

}
