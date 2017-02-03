package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.soap.Text;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class GUIManager {
	ExpandFrame exFrame;
	JButton tweetButton;
	Twitter twitter;
	JTextField text;
	public static final int TYPE_DEFAULT=0;

	public GUIManager(String title,Twitter twitter){
		exFrame = new ExpandFrame(title);
		makeLayout(TYPE_DEFAULT);
		this.twitter=twitter;
		exFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exFrame.setVisible(true);
	}
	
	private void makeLayout(int type){
		switch (type) {
		case TYPE_DEFAULT:
			tweetButton = new JButton("TWEET");
			tweetButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if(twitter!=null){
						try {
							twitter4j.Status status = twitter.updateStatus(text.getText());
							System.out.println("Tweet Succeed!");
						} catch (TwitterException e) {
							e.printStackTrace();
						}
					} else System.out.println("twitterがnull");
				}
			});
			text = new JTextField();
			JPanel rightFrame = new JPanel();
			text.setFont(new Font("メイリオ",Font.PLAIN,20));
			exFrame.add(tweetButton,BorderLayout.SOUTH);
			exFrame.add(text,BorderLayout.CENTER);
			exFrame.add(rightFrame,BorderLayout.EAST);
			break;
		default:
			break;
		}
	}
	

}
