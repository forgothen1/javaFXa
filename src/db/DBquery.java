package db;

import entites.Worker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBquery extends DBcon {
    private String variableForSearch;
    
    List<Worker> worker_collection =new ArrayList<>();

    public DBquery() throws SQLException {
    }
/*basic  method to get all workers from DB */
    public List<Worker> gettingAllWorkers() throws SQLException {

        String writeOut = "select  Name, Surname, payment, workplace, idWorker from radnik ";
        resultSet = statement.executeQuery(writeOut);
        while (resultSet.next()) {
            Integer id = null;
            // za poslije prebacivanje  na GUI i vizual
            worker = new Worker(id, resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getInt("payment"), resultSet.getString("workplace"), resultSet.getInt("idWorker"));
            worker_collection.add(worker);
            /* this allow to get all collums from DB, i<=5 is for id,name,surname,payment, workplace, idworker
             * for console  */
            for (int i = 1; i <= 5; i++) {
                System.out.print(resultSet.getString(i) + ", ");
            }
            System.out.println();

        }
        return worker_collection;

    }
/* getting  stuff for selecter / searcher  reciving to this class a string */
    public void LoaderForSearch(String optionWhatToSearch) {
        variableForSearch = optionWhatToSearch;
     //   return optionWhatToSearch;
    }

    /*this is  method that collect from DB selected in table  and sending it to  labels*/
    public List<Worker> searchOfWorkerByIdWorker() throws SQLException {
        // need to be cleared or it just stack inside LIST
        worker_collection.clear();
        String sqlQuerry="select * from radnik where idWorker="+variableForSearch;
        System.out.println(sqlQuerry);
        resultSet = statement.executeQuery(sqlQuerry);
        while (resultSet.next()) {
            // za poslije prebacivanje  na GUI i vizual
            worker = new Worker(null, resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getInt("payment"), resultSet.getString("workplace"), resultSet.getInt("idWorker"));
            worker_collection.add(worker);
        }
        return worker_collection;
    }
    /* method that get string from textfield to search in DB*/
    public List<Worker> searchOfWorkers() throws SQLException {
        worker_collection.clear();
        String sqlQuerry = "SELECT * FROM radnik where name like'%" + variableForSearch + "%' or " +
                "surname like '%" + variableForSearch + "%' or workplace like '%" + variableForSearch + "%'";
        System.out.println(sqlQuerry);
        resultSet = statement.executeQuery(sqlQuerry);
        while (resultSet.next()) {
            // za poslije prebacivanje  na GUI i vizual
            worker = new Worker(null, resultSet.getString("Name"), resultSet.getString("Surname"),
                    resultSet.getInt("payment"), resultSet.getString("workplace"), resultSet.getInt("idWorker"));

            worker_collection.add(worker);
        }
        return worker_collection;
    }

    //adding to DB value by value
    public void inputOfWorker(Worker worker) throws SQLException {
        String sqlQuestion = "insert into Radnik (name,surname,payment,workplace,idWorker) values (?,?,?,?,?)";
        int i = 1;
        preparedStatement = con.prepareStatement(sqlQuestion);
        System.out.println("/////////////////////////////");
        System.out.println(worker);
        System.out.println("/////////////////////////////");
            preparedStatement.setString(i++, worker.getName());
            preparedStatement.setString(i++, worker.getSurname());
            // payment is int value so it need to be casted to String
            preparedStatement.setInt(i++, worker.getPaymant());
            preparedStatement.setString(i++, worker.getWorkplace());
            // idworker is int value so it need to be casted to String
            preparedStatement.setInt(i, worker.getIdWorker());
            preparedStatement.executeUpdate();



    }

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
    /* updating DB with query varable is number to get idWorker */
    public void  editWorker(Worker worker) throws SQLException {
        String sqlQuery = "update radnik set name=? , surname=? , payment=? , workplace=? , idWorker=? where idWorker="
                +variableForSearch;
       System.out.println(sqlQuery);
        int i = 1;
          preparedStatement = con.prepareStatement(sqlQuery);
          System.out.println("/////////////////////////////");
          System.out.println(worker);
          System.out.println("/////////////////////////////");

          preparedStatement.setString(i++, worker.getName());
          preparedStatement.setString(i++, worker.getSurname());
          // payment is int value so it need to be casted to String
            preparedStatement.setInt(i++, worker.getPaymant());
          preparedStatement.setString(i++, worker.getWorkplace());
          // idworker is int value so it need to be casted to String
        preparedStatement.setInt(i, worker.getIdWorker());
        preparedStatement.executeUpdate();
    }
}
/*
    // returning array of strings to get all columns in DB in case of need
    public String[] OutputofColums() throws SQLException {

        String columnsOfDB="SHOW COLUMNS FROM osoblje.radnik";
        ResultSet resultSet = dataBase.statement.executeQuery(columnsOfDB);
        String collumsName=null;
        String[] collumsOfDB = new String[6];
        int i=0; //counter for array collumsOfDB;
        while(resultSet.next()) {
            collumsName=resultSet.getString(1);
            collumsOfDB[i]=collumsName;
            i++;
        }
        return collumsOfDB;
    } */