package main;

import java.awt.Font;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import twitter4j.*;

public class GetTimeline extends Thread {
	
	private Twitter twitter;
	private ExpandFrame exFrame;
	private JPanel rightFrame;
	private JTextArea debugField;

	public GetTimeline(Twitter twitter,ExpandFrame exframe,JPanel rightFrame,JTextArea debugField){
		
		this.twitter = twitter;
		this.exFrame = exframe;
		this.rightFrame = rightFrame;
		this.debugField = debugField;
	}
	
	public void run(){
		JTextArea[] list = new JTextArea[10];
		Paging page = null;
		ResponseList<Status> tl = null;
		String printTweet;
		String tweet;
		long lastStatus=0;
		int cnt=0;
		for(int i=0;i<list.length;i++) {
			list[i]=new JTextArea(20,60);
			list[i].setLineWrap(true);
			list[i].setBorder(new EtchedBorder(EtchedBorder.RAISED));
		}
		try {
			while(true){
				try{
					tl = twitter.getHomeTimeline();

					for(Status each:tl){
						printTweet = each.getUser().getName()+
								" @"+each.getUser().getScreenName()+
								"\n"+each.getText();
						tweet = each.getText();
						if(tweet.equals("@tuki_tan コンプリケイション")) printRap("comp",each.getUser().getScreenName());
						else if(tweet.equals("@tuki_tan ノリアキ")) printRap("nori",each.getUser().getScreenName());
                        else if(tweet.equals("@tuki_tan ファフニール")) printRap("fafu",each.getUser().getScreenName());
						System.out.println(printTweet);


						list[cnt].setText(printTweet);
						list[cnt].setFont(new Font("メイリオ",Font.PLAIN,15));
						rightFrame.add(list[cnt]);
						cnt++;
						if(cnt==10) break;
					}
					cnt=0;
					tl.clear();
				} catch (Exception e){
					e.printStackTrace();
					debugField.setText(e.getMessage());
					debugField.setCaretPosition(0);
					exFrame.repaint();
				}
				exFrame.repaint();
				Thread.sleep(60000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void printRap(String music,String target){
	    switch (music){
            case "comp":
                try {
                    twitter.updateStatus("@" + target + " 悩んでる自分がなんかダサくて" + " じっとしてらんなくてバックレ"
                            + " やりたい事そんなもんないぜ" + " 屋上でこっそり咥えるマイセン"
                            + " なんか楽しくねえ今日のサイゼ" + " 心配そうな目で見てるマイメン"
                            + " 「泣いてないぜ」なんて吐いて" + " 強がってみてもマジ辛いぜ");
                    twitter.updateStatus("@" + target + " 通いなれた薄暗い道路" + " やっとの思い買った i-Pod"
                            + " 上辺だけの薄っぺらい RAP" + " 何故か重い薄っぺらの BAG"
                            + " 優しく暖かいはずの場所" + " 重苦しくて開け放つ窓"
                            + " 重圧に押し潰されそうで" + " 逃げ込んだいつもの公園");
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                break;
            case "nori":
                try {
                    twitter.updateStatus("@" + target + "俺はノリアキ");
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                break;
            case "fafu":
                Random rnd = new Random();
                String[] tweetArray = {"うんちを焦がす","天地を焦がす","うんちぶり","ウ　ン　チ　ー　コ　ン　グ"};

                try {
                    twitter.updateStatus("@" + target + " " + tweetArray[rnd.nextInt(4)]);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                break;



        }

    }
}

