package entites;

/**
 * entity with data for new imput of product , from suplayer with number and price
 */
public class PurchesInvoice {
        private Integer ID;
        private String entry ;
        private  String orderNumber;
        private String suplayer;
        private String dateSent;
        private String dateRecive;
        private  Float price;
        private Float  PDV;
        private Float pricewithPDV;
        private Integer quantity;



    public PurchesInvoice() {
    }

    /**
     *
     * @param ID id in db per invoice
     * @param entry number of entry in this company prob will be 00/00
     * @param orderNumber number dat will be send by suplayer
     * @param suplayer organisacion who send it
     * @param dateSent its simple date when suplayer send it
     * @param dateRecive its when its recived in company
     * @param price price with out PDV
     * @param PDV tax for gov
     * @param pricewithPDV price with tax / PDV icluded
     * @param quantity number of articles in invoice;
     */
    public PurchesInvoice(Integer ID,String entry, String orderNumber, String suplayer, String dateSent,String dateRecive, Float price, Float PDV, Float pricewithPDV,Integer quantity) {
        this.ID=ID;
        this.entry = entry;
        this.orderNumber = orderNumber;
        this.suplayer = suplayer;
        this.dateSent = dateSent;
        this.dateRecive= dateRecive;
        this.price = price;
        this.PDV = PDV;
        this.pricewithPDV = pricewithPDV;
        this.quantity= quantity;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSuplayer() {
        return suplayer;
    }

    public void setSuplayer(String suplayer) {
        this.suplayer = suplayer;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getDateRecive() {
        return dateRecive;
    }

    public void setDateRecive(String dateRecive) {
        this.dateRecive = dateRecive;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPDV() {
        return PDV;
    }

    public void setPDV(Float PDV) {
        this.PDV = PDV;
    }

    public Float getPricewithPDV() {
        return pricewithPDV;
    }

    public void setPricewithPDV(Float pricewithPDV) {
        this.pricewithPDV = pricewithPDV;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PurchesInvoice{" +
                "ID=" + ID +
                ", entry='" + entry + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", suplayer='" + suplayer + '\'' +
                ", dateSent='" + dateSent + '\'' +
                ", dateRecive='" + dateRecive + '\'' +
                ", price=" + price +
                ", PDV=" + PDV +
                ", pricewithPDV=" + pricewithPDV +
                ", quantity " + quantity+
                '}';
    }
}
