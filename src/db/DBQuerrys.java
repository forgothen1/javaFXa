package db;

import entites.Articles;
import entites.PurchesInvoice;
import entites.Service;
import entites.Worker;
import javafx.fxml.FXML;

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
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Worker worker;
    private List<Worker> worker_collection =new ArrayList<>();
    private Articles articles;
    private List<Articles> article_collection = new ArrayList<>();
    private List<Service> service_collection= new ArrayList<>();
    private  Service service = new Service();
    private PurchesInvoice purchesInvoice = new PurchesInvoice();

    /**
     *  loading all data from articles table of DB and seding to table  in gui
     * @return article_collection list that collect articles
     * @throws SQLException exception that will be done somewhere else

     */
    /* loading all data from artikle table to table in gui*/
    public List<Articles> gettingAllArtikles() throws SQLException {
        article_collection.clear();
        /*query for DB*/
        String sqlQuerry = "select  name, serialNumber, idArtickle, description, kolicinaUslo, uUpotrebi, " +
                "kolicinaProdato, kolicinaUkupno, price,sumPrice,jedMjere  from artikli";
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next()) {
            articles = new Articles(null, resultSet.getString("name"), resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"), resultSet.getString("description"),
                    resultSet.getInt("kolicinaUslo"), resultSet.getInt("uUpotrebi"), resultSet.getInt("kolicinaProdato"),
                    resultSet.getInt("kolicinaUkupno"),null, resultSet.getFloat("price"),
                    resultSet.getFloat("sumPrice"),null,null,resultSet.getInt("jedMjere"),null);
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
        String sqlQuerry = "insert into artikli (name,serialNumber,idArtickle,description,kolicinaUslo,price) values (?,?,?,?,?,?)";
        int i =1;
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
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
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        preparedStatement.executeUpdate();
    }

    /**
     * edditing  existing  article
     * @param articles  its entity that is send from gui
     * @param variableForSearch variable that determes  line with what shud be searched
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
     */
    /*this search allow to get specific article by serialnumber, had to be sepereted becouse of probility of crosing
    numbers or letters in serialnumber or name / description /idArticle*/
    public  List<Articles> searchBySerialNumber( String variableForSearch) throws SQLException {
        article_collection.clear();
        System.out.println("proba1");
        String sqlQuerry = "SELECT name, serialNumber, idArtickle, description, kolicinaUslo, uUpotrebi,kolicinaProdato," +
                " kolicinaUkupno, price, entryPrice, sortOfArticle, location1 FROM artikli where serialNumber='"+variableForSearch+"'";
        resultSet=statement.executeQuery(sqlQuerry);
        System.out.println("proba3");
        while(resultSet.next()) {
            articles = new Articles(null, resultSet.getString("name"), resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"), resultSet.getString("description"),
                    resultSet.getInt("kolicinaUslo"), resultSet.getInt("uUpotrebi"),resultSet.getInt("kolicinaProdato"),
                    resultSet.getInt("kolicinaUkupno"),resultSet.getFloat("entryPrice") ,resultSet.getFloat("price"),
                    null,resultSet.getInt("sortOfArticle"),resultSet.getString("location1"),null,null);
            article_collection.add(articles);
        }
        System.out.println("proba2");
        return article_collection;
    }

    /**
     *  searching  artickles by name , description ,serial number or id by part of word
     * @param variableForSearch variable that determes  line with what shud be searched
     * @return article_collection return articles that is  determit with variableForSearch
     */
    /* searching  artickles by name , description ,serial number or id by part of word*/
    public List<Articles> searchArticles(String variableForSearch, String  filtering) throws SQLException {
        article_collection.clear();
        String sqlQuerry;
        if (variableForSearch.isEmpty()){
            sqlQuerry= "select  name, serialNumber, idArtickle, description, kolicinaUslo, uUpotrebi, " +
                    "kolicinaProdato, kolicinaUkupno, price  from artikli where sortOfArticle in "+filtering;
        }
        else {
             sqlQuerry =
                "SELECT name, serialNumber, idArtickle, description, kolicinaUslo, uUpotrebi,kolicinaProdato, kolicinaUkupno, " +
                        "price FROM artikli WHERE name like '%"+ variableForSearch + "%' or serialNumber like '%"+
                        variableForSearch+"%' or idArtickle  like" +
                        "'%"+variableForSearch+"%' or description like '%"+variableForSearch+"%' and sortOfArticle in "+filtering; }
        System.out.println(sqlQuerry);
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {
            articles= new Articles(null,resultSet.getString("name"),resultSet.getString("serialNumber"),
                    resultSet.getInt("idArtickle"),resultSet.getString("description"),
                    resultSet.getInt("kolicinaUslo"),resultSet.getInt("uUpotrebi"),resultSet.getInt("kolicinaProdato"),
                    resultSet.getInt("kolicinaUkupno"),null,resultSet.getFloat("price"),null,null,null,null,null);
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
     */
    /* getting  stuff for selecter / searcher  reciving to this class a string
    this is  method that collect from DB selected in table  and sending it to  labels*/
    public List<Worker> searchOfWorkerByIdWorker(String variableForSearch) throws SQLException {
        // need to be cleared or it just stack inside LIST
        worker_collection.clear();
        String sqlQuerry="select name,Surname,payment,workplace,idWorker from radnik where idWorker="+variableForSearch;
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
     */
    /* method that get string from textfield to search in DB*/
    public List<Worker> searchOfWorkers(String variableForSearch) throws SQLException {
        worker_collection.clear();
        String sqlQuerry = "SELECT name,Surname,payment,workplace,idWorker FROM radnik where name like'%" +variableForSearch+
                "%' or " + "surname like '%" + variableForSearch + "%' or workplace like '%" + variableForSearch
                + "%' or idWorker like '%"+variableForSearch+"%'";
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
     * @param idToRemove string that determents what shud be deleted
     */
    //geting string and removes idworker thats equal of that string
    public void remove(String idToRemove) throws SQLException {
        String sqlRemove = "delete from radnik where idWorker=" + idToRemove;
        preparedStatement = con.prepareStatement(sqlRemove);
        preparedStatement.executeUpdate();
    }
    /**
     * updating DB with query varable is number to get idWorker
     * @param worker entity of workers
     * @param variableForSearch var that is updated by
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
     *
     */

    /**
     * this is just to get last  number of service and to report it
     * @return incrising service number +1  and sending it to add new service
     * @throws SQLException its sql  and its done in addService
     */
    public Integer getingLastservice() throws SQLException {
        Integer neko = null;
        String sqlQuery="SELECT  MAX(servis_number) as largest FROM servisi";
        resultSet= statement.executeQuery(sqlQuery);
        if(resultSet.next())
        {neko=resultSet.getInt(1);}
        return neko+1;
    }

    /**
     * adding additional service
     * @param service collecting entiti
     */
    public void addServices(Service service) throws SQLException {
        int i=1;
        DateTimeFormatter myFormat= DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        LocalDateTime time = LocalDateTime.now();
        String timeFreez= time.format(myFormat);
        String sqlQuery="INSERT INTO servisi (nameOfproduct,owner,description,servis_number,telephone,time,status) values (?,?,?,?,?,?,?)";
        System.out.println(sqlQuery);
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement=con.prepareStatement(sqlQuery);
        preparedStatement.setString(i++,service.getName());
        preparedStatement.setString(i++,service.getOwner());
        preparedStatement.setString(i++,service.getDescription());
        preparedStatement.setInt(i++,getingLastservice());
        preparedStatement.setString(i++,service.getTelephone());
        preparedStatement.setString(i++,timeFreez);
        preparedStatement.setInt(i++,1);
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
    }

    /**
     *  loading services from table
     * @return service_collection as list
     */
    public List<Service> tableservis() throws SQLException {
        service_collection.clear();
        String sqlQuery= "select nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status,coment from servisi";
        resultSet=statement.executeQuery(sqlQuery);
        while(resultSet.next()) {
            service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                    resultSet.getString("owner"), resultSet.getString("telephone"), resultSet.getInt("servis_number"),
                    resultSet.getString("description"),resultSet.getString("time"),resultSet.getInt("status"),resultSet.getString("coment"));
            service_collection.add(service);
        }
        return service_collection;
    }

    /**
     * search for service with nameOfproduct, owner or servis number is mached
     * @param variableForSearch is parametar that is used for search
     * @return service_collection
     */
    public List<Service> searchOfService(String variableForSearch) throws SQLException {
        service_collection.clear();
        String sqlQuerry="SELECT nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status,coment " +
                " from servisi where  nameOfproduct like'%"+ variableForSearch +"%' or owner like '%"+ variableForSearch
                +"%' or servis_number like '%" + variableForSearch +"%'";
        resultSet= statement.executeQuery(sqlQuerry);
        while(resultSet.next()) {
            service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                    resultSet.getString("owner"), resultSet.getString("telephone"), resultSet.getInt("servis_number"),
                    resultSet.getString("description"),resultSet.getString("time"), resultSet.getInt("status"),resultSet.getString("coment"));
            service_collection.add(service);
        }
        return service_collection;
    }

    /**
     * get services with specific serviceNumber, its not used atm probably will be neeed later somewhere
     * @param variableForSearch string that u get value for serviceNumber
     * @return list of services  for further use
     */
    public List<Service> searchOfServiceByServiceNumber(String variableForSearch) throws SQLException {
        service_collection.clear();
        String sqlQuerry="SELECT nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status,coment " +
                " from servisi where  servis_number like '%" + variableForSearch +"%'";
        resultSet= statement.executeQuery(sqlQuerry);
        while(resultSet.next()) {
            service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                    resultSet.getString("owner"), resultSet.getString("telephone"), resultSet.getInt("servis_number"),
                    resultSet.getString("description"),resultSet.getString("time"),resultSet.getInt("status"),resultSet.getString("coment"));
            service_collection.add(service);
        }
        logCon.info("service is pulled from db ");
        return service_collection;
    }

    /**
     * edit serivis  if it is wrong in some info
     * @param service entity service to send whole data of service
     * @throws SQLException
     */
    public void editService(Service service) throws SQLException {
        int i=1;
        String sqlQuery="UPDATE servisi set nameOfProduct=?, cijenaServisa=?, owner=?, telephone=?, description=?,status=?,coment=? where servis_number="+service.getSerivisNumber();
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement=con.prepareStatement(sqlQuery);
        preparedStatement.setString(i++,service.getName());
        preparedStatement.setFloat(i++,service.getPrice());
        preparedStatement.setString(i++,service.getOwner());
        preparedStatement.setString(i++,service.getTelephone());
        preparedStatement.setString(i++,service.getDescription());
        preparedStatement.setInt(i++, service.getStatusInt());
        preparedStatement.setString(i++,service.getComment());
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        preparedStatement.executeUpdate();

    }
    /**
     *  changing status of proces of service
     * @param status represent in wich state of proces is servis
     * @param serviceNumber  unique number for every service
     */
    public void statusChange(Integer status,String serviceNumber) throws SQLException {
        String sqlQuerry="update servisi set status=? where servis_number="+serviceNumber;
        preparedStatement = con.prepareStatement(sqlQuerry);
        preparedStatement.setInt(1,status);
        preparedStatement.executeUpdate();
        logCon.info("status CHANGED for service="+serviceNumber);
    }

    /**
     * method is called if servic dont have specific article in gui table
     * @param service number of service
     * @param serialNumber number of article
     * @throws SQLException it will be done in mergingAricleService
     */
    private  void addingArticleService(String service, String serialNumber) throws SQLException {
        Float price=null;
        resultSet= statement.executeQuery("select price from artikli where  serialNumber='"+serialNumber+"'");
        if (resultSet.next()) {
            price=resultSet.getFloat(1);
        }
        String sqlQuerry2 = "update artikli set kolicinaUkupno= (kolicinaUkupno-1),uUpotrebi=(uUpotrebi+1) where serialNumber='"+serialNumber+"'";
        String sqlQuerry ="INSERT INTO article_in_service (serviceNumber, articleNumber,price,quanity) " +
                "values ("+service+ ",'"+serialNumber+"',"+price+",1);";
        System.out.println(sqlQuerry);
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(sqlQuerry);
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate(sqlQuerry2);
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        preparedStatement.executeUpdate();
    }

    /**
     * this method is called if servis have that article in  gui table then its just added quantty
     * @param service number of service
     * @param serialNumber number of article
     * @throws SQLException it will be done in mergingAricleService
     */
    private void edditingArticleService(String service, String serialNumber) throws SQLException {
        //still not working propery
        String sqlQuerry="update article_in_service set quanity=(quanity+1) where serviceNumber="+service+" and articleNumber='"+serialNumber+"'; ";
        String sqlQuerry2 = "update artikli set kolicinaUkupno= (kolicinaUkupno-1),uUpotrebi=(uUpotrebi+1) where serialNumber='"+serialNumber+"'";
        System.out.println("proba radi li ovo");
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(sqlQuerry);
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate(sqlQuerry2);
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        preparedStatement.executeUpdate();
    }

    /**
     * method that deside if its for update or insert in gui table
     * @param service number of servic
     * @param serialNumber number of article
     */
    public void mergingArticleService(String service, String serialNumber) throws SQLException {
        String brojzaTest=null;
        String sqlQuerry="select articleNumber from article_in_service where serviceNumber="+service+" and articleNumber='"+serialNumber+"'";
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {brojzaTest=resultSet.getString(1); }
        System.out.println(brojzaTest);
        if (brojzaTest == null) {
            addingArticleService(service,serialNumber);
            logCon.info("article is added to service="+service+", and article number="+serialNumber);
        }
        else {
            edditingArticleService(service,serialNumber);
            logCon.info("article is edited to service="+service+", and article number="+serialNumber);
        }
    }

    /**
     * geting what is added of articles in servis by servisnumber
     * @param servis_number is for specifying what servis iz
     * @return article_collection  return list of articles with sumprice
     */
    public List<Articles> listOfArticleInServis(Integer servis_number) throws SQLException {
        article_collection.clear();
        String sqlQuerry="select artikli.serialNumber, artikli.name, article_in_service.quanity,article_in_service.price," +
                " article_in_service.sumPrice from artikli inner join (select * from article_in_service where serviceNumber="+servis_number+
                ")article_in_service on article_in_service.articleNumber=artikli.serialNumber;";
        System.out.println(sqlQuerry);
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {
            articles = new Articles(null, resultSet.getString("artikli.name"), resultSet.getString("artikli.serialNumber"),
                    null, null, resultSet.getInt("article_in_service.quanity"), null,null,null,null,
                    resultSet.getFloat("article_in_service.price"),resultSet.getFloat("sumPrice"),null,null,null,null);
            article_collection.add(articles);
        }
        logCon.info("articles loaded for servis");
        return article_collection;

    }

    /**
     *  method that return sumof all artiles for per service when asked for  will see to skip this  trasfer of data later prob will be terminated
     * @param servisNumber  integer that is send what servis is selected
     * @return priceOfService float that can return null or sum of price
     */
    public Float getingVolePrice(Integer servisNumber) throws SQLException {
        Float priceOfService=null;
        String sqlQuery="select sum(sumPrice) from article_in_service where serviceNumber="+servisNumber;
        resultSet= statement.executeQuery(sqlQuery);
        if (resultSet.next())
        {
            priceOfService = resultSet.getFloat(1);
        }
        logCon.info("price of service is retrived");
        return priceOfService;
    }

    /**
     * updating /seting price for services
     * @param servisNumber servis that price has been updated /added
     * @param price amount that has been updated to service
     */
    public void setVolePrice(Integer servisNumber, Float price) throws SQLException {
        String sqlQuery="UPDATE servisi SET cijenaServisa="+price+" WHERE servis_number="+servisNumber;
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate(sqlQuery);
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        System.out.println("cijena je uploadana u servis");
        preparedStatement.executeUpdate();
        logCon.info("price was updated for service="+servisNumber);
    }

    /**
     * setting back quantity of article and quantityInUse , and removing or  reducing quantiyty in service of aricle
     * @param servisNUmber nubmber of service.
     * @param articleNumber signature code for article
     * @param quantity  how much there is in use of articles in that service
     */
    public void removeArticleFromServis(Integer servisNUmber, String articleNumber, Integer quantity) throws SQLException {
        String sqlQuery;
        //this if is depended of quantity article in service if there is more then 1  it will reduce only quantity , but if its only article then it will be deleted
        if (quantity>1) {
            sqlQuery="update article_in_service set quanity=(quanity-1) where serviceNumber="+servisNUmber+" and articleNumber='"+articleNumber+"'; ";
        }
        else {
            sqlQuery = "delete from article_in_service where serviceNumber=" + servisNUmber + " and articleNumber='" + articleNumber + "'";
        }
        String sqlQuerry2 = "update artikli set kolicinaUkupno= (kolicinaUkupno+1),uUpotrebi=(uUpotrebi-1) where serialNumber='"+articleNumber+"'";
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement.executeUpdate(sqlQuery);
        preparedStatement.executeUpdate(sqlQuerry2);
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        System.out.println("cijena je uploadana u servis");
        preparedStatement.executeUpdate();
        logCon.info("article is sucesfuly deleted from service="+servisNUmber+", article="+articleNumber);
    }

    /**
     * adding comment for services
     * @param service declaring what service it is
     * @param commnet what is needed to add
     * @throws SQLException
     */
    public void addComment(Integer service,String commnet) throws SQLException {
        String sqlQuerry="update servisi set coment='"+commnet+"' where servis_number="+service;
        resultSet=statement.executeQuery(" SET foreign_key_checks = 0");
        preparedStatement = con.prepareStatement(sqlQuerry);
        preparedStatement.executeUpdate();
        resultSet=statement.executeQuery(" SET foreign_key_checks = 1");
    }

    /**
     *  manualy adding article in service if needed to add and to add in db  in 1
     * @param service what service needed
     * @param serialNumber article what is needed to add
     * @param quantity how much its needed to add
     * @throws SQLException
     */
    public void manualArticleInServisDB(String service,String serialNumber,Integer quantity) throws SQLException {
         String numberOfArticle=null;
        String sqlQuerry="select articleNumber from article_in_service where serviceNumber="+service+" and articleNumber='"+serialNumber+"'";
        resultSet=statement.executeQuery(sqlQuerry);
        while(resultSet.next())
        {numberOfArticle=resultSet.getString(1); }
        if (numberOfArticle==null) {
            Float price=null;
            resultSet= statement.executeQuery("select price from artikli where  serialNumber='"+serialNumber+"'");
            if (resultSet.next()) {
                price=resultSet.getFloat(1);
            }
            String sqlQuerry2 = "update artikli set quantity= (quantity-"+quantity+"),quantityInUse=(quantityInUse+"+quantity+") where serialNumber='"+serialNumber+"'";
            String sqlQuerry3 ="INSERT INTO article_in_service (serviceNumber, articleNumber,price,quanity) " +
                    "values ("+service+ ",'"+serialNumber+"',"+price+","+quantity+");";
            System.out.println(sqlQuerry3);
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
            preparedStatement.executeUpdate();
            preparedStatement = con.prepareStatement(sqlQuerry3);
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate(sqlQuerry2);
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
            preparedStatement.executeUpdate();
        }
        else
        {
            String sqlQuerry3="update article_in_service set quanity=(quanity+"+quantity+") where serviceNumber="+service+" and articleNumber='"+serialNumber+"'; ";
            String sqlQuerry2 = "update artikli set quantity= (quantity-1),quantityInUse=(quantityInUse+1) where serialNumber='"+serialNumber+"'";
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
            preparedStatement.executeUpdate();
            preparedStatement = con.prepareStatement(sqlQuerry3);
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate(sqlQuerry2);
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
            preparedStatement.executeUpdate();
        }
    }

    /**
     * *************************************
     * part for invoice imputing incoming orders
     *
     */
    /**
     * writing purchesInvoice to DB;
     * @throws SQLException
     */
    @FXML
    public void addInvoice(PurchesInvoice purchesInvoice) throws SQLException {
    //logic for imput to db and to add  more ,
        int i =1;
        String sqlQuerry="insert into ulazna_roba(brojulaza, brojStavki, VPvrijednost, MPvrijednost, datumUlaskaRobe, " +
                "datumSlanjaRobe, nazivDobavljaca, brojFakture, PDV)  values (?,?,?,?,?,?,?,?,?)";
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement=con.prepareStatement(sqlQuerry);
        preparedStatement.setString(i++,purchesInvoice.getEntry());
        preparedStatement.setInt(i++, purchesInvoice.getQuantity());
        preparedStatement.setFloat(i++, purchesInvoice.getVPPrice());
        preparedStatement.setFloat(i++, purchesInvoice.getMPprice());
        preparedStatement.setString(i++, purchesInvoice.getDateRecive());
        preparedStatement.setString(i++, purchesInvoice.getDateSent());
        preparedStatement.setString(i++,purchesInvoice.getSuplayer());
        preparedStatement.setString(i++,purchesInvoice.getOrderNumber());
        preparedStatement.setFloat(i++,purchesInvoice.getPDV());
      //  preparedStatement.setFloat(i++,purchesInvoice.getSumPricewithPDV());
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        preparedStatement.executeUpdate();
    }

    /**
     * writing to DB connection bettween entry  and articles
     * @param purchesInvoice  what entry it is atm
     * @param articles articles that is adding in store
     * @throws SQLException
     */
    @FXML
    public void addingArtiklesFromPurchase(PurchesInvoice purchesInvoice, Articles articles) throws SQLException {
        int i = 1;
        String sqlQuerry = "insert into artiklipoulazu(idArtikla, brojUlaza, kolicina, VPCijena, MPCijena) values (?,?,?,?,?)";

        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(sqlQuerry);
        preparedStatement.setString(i++, articles.getSerialNumber());
        preparedStatement.setString(i++, purchesInvoice.getEntry());
        preparedStatement.setInt(i++, articles.getQuantity());
        preparedStatement.setFloat(i++, articles.getImputPrice());
        // preparedStatement.setFloat(i++, articles.getPDV);
        preparedStatement.setFloat(i++, articles.getPrice());
        preparedStatement.executeUpdate();
        preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
        preparedStatement.executeUpdate();
    }
}



