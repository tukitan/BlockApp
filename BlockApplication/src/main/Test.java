package main;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.conf.ConfigurationBuilder;


class Test {
	ConfigurationBuilder cb;
	Twitter twitter;
	User user;
    public static void main(String args[]) throws TwitterException{
    	 Test tst = new Test();
    	 tst.getFollowerList();
        //ついーとしてみる
        //Status status = tst.twitter.updateStatus("ツイッターはじめました！ #初めてのツイート");
        
    }
    
    public Test() throws TwitterException{
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("u2sWakQvjNRi8Hc9X2MtWq1Dj")
                .setOAuthConsumerSecret("uhPyJ11NhZFSdFPxq2pRNr6AKrsTOtgoyShwjYLBUsYiZ2fUIe")
                .setOAuthAccessToken("603245243-Nq2HNKZ99GAi4dLFIGEmRy1UXORYS7yCvYcYTiCy")
                .setOAuthAccessTokenSecret("DsFxp76NBEaI5ypUI1l9NsCkMfK4dJtRaaCANntCUrotm");
        twitter = new TwitterFactory(cb.build()).getInstance();
        user = twitter.verifyCredentials();
        
    }
    
    public void getFollowerList() throws TwitterException{
    	long cursor = -1L;
    	PagableResponseList<User> followlist = twitter.getFollowersList("ID", cursor,200);
    	for(User user :followlist){    		
    		System.out.println(user.getName());
    	}
    	
    }
}
