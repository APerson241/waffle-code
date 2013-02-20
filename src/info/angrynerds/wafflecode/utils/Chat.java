package info.angrynerds.wafflecode.utils;

import info.angrynerds.wafflecode.mvc.Controller;
import info.angrynerds.wafflecode.mvc.WaffleController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat {
	private JTextArea incoming;
	private JTextField outgoing;
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	
	private Controller controller;
	
	public Chat(Controller controller) {
		this.controller = controller;
	}
	
	public JPanel buildUI(Color bg) {
		JPanel chatPanel = new JPanel(new BorderLayout());
		chatPanel.setBackground(bg);
		chatPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		incoming = new JTextArea(15, 10);
			incoming.setEditable(false);
		chatPanel.add(new JScrollPane(incoming));
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
			sendButton.addActionListener(new SendChatListener());
		JPanel bottomChatPanel = new JPanel();
			bottomChatPanel.add(outgoing);
			bottomChatPanel.add(sendButton);
		chatPanel.add(bottomChatPanel, BorderLayout.SOUTH);
		
		return chatPanel;
	}
	
	public void println(Object o) {
		String str = o.toString();
		str += (str.endsWith("\n"))?"":"\n";
		incoming.append(str);
	}
	
	private void setUpNetworking() {
		try {
			println("[Network] Attempting to connect to server...");
			socket = new Socket(WaffleController.SERVER_IP, 5000);
			InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(socket.getOutputStream());
			println("[Network] Connection to server established.");
		} catch(IOException ex) {
			println("[Network] Attempt failed because of IOException:\n" +
					ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}
	
	private class SendChatListener implements ActionListener, KeyListener {
		public void sendMessage() {
			String text = outgoing.getText();
			outgoing.setText("");
			if(text.startsWith("/")) {
				if(text.equals("/start")) {
					//code.setEnabled(true);
					setUpNetworking();
					Thread readerThread = new Thread(new IncomingReader());
					readerThread.start();
					println("[Network] Reader thread started.");
				}
			} else {
				try {
					writer.println(controller.getUsername() + ": " + text);
					writer.flush();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		public void actionPerformed(ActionEvent arg0) {
			sendMessage();
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMessage();
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	
	private class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while((message = reader.readLine()) != null) {
					System.out.println("read " + message);
					incoming.append(message + "\n");
				}
				reader.close();
				writer.close();
				socket.close();
			} catch(Exception ex) {ex.printStackTrace();}
		}
	}
}
