package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RepaintManager;

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
	JTextField text;
	User user;

	public static final int TYPE_DEFAULT=0;

	public GUIManager(String title,Twitter twitter,User user) throws TwitterException{
		exFrame = new ExpandFrame(1000,400,title);
		makeLayout(TYPE_DEFAULT);
		this.twitter=twitter;
		this.user=user;
		exFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exFrame.setVisible(true);
	}
	
	private void makeLayout(int type) throws TwitterException{
		switch (type) {
		case TYPE_DEFAULT:
			JTextArea[] list = new JTextArea[20];
			for(int i=0;i<list.length;i++) list[i]=new JTextArea();
			Paging page = new Paging();
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
			rightFrame.setLayout(new BoxLayout(rightFrame,BoxLayout.Y_AXIS));
			text.setFont(new Font("メイリオ",Font.PLAIN,20));
			(new Thread(new Runnable() {
				@Override
				public void run() {
					int cnt=0;
					try {
						while(true){
							if(twitter!=null) {
								try{
									ResponseList<Status> tl = twitter.getHomeTimeline(page);
									for(Status each:tl){
										System.out.println(each.getText());
										list[cnt].setText(each.getText());
										list[cnt].setFont(new Font("メイリオ",Font.PLAIN,15));
										rightFrame.add(list[cnt]);
										cnt++;
									}
									Status status=tl.get(tl.size()-1);
									page.setMaxId(status.getId());
									cnt=0;
									tl=null;
									for(JTextArea tmp:list) tmp=null;
								} catch (Exception e){
									e.printStackTrace();
									System.exit(-1);
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
			exFrame.add(text,BorderLayout.CENTER);
			exFrame.add(rightFrame,BorderLayout.EAST);
			break;
		default:
			break;
		}
	}
	

}
