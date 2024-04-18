package model.dto;

public class MailDTO implements Comparable<MailDTO>{
    private int mailno;
    private int eno;
    private String mailtitle;
    private String mailcontetnt;
    private String maildate;

    public MailDTO() {
    }

    public MailDTO(int mailno, int eno, String mailtitle, String mailcontetnt, String maildate) {
        this.mailno = mailno;
        this.eno = eno;
        this.mailtitle = mailtitle;
        this.mailcontetnt = mailcontetnt;
        this.maildate = maildate;
    }

    public int getMailno() {
        return mailno;
    }

    public void setMailno(int mailno) {
        this.mailno = mailno;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public String getMailtitle() {
        return mailtitle;
    }

    public void setMailtitle(String mailtitle) {
        this.mailtitle = mailtitle;
    }

    public String getMailcontetnt() {
        return mailcontetnt;
    }

    public void setMailcontetnt(String mailcontetnt) {
        this.mailcontetnt = mailcontetnt;
    }

    public String getMaildate() {
        return maildate;
    }

    public void setMaildate(String maildate) {
        this.maildate = maildate;
    }

    public int compareTo(MailDTO other) {
        // 여기서는 mailno를 기준으로 정렬하도록 구현합니다.
        return Integer.compare(other.mailno, this.mailno);
        //다른애들이 메일 넘버와 지금 현재 mailno를 비교해서 return 한다.
    }
}
