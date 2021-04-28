package entites;

/**
 * entity with data for new imput of product , from suplayer with number and price
 */
public class PurchesInvoice {
        private Integer ID;
        private String entry ;
        private String orderNumber;
        private String suplayer;
        private String dateSent;
        private String dateRecive;
        private  Float MPprice;
        private Float  PDV;
        private Float VPPrice;
        private Float sumPricewithPDV;
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
     * @param MPprice price with out PDV
     * @param PDV tax for gov
     * @param VPPrice price with tax / PDV icluded
     * @param quantity number of articles in invoice;
     */
    public PurchesInvoice(Integer ID,String entry, String orderNumber, String suplayer, String dateSent,String dateRecive, Float MPprice, Float PDV, Float VPPrice,Float sumPricewithPDV,Integer quantity) {
        this.ID=ID;
        this.entry = entry;
        this.orderNumber = orderNumber;
        this.suplayer = suplayer;
        this.dateSent = dateSent;
        this.dateRecive= dateRecive;
        this.MPprice = MPprice;
        this.PDV = PDV;
        this.VPPrice = VPPrice;
        this.sumPricewithPDV=sumPricewithPDV;
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

    public Float getMPprice() {
        return MPprice;
    }

    public void setMPprice(Float price) {
        this.MPprice = price;
    }

    public Float getPDV() {
        return PDV;
    }

    public void setPDV(Float PDV) {
        this.PDV = PDV;
    }

    public Float getVPPrice() {
        return VPPrice;
    }

    public void setVPPrice(Float VPPrice) {
        this.VPPrice = VPPrice;
    }

    public Float getSumPricewithPDV() {
        return sumPricewithPDV;
    }

    public void setSumPricewithPDV(Float sumPricewithPDV) {
        this.sumPricewithPDV = sumPricewithPDV;
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
                ", MPprice=" + MPprice +
                ", PDV=" + PDV +
                ", VPPrice=" + VPPrice +
                ", sumPricewithPDV=" + sumPricewithPDV +
                ", quantity=" + quantity +
                '}';
    }
}
