
package sims.functions;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import sims.startUpPage.SidePanel;

public class Tools {
    
    /*Creates a back button with an arrow and no decorations*/
    
    public JButton backButton(){
        JButton backButton = createIconButton("images/back-arrow.png", "Back");
        backButton.setPreferredSize(new Dimension(50, 30));
        backButton.setMargin(new Insets(0,0,0,0));
        removeExtras(backButton);
        
        return backButton;
    }
    
    /*Creates a button with an icon as its text, located in the path attribute*/
    
    public JButton createIconButton(String path, String description){
        
        URL imgURL = getClass().getResource(path);
        JButton iconButton = new JButton();
        
        if (imgURL != null) {
            ImageIcon imageIcon = new ImageIcon(imgURL, description);
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
            iconButton.setIcon(imageIcon);
            iconButton.setToolTipText(description);
            
            return iconButton;
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    /*Removes all decorations from a button leaving only the text*/
    
    public static void removeExtras(JButton button){
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }
    
    /*Changes the color of the sidebar buttons once one is selected*/
    
    public static void changeColor(SidePanel sidePanel, int no){
        for( int i = 0; i < sidePanel.button.length; i++){
            sidePanel.button[i].setBackground(Color.BLACK);
            sidePanel.button[i].setForeground(Color.WHITE);
        }
        
        sidePanel.button[no].setBackground(Color.CYAN);
        sidePanel.button[no].setForeground(Color.BLACK);
    }
    
    /*Changes the card shown in the main cardLayout manager*/
    
    public static void cardChanger(JPanel cardHolder, String name){
        CardLayout cardLayout = (CardLayout)(cardHolder.getLayout());
        cardLayout.show(cardHolder, name );
    }
    
    /*Calls the first card in the cardlayout manager*/
    
    public static void callFirstCard(JPanel cardHolder, JComboBox listCombo){
        CardLayout cardLayout = (CardLayout)(cardHolder.getLayout());
        cardLayout.show(cardHolder, (String)listCombo.getItemAt(0));
    }
}
