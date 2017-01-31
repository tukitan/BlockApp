package main;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

class Test {
    public static void main(String args[]){
        ExpandFrame eFrame = new ExpandFrame(100,100,"huga");
        JButton button = new JButton("hoge");
        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        eFrame.add(panel,BorderLayout.SOUTH);
        eFrame.add(button,BorderLayout.NORTH);
        eFrame.setVisible(true);
        
    }

}
