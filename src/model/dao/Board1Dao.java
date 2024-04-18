package model.dao;


import model.dto.BoardDTO;
import model.dto.EmployeeDTO;

import java.util.HashMap;
import java.util.Map;

public class Board1Dao extends SuperDao{ // 개별 글 관련 dao
    // 싱글톤
    private Board1Dao(){};
    private static Board1Dao board1Dao = new Board1Dao();
    public static Board1Dao getInstance(){
        return board1Dao;
    }

    public Map board1(int boardno){ // 없으면 리턴 null, 있으면 Map보내기
        BoardDTO boardDTO;
        try {
            String sql = "select * from board where boardno = ?;";   // boardno넘버대로 전체를 가져왔다.
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardno);
            rs = ps.executeQuery();     //실행 후 결과 값 rs저장
            while (rs.next()) { // rs.next() -> 데이터베이스 테이블에서 한칸이동후 값이 있으면 true 없으면 false
                boardDTO = new BoardDTO();
                boardDTO.setBoardno(rs.getInt("boardno"));  // boardno 가져와 객체에 넣어준다.
                boardDTO.setBoardtitle(rs.getString("boardtitle"));  // boardtitle 가져와 객체에 넣어준다.
                boardDTO.setBoardcontent(rs.getString("boardcontent"));  // boardcontent 가져와 객체에 넣어준다.
                boardDTO.setBoarddate(rs.getString("boarddate"));   // boarddate 가져와 객체에 넣어준다.
                int eno = rs.getInt("eno"); // eno 가져와서 작성자를 확인한다. // 메소드로 만들기.
                EmployeeDTO employeeDTO = enoSearch(eno); // eno로 가져온 회원DTO 정보저장.


                Map<String , String> map = new HashMap<>();
                map.put("boardno", boardDTO.getBoardno()+"");
                map.put("boardtitle", boardDTO.getBoardtitle());
                map.put("boardcontent", boardDTO.getBoardcontent());
                map.put("boarddate", boardDTO.getBoarddate());
                map.put("eno", employeeDTO.getEno()+"");
                map.put("eid", employeeDTO.getEid());
                map.put("eemail", employeeDTO.getEemail());
                map.put("ename", employeeDTO.getEname());
                map.put("gradeno", employeeDTO.getGradeno()+"");
                map.put("partno", employeeDTO.getPartno()+"");


                return map;
            }   // 종료
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;// DB에서 찾은 boardno에 맞는 Query쓰기
    }

    public boolean board1Checking(int boradno, EmployeeDTO loginEno){
        try {
            String sql = "select * from board where boardno = ? and eno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,boradno);
            ps.setInt(2,loginEno.getEno());
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            } else {
                sql = "select * from boardpermit where boardno = ? and (gradeno = ? or partno = ?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1,boradno);
                ps.setInt(2,loginEno.getGradeno());
                ps.setInt(3,loginEno.getPartno());
                rs = ps.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }
}
