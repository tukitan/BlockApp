package main;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;

public class GetTimeline extends Thread {
	
	private Twitter twitter;
	private ExpandFrame exFrame;
	private JPanel rightFrame;

	public GetTimeline(Twitter twitter,ExpandFrame exframe,JPanel rightFrame){
		
		this.twitter = twitter;
		this.exFrame = exframe;
		this.rightFrame = rightFrame;
	}
	
	public void run(){
		JTextArea[] list = new JTextArea[10];
		JTextArea debugField = new JTextArea();
		Paging page = null;
		ResponseList<Status> tl = null;
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
					if(page==null) {
						page = new Paging(1,10);
					}
					else page = new Paging(lastStatus);
					tl = twitter.getHomeTimeline(page);

					for(Status each:tl){
						System.out.println(each.getText());
						list[cnt].setText(each.getText());
						list[cnt].setFont(new Font("メイリオ",Font.PLAIN,15));
						rightFrame.add(list[cnt]);
						cnt++;
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
}

