package sims.addPanels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.time.Month;
import java.time.Period;
import java.util.Calendar;
import java.util.GregorianCalendar;
import sims.functions.AddFunctions;
import sims.functions.LoadFunctions;

public class StudentAddPanel extends JPanel {
    JPanel[] studentMiniPanel = new JPanel[9];
    JLabel[] label = new JLabel[8];
    private final String[] labelTags = {"Reg No", "Surname", "FirstName", 
        "SecondName", "Date of Birth", "Gender", "Course", "Year Of Study"};
    JTextField[] studentTextField = new JTextField[4];
    JComboBox[] dateOfBirth = new JComboBox[3];
    JRadioButton[] studentRadioButton = new JRadioButton[2];
    JComboBox studentCourseComboBox = new JComboBox();
    JComboBox studentYearJoined = new JComboBox();
    GridLayout gridLayout = new GridLayout(7, 1);
    BorderLayout borderLayout = new BorderLayout();
    ButtonGroup buttonGroup = new ButtonGroup();
    MatteBorder matteBorder = new MatteBorder(0, 0, 1, 0, Color.GRAY);
    JButton submitButton = new JButton("Submit...");
    JButton allButton = new JButton("Display All");
    
    StudentAddPanel(){
        setLayout(new BorderLayout());
        JPanel regNoPanel = new JPanel();
        JPanel agePanel = new JPanel();
        JPanel namePanel[] = new JPanel[3];
        JPanel studentCoursePanel = new JPanel();
        JPanel studentYearPanel = new JPanel();
        JPanel holderPanel[] = new JPanel[2];
        JPanel splitPanel = new JPanel(){
            {
                setMaximumSize(new Dimension(10, Integer.MAX_VALUE));
                setMinimumSize(new Dimension(10, Integer.MAX_VALUE));
            }
        };
        JLabel[] messageLabel = AddDataPanel.messageLabel();
        
        
        for (int i = 0; i < holderPanel.length; i++){
            holderPanel[i] = new JPanel();
            holderPanel[i].setLayout(gridLayout);
        }
        splitPanel.setBorder(new MatteBorder(0,1,0,0, Color.BLACK));
        for (int i = 0; i < namePanel.length; i++){
            namePanel[i] = new JPanel();
        }
        
        /*Basic Instantiating, setting Dimensions and labelTags for miniPanels, 
        labels, and textfields.
        */
        
        for (int i = 0; i < studentMiniPanel.length; i++){
            studentMiniPanel[i] = new JPanel();
            studentMiniPanel[i].setPreferredSize(new Dimension(400, 40));
            studentMiniPanel[i].setBorder(matteBorder);
        }
        
        for (int i = 0; i < label.length; i++){
            label[i] = new JLabel();
            label[i].setText(labelTags[i]);
            label[i].setPreferredSize(new Dimension(150,50));
        }
        
        for (int i = 0; i < studentTextField.length; i++){
            studentTextField[i] = new JTextField();
            if (i == 0)
                studentTextField[i].setPreferredSize(new Dimension(250, 25));
            if (i>0 && i <=3)
                studentTextField[i].setPreferredSize(new Dimension(250, 25));
        }
        
        /*Getting and adding courses to the course Combo Box*/
        
        HashMap<String, String> comboCourses = LoadFunctions.comboCourses();
        
        Set<String> set = comboCourses.keySet();
        Iterator<String> iterator = set.iterator();
        studentCourseComboBox.addItem("~Select~");
        while(iterator.hasNext()){
            String key = iterator.next();
            studentCourseComboBox.addItem(key);
        }
        
        /*Below the code that assigns the dates to the Date of Birth JComboBox */
        
        for (int i = 0; i < dateOfBirth.length; i++){
            dateOfBirth[i] = new JComboBox();
        }
        dateOfBirth[0].setPreferredSize(new Dimension(80, 25));
        dateOfBirth[1].setPreferredSize(new Dimension(100, 25));
        dateOfBirth[2].setPreferredSize(new Dimension(50, 25));
        
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        
        for (int i = currentYear - 100; i <= currentYear; i++){
            dateOfBirth[0].addItem(i);
        }
        dateOfBirth[0].setSelectedItem(currentYear);
        
        for (Month m: Month.values()){
            dateOfBirth[1].addItem(m);
        }
        for(int i = 1; i <= 31; i++){
            dateOfBirth[2].addItem(i);
        }
        
        for (int i = currentYear -10; i <= currentYear; i++){
            studentYearJoined.addItem(i);
        }
        studentYearJoined.setSelectedItem(currentYear);
        
        int year = (int) dateOfBirth[0].getSelectedItem();
        Month currentMonth = (Month) dateOfBirth[1].getSelectedItem();
        int date = (int) dateOfBirth[2].getSelectedItem();
        
        Calendar cal = new GregorianCalendar(year, currentMonth.getValue()-1, date);
        
        /*Assingment of values into the gender radio Buttons*/
        
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
        
        /*Below starts the assignment of textfields for name, and Reg No into respective minipanels*/
        
        studentMiniPanel[0].setLayout(new BorderLayout());
        studentMiniPanel[0].add(label[0], BorderLayout.WEST);
        regNoPanel.add(studentTextField[0]);
        studentMiniPanel[0].add(regNoPanel, BorderLayout.CENTER);
        
        studentMiniPanel[1].setLayout(new BorderLayout());
        studentMiniPanel[1].add(label[1], BorderLayout.WEST);
        namePanel[0].add(studentTextField[1]);
        studentMiniPanel[1].add(namePanel[0], BorderLayout.CENTER);
        
        studentMiniPanel[2].setLayout(new BorderLayout());
        studentMiniPanel[2].add(label[2], BorderLayout.WEST);
        namePanel[1].add(studentTextField[2]);
        studentMiniPanel[2].add(namePanel[1], BorderLayout.CENTER);
        
        studentMiniPanel[3].setLayout(new BorderLayout());
        studentMiniPanel[3].add(label[3], BorderLayout.WEST);
        namePanel[2].add(studentTextField[3]);
        studentMiniPanel[3].add(namePanel[2], BorderLayout.CENTER);
        
        /*Below starts the assingning of Combo Boxes and radio Buttons into respective minipanels*/
        
        studentMiniPanel[4].setLayout(new BorderLayout());
        studentMiniPanel[4].add(label[4], BorderLayout.WEST);
        for (int i = 0; i < dateOfBirth.length; i++)
            agePanel.add(dateOfBirth[i]);
        studentMiniPanel[4].add(agePanel, BorderLayout.CENTER);
        
        studentMiniPanel[5].setLayout(new BorderLayout());
        studentMiniPanel[5].add(label[5], BorderLayout.WEST);
        JPanel radioPanel = new JPanel();
        for(int i = 0; i < studentRadioButton.length; i++)
            radioPanel.add(studentRadioButton[i]);
        studentMiniPanel[5].add(radioPanel, BorderLayout.CENTER);
        
        studentMiniPanel[6].setLayout(new BorderLayout());
        studentMiniPanel[6].add(label[6], BorderLayout.WEST);
        studentCourseComboBox.setPreferredSize(new Dimension(250, 25));
        studentCoursePanel.add(studentCourseComboBox);
        studentMiniPanel[6].add(studentCoursePanel, BorderLayout.CENTER);
        
        studentMiniPanel[7].setLayout(new BorderLayout());
        studentMiniPanel[7].add(label[7], BorderLayout.WEST);
        studentYearJoined.setPreferredSize(new Dimension(250, 25));
        studentYearPanel.add(studentYearJoined);
        studentMiniPanel[7].add(studentYearPanel,BorderLayout.CENTER);
        
        /*The buttons for submit and display all are added below into their minipanel*/
        
        studentMiniPanel[8].setLayout(new BorderLayout());
        allButton.setPreferredSize(new Dimension(400, 30));
        allButton.setMargin(new Insets(0,0,0,0));
        allButton.setBackground(Color.CYAN);
        submitButton.setPreferredSize(new Dimension(400, 30));
        submitButton.setMargin(new Insets(0,0,0,0));
        submitButton.setBackground(Color.GREEN);
        studentMiniPanel[8].add(allButton,BorderLayout.WEST);
        studentMiniPanel[8].add(submitButton,BorderLayout.EAST);
        
        /*Adding action listeners for the submit button*/
        
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int regNo;
                if(!studentTextField[0].getText().equals("")){
                    regNo = Integer.parseInt(studentTextField[0].getText());
                }
                else{
                    regNo = 0;
                }
                String surName = studentTextField[1].getText();
                String firstName = studentTextField[2].getText();
                String secondName = studentTextField[3].getText();
                String[] nameArray = {surName, firstName, secondName};
                
                boolean text = textChecker(regNo, surName, firstName, secondName);
                
                int birthYear = (int) dateOfBirth[0].getSelectedItem();
                Month birthMonth = (Month) dateOfBirth[1].getSelectedItem();
                int birthDate = (int) dateOfBirth[2].getSelectedItem();
                LocalDate birthDay = LocalDate.of(birthYear, birthMonth, birthDate);
                LocalDate now = LocalDate.now();
                Period period = Period.between(birthDay, now);
                int years = period.getYears();
                int months = period.getMonths();
                int days = period.getDays();
                
                String gender = "";
                if (!(buttonGroup.getSelection().getActionCommand() == null)){
                    gender = buttonGroup.getSelection().getActionCommand();
                }
                else{
                    gender = "";
                }
                boolean genderDet = genderChecker(gender);
                
                String courseName = (String) studentCourseComboBox.getSelectedItem();
                String courseId = comboCourses.get(courseName);
                boolean data = yearChecker(currentYear, birthYear, courseName);
                
                int yearJoined = (int) studentYearJoined.getSelectedItem();
                
                for(int i = 0; i < messageLabel.length; i++){
                    messageLabel[i].setText("");
                }
                if (data && genderDet && text){
                    AddFunctions.addStudent(regNo, nameArray, gender, yearJoined, courseId, birthDay);
                    resetDefaults(currentYear, studentTextField, dateOfBirth, buttonGroup, studentCourseComboBox, studentYearJoined);
                    messageLabel[0].setForeground(Color.GREEN);
                    messageLabel[0].setText("Succesfull");
                }
                if (!text){
                    messageLabel[0].setForeground(Color.RED);
                    messageLabel[0].setText("Check your RegNo and Name for Errors");
                }
                if (!genderDet){
                    System.out.println("Gender Error");
                }
                if (!data){
                    messageLabel[2].setText("Check your BirthDay, Gender, course and year joined for errors");
                }
                
                
            }
        });
        
        /*Finalising addition of minipanels => holderPanels => the current Class Panel*/
        
        for (int i = 0; i < 4; i++) {
            holderPanel[0].add(studentMiniPanel[i]);
        }
        for (int i = 4; i < studentMiniPanel.length -1; i++){
            holderPanel[1].add(studentMiniPanel[i]);
        }
        
        add(studentMiniPanel[studentMiniPanel.length-1], BorderLayout.SOUTH);
        add(holderPanel[0], BorderLayout.WEST);
        add(splitPanel, BorderLayout.CENTER);
        add(holderPanel[1], BorderLayout.EAST);
    }
    
    public static boolean textChecker(int regNo, String surName, String firstName, String secondName){
        if (regNo < 1 || surName.length() < 2 || firstName.length() < 2 || secondName.length() < 2){
            return false;
        }
        return true;
    }
    
    public static boolean yearChecker(int currentYear, int birthYear, String course){
        if (birthYear > currentYear-8 || course.equals("~Select~")){
            return false;
        }
        return true;
    }
    
    public static boolean genderChecker(String gender){
        return (!gender.equals(Gender.male()) || !gender.equals(Gender.female()));
    }
    
    public static void resetDefaults(int currentYear, JTextField studentTextField[], JComboBox dateOfBirth[], 
            ButtonGroup buttonGroup, JComboBox studentComboBox, JComboBox studentYearJoined){
        
        for (int i = 0; i < studentTextField.length; i++){
            studentTextField[i].setText("");
        }
        dateOfBirth[0].setSelectedIndex(0);
        dateOfBirth[1].setSelectedIndex(0);
        dateOfBirth[2].setSelectedIndex(0);
        
        studentComboBox.setSelectedIndex(0);
        studentYearJoined.setSelectedIndex(0);
    }
}

class Gender{
   
    Gender(){
        
    }
    public static String male(){
        return "Male";
    }
    public static String female(){
        return "Female";
    }
}

class Age{
    Age(){
        
    }
}