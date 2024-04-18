package model.dao;

import controller.BoadAllViewController;
import model.dto.BoardDTO;
import model.dto.EmployeeDTO;

import java.util.ArrayList;

public class BoardAllViewDao extends SuperDao {
    private BoardAllViewDao(){}
    private static BoardAllViewDao boardAllViewDao = new BoardAllViewDao();
    public static BoardAllViewDao getInstance(){return boardAllViewDao;}

    public ArrayList<BoardDTO> boardAllView(int ch, EmployeeDTO loginstate){
        ArrayList<BoardDTO> boardDTOArrayList = new ArrayList<>();
        // [5] 받은 ch마다 카테고리 값 변경
        try {
            String sql = "select * from board where bcno = ? and bcno2 = ? order by boardno desc;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,ch+1);
            int bcno2 = 0;
            if(ch+1 == 3  ){
                bcno2 = loginstate.getPartno();
            } else if(ch+1 == 4){
                bcno2 = loginstate.getGradeno();
            } else {
                bcno2 = 0;
            }
            ps.setInt(2,bcno2);
            rs = ps.executeQuery();
            while (rs.next()){  //  rs.next() -> 데이터베이스 테이블에서 한칸이동후 값이 있으면 true 없으면 false
                // true 일때
                BoardDTO boardDto = new BoardDTO();
                boardDto.setEno(rs.getInt("eno"));  // eno 가져와 객체에 넣어준다.
                boardDto.setBoardno(rs.getInt("boardno"));  // boardno 가져와 객체에 넣어준다.
                boardDto.setBoardtitle(rs.getString("boardtitle"));  // boardtitle 가져와 객체에 넣어준다.
                boardDto.setBoardcontent(rs.getString("boardcontent")); // boardcontent 가져와 객체에 넣어준다.
                boardDto.setBoarddate(rs.getString("boarddate"));   // boarddate 가져와 객체에 넣어준다.
                boardDto.setBcno(rs.getInt("bcno"));  // bcno 가져와 객체에 넣어준다.
                boardDTOArrayList.add(boardDto); // 9.필요한 자료를 넣은 객체를 배열에 넣어준다.
            }   // false(다음께 없으면) 종료
            return boardDTOArrayList;
        } catch (Exception e){
            System.out.println(e);
        }

        return null;
    }
}
