import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//calling user built package algo
import algo.*;

class MyWindowAdapter extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		Window w = e.getWindow();
		w.dispose();
	}
}

class AppDesk extends JFrame implements ActionListener, ItemListener {
	// String initialized for File i/o
	public String toEncrypt = "";
	public String toDecrypt = "";
	public String Encrypted = "";
	public String Decrypted = "";

	/*
	 * Choice defined for lists
	 * clr defined for choice selected
	 * key defined for key value input
	 */
	public Choice ch;
	public String clr;
	public int key;

	public AppDesk() {
		// Headng for the tool
		super("Cryptography Tool");

		MyWindowAdapter wa = new MyWindowAdapter();
		super.addWindowListener(wa);
		super.setSize(550, 350);
		super.setVisible(true);
		super.setLayout(null);

		// Label for choosing the Encryption Algorithm
		Label lbl = new Label("Select Algorithm :");
		lbl.setBounds(30, 50, 200, 80);
		lbl.setFont(new Font("Georgia", Font.BOLD, 20));
		this.add(lbl);

		// code to create a listing space
		ch = new Choice();
		ch.setBounds(230, 80, 200, 80);
		ch.setFont(new Font("Arial", Font.BOLD, 16));
		ch.add("Select Algorithm");
		this.add(ch);
		ch.addItemListener(this);

		// Button for Encryption Option
		Button b1 = new Button("Encrypt");
		b1.setBounds(130, 150, 120, 50);
		b1.setFont(new Font("Arial", Font.BOLD, 18));
		b1.setBackground(Color.RED);
		this.add(b1);
		b1.addActionListener(this);

		// Button for Decryption Option
		Button b2 = new Button("Decrypt");
		b2.setBounds(280, 150, 120, 50);
		b2.setFont(new Font("Arial", Font.BOLD, 18));
		b2.setBackground(Color.GREEN);
		this.add(b2);
		b2.addActionListener(this);

		// code to find algorithm names from the algo folder into a single File Object
		File a = new File("C:/Crypto-Tool/algo");

		// code to add algorithm names into the choice folder
		String item[] = a.list();

		// code to remove .class and .java extension from the algorithm names
		for (int i = 0; i < item.length; i++) {
			item[i] = item[i].replace(".java", "");
			item[i] = item[i].replace(".class", "");
		}

		// code to add only distinct algorithm names into the choices.
		item = Arrays.stream(item).distinct().toArray(String[]::new);
		for (int i = 0; i < item.length; i++)
			ch.add(item[i]);
	}

	// Method called for choices selected
	public void itemStateChanged(ItemEvent e) {
		// code to get selected Algorithm name
		clr = ch.getSelectedItem();
	}

	// Method called for Button selected
	public void actionPerformed(ActionEvent e) {
		// Code to get Operation Command
		String cap = e.getActionCommand();

		// code to call Encryption Process
		if (cap.equals("Encrypt")) {
			try {
				FileDialog fd = new FileDialog(this, "Select File", FileDialog.LOAD);
				fd.setVisible(true);
				if (fd.getFile() != null) {
					// code to select the File for Encryption
					toEncrypt = fd.getDirectory() + fd.getFile();
					setTitle(toEncrypt);
					FileInputStream fin = new FileInputStream(toEncrypt);
					byte data[] = new byte[fin.available()];
					fin.read(data);

					// code to create the byte array into a single String.
					String str = new String(data);

					byte bytes[] = new byte[data.length];

					switch (clr) {
						case "CaesarCypher": {
							bytes = CaesarCypher.encrypt(str, key).toString().getBytes();
						}
							break;

						case "ModifiedCaesarCypher": {
							bytes = ModifiedCaesarCypher.encrypt(str).toString().getBytes();
						}
							break;

						case "RSA": {
							bytes = RSA.encrypt(str).toString().getBytes();
						}
							break;

						case "AESEncryption":{
							bytes = AESEncryption.encrypt(str).toString().getBytes();
						}
							break;
					}

					FileDialog fd1 = new FileDialog(this, "Create File", FileDialog.LOAD);
					fd1.setVisible(true);
					if (fd.getFile() != null) {
						// code to select carrier Image and hide The payload in it
						Encrypted = fd1.getDirectory() + fd1.getFile();
						FileOutputStream fout = new FileOutputStream(Encrypted);

						fout.write(bytes);

						// closing file
						fout.close();
					}
					fin.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// code to call Decryption Process
		if (cap.equals("Decrypt")) {
			try {
				FileDialog fd = new FileDialog(this, "Select File", FileDialog.LOAD);
				fd.setVisible(true);
				if (fd.getFile() != null) {
					// code to select the File for Decryption
					toDecrypt = fd.getDirectory() + fd.getFile();
					setTitle(toDecrypt);
					FileInputStream fin = new FileInputStream(toDecrypt);
					byte data[] = new byte[fin.available()];
					fin.read(data);

					// code to create the byte array into a single String.
					String str = new String(data);

					byte bytes[] = new byte[data.length];

					switch (clr) {
						case "CaesarCypher": {
							bytes = CaesarCypher.decrypt(str, key).toString().getBytes();
						}
							break;

						case "ModifiedCaesarCypher": {
							bytes = ModifiedCaesarCypher.decrypt(str).toString().getBytes();
						}
							break;
						
						case "RSA": {
							bytes = RSA.decrypt(str).toString().getBytes();
						}
							break;

						case "AESEncryption":{
							bytes = AESEncryption.decrypt(str).toString().getBytes();
						}
							break;
					}

					FileDialog fd1 = new FileDialog(this, "Create File", FileDialog.LOAD);
					fd1.setVisible(true);
					if (fd.getFile() != null) {
						// code to get the File for storing Decrypted data.
						Decrypted = fd1.getDirectory() + fd1.getFile();
						FileOutputStream fout = new FileOutputStream(Decrypted);

						fout.write(bytes);

						// closing file
						fout.close();
					}
					fin.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

class Crypto_Tool {
	public static void main(String[] args) throws IOException {
		// Initializing the Constructor for starting the Tool.
		AppDesk a = new AppDesk();
	}
}
