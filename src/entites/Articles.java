package entites;

import java.text.DecimalFormat;

public class Articles {
    private Integer id;
    private  String name;
    private   String SerialNumber;
    private   Integer IdArticles;
    private   String description;
    private   Integer quantity;
    private   Integer quantityUse;
    private   Integer kolicinaProdato;
    private  Integer kolicinaUkupno;
    private Float entryPrice;
    private  Float price;
    private Float imputPrice;
    private Float outputPrice;
    private Float sumPrice;
    private Integer  sortOfProduct;
    private String location;
    private DecimalFormat df = new DecimalFormat("#.##");

    /**
     * empty constructor
     */
    public Articles() {}

    /**
     *  constructor
     * @param id return int when needed
     * @param name name of article return String
     * @param serialNumber  serial number specific for every article
     * @param idArticles id of article that is put manually possible to be deleted later
     * @param description of article
     * @param quantity ...
     * @param quantityUse how much article is used in production , this is different of sold something like sold but not yet
     * @param kolicinaProdato quantity how much it was sold
     * @param kolicinaUkupno quantity how much there is left
     * @param entryPrice price for start
     * @param price its float
     * @param sumPrice its price of kolicinaUkupno * price probably won't be use in normal use of table
     * @param sortOfProduct what type is product its in numbers can be only 1,2,3
     * @param location its for location of product
     */
    public Articles(Integer id, String name, String serialNumber, Integer idArticles, String description, Integer quantity,
                    Integer quantityUse, Integer kolicinaProdato, Integer kolicinaUkupno,Float entryPrice, Float price, Float sumPrice, Integer sortOfProduct, String location) {
        this.id = id;
        this.name = name;
        this.SerialNumber = serialNumber;
        this.IdArticles = idArticles;
        this.description = description;
        this.quantity = quantity;
        this.quantityUse = quantityUse;
        this.entryPrice= entryPrice;
        this.price=price;
        this.sumPrice=sumPrice;
        this.kolicinaProdato=kolicinaProdato;
        this.kolicinaUkupno=kolicinaUkupno;
        this.sortOfProduct=sortOfProduct;
        this.location=location;

    }

    public Integer getKolicinaProdato() {
        return kolicinaProdato;
    }

    public void setKolicinaProdato(Integer kolicinaProdato) {
        this.kolicinaProdato = kolicinaProdato;
    }

    public Integer getKolicinaUkupno() {
        return kolicinaUkupno;
    }

    public void setKolicinaUkupno(Integer kolicinaUkupno) {
        this.kolicinaUkupno = kolicinaUkupno;
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

    public Float getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(Float entryPrice) {
        this.entryPrice = entryPrice;
    }

    public Float getImputPrice() {
        imputPrice=price*quantity;
        imputPrice= Float.valueOf(df.format(imputPrice));
        return imputPrice;
    }

    public void setImputPrice(Float imputPrice) {
        this.imputPrice = imputPrice;
    }

    public Float getOutputPrice() {
        outputPrice=price*kolicinaProdato;
        outputPrice= Float.valueOf(df.format(outputPrice));
        return outputPrice;
    }

    public void setOutputPrice(Float outputPrice) {
        this.outputPrice = outputPrice;
    }

    public Float getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Float sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Integer getSortOfProduct() {
        return sortOfProduct;
    }

    public void setSortOfProduct(Integer sortOfProduct) {
        this.sortOfProduct = sortOfProduct;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", SerialNumber='" + SerialNumber + '\'' +
                ", IdArticles=" + IdArticles +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", quantityUse=" + quantityUse +
                ", kolicinaProdato=" + kolicinaProdato +
                ", kolicinaUkupno=" + kolicinaUkupno +
                ", entryPrice=" + entryPrice +
                ", price=" + price +
                ", imputPrice=" + imputPrice +
                ", outputPrice=" + outputPrice +
                ", sumPrice=" + sumPrice +
                ", sortOfProduct=" + sortOfProduct +
                ", location='" + location + '\'' +
                ", df=" + df +
                '}';
    }
}
