package db;

import entites.Articles;
import entites.Service;
import entites.Worker;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DBQuerrys extends DBcon {
 private Logger log = Logger.getLogger("prebacivanje u bazu");
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Worker worker;
    private List<Worker> worker_collection =new ArrayList<>();
    private Articles articles;
    private List<Articles> article_collection = new ArrayList<>();
    private List<Service> service_collection= new ArrayList<>();
    private  Service service = new Service();

    /**
     *  loading all data from articles table of DB and seding to table  in gui
     * @return article_collection list that collect articles
     * @throws SQLException exception that will be done somewhere else
     */
    /* loading all data from artikle table to table in gui*/
    public List<Articles> gettingAllArtikles() throws SQLException {

        /*query for DB*/
        String sqlQuerry = "select  name, serialNumber, idArtickle, description, quantity, quantityInUse, price  from artikli ";
                resultSet=statement.executeQuery(sqlQuerry);
                while(resultSet.next()) {
                    articles = new Articles(null, resultSet.getString("name"), resultSet.getString("serialNumber"),
                            resultSet.getInt("idArtickle"), resultSet.getString("description"),
                            resultSet.getInt("quantity"), resultSet.getInt("quantityInUse"), resultSet.getFloat("price"));
                    article_collection.add(articles);
                }
        return article_collection;
    }

    /**
     * this allows to add new article in DB
     * @param articles its entity that is send from gui
     * @throws SQLException exception that will be done somewhere else
     */
    /*this allows to add new article in DB*/
    public void addingToArticles(Articles articles) throws SQLException {
        String sqlQuerry =
                "insert into artikli (name,serialNumber,idArtickle,description,quantity,price) values (?,?,?,?,?,?)";
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

    /**
     * edditing  existing  article
     * @param articles  its entity that is send from gui
     * @param variableForSearch variable that determes  line with what shud be searched
     * @throws SQLException exception that will be done somewhere else
     */
    // edditing  existing  article
    public void editingArticle(Articles articles,String variableForSearch) throws SQLException {
        String sqlQuerry =
                "update artikli set name=? , serialNumber=? , idArtickle=? , description=? , quantity=? , price=? " +
                " where serialNumber='"+variableForSearch+"'";
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

    /**
     * this search allow to get specific article by serialnumber, had to be sepereted becouse of probility of crosing
     *     numbers or letters in serialnumber or name / description /idArticle
     *     also if this is not functional  method editingArticle is not functional becouse of setup in gui
     * @param variableForSearch variable that determes  line with what shud be searched
     * @return article_collection
     * @throws SQLException exception that will be done somewhere else
     */
    /*this search allow to get specific article by serialnumber, had to be sepereted becouse of probility of crosing
    numbers or letters in serialnumber or name / description /idArticle*/
    public  List<Articles> searchBySerialNumber( String variableForSearch) throws SQLException {
        article_collection.clear();
        String sqlQuerry = "SELECT name, serialNumber, idArtickle, description, quantity, quantityInUse, price FROM" +
                " artikli where serialNumber='"+variableForSearch+"'";
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {
            articles= new Articles(null,resultSet.getString("name"),resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"),resultSet.getString("description"),
                    resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"));
            article_collection.add(articles);
        }
        return article_collection;
    }

    /**
     *  searching  artickles by name , description ,serial number or id by part of word
     * @param variableForSearch variable that determes  line with what shud be searched
     * @return article_collection return articles that is  determit with variableForSearch
     * @throws SQLException exception that will be done somewhere else
     */
    /* searching  artickles by name , description ,serial number or id by part of word*/
    public List<Articles> searchArticles(String variableForSearch) throws SQLException {
        article_collection.clear();
        String sqlQuerry =
                "SELECT name, serialNumber, idArtickle, description, quantity, quantityInUse, price FROM artikli " +
                "WHERE name like '%"+ variableForSearch + "%' or serialNumber like '%"+
                        variableForSearch+"%' or idArtickle  like" +
                "'%"+variableForSearch+"%' or description like '%"+variableForSearch+"%'";

            resultSet=statement.executeQuery(sqlQuerry);
            while(resultSet.next())
            {
                articles= new Articles(null,resultSet.getString("name"),resultSet.getString("serialNumber"),
                        resultSet.getInt("idArtickle"),resultSet.getString("description"),
                        resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"));
                article_collection.add(articles);
            }
        return article_collection;
    }

    /**
     * *******************************************************
     * part for querys for workers
     */

    /**
     * basic method to get all workers from DB
     * @return worker_collection  collect all workers
     * @throws SQLException exception that will be done somewhere else
     */
    /*basic  method to get all workers from DB */
    public List<Worker> gettingAllWorkers() throws SQLException {

        String sqlQuerry = "select  Name, Surname, payment, workplace, idWorker from radnik ";
        resultSet = statement.executeQuery(sqlQuerry);
        while (resultSet.next()) {
            // za poslije prebacivanje  na GUI i vizual
            worker = new Worker(null, resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getFloat("payment"), resultSet.getString("workplace"),
                    resultSet.getInt("idWorker"));
            worker_collection.add(worker);
        }
        return worker_collection;
    }

    /**
     *   getting  stuff for selecter / searcher reciving to this class a string
     *   this is  method that collect from DB selected in table and sending it to labels
     * @param variableForSearch  var for search
     * @return worker_collection return workers by idWorker
     * @throws SQLException exception that will be done somewhere else
     */
    /* getting  stuff for selecter / searcher  reciving to this class a string
    this is  method that collect from DB selected in table  and sending it to  labels*/
    public List<Worker> searchOfWorkerByIdWorker(String variableForSearch) throws SQLException {
        // need to be cleared or it just stack inside LIST
        worker_collection.clear();
        String sqlQuerry="select * from radnik where idWorker="+variableForSearch;
        resultSet = statement.executeQuery(sqlQuerry);
        while (resultSet.next()) {
            // za poslije prebacivanje  na GUI i vizual
            worker = new Worker(null, resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getFloat("payment"), resultSet.getString("workplace"),
                    resultSet.getInt("idWorker"));
            worker_collection.add(worker);
        }
        return worker_collection;
    }

    /**
     * method that get string from textfield to search in DB
     * @param variableForSearch  give var that is searched by
     * @return worker_collection return workers that is result of querry and variableForSearch
     * @throws SQLException exception that will be done somewhere else
     */
    /* method that get string from textfield to search in DB*/
    public List<Worker> searchOfWorkers(String variableForSearch) throws SQLException {
        worker_collection.clear();
        String sqlQuerry = "SELECT * FROM radnik where name like'%" + variableForSearch + "%' or " +
                "surname like '%" + variableForSearch + "%' or workplace like '%" + variableForSearch + "%'";
        resultSet = statement.executeQuery(sqlQuerry);
        while (resultSet.next()) {
            // za poslije prebacivanje  na GUI i vizual
            worker = new Worker(null, resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getFloat("payment"), resultSet.getString("workplace"),
                    resultSet.getInt("idWorker"));
            worker_collection.add(worker);
        }
        return worker_collection;
    }

    /**
     * adding to DB workers
     * @param worker entity of worker cannot be null
     * @throws SQLException  exception that will be done somewhere else
     */
    //adding to DB value by value
    public void inputOfWorker( Worker worker) throws SQLException {
        String sqlQuery = "insert into Radnik (name,surname,payment,workplace,idWorker) values (?,?,?,?,?)";
        System.out.println(sqlQuery);
        int i = 1;
        preparedStatement = con.prepareStatement(sqlQuery);
        preparedStatement.setString(i++, worker.getName());
        preparedStatement.setString(i++, worker.getSurname());
        // payment is int value so it need to be casted to String
        preparedStatement.setFloat(i++, worker.getPaymant());
        preparedStatement.setString(i++, worker.getWorkplace());
        // idworker is int value so it need to be casted to String
        preparedStatement.setInt(i, worker.getIdWorker());
        preparedStatement.executeUpdate();
    }

    /**
     * geting string and removes idworker thats equal of that string
     * @param idToremove string that determents what shud be deleted
     */
    //geting string and removes idworker thats equal of that string
    public void remove(String idToremove) {

        String sqlRemove = "delete from radnik where idWorker=" + idToremove;
        try {
            preparedStatement = con.prepareStatement(sqlRemove);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("this is sql command " + sqlRemove);

        }
    }
    /**
     * updating DB with query varable is number to get idWorker
     * @param worker entity of workers
     * @param variableForSearch var that is updated by
     * @throws SQLException  exception that will be done somewhere else
     */
    /* updating DB with query varable is number to get idWorker */
    public void  editWorker(Worker worker, String variableForSearch ) throws SQLException {
        String sqlQuery = "update radnik set name=? , surname=? , payment=? , workplace=? , idWorker=? where idWorker="
                +variableForSearch;
        System.out.println(sqlQuery);
        int i = 1;
        preparedStatement = con.prepareStatement(sqlQuery);
        preparedStatement.setString(i++, worker.getName());
        preparedStatement.setString(i++, worker.getSurname());
        // payment is int value so it need to be casted to String
        preparedStatement.setFloat(i++, worker.getPaymant());
        preparedStatement.setString(i++, worker.getWorkplace());
        // idworker is int value so it need to be casted to String
        preparedStatement.setInt(i, worker.getIdWorker());
        preparedStatement.executeUpdate();
    }

    // *********************************************************************
    // part for services
    /**
     *  ***********************************
     *  part for services
     * @return
     */

    public Integer getingLastservice() throws SQLException {
        Integer neko = null;
        String sqlQuery="SELECT  MAX(servis_number) as largest FROM servisi";
        resultSet= statement.executeQuery(sqlQuery);
        System.out.println(resultSet.toString());
        if(resultSet.next())
        {neko=resultSet.getInt(1);}
        System.out.println(neko );

        return neko+1;
        /*napraviti logiku za citanje zadnjeg servisa */
    }

    public void addServices(Service service) throws SQLException {
        int i=1;
        DateTimeFormatter myFormat= DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        LocalDateTime time = LocalDateTime.now();

        String timeFreez= time.format(myFormat);
        String sqlQuery="INSERT INTO servisi (nameOfproduct,owner,description,servis_number,telephone,time,status) values (?,?,?,?,?,?,?)";
        System.out.println(sqlQuery);
        preparedStatement=con.prepareStatement(sqlQuery);
        preparedStatement.setString(i++,service.getName());
        preparedStatement.setString(i++,service.getOwner());
        preparedStatement.setString(i++,service.getDescription());
        //preparedStatement.setFloat(i++,service.getPrice());
        preparedStatement.setInt(i++,getingLastservice());
        preparedStatement.setString(i++,service.getTelephone());
        preparedStatement.setString(i++,timeFreez);
        preparedStatement.setInt(i++,1);
        preparedStatement.executeUpdate();
    }
    public List<Service> tableservis() throws SQLException {

        String sqlQuery= "select nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status  from servisi";
        resultSet=statement.executeQuery(sqlQuery);
        while(resultSet.next()) {
            service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                    resultSet.getString("owner"), resultSet.getString("telephone"),
                    resultSet.getInt("servis_number"), resultSet.getString("description"),resultSet.getString("time"),resultSet.getInt("status"));

            service_collection.add(service);
          //uradi sutra nastavak pisanja  dodavanja u servis entitet  + pokusaj da neide u worker gore ,
            // nego da ide resultset.get to servis.set  ili tako nesto na ljepsi nacin
        }
        return service_collection;
    }
    public List<Service> searchOfService(String variableForSearch) throws SQLException {
        String sqlQuerry="SELECT nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status " +
                " from servisi where  nameOfproduct like'%"+ variableForSearch +"%' or owner like '%"+ variableForSearch
                +"%' or servis_number like '%" + variableForSearch +"%'";
        resultSet= statement.executeQuery(sqlQuerry);

        return service_collection;
    }

}



