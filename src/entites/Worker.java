package entites;

import java.util.ArrayList;

/**
 * view for worker
 */
public class Worker {
    private  Integer id;
    private   String Name;
    private  String Surname;
    private Float paymant;
    private String workplace;
    private Integer idWorker;

    /**
     * empty constructor
     */
    public Worker(){}

    /**
     * constructor
     * @param id if needed id from DB
     * @param name name of worker
     * @param surname last name of worker
     * @param paymant  its float payment for work
     * @param workplace place where they work
     * @param idWorker manual id of worker
     */
    //to have constructor with id where its needed like getting data from DB
    public Worker(Integer id, String name, String surname, Float paymant, String workplace, Integer idWorker) {
        this.id = id;
        this.Name = name;
        this.Surname = surname;
        this.paymant = paymant;
        this.workplace= workplace;
        this.idWorker = idWorker;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public Float getPaymant() {
        return paymant;
    }

    public void setPaymant(Float paymant) {
        this.paymant = paymant;
    }

    public Integer getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Integer idWorker) {
        this.idWorker = idWorker;
    }

    /*pripered to print out worker for cheking in console*/
    @Override
    public String toString() {
        return "Worker{" +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", paymant=" + paymant +
                ", workplace='" + workplace + '\'' +
                ", idWorker=" + idWorker +
                '}';
    }
}
