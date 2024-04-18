package model.dto;

public class BoardDTO {

    private int boardno;
    private String boardtitle;

    private String boardcontent;
    private int eno;
    private String boarddate;

    private int bcno;

    private int bcno2;

    public BoardDTO() {
    }

    public BoardDTO(int boardno, String boardtitle, String boardcontent, int eno, String boarddate, int bcno,int bcno2) {
        this.boardno = boardno;
        this.boardtitle = boardtitle;
        this.boardcontent = boardcontent;
        this.eno = eno;
        this.boarddate = boarddate;
        this.bcno = bcno;
        this.bcno2 = bcno2;
    }

    public int getBoardno() {
        return boardno;
    }

    public void setBoardno(int boardno) {
        this.boardno = boardno;
    }

    public String getBoardtitle() {
        return boardtitle;
    }

    public void setBoardtitle(String boardtitle) {
        this.boardtitle = boardtitle;
    }

    public String getBoardcontent() {
        return boardcontent;
    }

    public void setBoardcontent(String boardcontent) {
        this.boardcontent = boardcontent;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public String getBoarddate() {
        return boarddate;
    }

    public void setBoarddate(String boarddate) {
        this.boarddate = boarddate;
    }

    public int getBcno() {
        return bcno;
    }

    public void setBcno(int bcno) {
        this.bcno = bcno;
    }

    public int getBcno2() {
        return bcno2;
    }

    public void setBcno2(int bcno2) {
        this.bcno2 = bcno2;
    }
}
