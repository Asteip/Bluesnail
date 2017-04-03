package com.alma.extension;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.alma.application.IHighScore;
import com.alma.extension.PersistentHighScore;;
/**
 * Extension to manage the HighScore
 * 
 */
public class HighScore extends JFrame implements IHighScore, ActionListener {

	/**
	 * attributes about frame
	 */
	private JPanel container;
	private JFormattedTextField nameJField;
	private JLabel label;
	private JButton Validate;
	private JScrollPane scrollPanel;
	JTable tableauScore;

	/**
	 * attributes about HighScore and score
	 */
	private HashMap<String, Integer> listHighScore;
	private int score;
	private PersistentHighScore hardSave;

	/**
	 * constructor
	 */
	public HighScore() {
		this.listHighScore = new HashMap<String, Integer>();
		this.score = 0;
		hardSave= new PersistentHighScore();
	}

	

	/**
	 * increment score
	 */
	public Integer incrementScore() {
		this.score++;
		return this.score;
	}

	/**
	 * getter on score
	 * 
	 * @return score
	 */
	public Integer getScore() {
		return this.score;
	}

	public HashMap<String, Integer> getListHighScore() {
		return this.listHighScore;
	}

	/**
	 * Display the window to get the name of the player and displaythe list of score
	 */
	public void DisplayNameWindow() {
		
		container = new JPanel();
		nameJField = new JFormattedTextField();
		label = new JLabel("Player's name :");
		Validate = new JButton("OK");
		
		
		this.setTitle("High Score");
		//this.setSize(400, 100);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		//container.setBackground(Color.white);
		//container.setLayout(new BorderLayout());
		JPanel top = new JPanel();
		Font police = new Font("Arial", Font.BOLD, 12);
		nameJField.setFont(police);
		nameJField.setPreferredSize(new Dimension(150, 30));
		Validate.addActionListener(this);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.add(label);
		top.add(nameJField);

	    this.pack();		
	    this.add(container);
		try {
			this.listHighScore= hardSave.getScore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getListScoreToJtable();
		scrollPanel = new JScrollPane(tableauScore);
		top.add(scrollPanel);
		top.add(Validate);
		container.add(top);
		this.setContentPane(top);
		this.setVisible(true);
	}

	/**
	 * manage buttons on the scores' window
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.listHighScore.put(nameJField.getText(), score);
		try {
			hardSave.saveScore(listHighScore);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dispose();
	}

	
	/**
	 * Convert a Map to an Array, to be display in a Jpanel.
	 */
	public void getListScoreToJtable() {

		Object[][] donnees = new Object[listHighScore.size()][2];
		int iterator = 0;
		for (Entry<String, Integer> mapentry : listHighScore.entrySet()) {
			donnees[iterator][0] = mapentry.getKey();
			donnees[iterator][1] = mapentry.getValue().toString();
			iterator++;
		}

		String[] entetes = { "Player", "Score" };

		tableauScore = new JTable(donnees, entetes);
	}

}
