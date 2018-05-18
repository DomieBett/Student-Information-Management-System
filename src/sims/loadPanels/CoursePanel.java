package sims.loadPanels;

import sims.functions.ChangeFunctions;
import java.awt.*;
import javax.swing.*;
import sims.functions.LoadFunctions;
import sims.functions.Tools;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;

public class CoursePanel extends JPanel{
    BorderLayout borderLayout = new BorderLayout();
    CardLayout cardLayout = new CardLayout();
    public JButton backButton = createIconButton("/images/back-arrow.png", "Back");
    JPanel[] panel = new JPanel[2];
    JPanel toolsPanel;
    JPanel cardHolder = new JPanel();
    JLabel holderLabel;
    JLabel addCourseLabel;
    JButton studentButton,trashButton,unitButton;
    JButton addButton = createIconButton("/images/plus-main.png", "Add Course");
    
    ArrayList<JPanel> panelArray = new ArrayList<>();
    
    public CoursePanel(){
        setLayout(borderLayout);
        
        for (int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
        }
        
        panel[0].setPreferredSize(new Dimension(810, 30));
        panel[0].setLayout(new BorderLayout());
        removeExtras(backButton);
        panel[0].add(backButton, BorderLayout.WEST);
        
        cardLayout.setHgap(10);
        cardLayout.setVgap(10);
        cardHolder.setLayout(cardLayout);
        panel[1].setLayout(new BoxLayout(panel[1], BoxLayout.Y_AXIS));
        
        LoadFunctions.loadCourseArray();
        for (int i = 0; i< LoadFunctions.courseArray().size(); i++){
            holderLabel = new JLabel();
            JPanel holderPanel = new JPanel(){
                {
                    setSize(810, 40);
                    setMaximumSize(getSize());
                }
            };
            
            holderPanel.setLayout(new BorderLayout());
            holderPanel.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
            
            holderLabel.setText(LoadFunctions.courseName(i) + " (" + LoadFunctions.courseId(i) + ")");
            holderPanel.add(holderLabel, BorderLayout.WEST);
            holderPanel.add(toolsPanel(), BorderLayout.EAST);
            
            panelArray.add(holderPanel);
        }
        
        for (int i = 0; i < panelArray.size(); i++){
            panelArray.get(i).setName(LoadFunctions.courseId(i));
            panel[1].add(panelArray.get(i));
            
        }
        
        cardHolder.add(panel[1], "mainPanel");
        
        add(panel[0], BorderLayout.NORTH);
        add(cardHolder, BorderLayout.CENTER);
    }
    
    CoursePanel(JButton backButton){
        this.backButton = backButton;
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
            
            iconButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    String argument = iconButton.getParent().getParent().getName();
                    buttonChooser(iconButton, argument);
                }
            });
            
            return iconButton;
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
        /* Creates a panel that holds buttons with icons which are clicked to
        call the correspoding function.*/
    
    public JPanel toolsPanel(){
        toolsPanel = new JPanel();
        
        studentButton = createIconButton("/images/student.png", "Show Students");
        studentButton.setName("studentButton");
        removeExtras(studentButton);

        unitButton = createIconButton("/images/add-unit.png", "Show Units");
        unitButton.setName("unitButton");
        removeExtras(unitButton);
        
        trashButton = createIconButton("/images/trash.png", "Delete Course");
        trashButton.setName("trashButton");
        removeExtras(trashButton);

        toolsPanel.setLayout(new BorderLayout());
        toolsPanel.add(studentButton, BorderLayout.WEST);
        toolsPanel.add(unitButton, BorderLayout.CENTER);
        toolsPanel.add(trashButton, BorderLayout.EAST);
        
        return toolsPanel;
    }
    
    public void removeExtras(JButton button){
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }
    
        /* Determines which button has been clicked and calls 
        an action listener event for it*/
    
    public void buttonChooser(JButton button, String courseId) throws NullPointerException{
        
        if (button.getName().equalsIgnoreCase("studentButton")){
            cardHolder.add(LoadFunctions.studentPanel(courseId), "studentPanel");
            ChangeFunctions.cardChanger(cardHolder, "studentPanel");
            backButton.setEnabled(true);
            backButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    ChangeFunctions.cardChanger(cardHolder, "mainPanel");
                    backButton.setEnabled(false);
                }
            });
        }
        
        if (button.getName().equalsIgnoreCase("unitButton")){
            LoadFunctions.unitFrame(courseId);
        }
        
        if (button.getName().equalsIgnoreCase("trashButton")){
            
        }
    }
    
    public static void callPanel(String courseId){
        
    }
}
