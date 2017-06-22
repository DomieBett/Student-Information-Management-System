
package sims.mainFrame;

import sims.loadPanels.StudentPanel;
import sims.loadPanels.CoursePanel;
import sims.startUpPage.MainPanel;
import sims.startUpPage.HeaderPanel;
import sims.startUpPage.SidePanel;
import sims.functions.ChangeFunctions;
import sims.addPanels.AddDataPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main (String [] args){
        
        try{
            
            mainFrame();
        }catch(Exception e){
            System.out.println("You have an exception");
            e.printStackTrace();
        }
        
    }
    public static void mainFrame(){
        JFrame frame = new JFrame("Students Information Management System.");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(1100, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);
        
        JPanel cardHolder = new JPanel();
        HeaderPanel headerPanel = new HeaderPanel();
        SidePanel sidePanel = new SidePanel();
        MainPanel mainPanel = new MainPanel();
        AddDataPanel addDataPanel = new AddDataPanel();
        CoursePanel coursePanel = new CoursePanel();
        
        
        CardLayout cardLayout = new CardLayout();
        cardLayout.setHgap(10);
        cardLayout.setVgap(10);
        cardHolder.setLayout(cardLayout);
        
        cardHolder.setSize(new Dimension(800, 700));
        
        cardHolder.add(mainPanel, "mainPanel");
        cardHolder.add(addDataPanel, "addDataPanel");
        
        DefaultComboBoxModel panelName = new DefaultComboBoxModel();
        panelName.addElement( "mainPanel" );
        panelName.addElement( "addDataPanel" );
        panelName.addElement( "unitPanel" );
        panelName.addElement( "coursePanel" );
        panelName.addElement( "studentPanel" );
        
        JComboBox listCombo = new JComboBox(panelName);
        
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(sidePanel, BorderLayout.WEST);
        frame.add(cardHolder, BorderLayout.CENTER);
        
        sidePanel.button[0].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                ChangeFunctions.changeColor(sidePanel, 0);
                ChangeFunctions.cardChanger(cardHolder, "addDataPanel");
                
                coursePanel.backButton.setEnabled(true);
                coursePanel.backButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        ChangeFunctions.cardChanger(cardHolder, "addDataPanel");
                        ChangeFunctions.changeColor(sidePanel, 0);
                    }
                });
            }
        });
        
        sidePanel.button[1].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                StudentPanel studentPanel = new StudentPanel();
                cardHolder.add(studentPanel, "studentPanel");
                ChangeFunctions.changeColor(sidePanel, 1);
                ChangeFunctions.cardChanger(cardHolder, "studentPanel");
            }
        });
        
        sidePanel.button[2].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CoursePanel coursePanel = new CoursePanel();
                cardHolder.remove(coursePanel);
                cardHolder.add(coursePanel, "coursePanel");
                ChangeFunctions.changeColor(sidePanel,2);
                coursePanel.backButton.setEnabled(false);
                ChangeFunctions.cardChanger(cardHolder, "coursePanel");
            }
        });
        
        addDataPanel.courseAddPanel.courseViewButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CoursePanel coursePanel = new CoursePanel();
                cardHolder.remove(coursePanel);
                cardHolder.add(coursePanel, "coursePanel");
                ChangeFunctions.changeColor(sidePanel, 2);
                ChangeFunctions.cardChanger(cardHolder, "coursePanel");
            }
        });
        
        frame.pack();
        frame.add(cardHolder);
        frame.setVisible(true);
    }
}
