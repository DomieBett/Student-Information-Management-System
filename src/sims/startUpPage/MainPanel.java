
package sims.startUpPage;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    
    FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 1, 1);
    JLabel label = new JLabel("Welcome to the MMUST Student Information Management System.");
    
    public MainPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        add(label, gc);
    }
}
