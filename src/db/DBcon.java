
/* class that connect to DB  ,has stored user , pass and location of DB*/

package db;

import fechingData.FechingData;
import java.sql.*;
import static java.sql.DriverManager.getConnection;

/**
 * class for connection with DB here is i location to prop for db ,user ,and  pass
 */
public class DBcon {
    FechingData fechingData = new FechingData();
    String path ="C://branesStuff/programingStuff/FileName.txt";
    String base = fechingData.excractor("database",path);
            //"jdbc:mysql://localhost:3306/osoblje" +
            //after ? is code for ssl cript pass and for time zone  so you dont get timed out
              //        "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String user=fechingData.excractor("username",path);
    String pass = fechingData.excractor("password",path);
    Connection con;
    Statement statement;

    {
        try {
            con = getConnection(base, user, pass);
             statement=con.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
