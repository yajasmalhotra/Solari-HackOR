package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:hackor.db"); // connect to database
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e+"");
        }
        return con;
    }

    public static void disconnect(Connection con){
        if (con != null){
            try{
                con.close();
            } catch (SQLException e){
                System.out.println(e);
            }
        }
    }
}
