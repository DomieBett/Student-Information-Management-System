/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sims.addPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;

public class AddDataPanel extends JPanel {
    ArrayList<String> studentCourses = new ArrayList<>();
    JTabbedPane tabbedPane = new JTabbedPane();
    public CourseAddPanel courseAddPanel;
    JPanel messagePanel = new JPanel();
    static JLabel[] messageLabel = new JLabel[3];
    
    public AddDataPanel(){
//        setLayout(new BorderLayout());
        StudentAddPanel studentPanel = new StudentAddPanel();
        courseAddPanel = new CourseAddPanel();
        tabbedPane.add("Add Student", studentPanel);
        tabbedPane.add("Add Courses", courseAddPanel);
        add(tabbedPane);
        
        messagePanel.setSize(500, 70);
        messagePanel.setLayout(new BorderLayout());
        for (int i = 0; i < messageLabel.length; i++){
            messageLabel[i] = new JLabel();
            messageLabel[i].setSize(new Dimension(500, 20));
            messageLabel[i].setForeground(Color.RED);
            messagePanel.add(messageLabel[i]);
        }
        messagePanel.add(messageLabel[0], BorderLayout.NORTH);
        messagePanel.add(messageLabel[1], BorderLayout.CENTER);
        messagePanel.add(messageLabel[2], BorderLayout.SOUTH);
        add(messagePanel);
    }
    
    public static JLabel[] messageLabel(){
        return messageLabel;
    }
    
    public AddDataPanel(CourseAddPanel courseAddPanel){
        this.courseAddPanel = courseAddPanel;
    }
}
