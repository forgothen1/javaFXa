package entites;

import java.util.ArrayList;

public class Worker {
    private  Integer id;
    private   String Name;
    private  String Surname;
    private Integer paymant;
    private String workplace;
    private Integer idWorker;
    public Worker(){}
    //to have constructor with id where its needed like getting data from DB
    public Worker(Integer id, String name, String surname, Integer paymant, String workplace, Integer idWorker) {
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

    public Integer getPaymant() {
        return paymant;
    }

    public void setPaymant(Integer paymant) {
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
