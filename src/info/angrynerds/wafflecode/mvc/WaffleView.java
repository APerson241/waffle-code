package info.angrynerds.wafflecode.mvc;

import info.angrynerds.wafflecode.AppLauncher;
import info.angrynerds.wafflecode.utils.AutoCompleter;
import info.angrynerds.wafflecode.utils.Chat;
import info.angrynerds.wafflecode.utils.Doc;
import info.angrynerds.wafflecode.utils.MenuBarListener;
import info.angrynerds.wafflecode.utils.Project;
import info.angrynerds.wafflecode.utils.SyntaxHighlighter;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;

public class WaffleView implements View {
	private JFrame frame;
	private Controller controller;
	// CHAT
	private Chat chat;
	// CODE
	private JEditorPane code;
	private JTextField title;
	private Project project;
	private Doc doc;
	private SyntaxHighlighter highlighter;
	public static final String HTML_TOP = "<html><head><style type='text/css'>" +
			"body { font-family: monospace, sans-serif;" +
			"font-size: 10px; }" +
			"</style></head><body>",
			HTML_BOTTOM = "</body></html>";
	// STYLE
	private Color background;
	
	public WaffleView(Controller controller) {
		this.controller = controller;
		chat = new Chat(controller);
	}
	
	public void openProject(Project proj) {
		project = proj;
		title.setText(project.getTitle());
	}
	
	public void openDoc(Doc doc) {
		this.doc = doc;
		code.setText(doc.contents());
	}
	
	public void setVisible(boolean visible) {
		if(frame == null) {
			frame = new JFrame("WaffleCode (" + AppLauncher.VERSION + ")");
			setUpMenuBar();
			background = new Color(141, 182, 205);
			JPanel chatPanel = chat.buildUI(background);
			JPanel codePanel = new JPanel(new BorderLayout());
				codePanel.setBackground(background);
				codePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				code = new JEditorPane();
					code.setContentType("text/html");
					code.setText(HTML_TOP + HTML_BOTTOM);
					code.setEnabled(true);
					code.setEditable(true);
					code.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					AutoCompleter ac = new AutoCompleter(this);
					code.addCaretListener((CaretListener) ac);
					code.addKeyListener((KeyListener) ac);
					code.getDocument().addDocumentListener((DocumentListener) ac);
					//code.getDocument().putProperty("name", doc.getID());
					highlighter = new SyntaxHighlighter(code);
					Timer t = new Timer(1000/5, highlighter);
					t.setRepeats(true);
					t.start();
				codePanel.add(code, BorderLayout.CENTER);
				title = new JTextField("[title]", 20);
					title.setHorizontalAlignment(JTextField.CENTER);
					title.setBackground(background);
					title.setBorder(null);
				JPanel titlePanel = new JPanel();	// Just for layout and aesthetic purposes
					titlePanel.add(title);
					titlePanel.setBackground(background);
				codePanel.add(titlePanel, BorderLayout.NORTH);
			frame.getContentPane().add(codePanel, BorderLayout.CENTER);
			frame.getContentPane().add(chatPanel, BorderLayout.EAST);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(50, 50, 1000, 500);
			frame.setBackground(background);
			chat.println("Type \"/start\" into the chat box to set up networking\nand enable everything.");
		}
		frame.setVisible(visible);
		
		setUpCodeListener();
	}
	
	private void setUpMenuBar() {
		if(frame != null) {
			JMenuBar bar = new JMenuBar();
			
			JMenu file = new JMenu("File");
			JMenu edit = new JMenu("Edit");
			JMenu network = new JMenu("Network");
				JMenuItem ip = new JMenuItem("Change Server IP...");
				ip.addActionListener(new MenuBarListener(controller));
				network.add(ip);

			bar.add(file);
			bar.add(edit);
			bar.add(network);
			frame.setJMenuBar(bar);
		}
	}

	private void setUpCodeListener() {
		// TODO Auto-generated method stub
		
	}

	
	
	public void inform(String updated) {/*
		// TODO Auto-generated method stub
		if(updated != code.getText()) {
			code.setText(updated);
		}
	*/}

	public JEditorPane getCodeArea() {
		// TODO Auto-generated method stub
		return code;
	}

	public Doc getDoc() {
		return doc;
	}
}