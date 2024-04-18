package model.dto;

public class WorkreportDTO extends ReportDTO{
    String content2;

    public WorkreportDTO(String content2) {
        this.content2 = content2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public WorkreportDTO() {
    }

    public WorkreportDTO(String reporttitle, String reportcontent, String content2) {
        super(reporttitle, reportcontent);
        this.content2 = content2;
    }

    @Override
    public String toString() {
        return super.toString() + "\n업무 내용 :"+super.getReportcontent()+"\n업무 결과 :"+content2;
    }

}
