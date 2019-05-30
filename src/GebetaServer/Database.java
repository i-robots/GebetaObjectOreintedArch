package GebetaServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
    Connection con;
    public Database(){   
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gebeta","root","");
            if(con != null){
                System.out.println("connection established.");   
            }
        } catch (ClassNotFoundException | SQLException ex) {System.out.println("Error in connecting to Database.");}
    }
    
    void UpdateGameStatus(Connection con,int p1Score,int p2Score,String gamelist,int playerturn)throws SQLException{
                Statement st = con.createStatement();
                st.executeUpdate("insert into status values("+p1Score+","+p2Score+",'"+gamelist+"',"+playerturn+")");
                //con.close();      
    }
    int ReturnStatus(Connection con) throws SQLException{
        Statement st = con.createStatement();
        return st.executeUpdate("SELECT GameList FROM status");
    }
}
