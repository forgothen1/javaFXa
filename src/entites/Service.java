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
    private Integer status;
    private String status1;


    public  Service() {}

    public Service(Integer id, String name, Float price, String owner, String telephone, Integer serivisNumber, String description, String time,Integer status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.telephone = telephone;
        this.serivisNumber = serivisNumber;
        this.description = description;
        this.time=time;
        this.status=status;
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

    public void setStatus(Integer status) {this.status=status;}
    public String getStatus(){
        if (status == 1)
                return status1="prijem";
        else if (status== 2)
            return status1="obrada";
        else
            return status1="zavrseno";
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
                '}';
    }
}
