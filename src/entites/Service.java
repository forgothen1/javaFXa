package entites;

public class Service {
   private Integer id;
    private String name;
    private Float price;
    private String owner;
    private String telephone;
    private Integer serivisNumber;
    private String description;

    public  Service() {}

    public Service(Integer id, String name, Float price, String owner, String telephone, Integer serivisNumber, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.owner = owner;
        this.telephone = telephone;
        this.serivisNumber = serivisNumber;
        this.description = description;
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
