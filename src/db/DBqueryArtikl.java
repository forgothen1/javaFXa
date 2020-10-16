package db;

import entites.Articles;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBqueryArtikl extends DBcon {
    Articles articles;
    List<Articles> artikle_collection = new ArrayList<>();
    public DBqueryArtikl() throws SQLException {
    }
    /*method that do loging for geting data from DB used by other methods*/
    public void settingForOuput(String sqlQuerry) throws SQLException {
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {
            articles= new Articles(null,resultSet.getString("name"),resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"),resultSet.getString("description"),
                    resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"));
            artikle_collection.add(articles);
        }
    }
    /*method that  have essential for imputing into DB*/
    public void settingForInput(String sqlQuerry, Articles articles) throws SQLException {
        int i =1;
        preparedStatement= con.prepareStatement(sqlQuerry);
        System.out.println("///////////////");
        System.out.println(articles);
        preparedStatement.setString(i++,articles.getName());
        preparedStatement.setString(i++,articles.getSerialNumber());
        preparedStatement.setInt(i++,articles.getIdArticles());
        preparedStatement.setString(i++,articles.getDescription());
        preparedStatement.setInt(i++,articles.getQuantity());
        preparedStatement.setFloat(i,articles.getPrice());
        preparedStatement.executeUpdate();
    }
            /* loading all data from artikle table to table in gui*/
    public List<Articles> gettingAllArtikles() throws SQLException {
        String sqlQuerry = "select  name, serialNumber, idArtickle, description, quantity, quantityInUse, price  from artikli ";
        System.out.println(sqlQuerry);
        settingForOuput(sqlQuerry);
        return artikle_collection;
    }
    /*this allows to add new artickle in DB*/
    public void addingToArticles(Articles articles) throws SQLException {
        String sqlQuerry = "insert into artikli (name,serialNumber,idArtickle,description,quantity,price) values (?,?,?,?,?,?)";
       settingForInput(sqlQuerry,articles);
    }


    // edditing  existing  article probably gona need refactoring
    public void editingArticle(Articles articles,String variableForSearch) throws SQLException {

        String sqlQuerry = "update artikli set name=? , serialNumber=? , idArtickle=? , description=? , quantity=? , price=? " +
                " where serialNumber='"+variableForSearch+"'";
        System.out.println(sqlQuerry);
     settingForInput(sqlQuerry,articles);
    }
    /*this search allow to get specific article by serialnumber, had to be sepereted becouse of probility of crosing
    numbers or letters in serialnumber or name / description /idArticle*/
    public  List<Articles> searchBySerialNumber( String variableForSearch) throws SQLException {
        artikle_collection.clear();
        String sqlQuerry = "SELECT name, serialNumber, idArtickle, description, quantity, quantityInUse, price FROM" +
                " artikli where serialNumber='"+variableForSearch+"'";
        System.out.println(sqlQuerry);
        settingForOuput(sqlQuerry);
        return artikle_collection;
    }

    /* searching  artickles by name , description ,serial number or id by part of word*/
    public List<Articles> searchArticles(String variableForSearch) throws SQLException {
        artikle_collection.clear();
        String sqlQuerry = "SELECT name, serialNumber, idArtickle, description, quantity, quantityInUse, price FROM artikli " +
                "WHERE name like '%"+ variableForSearch + "%' or serialNumber like '%"+variableForSearch+"%' or idArtickle  like" +
                "'%"+variableForSearch+"%' or description like '%"+variableForSearch+"%'";
        System.out.println(sqlQuerry);
        settingForOuput(sqlQuerry);
        return artikle_collection;
    }
}
