/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sims.startUpPage;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel{
    JLabel headerLabel = new JLabel();
    
    public HeaderPanel(){
        setPreferredSize(new Dimension(1000, 100));
        setBackground(Color.CYAN);
        
        headerLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        headerLabel.setText("<html>MASINDE MULIRO UNIVERSITY<br>  OF SCIENCE AND TECHNOLOGY.</html>");
        add(headerLabel);
    }
}
