package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Pageable;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.omg.CORBA.ParameterModeHelper;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class GUIManager {
	ExpandFrame exFrame;
	JButton tweetButton;
	Twitter twitter;
	JTextArea text;
	User user;

	public static final int TYPE_DEFAULT=0;

	public GUIManager(String title,Twitter twitter,User user) throws TwitterException{
		exFrame = new ExpandFrame(600,400,title);
		this.twitter=twitter;
		this.user=user;
		makeLayout(TYPE_DEFAULT);

		exFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exFrame.setVisible(true);
	}
	
	private synchronized void makeLayout(int type) throws TwitterException{
		switch (type) {
		case TYPE_DEFAULT:
			// GUI周り
			JPanel leftFrame = new JPanel();
			leftFrame.setLayout(new BoxLayout(leftFrame,BoxLayout.Y_AXIS));
			JTextArea debugField = new JTextArea();
			JPanel rightFrame = new JPanel();
			rightFrame.setLayout(new BoxLayout(rightFrame,BoxLayout.Y_AXIS));
			JScrollPane jScrollPane = new JScrollPane();
			debugField.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			debugField.setEditable(false);
			debugField.setFont(new Font("メイリオ",Font.PLAIN,15));
			debugField.setLineWrap(true);
			tweetButton = new JButton("TWEET");
			tweetButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if(twitter!=null){
						try {
							twitter4j.Status status = twitter.updateStatus(text.getText());
							debugField.setText("Tweet Succeed!");
							debugField.setCaretPosition(0);
							exFrame.repaint();
						} catch (TwitterException e) {
							e.printStackTrace();
						}
					} else System.out.println("twitterがnull");
				}
			});
			text = new JTextArea();
			text.setLineWrap(true);
			text.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			leftFrame.add(text);
			leftFrame.add(debugField);
			text.setFont(new Font("メイリオ",Font.PLAIN,20));

			GetTimeline tlThread = new GetTimeline(twitter, exFrame, rightFrame);
			tlThread.start();
			exFrame.add(tweetButton,BorderLayout.SOUTH);
			exFrame.add(leftFrame,BorderLayout.CENTER);
			exFrame.add(rightFrame,BorderLayout.EAST);
			break;
		default:
			break;
		}
	}
}
