package model.dto;

public class GradeDTO {
    private int gradeno;
    private String gradename;

    public GradeDTO() {
    }

    public GradeDTO(int gradeno, String gradename) {
        this.gradeno = gradeno;
        this.gradename = gradename;
    }

    public int getGradeno() {
        return gradeno;
    }

    public void setGradeno(int gradeno) {
        this.gradeno = gradeno;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }
}
