package entites;

public class Service {
    private Integer id;
    private String name;
    private Float price;
    private String owner;
    private String telephone;
    private Integer serivisNumber;
    private String description;
    private String time;
    private Integer statusInt;
    private String statusStrg;
    private String comment;


    public  Service() {}

    /**
     *
     * @param id id return INTEGER
     * @param name name of product that is serviced string(20)
     * @param price of service Float(5,2)
     * @param owner  of  product String(30)
     * @param telephone number varchar(20)
     * @param serivisNumber number of service Integer and its auto increment
     * @param description  what is needed to do in service blob
     * @param time its auto incremented and its time when its acepted in service
     * @param status  represent in what state it is  working on it finished or  peid  Integer(1)
     */

    public Service(Integer id, String name, Float price, String owner, String telephone, Integer serivisNumber, String description, String time,Integer status,String comment) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.telephone = telephone;
        this.serivisNumber = serivisNumber;
        this.description = description;
        this.time=time;
        this.statusInt =status;
        this.comment=comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getSerivisNumber() {
        return serivisNumber;
    }

    public void setSerivisNumber(Integer serivisNumber) {
        this.serivisNumber = serivisNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime(){return time ;}

    public void setTime(String time){this.time=time ;}

    public void setStatusInt(Integer statusInt) {this.statusInt = statusInt;}

    /**
     * return status as string  with proper name
     * @return string
     */
    public String getStatusStg(){
        if (statusInt == 1)
            return statusStrg ="prijem";
        else if (statusInt == 2)
            return statusStrg ="obrada";
        else if (statusInt == 3)
            return statusStrg ="zavrseno";
        else if (statusInt== 4)
            return statusStrg ="naplaceno";
        else
            return String.valueOf(statusInt);
    }
    public Integer getStatusInt(){
        return statusInt;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", owner='" + owner + '\'' +
                ", telephone='" + telephone + '\'' +
                ", serivisNumber=" + serivisNumber +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", status=" + statusInt +
                ", status1='" + statusStrg + '\'' +
                ", status1='" + comment + '\'' +
                '}';
    }
}
