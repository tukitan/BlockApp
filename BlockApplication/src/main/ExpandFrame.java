package main;
import javax.swing.JFrame;


class ExpandFrame extends JFrame{
	public ExpandFrame(int xSize,int ySize,String title){
		setTitle(title);
		setBounds(300,0,300+xSize,300+ySize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public ExpandFrame(String title) {
		this(400,300,title);
	}
	public ExpandFrame(){
	   this(400,300,"application");
	}
}
