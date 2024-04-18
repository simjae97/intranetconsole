package model.dto;

public class PurchasereportDTO extends ReportDTO{
    String itemlist;
    int totalprice;

    public PurchasereportDTO(){}

    public PurchasereportDTO(String reporttitle, String reportcontent, String itemlist, int totalprice) {
        super(reporttitle, reportcontent);
        this.itemlist = itemlist;
        this.totalprice = totalprice;
    }

    public String getItemlist() {
        return itemlist;
    }

    public void setItemlist(String itemlist) {
        this.itemlist = itemlist;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }



    @Override
    public String toString() {
        return super.toString() +
                "\n구매사유 : "+super.getReportcontent()+
                "\n구매물품리스트 : " + itemlist +
                "\n총 가격 :" + totalprice
               ;
    }
}
