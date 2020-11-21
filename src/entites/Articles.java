package entites;

public class Articles {
   private Integer id;
    private  String name;
    private   String SerialNumber;
    private   Integer IdArticles;
    private   String description;
    private   Integer quantity;
    private   Integer quantityUse;
    private  Float price;
    private Float sumPrice;
    /**
     * empty constructor
     */
    public Articles() {}

    /**
     *  constructor
     * @param id return int when needed
     * @param name name of article return String
     * @param serialNumber  serial number specific for every article
     * @param idArticles id of article that is put manualy *posible to be deleted later
     * @param description of article
     * @param quantity ...
     * @param quantityUse how much article is used in production , this is difrent of sold  someting like sold but not yet
     * @param price its float
     * @param sumPrice its price of quantity * price probably wont be use in normal use of table
     */
    public Articles(Integer id, String name, String serialNumber, Integer idArticles, String description, Integer quantity, Integer quantityUse, Float price, Float sumPrice) {
        this.id = id;
        this.name = name;
         this.SerialNumber = serialNumber;
        this.IdArticles = idArticles;
        this.description = description;
        this.quantity = quantity;
        this.quantityUse = quantityUse;
        this.price=price;
        this.sumPrice=sumPrice;

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

    public Float getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Float sumPrice) {
        this.sumPrice = sumPrice;
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
