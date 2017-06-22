
package sims.functions;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import sims.startUpPage.SidePanel;

public class ChangeFunctions {
    
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
