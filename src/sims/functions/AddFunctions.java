package sims.functions;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import sims.loadPanels.CoursePanel;

public class AddFunctions {
    
    static final String DB_URL = "jdbc:mysql://localhost/SIMS";
    static final String USER = "root";
    static final String PASSWORD = "dominic";
    static String tempCourseName;
    static String tempCourseId;
    
    public static void addCourse(String courseName, String courseId, String unit, 
            String lecturer, int studyYear, String department){
        
        Connection conn = null;
        PreparedStatement prpstmt = null;
        Statement stmt = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            String query = "SELECT * FROM Abbrevs";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String[]> arrayList = new ArrayList<>();
            boolean addData = false;
            
            while (rs.next()){
                String id = rs.getString("CourseId");
                String name = rs.getString("CourseName");
                String [] array = {id, name};
                arrayList.add(array);
            }
            for (int i = 0; i < arrayList.size(); i++){
                if (!(arrayList.get(i)[0].equals(courseId)) && !(arrayList.get(i)[1].equals(courseName))){
                    addData = true;
                }
            }
            if (addData){
                String sql;
                sql = "INSERT INTO Abbrevs (CourseId, CourseName) VALUES (?, ?)";
                prpstmt = conn.prepareStatement(sql);
                prpstmt.setString(1, courseId);
                prpstmt.setString(2, courseName);
                prpstmt.execute();
                
                addUnit(unit, courseId, department, "Test Faculty", lecturer, studyYear);
                LoadFunctions.unitFrame(courseId);
            }
            
            
        } catch (Exception e) {
        }
    }
    
    public static void addStudent(int regNo, String[] nameArray, String gender, 
            int yearJoined, String courseId, LocalDate birthDay ){
        
        Connection conn = null;
        java.sql.Date b_Day = java.sql.Date.valueOf(birthDay);
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            String sql = "INSERT INTO Students VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            prpstmt.setInt(1, regNo);
            prpstmt.setString(2, nameArray[0]);
            prpstmt.setString(3, nameArray[1]);
            prpstmt.setString(4, nameArray[2]);
            prpstmt.setString(5, gender);
            prpstmt.setInt(6, yearJoined);
            prpstmt.setString(7, courseId);
            prpstmt.setDate(8, b_Day);
            
            prpstmt.execute();
            
            prpstmt.close();
            conn.close();
            
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void addUnit(String unit, String courseId, String faculty, String department, String lecturer, int studyYear){
        Connection conn = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            
            String sql = "INSERT INTO Units VALUES (?, ?, ?, ?, ? , ?);";
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            prpstmt.setString(1, unit);
            prpstmt.setString(2, courseId);
            prpstmt.setString(3, faculty);
            prpstmt.setString(4, department);
            prpstmt.setString(5, lecturer);
            prpstmt.setInt(6, studyYear);
            prpstmt.execute();
            
            prpstmt.close();
            conn.close();
            
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
