package entites;

public class Articles {
    Integer id;
    String name;
    String SerialNumber;
    Integer IdArticles;
    String description;
    Integer quantity;
    Integer quantityUse;
    Float price;
    public Articles()
    {}
    public Articles(Integer id, String name, String serialNumber, Integer idArticles, String description, Integer quantity, Integer quantityUse, Float price) {
        this.id = id;
        this.name = name;
         SerialNumber = serialNumber;
        IdArticles = idArticles;
        this.description = description;
        this.quantity = quantity;
        this.quantityUse = quantityUse;
        this.price=price;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public Integer getIdArticles() {
        return IdArticles;
    }

    public void setIdArticles(Integer idArticles) {
        IdArticles = idArticles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUse() {
        return quantityUse;
    }

    public void setQuantityUse(Integer quantityUse) {
        this.quantityUse = quantityUse;
    }

    @Override
    public String toString() {
        return "Articles{" +

                ", name='" + name + '\'' +
                ", SerialNumber='" + SerialNumber + '\'' +
                ", IdArticles=" + IdArticles +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", quantityUse=" + quantityUse +
                ", price=" + price +
                '}';
    }
}
