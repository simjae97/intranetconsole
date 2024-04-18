package model.dto;

public class PartDTO {
    private int partno;
    private String partname;

    public PartDTO() {
    }

    public PartDTO(int partno, String partname) {
        this.partno = partno;
        this.partname = partname;
    }

    public int getPartno() {
        return partno;
    }

    public void setPartno(int partno) {
        this.partno = partno;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }
}
