package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class textEditor extends JFrame implements ActionListener{
	
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSpinner;
	JLabel label;
	JColorChooser colorChooser;
	JButton button;
	JComboBox box;
	ImageIcon icon=new ImageIcon("letter.png");
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	
	textEditor() {
		this.setSize(1500,1500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.setTitle("Text Editor Application");
		this.setLocationRelativeTo(null);
		this.setIconImage(icon.getImage());
		
		textArea=new JTextArea();
		textArea.setPreferredSize(new Dimension(1450,1450));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Italic", Font.PLAIN, 20));
		
		scrollPane=new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(950,950));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		label=new JLabel("FONT SIZE:");
		
		fontSpinner=new JSpinner();
		fontSpinner.setPreferredSize(new Dimension(50,25));
		fontSpinner.setValue(20);
		fontSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSpinner.getValue()));
			}
		});
		
		button=new JButton("Color");
		button.setFocusable(false);
		button.addActionListener(this);
		
		String[] font= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		box=new JComboBox(font);
		box.addActionListener(this);
		box.setSelectedItem("Arial");
		
		menuBar = new JMenuBar();
		menu=new JMenu("File");
		
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);

		
		menu.add(openItem);
		menu.add(saveItem);
		menu.add(exitItem);
		
		menuBar.add(menu);
		
		this.setJMenuBar(menuBar);
		this.add(button);
		this.add(label);
		this.add(fontSpinner);
		this.add(box);
		this.add(scrollPane);
		this.setVisible(true);
	}

	@Override
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			colorChooser=new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Choose a color bro", Color.black);
			textArea.setForeground(color);			 
		}
		
		if(e.getSource()==box) {
			textArea.setFont(new Font((String)box.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}
		
		if(e.getSource()==openItem) {
			JFileChooser fileChooser= new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
	
			int response = fileChooser.showSaveDialog(null);
			
			if(response== fileChooser.APPROVE_OPTION) {
				File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner in = null;
				
				try {
					in=new Scanner(file);
					if(file.isFile()) {
						while(in.hasNextLine()) {
							String line = in.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					in.close();
				}
				
			}
			
		}
		
		if(e.getSource()==saveItem) {
			JFileChooser fileChooser= new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			if(response== fileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter out=null;
				
				file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					out=new PrintWriter(file);
					out.println(textArea.getText());
					
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				finally {
					out.close();
				}
			}
			
		}
		
		if(e.getSource()==exitItem) {
			System.exit(0);
		}
	}
	
}
