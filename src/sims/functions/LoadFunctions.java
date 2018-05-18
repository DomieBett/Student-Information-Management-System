
package sims.functions;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.prompt.PromptSupport;

public class LoadFunctions {

    private static final String DB_URL = "jdbc:mysql://localhost/SIMS";
    private static final String USER = "root";
    private static final String PASSWORD = "dominic";
    
    private static ArrayList<Object[]> studentArray = new ArrayList<>();
    static ArrayList<Courses> courseArray = new ArrayList<>();
    private static ArrayList<JButton> buttonArray = new ArrayList<>();
    private static ArrayList<Object> unitArray = new ArrayList<>();
    private static ArrayList<String[]> departmentArray = new ArrayList<>();
    
    
    public static ArrayList<Courses> courseArray(){
        return courseArray;
    }
    
    public static ArrayList<String[]> departmentArray(){
        return departmentArray;
    }
    
    public static String courseId(int i){
        return courseArray.get(i).courseId;
    }
    
    public static String courseName(int i){
        return courseArray.get(i).courseName;
    }
    
    public static ArrayList<JButton> buttonArray(){
        return buttonArray;
    }
    
    public static int coursePos(String courseId){
        int coursePos = 0;
        for (int i = 0; i < courseArray.size(); i++){
            if (courseId(i).equalsIgnoreCase(courseId)){
                coursePos = i;
                return coursePos;
            }
        }
        return coursePos;
    }
    
    public static void loadButtonArray(){
        loadCourseArray();
        
        buttonArray = new ArrayList<>();
        int courseNumber = courseArray.size();
        JButton[] button = new JButton[courseNumber];
        
        
        for (int i = 0; i < button.length; i++){
            button[i] = new JButton(){
                {
                    setSize(Integer.MAX_VALUE, 40);
                    setMaximumSize(getSize());
                }
            };
            button[i].setText(courseArray.get(i).courseName + " (" + courseArray.get(i).courseId + ")");
            buttonArray.add(button[i]);
        }
    }
    
    public static void loadCourseArray() {
        courseArray = new ArrayList<>();
        Connection conn;
        Statement stmt;
        PreparedStatement prpstmt = null;
        PreparedStatement prpstmt2 = null;
        
        try{
            System.out.println("Creating connection...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            String unitSql = "SELECT Course, Faculty, Department FROM Units";
            String courseSql;
            String unitQuery;
            String courseName = "";
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(unitSql);
            ResultSet rs2 = null;
            ResultSet rs3 = null;
            
            ArrayList<String> units;
            ArrayList<String> lecturers;
            ArrayList<String> tempCourseArray = new ArrayList<>();
            
            while (rs.next()){
                String courseId = rs.getString("Course");
                String faculty = rs.getString("Faculty");
                String department = rs.getString("Department");
                String tempCourseName = "";
                
                units = new ArrayList<>();
                lecturers = new ArrayList<>();
                unitQuery = "SELECT Unit, Lecturer FROM Units WHERE Course=?";
                prpstmt2 = conn.prepareStatement(unitQuery);
                prpstmt2.setString(1, courseId);
                rs3 = prpstmt2.executeQuery();
                                
                while(rs3.next()){
                    String unit = rs3.getString("Unit");
                    String lecturer = rs3.getString("Lecturer");
                    units.add(unit);
                    lecturers.add(lecturer);
                }
                
                courseSql = "SELECT CourseName FROM Abbrevs WHERE CourseId=?;";
                prpstmt = conn.prepareStatement(courseSql);
                prpstmt.setString(1, courseId);
                rs2 = prpstmt.executeQuery();
                
                if(rs2.next()){
                    tempCourseName = rs2.getString("CourseName");
                }
                
                boolean toSave = true;
                for(int i = 0; i < tempCourseArray.size(); i++){
                    if (tempCourseName.equals(tempCourseArray.get(i))){
                        toSave = false;
                    }
                }
                if(toSave){
                    courseName = tempCourseName;
                    courseArray.add(new Courses(courseName, courseId, faculty, department, units, lecturers));
                    tempCourseArray.add(courseName);
                }
                
                units = new ArrayList<>();
            }
            tempCourseArray = new ArrayList<>();
            
            rs.close();
            
            if (rs2!=null)
                rs2.close();
            if (rs3!=null)
                rs3.close();
            if (prpstmt!=null)
                prpstmt.close();
            if (prpstmt2!=null)
                prpstmt2.close();
            
            stmt.close();
            conn.close();
        }catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }
    
    public static HashMap<String, String> comboCourses(){
        loadCourseArray();
        HashMap<String, String> comboCourses =  new HashMap<>();
        
        for (int i = 0; i < courseArray.size(); i++){
            comboCourses.put(courseArray.get(i).courseName, courseArray.get(i).courseId);
        }
        
        return comboCourses;
    }
    
    /* Creates a new JFrame user interface where the user can view and 
    manipulate Units for a specific course. It is called when a user submits
    a course in the CourseAddPanel or select the show unit button in the 
    CoursePanel
    */
    
    public static void unitFrame(String courseId){
        loadCourseArray();
        JFrame frame = new JFrame("Units");
        frame.setSize(600, 650);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(600, 650);
        mainPanel.setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setSize(580, 100);
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setForeground(Color.WHITE);
        
        JPanel unitPanel = new JPanel(){
            {
                setSize(580, 500);
                setMaximumSize(getSize());
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            }
        };
        
        JPanel holderPanel, labelPanel;
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setSize(580, 50);
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER,1,1));
        
