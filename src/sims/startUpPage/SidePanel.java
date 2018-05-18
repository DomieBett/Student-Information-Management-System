/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sims.startUpPage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel{
    public JButton[] button = new JButton[6];
    FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 1, 1);
    
    public SidePanel(){
        
        setPreferredSize(new Dimension(190, 500));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
                
        String[] buttonText = {"Add Data", "Students", "Courses", "TimeTable", "Fees Statement", "Examination Result"};
        for (int i = 0; i < button.length; i++){
            button[i] = new JButton();
            button[i].setText(buttonText[i]);
            button[i].setPreferredSize(new Dimension(190, 50));
            button[i].setBackground(null);
            button[i].setForeground(Color.WHITE);
        }
        
        for(int i = 0; i < button.length; i++){
            add(button[i]);
        }
    }
    public SidePanel(JButton addButton){
        this.button[0] = addButton;
    }
}
