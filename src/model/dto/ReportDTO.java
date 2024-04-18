package model.dto;

import controller.ReportController;
import model.dao.EmployeeDao;
import model.dao.ReportDAO;
import model.dao.SuperDao;

public class ReportDTO implements Comparable<ReportDTO> {

    private int eno;
    private int reportno;
    private String reporttitle;
    private String reportcontent;

    private String reportdate;
    private int cno;


    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public ReportDTO() {
    }

    @Override
    public int compareTo(ReportDTO o) {
        return Integer.compare(this.reportno, o.reportno);
    }

    @Override
    public String toString() {
        return "======================================================\n\n\n"+"제목 : " + reporttitle+"\n번호 :" + reportno+
                "\n보낸사람 :" + ReportController.getInstance().enoSearch(eno).getEname()+ "\n보낸 일자 : " + reportdate;
    }

    public ReportDTO(String reporttitle, String reportcontent) {
        this.reporttitle = reporttitle;
        this.reportcontent = reportcontent;
    }

    public ReportDTO(int eno, int reportno, String reporttitle, String reportcontent) {
        this.eno = eno;
        this.reportno = reportno;
        this.reporttitle = reporttitle;
        this.reportcontent = reportcontent;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public int getReportno() {
        return reportno;
    }

    public void setReportno(int reportno) {
        this.reportno = reportno;
    }

    public String getReporttitle() {
        return reporttitle;
    }

    public void setReporttitle(String reporttitle) {
        this.reporttitle = reporttitle;
    }

    public String getReportcontent() {
        return reportcontent;
    }

    public void setReportcontent(String reportcontent) {
        this.reportcontent = reportcontent;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

}
