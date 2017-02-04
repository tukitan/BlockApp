package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		makeLayout(TYPE_DEFAULT);
		this.twitter=twitter;
		this.user=user;
		exFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exFrame.setVisible(true);
	}
	
	private void makeLayout(int type) throws TwitterException{
		switch (type) {
		case TYPE_DEFAULT:
			JPanel leftFrame = new JPanel();
			leftFrame.setLayout(new BoxLayout(leftFrame,BoxLayout.Y_AXIS));
			JTextArea[] list = new JTextArea[20];
			JTextArea debugField = new JTextArea();
			JPanel rightFrame = new JPanel();
			rightFrame.setLayout(new BoxLayout(rightFrame,BoxLayout.Y_AXIS));
			JScrollPane jScrollPane = new JScrollPane(rightFrame);
			debugField.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			debugField.setEditable(false);
			debugField.setFont(new Font("メイリオ",Font.PLAIN,15));
			for(int i=0;i<list.length;i++) {

				list[i]=new JTextArea(5,60);
				list[i].setLineWrap(true);
			}
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

			(new Thread(new Runnable() {
				@Override
				public void run() {
					Status pastStatus = null;
					int cnt=0;
					try {
						while(true){
							if(twitter!=null) {
								try{
									ResponseList<Status> tl = twitter.getHomeTimeline();
									for(Status each:tl){
										if(pastStatus==tl.get(cnt)) break;
										System.out.println(each.getText());
										list[cnt].setText(each.getText());
										list[cnt].setFont(new Font("メイリオ",Font.PLAIN,15));
										rightFrame.add(list[cnt]);
										cnt++;
									}
									pastStatus= tl.get(cnt-1);
									cnt=0;
									tl.clear();
								} catch (Exception e){
									e.printStackTrace();
									debugField.setText(e.getMessage());
									debugField.setCaretPosition(0);
									exFrame.repaint();
								}
							}
							exFrame.repaint();
							Thread.sleep(60000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			})).start();
			exFrame.add(tweetButton,BorderLayout.SOUTH);
			exFrame.add(leftFrame,BorderLayout.CENTER);
			exFrame.add(rightFrame,BorderLayout.EAST);
			break;
		default:
			break;
		}
	}
	

}
