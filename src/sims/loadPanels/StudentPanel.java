
package sims.loadPanels;

import sims.functions.ChangeFunctions;
import sims.functions.LoadFunctions;
import sims.functions.Tools;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class StudentPanel extends JPanel implements ActionListener{
    GridBagLayout gridbag = new GridBagLayout();
    CardLayout cardLayout = new CardLayout();
    BorderLayout borderLayout = new BorderLayout();
    GridBagConstraints gc = new GridBagConstraints();
    JPanel[] panel = new JPanel[3];
    JPanel cardHolder2;
    JPanel studentCoursePanel = new JPanel();
    JLabel label = new JLabel("This is the Students Panel page.");
    JLabel headerLabel = new JLabel();
    JButton courseButton;
    JButton backButton = createIconButton("/images/back-arrow.png", "Back");
    JButton allButton = new JButton("Display All");
    
    public StudentPanel(){
        setLayout(borderLayout);
        cardLayout.setHgap(10);
        cardLayout.setVgap(10);
        
        for (int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
        
        studentCoursePanel.setLayout(new BoxLayout(studentCoursePanel, BoxLayout.Y_AXIS));
        
        LoadFunctions.loadButtonArray();
        for (int i = 0; i < LoadFunctions.buttonArray().size(); i++){
            courseButton = LoadFunctions.buttonArray().get(i);
            courseButton.addActionListener(this);
            studentCoursePanel.add(courseButton);
        }
        
        panel[0].setLayout(new BorderLayout());
        backButton.setMargin(new Insets(0,0,0,0));
        Tools.removeExtras(backButton);
        panel[0].add(backButton, BorderLayout.WEST);
        panel[0].add(headerLabel, BorderLayout.CENTER);
        
        cardHolder2 = panel[1];
        cardHolder2.setLayout(cardLayout);
        cardHolder2.add(studentCoursePanel, "studentCoursePanel");
        
        
        DefaultComboBoxModel panelName = new DefaultComboBoxModel();
        panelName.addElement("studentCoursePanel");
        panelName.addElement("allStudentPanel");
        
        JComboBox listCombo = new JComboBox(panelName);
        
        panel[2].setLayout(new BorderLayout());
        allButton.setPreferredSize(new Dimension(100, 30));
        allButton.setMargin(new Insets(0,0,0,0));
        allButton.setBackground(Color.CYAN);
        panel[2].add(allButton, BorderLayout.EAST);
        
        add(panel[0], BorderLayout.NORTH);
        add(panel[1], BorderLayout.CENTER);
        add(panel[2], BorderLayout.SOUTH);
        
        allButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                allButton.setText("Refresh");
                headerLabel.setText("All Students.");
                cardHolder2.add(LoadFunctions.studentPanel("all"), "allStudentPanel");
                ChangeFunctions.cardChanger(cardHolder2, "allStudentPanel");
                
            }
        });
        
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                allButton.setText("Display all");
                headerLabel.setText("");
                ChangeFunctions.callFirstCard(cardHolder2, listCombo);
                
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        int i = LoadFunctions.buttonArray().indexOf(e.getSource());
        String s = LoadFunctions.courseId(i);
        allButton.setText("Display All");
        headerLabel.setText(LoadFunctions.courseName(i));
        cardHolder2.add(LoadFunctions.studentPanel(s), "studentPanel");
        ChangeFunctions.cardChanger(cardHolder2, "studentPanel");
        
    }
    
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
}
