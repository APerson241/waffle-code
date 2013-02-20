package info.angrynerds.wafflecode.network;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WaffleServer implements KeyListener {
	ArrayList<PrintWriter> clientOutputStreams;
	
	JFrame frame;
	JTextArea area;
	JTextField command;
	
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isr = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isr);
			} catch(Exception ex) {ex.printStackTrace();}
		}
		
		public void run() {
			String message;
			try {
				while((message = reader.readLine()) != null) {
					println("Got a message: \"" + message + "\".");
					tellEveryone(message);
				}
			} catch(Exception ex) {ex.printStackTrace();}
		}
	}
	
	public static  void main(String[] args) {
		new WaffleServer().go();
	}
	
	public void go() {
		clientOutputStreams = new ArrayList<PrintWriter>();
		buildGUI();
		println("Initialized non-network stuff.");
		try {
			ServerSocket serverSock = new ServerSocket(5000);
			println("Initialized ServerSocket.\n-");
			while(true) {
				println("Waiting for a connection...");
				Socket clientSocket = serverSock.accept();
				println("ServerSocket accepted a client socket.");
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				println("Got a connection!\n" + clientOutputStreams + " people are connected.\n-");
			}
		} catch(Exception ex) {ex.printStackTrace();}
	}
	
	public void tellEveryone(String message) {
		for(PrintWriter writer:clientOutputStreams) {
			writer.println(message);
			writer.flush();
		}
	}
	
	public void println(Object o) {
		String str = o.toString();
		str += (str.endsWith("\n"))?"":"\n";
		area.append(str);
	}
	
	public void buildGUI() {
		frame = new JFrame("WaffleCode Server");
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		area = new JTextArea();
		area.setEditable(false);
		area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(new JScrollPane(area));
		command = new JTextField();
		command.addKeyListener(this);
		frame.add(command, BorderLayout.SOUTH);
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(50, 50, 750, 500);
		frame.setVisible(true);
	}

	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			String input = command.getText();
			command.setText("");
			if(input.equals("help")) {
				println("-\n===HELP===\nclear:\tClears screen.\nhelp:\tGets help.\nlist:\tLists" +
						" the people connected.");
			} else if(input.equals("clear")) {
				area.setText("");
			} else if(input.equals("list")) {
				println("-\n===LIST OF PLAYERS===\nNumber of connections: " + 
						clientOutputStreams.size());
			}
		}
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}