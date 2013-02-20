package info.angrynerds.wafflecode.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.JEditorPane;

public class SyntaxHighlighter extends TimerTask implements ActionListener {
	
	private JEditorPane codeArea;
	private String[] keywords;
	
	public SyntaxHighlighter(JEditorPane code) {
		codeArea = code;
		String[] keywords = {"boolean", "byte", "char", "double", "float", "int", "long", "short", "public",
				"private", "protected", "abstract", "final", "native", "static", "strictfp", "synchronized",
				"transient", "volatile", "if", "else", "do", "while", "switch", "case", "default", "for",
				"break", "continue", "assert", "class", "extends", "implements", "import", "instanceof", "interface",
				"new", "package", "super", "this", "catch", "finally", "try", "throw", "throws", "return",
				"void", "const", "goto", "enum"}; //It took me so long to type all that...
		this.keywords = keywords;
	}

	@Override
	public void run() {
		String text = codeArea.getText().replace("<b>","").replace("</b>","");
		for(String keyword : keywords) {
			text = text.replace(keyword, "<b>" + keyword + "</b>");
		}
		if(!text.equals(codeArea.getText())) {
			codeArea.setText(text);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		run();
	}

}
