
/* class that connect to DB  ,has stored user , pass and location of DB*/
package db;

import entites.Worker;
import fechingData.FechingData;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DBcon {
    FechingData fechingData = new FechingData();
    String base = fechingData.fechingDB();
            //"jdbc:mysql://localhost:3306/osoblje" +
            //after ? is code for ssl cript pass and for time zone  so you dont get timed out
              //        "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String user=fechingData.fechingUser();
    String pass = fechingData.fechingPassowrd(); //treba se dodati sifra...
    Connection con=getConnection(base, user, pass);
    Statement statement=con.createStatement();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Worker worker;
    public DBcon() throws SQLException {
    }

}