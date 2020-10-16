package db;

import entites.Articles;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBqueryArtikl extends DBcon {
    Articles articles;
    private String variableForSearch;
    List<Articles> artikle_collection = new ArrayList<>();
    public DBqueryArtikl() throws SQLException {
    }
            /* loading all data from artikle table to table in gui*/
    public List<Articles> gettingAllArtikles() throws SQLException {

        String writeOut = "select  name, serialNumber, idArtickle, description, quantity, quantityInUse, price  from artikli ";
        resultSet = statement.executeQuery(writeOut);
        while (resultSet.next()) {
            Integer id = null;
            // za poslije prebacivanje  na GUI i vizual
            articles = new Articles(id, resultSet.getString("name"), resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"), resultSet.getString("description"), resultSet.getInt("quantity"),
                    resultSet.getInt("quantityInUse"),resultSet.getFloat("price"));
            artikle_collection.add(articles);
            /* this allow to get all collums from DB, i<=5 is for id,name,surname,payment, workplace, idworker
             * for console  */
            for (int i = 1; i <= 5; i++) {
                System.out.print(resultSet.getString(i) + ", ");
            }
            System.out.println();

        }
        return artikle_collection;
    }
    /*this allows to add new artickle in DB*/
    public void addingToArticles(Articles articles) throws SQLException {
        String sqlQuerry = "insert into artikli (name,serialNumber,idArtickle,description,quantity,price) values (?,?,?,?,?,?)";
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
    //method that helps load searching paremeter
    public void LoaderForSearch(String optionWhatToSearch)
    {variableForSearch=optionWhatToSearch;}

    // edditing  existing  article probably gona need refactoring
    public void editingArticle(Articles articles) throws SQLException {
        int i=1;
        String sqlQuerry = "update artikli set name=? , serialNumber=? , idArtickle=? , description=? , quantity=? , price=? " +
                " where serialNumber='"+variableForSearch+"'";
        System.out.println(sqlQuerry);
        System.out.println(articles.toString());
        preparedStatement= con.prepareStatement(sqlQuerry);
        preparedStatement.setString(i++,articles.getName());
        preparedStatement.setString(i++,articles.getSerialNumber());
        preparedStatement.setInt(i++,articles.getIdArticles());
        preparedStatement.setString(i++,articles.getDescription());
        preparedStatement.setInt(i++,articles.getQuantity());
        preparedStatement.setFloat(i,articles.getPrice());
        preparedStatement.executeUpdate();
    }
    /*this search allow to get specific article by serialnumber, had to be sepereted becouse of probility of crosing
    numbers or letters in serialnumber or name / description /idArticle*/
    public  List<Articles> searchBySerialNumber() throws SQLException {
        artikle_collection.clear();
        String sqlQuerry = "SELECT name, serialNumber, idArtickle, description, quantity, quantityInUse, price FROM" +
                " artikli where serialNumber='"+variableForSearch+"'";
        System.out.println(sqlQuerry);
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {
            articles= new Articles(null,resultSet.getString("name"),resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"),resultSet.getString("description"),
                    resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"));
            artikle_collection.add(articles);
        }
        return artikle_collection;
    }

    /* searching  artickles by name , description ,serial number or id by part of word*/
    public List<Articles> searchArticles() throws SQLException {
        artikle_collection.clear();
        String sqlQuerry = "SELECT name, serialNumber, idArtickle, description, quantity, quantityInUse, price FROM artikli " +
                "WHERE name like '%"+ variableForSearch + "%' or serialNumber like '%"+variableForSearch+"%' or idArtickle  like" +
                "'%"+variableForSearch+"%' or description like '%"+variableForSearch+"%'";
        System.out.println(sqlQuerry);
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {
            articles= new Articles(null,resultSet.getString("name"),resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"),resultSet.getString("description"),
                    resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"));
            artikle_collection.add(articles);
        }

        return artikle_collection;
    }

    public void nesto (String sqlQuerry) throws SQLException {
        resultSet=statement.executeQuery(sqlQuerry);



    }
}
