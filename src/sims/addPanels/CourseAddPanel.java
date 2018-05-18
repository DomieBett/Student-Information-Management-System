
package sims.addPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jdesktop.swingx.prompt.PromptSupport;
import sims.functions.AddFunctions;
import sims.functions.LoadFunctions;

public class CourseAddPanel extends JPanel{
    JLabel label = new JLabel();
    JLabel label2 = new JLabel();
    JTextField courseTextField = new JTextField();
    JTextField courseIdField = new JTextField();
    JTextField unitName = new JTextField(25);
    JTextField lecturerName = new JTextField(9);
    JComboBox yearOfStudy = new JComboBox();
    JComboBox departmentCombo = new JComboBox();
    public JButton courseViewButton = new JButton();
    JButton submitButton = new JButton("Submit...");
    JPanel[] panel = new JPanel[7];
    JPanel holderPanel = new JPanel();
    JPanel miniUnitPanel = new JPanel();
    JLabel instructions = new JLabel();
    
    public CourseAddPanel(){
        setLayout(new GridLayout(7, 1));
        
        for (int i = 0; i < panel.length; i++){
            panel[i] = new JPanel();
            panel[i].setPreferredSize(new Dimension(800, 40));
        }
       
        label.setSize(800, 100);
        label.setText("Enter the course Name, course Code, then SUBMIT...");
        panel[0].add(label);
        
        courseTextField.setPreferredSize(new Dimension(400, 30));
        PromptSupport.setPrompt("Enter Course Name", courseTextField);
        courseIdField.setPreferredSize(new Dimension(60, 30));
        PromptSupport.setPrompt("Course Code", courseIdField);
        submitButton.setPreferredSize(new Dimension(100, 30));
        panel[1].setLayout(new FlowLayout());
        panel[1].add(courseTextField);
        panel[1].add(courseIdField);
        panel[1].add(submitButton);
        
        departmentCombo.addItem("~Department~");
        for (int i = 0; i < LoadFunctions.departmentArray().size(); i++){
            String department = LoadFunctions.departmentArray().get(i)[0];
            departmentCombo.addItem(department);
        }
        departmentCombo.setPreferredSize(new Dimension(400, 30));
        panel[2].setLayout(new FlowLayout());
        panel[2].add(departmentCombo);
        
        instructions.setText("<html>You must enter atleast one unit below to submit a course.<br>"
                + "Enter the unit Name, Lecturer Name and year of study</html>");
        panel[3].add(instructions);
        
        miniUnitPanel.setSize(500, 20);
        unitName.setPreferredSize(new Dimension(400, 30));
        PromptSupport.setPrompt("Enter First Unit", unitName);
        lecturerName.setPreferredSize(new Dimension(100, 30));
        PromptSupport.setPrompt("Lecturer", lecturerName);
        yearOfStudy.setPreferredSize(new Dimension(50, 30));
        for( int i = 1; i < 5; i++){
            yearOfStudy.addItem(i);
        }
        holderPanel.setSize(50, 20);
        submitButton.setSize(50, 20);
        submitButton.setBackground(Color.CYAN);
        holderPanel.setLayout(new BorderLayout());
        miniUnitPanel.add(unitName);
        miniUnitPanel.add(lecturerName);
        miniUnitPanel.add(yearOfStudy);
        holderPanel.add(submitButton, BorderLayout.WEST);
        panel[4].setLayout(new FlowLayout());
        panel[4].add(miniUnitPanel);
        panel[4].add(holderPanel);
        
        label2.setText("View courses added to the database...");
        label2.setSize(800, 100);
        panel[5].add(label2);
        
        courseViewButton.setPreferredSize(new Dimension(400, 50));
        courseViewButton.setBackground(Color.CYAN);
        courseViewButton.setText("View Courses");
        panel[6].add(courseViewButton);
        JTextField [] textFields = {courseTextField, courseIdField, unitName, lecturerName};
        
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String courseName = courseTextField.getText();
                String courseId = courseIdField.getText();
                String unit = unitName.getText();
                String lecturer = lecturerName.getText();
                int studyYear = (int) yearOfStudy.getSelectedItem();
                String department = (String) departmentCombo.getSelectedItem();
                String[] arrayForChecker = {courseName, courseId, unit, lecturer, department};
                if (checker(arrayForChecker, studyYear)){
                    AddFunctions.addCourse(courseName, courseId, unit, lecturer, studyYear, department);
                    resetter(textFields);
                }
            }
        });
        
        add(panel[0]);
        add(panel[1]);
        add(panel[2]);
        add(panel[3]);
        add(panel[4]);
        add(panel[5]);
        add(panel[6]);
        
    }
    
    public static boolean checker(String [] array, int studyYear){
        boolean det = false;
        
        if((array[0].length() > 1) && (array[2].length() > 1) && !(array[4].equals("~Department~"))
                && (array[3].length() > 1) && array[1].length() == 3 && (studyYear > 0)){
            det = true;
        }
        
        return det;
    }
    
    public static void resetter (JTextField [] textField){
        for (JTextField textFld: textField){
            textFld.setText("");
        }
    }
    CourseAddPanel(JButton submitButton, JButton courseViewButton, JTextField courseTextField, JTextField courseId){
        this.courseViewButton = courseViewButton;
    }
}