        String course;
        int coursePos = 0;
        
        /*Determines the course and the course position of the current course
        and assigns these values to respective variables.
        */
        
        for (int i = 0; i < courseArray.size(); i++){
            if (courseArray.get(i).courseId.equalsIgnoreCase(courseId)){
                course = courseArray.get(i).courseId;
                coursePos = i;
            }
        }
        
        JLabel headerLabel = new JLabel();
        headerLabel.setText("Units for " + courseArray.get(coursePos).courseName);
        headerLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        headerPanel.add(headerLabel);
        
        for (int i = 0; i < courseArray.get(coursePos).units.size(); i++){
            holderPanel = new JPanel(){
                {
                    setSize(580, 30);
                    setMaximumSize(getSize());
                    setMinimumSize(getSize());
                }
            };
            holderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            holderPanel.setLayout(new BorderLayout());
            holderPanel.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
            
            labelPanel = new JPanel();
            JLabel label = new JLabel(courseArray.get(coursePos).units.get(i) + "      "
                    + "Lecturer: " + courseArray.get(coursePos).lecturers.get(i));
            JButton editButton = new JButton("Edit");
            editButton.setBackground(Color.GREEN);
            editButton.setSize(30, 20);
            
            label.setSize(Integer.MIN_VALUE, 30);
            label.setFont(new Font("Verdana", Font.PLAIN, 12));
            labelPanel.add(label);
            holderPanel.add(labelPanel, BorderLayout.WEST);
            holderPanel.add(editButton, BorderLayout.EAST);
            unitPanel.add(holderPanel);
        }
        
