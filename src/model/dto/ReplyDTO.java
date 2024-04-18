package model.dto;

public class ReplyDTO {

    private int eno;
    private int boardno;
    private int replyno;
    private String replycontent;
    private String replydate;

    public ReplyDTO() {
    }

    public ReplyDTO(int eno, int boardno, int replyno, String replycontent, String replydate) {
        this.eno = eno;
        this.boardno = boardno;
        this.replyno = replyno;
        this.replycontent = replycontent;
        this.replydate = replydate;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public int getBoardno() {
        return boardno;
    }

    public void setBoardno(int boardno) {
        this.boardno = boardno;
    }

    public int getReplyno() {
        return replyno;
    }

    public void setReplyno(int replyno) {
        this.replyno = replyno;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public String getReplydate() {
        return replydate;
    }

    public void setReplydate(String replydate) {
        this.replydate = replydate;
    }
}
