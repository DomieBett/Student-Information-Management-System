package sims.addPanels;

import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import sims.functions.LoadFunctions;

public class StudentAddPanelBackUp extends JPanel {
    JPanel[] studentMiniPanel = new JPanel[7];
    JLabel[] label = new JLabel[8];
    private final String[] labelTags = {"Reg No", "Surname", "FirstName", 
        "SecondName", "Date of Birth", "Gender", "Course", "Year Of Study"};
    JTextField[] studentTextField = new JTextField[3];
    JComboBox[] dateOfBirth = new JComboBox[3];
    JRadioButton[] studentRadioButton = new JRadioButton[2];
    JComboBox studentCourseComboBox = new JComboBox();
    JComboBox studentYearOfStudy = new JComboBox();
    GridLayout gridLayout = new GridLayout(7, 1);
    BorderLayout borderLayout = new BorderLayout();
    ButtonGroup buttonGroup = new ButtonGroup();
    MatteBorder matteBorder = new MatteBorder(0, 0, 1, 0, Color.GRAY);
    JButton submitButton = new JButton("Submit...");
    JButton allButton = new JButton("Display All");
    
    StudentAddPanelBackUp(){
        setLayout(gridLayout);
        JPanel regNoPanel = new JPanel();
        JPanel agePanel = new JPanel();
        JPanel namePanel = new JPanel();
        JPanel studentCoursePanel = new JPanel();
        JPanel studentYearPanel = new JPanel();
        HashMap<String, String> comboCourses = LoadFunctions.comboCourses();
        
        Set<String> set = comboCourses.keySet();
        Iterator<String> iterator = set.iterator();
        studentCourseComboBox.addItem("~Select~");
        while(iterator.hasNext()){
            String key = iterator.next();
            studentCourseComboBox.addItem(key);
        }
        
        for (int i = 0; i < studentMiniPanel.length; i++){
            studentMiniPanel[i] = new JPanel();
            studentMiniPanel[i].setPreferredSize(new Dimension(810, 30));
            studentMiniPanel[i].setBorder(matteBorder);
        }
        
        for (int i = 0; i < label.length; i++){
            label[i] = new JLabel();
            label[i].setText(labelTags[i]);
            label[i].setPreferredSize(new Dimension(200,50));
        }
        
        for (int i = 0; i < studentTextField.length; i++){
            studentTextField[i] = new JTextField();
            if (i == 0)
                studentTextField[i].setPreferredSize(new Dimension(600, 25));
            if (i>0 && i <=2)
                studentTextField[i].setPreferredSize(new Dimension(300, 25));
        }
        
        for (int i = 0; i < dateOfBirth.length; i++){
            dateOfBirth[i] = new JComboBox();
            dateOfBirth[i].setPreferredSize(new Dimension(198, 25));
        }
        
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        
        for (int i = currentYear - 100; i <= currentYear; i++){
            dateOfBirth[0].addItem(i);
        }
        dateOfBirth[0].setSelectedItem(2017);
        
        for (Month m: Month.values()){
            dateOfBirth[1].addItem(m);
        }
        for(int i = 1; i <= 31; i++){
            dateOfBirth[2].addItem(i);
        }
        
        int year = (int) dateOfBirth[0].getSelectedItem();
        Month currentMonth = (Month) dateOfBirth[1].getSelectedItem();
        int date = (int) dateOfBirth[2].getSelectedItem();
        
        Calendar cal = new GregorianCalendar(year, currentMonth.getValue()-1, date);
        
        int birthYear = (int) dateOfBirth[0].getSelectedItem();
        Month birthMonth = (Month) dateOfBirth[1].getSelectedItem();
        int birthDate = (int) dateOfBirth[2].getSelectedItem();
        LocalDate birthDay = LocalDate.of(birthYear, birthMonth, birthDate);
        
        for (int i = 0; i < studentRadioButton.length; i++){
            if (i == 0){
                studentRadioButton[i] = new JRadioButton(Gender.male());
                studentRadioButton[i].setActionCommand(Gender.male());
            }
            if (i == 1){
                studentRadioButton[i] = new JRadioButton(Gender.female());
                studentRadioButton[i].setActionCommand(Gender.female());
            }
            buttonGroup.add(studentRadioButton[i]);
        }
        
        studentMiniPanel[0].setLayout(new BorderLayout());
        studentMiniPanel[0].add(label[0], BorderLayout.WEST);
        regNoPanel.add(studentTextField[0]);
        studentMiniPanel[0].add(regNoPanel, BorderLayout.CENTER);
        
        studentMiniPanel[1].setLayout(new BorderLayout());
        studentMiniPanel[1].add(label[1], BorderLayout.WEST);
        for(int i = 1; i <=2; i++)
            namePanel.add(studentTextField[i]);
        studentMiniPanel[1].add(namePanel, BorderLayout.CENTER);
        
        studentMiniPanel[2].setLayout(new BorderLayout());
        studentMiniPanel[2].add(label[2], BorderLayout.WEST);
        for (int i = 0; i < dateOfBirth.length; i++)
            agePanel.add(dateOfBirth[i]);
        studentMiniPanel[2].add(agePanel, BorderLayout.CENTER);
        
        studentMiniPanel[3].setLayout(new BorderLayout());
        studentMiniPanel[3].add(label[3], BorderLayout.WEST);
        JPanel radioPanel = new JPanel();
        for(int i = 0; i < studentRadioButton.length; i++)
            radioPanel.add(studentRadioButton[i]);
        studentMiniPanel[3].add(radioPanel, BorderLayout.CENTER);
        
        studentMiniPanel[4].setLayout(new BorderLayout());
        studentMiniPanel[4].add(label[4], BorderLayout.WEST);
        studentCourseComboBox.setPreferredSize(new Dimension(600, 25));
        studentCoursePanel.add(studentCourseComboBox);
        studentMiniPanel[4].add(studentCoursePanel, BorderLayout.CENTER);
        
        studentMiniPanel[5].setLayout(new BorderLayout());
        studentMiniPanel[5].add(label[5], BorderLayout.WEST);
        studentYearOfStudy.setPreferredSize(new Dimension(600, 25));
        studentYearPanel.add(studentYearOfStudy);
        studentMiniPanel[5].add(studentYearPanel,BorderLayout.CENTER);
        
        studentMiniPanel[6].setLayout(new BorderLayout());
        submitButton.setPreferredSize(new Dimension(400, 30));
        submitButton.setMargin(new Insets(0,0,0,0));
        submitButton.setBackground(Color.CYAN);
        allButton.setPreferredSize(new Dimension(400, 30));
        allButton.setMargin(new Insets(0,0,0,0));
        allButton.setBackground(Color.GREEN);
        studentMiniPanel[6].add(submitButton,BorderLayout.WEST);
        studentMiniPanel[6].add(allButton,BorderLayout.EAST);
        
        
        for (JPanel miniPanel : studentMiniPanel) {
            add(miniPanel);
        }
    }
}

class GenderBackUp{
   
    GenderBackUp(){
        
    }
    public static String male(){
        return "Male";
    }
    public static String female(){
        return "Female";
    }
}

class AgeBackUp{
    AgeBackUp(){
        
    }
}