package db;

import entites.Articles;
import entites.Service;
import entites.Worker;
import org.apache.log4j.Logger;
import recordInfo.RecordInfo;

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
    RecordInfo logginfo;

    /**
     *  loading all data from articles table of DB and seding to table  in gui
     * @return article_collection list that collect articles
     * @throws SQLException exception that will be done somewhere else
     */
    /* loading all data from artikle table to table in gui*/
    public List<Articles> gettingAllArtikles() throws SQLException {
        article_collection.clear();
        /*query for DB*/
        String sqlQuerry = "select  name, serialNumber, idArtickle, description, quantity, quantityInUse, price  from artikli ";
                resultSet=statement.executeQuery(sqlQuerry);
                while(resultSet.next()) {
                    articles = new Articles(null, resultSet.getString("name"), resultSet.getString("serialNumber"),
                            resultSet.getInt("idArtickle"), resultSet.getString("description"),
                            resultSet.getInt("quantity"), resultSet.getInt("quantityInUse"), resultSet.getFloat("price"),null);
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
                    resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"),null);
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
                        resultSet.getInt("quantity"),resultSet.getInt("quantityInUse"),resultSet.getFloat("price"),null);
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
                "surname like '%" + variableForSearch + "%' or workplace like '%" + variableForSearch
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
    public void remove(String idToRemove) {

        String sqlRemove = "delete from radnik where idWorker=" + idToRemove;
        try {
            preparedStatement = con.prepareStatement(sqlRemove);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
           logCon.error("this is sql command " + sqlRemove,e);

        }
    }
    /**
     * updating DB with query varable is number to get idWorker
     * @param worker entity of workers
     * @param variableForSearch var that is updated by
     * @throws SQLException  exception that will be done somewhere else
     */
    /* updating DB with query varable is number to get idWorker */
    public void  editWorker(Worker worker, String variableForSearch ) {
        String sqlQuery = "update radnik set name=? , surname=? , payment=? , workplace=? , idWorker=? where idWorker="
                +variableForSearch;
        System.out.println(sqlQuery);
        int i = 1;
        try {
            preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setString(i++, worker.getName());
            preparedStatement.setString(i++, worker.getSurname());
            // payment is int value so it need to be casted to String
            preparedStatement.setFloat(i++, worker.getPaymant());
            preparedStatement.setString(i++, worker.getWorkplace());
            // idworker is int value so it need to be casted to String
            preparedStatement.setInt(i, worker.getIdWorker());
            preparedStatement.executeUpdate();
            logCon.info("worker is eddited");
        } catch (SQLException e) {
          logCon.info("worker didnt get eddited",e);
        }

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
    public void addServices(Service service)  {
        int i=1;
        DateTimeFormatter myFormat= DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        LocalDateTime time = LocalDateTime.now();

        String timeFreez= time.format(myFormat);
        String sqlQuery="INSERT INTO servisi (nameOfproduct,owner,description,servis_number,telephone,time,status) values (?,?,?,?,?,?,?)";
        System.out.println(sqlQuery);
        try {
            preparedStatement=con.prepareStatement(sqlQuery);
            preparedStatement.setString(i++,service.getName());
            preparedStatement.setString(i++,service.getOwner());
            preparedStatement.setString(i++,service.getDescription());
            preparedStatement.setInt(i++,getingLastservice());
            preparedStatement.setString(i++,service.getTelephone());
            preparedStatement.setString(i++,timeFreez);
            preparedStatement.setInt(i++,1);
            preparedStatement.executeUpdate();
            logCon.info("service succesfuly added to DB");
        } catch (SQLException e) {
            logCon.error("service didint added to db",e);
        }

    }

    /**
     *  loading services from table
     * @return service_collection as list
     */
    public List<Service> tableservis()  {
        service_collection.clear();
        String sqlQuery= "select nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status from servisi";
        try {
            resultSet=statement.executeQuery(sqlQuery);
            while(resultSet.next()) {
                service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                        resultSet.getString("owner"), resultSet.getString("telephone"), resultSet.getInt("servis_number"),
                        resultSet.getString("description"),resultSet.getString("time"),resultSet.getInt("status"));
                service_collection.add(service);
            }
            logCon.info("table witb service is loaded");
        } catch (SQLException e) {
          logCon.error("table didnt load from db ",e);
        }

        return service_collection;
    }

    /**
     * search for service with nameOfproduct, owner or servis number is mached
     * @param variableForSearch is parametar that is used for search
     * @return service_collection
     */
    public List<Service> searchOfService(String variableForSearch)  {
        service_collection.clear();
        String sqlQuerry="SELECT nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status " +
                " from servisi where  nameOfproduct like'%"+ variableForSearch +"%' or owner like '%"+ variableForSearch
                +"%' or servis_number like '%" + variableForSearch +"%'";
        try {
            resultSet= statement.executeQuery(sqlQuerry);
            while(resultSet.next()) {
                service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                        resultSet.getString("owner"), resultSet.getString("telephone"),
                        resultSet.getInt("servis_number"), resultSet.getString("description"),resultSet.getString("time"),resultSet.getInt("status"));

                service_collection.add(service);
            }
            logCon.info("service with specific search is loaded from db");
            //ovdje se stalo fali e u catch
        } catch (SQLException e) {
           logCon.error("service didnt load from DB where serviceid="+variableForSearch,e);
        }

        return service_collection;
    }

    /**
     * get services with specific serviceNumber, its not used atm probably will be neeed later somewhere
     * @param variableForSearch string that u get value for serviceNumber
     * @return list of services  for further use
     */
    public List<Service> searchOfServiceByServiceNumber(String variableForSearch) {
        service_collection.clear();
        String sqlQuerry="SELECT nameOfproduct,owner,description,servis_number,telephone,time,cijenaServisa,status " +
                " from servisi where  servis_number like '%" + variableForSearch +"%'";
        try {
            resultSet= statement.executeQuery(sqlQuerry);
            while(resultSet.next()) {
                service = new Service(null,resultSet.getString("nameOfProduct"),resultSet.getFloat("cijenaServisa"),
                        resultSet.getString("owner"), resultSet.getString("telephone"), resultSet.getInt("servis_number"),
                        resultSet.getString("description"),resultSet.getString("time"),resultSet.getInt("status"));
                service_collection.add(service);
            }
            logCon.info("service is pulled from db ");
        } catch (SQLException e)
        {
            logCon.error("feild to get service="+variableForSearch,e);
        }

        return service_collection;
    }

    /**
     *  changing status of proces of service
     * @param status represent in wich state of proces is servis
     * @param serviceNumber  unique number for every service
     */
    public void statusChange(Integer status,String serviceNumber)  {
        String sqlQuerry="update servisi set status=? where servis_number="+serviceNumber;
        try {
            preparedStatement = con.prepareStatement(sqlQuerry);
            preparedStatement.setInt(1,status);
            preparedStatement.executeUpdate();
            logCon.info("status CHANGED for service="+serviceNumber);
        } catch (SQLException e) {
            logCon.error("status didnt change becouse it didint reach db for service="+serviceNumber,e);
            e.printStackTrace();
        }

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
        if (resultSet.next())
        {
            price=resultSet.getFloat(1);
        }
        String sqlQuerry2 = "update artikli set quantity= (quantity-1),quantityInUse=(quantityInUse+1) where serialNumber='"+serialNumber+"'";
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
        String sqlQuerry2 = "update artikli set quantity= (quantity-1),quantityInUse=(quantityInUse+1) where serialNumber='"+serialNumber+"'";

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
    public void mergingArticleService(String service, String serialNumber)  {
        String brojzaTest=null;
        String sqlQuerry="select articleNumber from article_in_service where serviceNumber="+service+" and articleNumber='"+serialNumber+"'";
        try {
            resultSet=statement.executeQuery(sqlQuerry);
            while(resultSet.next())
            {brojzaTest=resultSet.getString(1); }
            System.out.println(brojzaTest);
            if (brojzaTest == null)
            {
                addingArticleService(service,serialNumber);
                logCon.info("article is added to service="+service+", and article number="+serialNumber);
            }
            else
            {
                edditingArticleService(service,serialNumber);
                logCon.info("article is edited to service="+service+", and article number="+serialNumber);
            }

        } catch (SQLException e) {
            logCon.error("article="+serialNumber+", didint add/eddit to service="+service,e);
        }

    }

    /**
     * geting what is added of articles in servis by servisnumber
     * @param servis_number is for specifying what servis iz
     * @return article_collection  return list of articles with sumprice
     */
    public List<Articles> listOfArticleInServis(Integer servis_number) {
    article_collection.clear();
    String sqlQuerry="select artikli.serialNumber, artikli.name, article_in_service.quanity,article_in_service.price," +
            " article_in_service.sumPrice from artikli inner join (select * from article_in_service where serviceNumber="+servis_number+
            ")article_in_service on article_in_service.articleNumber=artikli.serialNumber;";
    System.out.println(sqlQuerry);
        try {
            resultSet=statement.executeQuery(sqlQuerry);
            while(resultSet.next())
            {
                articles = new Articles(null, resultSet.getString("artikli.name"), resultSet.getString("artikli.serialNumber"),
                        null, null, resultSet.getInt("article_in_service.quanity"), null,
                        resultSet.getFloat("article_in_service.price"),resultSet.getFloat("sumPrice"));
                article_collection.add(articles);
            }
            logCon.info("articles loaded for servis");
        } catch (SQLException e) {
            logCon.error("articles didint load from db for service="+servis_number,e);
            e.printStackTrace();
        }

        return article_collection;
    }

    /**
     *  method that return sumof all artiles for per service when asked for  will see to skip this  trasfer of data later prob will be terminated
     * @param servisNumber  integer that is send what servis is selected
     * @return priceOfService float that can return null or sum of price
     */
    public Float getingVolePrice(Integer servisNumber)  {
        Float priceOfService=null;
        String sqlQuery="select sum(sumPrice) from article_in_service where serviceNumber="+servisNumber;
        try {
            resultSet= statement.executeQuery(sqlQuery);
            if (resultSet.next())
            {
                priceOfService = resultSet.getFloat(1);
            }
            logCon.info("price of service is retrived");
        } catch (SQLException e) {
            logCon.error("price didint collected from db for service="+servisNumber+" ",e);
        }
        return priceOfService;
    }

    /**
     * updating /seting price for services
     * @param servisNumber servis that price has been updated /added
     * @param price amount that has been updated to service
     */
    public void setVolePrice(Integer servisNumber, Float price)  {
        String sqlQuery="UPDATE servisi SET cijenaServisa="+price+" WHERE servis_number="+servisNumber;
        try {
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate(sqlQuery);
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
            System.out.println("cijena je uploadana u servis");
            preparedStatement.executeUpdate();
            logCon.info("price was updated for service="+servisNumber);
        } catch (SQLException e) {
            logCon.error("it faild to connect to db and to set price for service , servisNuber="
                    + servisNumber+", price="+price+"\n",e);
        }
    }

    /**
     * setting back quantity of article and quantityInUse , and removing or  reducing quantiyty in service of aricle
     * @param servisNUmber nubmber of service.
     * @param articleNumber signature code for article
     * @param quantity  how much there is in use of articles in that service
     */
    public void removeArticleFromServis(Integer servisNUmber, String articleNumber, Integer quantity) {
        String sqlQuery;
        //this if is depended of quantity article in service if there is more then 1  it will reduce only quantity , but if its only article then it will be deleted
        if (quantity>1) {
             sqlQuery="update article_in_service set quanity=(quanity-1) where serviceNumber="+servisNUmber+" and articleNumber='"+articleNumber+"'; ";
        }
        else {
            sqlQuery = "delete from article_in_service where serviceNumber=" + servisNUmber + " and articleNumber='" + articleNumber + "'";
        }
        String sqlQuerry2 = "update artikli set quantity= (quantity+1),quantityInUse=(quantityInUse-1) where serialNumber='"+articleNumber+"'";
        try {
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 0");
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate(sqlQuery);
            preparedStatement.executeUpdate(sqlQuerry2);
            preparedStatement = con.prepareStatement(" SET foreign_key_checks = 1");
            System.out.println("cijena je uploadana u servis");
            preparedStatement.executeUpdate();
            logCon.info("article is sucesfuly deleted from service="+servisNUmber+", article="+articleNumber);
        } catch (SQLException e) {
            logCon.error("it faild to connect to db and to delete article from article in servis, servisNuber="
                    +servisNUmber+", articleNumber="+articleNumber+", quantity="+quantity+"\n",e);
        }
    }
}