        JButton addButton = new Tools().createIconButton("/images/plus-main.png", "Add Unit");
        Tools.removeExtras(addButton);
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                unitPanel.add(unitHolderPanel(courseId, unitPanel));
                unitPanel.revalidate();
                unitPanel.repaint();
            }
        });
        bottomPanel.add(addButton);
        
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(unitPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    public static JPanel unitHolderPanel(String courseId, JPanel unitPanel){

        JPanel unitHolderPanel = new JPanel(){
            {
                setSize(580, 40);
                setMaximumSize(getSize());
                setMinimumSize(getSize());
            }
        };
        JTextField unitName = new JTextField(25);
        JTextField lecturerName = new JTextField(9);
        JTextField yearOfStudy = new JTextField(3);
        JPanel holderPanel = new JPanel();
        JPanel miniUnitPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        JButton closeButton = new JButton("X");
        JLabel instructions = new JLabel();
        unitHolderPanel.setLayout(new BorderLayout());
        
        miniUnitPanel.setSize(500, 20);
        unitName.setMaximumSize(unitName.getPreferredSize());
        PromptSupport.setPrompt("Enter Unit Name", unitName);
        unitName.setMinimumSize(unitName.getPreferredSize());
        yearOfStudy.setMaximumSize(unitName.getPreferredSize());
        PromptSupport.setPrompt("Year", yearOfStudy);
        yearOfStudy.setMinimumSize(unitName.getPreferredSize());
        lecturerName.setMaximumSize(lecturerName.getPreferredSize());
        lecturerName.setMinimumSize(lecturerName.getPreferredSize());
        PromptSupport.setPrompt("Lecturer", lecturerName);
        
        holderPanel.setSize(70, 20);
        submitButton.setSize(50, 20);
        closeButton.setSize(20, 20);
        submitButton.setBackground(Color.CYAN);
        closeButton.setBackground(Color.RED);
        
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String unit = unitName.getText();
                String lecturer = lecturerName.getText();
                int studyYear = Integer.parseInt(yearOfStudy.getText());
                int coursePos = coursePos(courseId);
                String faculty = courseArray.get(coursePos).faculty;
                String department = courseArray.get(coursePos).department;
                
                if (unit.length() > 0 && lecturer.length() > 0 && studyYear != 0){
                    AddFunctions.addUnit(unit, courseId, faculty, department, lecturer, studyYear);
                    loadCourseArray();
                    unitPanel.add(holderPanel(coursePos));
                    removePanel(unitPanel, unitHolderPanel);
                    unitPanel.revalidate();
                    unitPanel.repaint();
                }
            }
        });
        
        closeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removePanel(unitPanel, unitHolderPanel);
                unitPanel.revalidate();
                unitPanel.repaint();
            }
        });
        instructions.setSize(580, 10);
        instructions.setText("Enter the unit Name, Lecturer Name and year of study");
        
        miniUnitPanel.setLayout(new BorderLayout());
        holderPanel.setLayout(new BorderLayout());
        miniUnitPanel.add(unitName, BorderLayout.WEST);
        miniUnitPanel.add(lecturerName, BorderLayout.CENTER);
        miniUnitPanel.add(yearOfStudy, BorderLayout.EAST);
        holderPanel.add(submitButton, BorderLayout.WEST);
        holderPanel.add(closeButton, BorderLayout.EAST);
        unitHolderPanel.add(miniUnitPanel, BorderLayout.CENTER);
        unitHolderPanel.add(holderPanel, BorderLayout.EAST);
        unitHolderPanel.add(instructions, BorderLayout.SOUTH);
        
        return unitHolderPanel;
    }
    
    public static JPanel holderPanel(int coursePos){
        JPanel holderPanel;
        JPanel labelPanel;
        holderPanel = new JPanel(){
            {
                setSize(580, 30);
                setMaximumSize(getSize());
                setMinimumSize(getSize());
            }
        };
        holderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        holderPanel.setLayout(new BorderLayout());
        holderPanel.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));

        labelPanel = new JPanel();
        int unitsSize = courseArray.get(coursePos).units.size();
        int lecturersSize = courseArray.get(coursePos).lecturers.size();
        JLabel label = new JLabel(courseArray.get(coursePos).units.get(unitsSize-1) + "      "
                + "Lecturer: " + courseArray.get(coursePos).lecturers.get(lecturersSize-1));
        JButton editButton = new JButton("Edit");
        editButton.setBackground(Color.GREEN);
        editButton.setSize(30, 20);
        label.setSize(Integer.MIN_VALUE, 30);
        label.setFont(new Font("Verdana", Font.PLAIN, 12));
        labelPanel.add(label);
        holderPanel.add(labelPanel, BorderLayout.WEST);
        holderPanel.add(editButton, BorderLayout.EAST);
        return holderPanel;
    }
    
    public static void removePanel(JPanel unitPanel, JPanel unitHolderPanel){
        unitPanel.remove(unitHolderPanel);
    }
    
    /*Creates a panel that displays students taking a specific course. 
    Displays all students if argument passed is the String "all"*/
    
    public static JPanel studentPanel(String courseId){
        loadStudentArray(courseId);
        
        JPanel panel = new JPanel();
        JTable table = studentTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.setLayout(new BorderLayout());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /*Retrieves data from studentsArray and creates the table that displays the students*/
    
    public static JTable studentTable(){
        JTable table;
        DefaultTableModel tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        String[] col = {"Reg No,", "Name", "Age", "Gender", "Course", "Year of Study"};
        for (int i = 0; i < col.length; i++){
            tableModel.addColumn(col[i]);
        }
        
        for (int i = 0; i < studentArray.size(); i++){
            tableModel.addRow(studentArray.get(i));
        }
        
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
        
    }
    
    /*Loads up the array studentArray ArrayList*/
    
    public static void loadStudentArray(String courseId){
        
        Connection conn = null;
        PreparedStatement prpstmt = null;
        Statement stmt = null;
        studentArray = new ArrayList<>();
        
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            System.out.println("Creating connection...");
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            ResultSet rs = null;
            String sql;
            if (courseId.equals("all")){
                sql = "SELECT RegNo, Surname, FirstName, SecondName, Gender, Course, YearJoined, BirthDay FROM Students ORDER BY RegNo ASC;";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
            }
            else{
                sql = "SELECT RegNo, Surname, FirstName, SecondName, Gender, Course, YearJoined, BirthDay FROM Students WHERE Course =? ORDER BY RegNo ASC;";
                prpstmt = conn.prepareStatement(sql);
                prpstmt.setString(1, courseId);

                rs = prpstmt.executeQuery();
            }
            
            int regNo, age, yearJoined, studyYear;
            String surName, firstName, secondName, gender, fullRegNo, course;
            Object yearOfStudy;
            
            
            while (rs.next()){
                System.out.println("Retrieving data...");
                
                regNo = rs.getInt("RegNo");
                surName = rs.getString("Surname");
                firstName = rs.getString("FirstName");
                secondName = rs.getString ("SecondName");
                String name = surName + " " + firstName + " " + secondName;
                LocalDate birthDay = rs.getDate("BirthDay").toLocalDate();
                LocalDate now = LocalDate.now();
                Period period = Period.between(birthDay, now);
                age = period.getYears();
                gender = rs.getString("Gender");
                yearJoined = rs.getInt("yearJoined");
                studyYear = now.getYear() - rs.getInt("YearJoined");
                if (studyYear > 4){
                    yearOfStudy = "Completed" + " (" + (yearJoined +4) + ")";
                }
                else{
                    yearOfStudy = studyYear;
                }
                course = rs.getString("Course");
                fullRegNo = course + "/ 00" + regNo + "/" + yearJoined;
                
                Object[] data = {fullRegNo, name, age, gender, course, yearOfStudy};
                studentArray.add(data);
            }
            
            System.out.println("Closing connection...");
            rs.close();
            conn.close();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
}

class Courses{
    String courseName;
    String courseId;
    String faculty;
    String department;
    ArrayList<String> units;
    ArrayList<String> lecturers;
    
    Courses(){
        
    }
    Courses(String courseName, String courseId, String faculty, String department, ArrayList<String> units, ArrayList<String> lecturers){
        this.courseName = courseName;
        this.courseId = courseId;
        this.faculty = faculty;
        this.department = department;
        this.units = units;
        this.lecturers = lecturers;
    }
    
    public String courseName(){
        return courseName();
    }
}