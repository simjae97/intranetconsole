package model.dao;

import model.dto.BoardDTO;

public class BoardUpdateDao extends SuperDao {
    //싱글톤
    private BoardUpdateDao(){};
    private static BoardUpdateDao boardUpdateDao = new BoardUpdateDao();
    public static BoardUpdateDao getInstance(){
        return boardUpdateDao;
    }

    public boolean boardUpdate(BoardDTO boardDTO){ // 글 수정 실행 함수 DTO로 받기
        try {
            String sql = "update board set boardtitle = ?, boardcontent =? where boardno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, boardDTO.getBoardtitle());
            ps.setString(2, boardDTO.getBoardcontent());
            ps.setInt(3, boardDTO.getBoardno());
            if(ps.executeUpdate()==1){ // 업데이트 된건가 확인할 것.
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    } // 글 수정 끝

    public boolean boardDelete(int boardno){ // 글 삭제 실행 함수
        try {
            String sql = "delete from board where boardno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardno);
            if(ps.executeUpdate()==1){ // 업데이트 된건가 확인할 것.
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    } // 글 삭제 끝

}
