package model.dao;

import model.dto.EmployeeDTO;
import model.dto.PartDTO;
import model.dto.ReplyDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReplyDao extends SuperDao{

    // 싱글톤
    private ReplyDao(){};
    private static ReplyDao replyDao = new ReplyDao();
    public static ReplyDao getInstance(){return replyDao;}

    public ArrayList<Map> replyview(int boardno){ // 리플들을 담을 배열 빼오기.
        ArrayList<Map> result = new ArrayList<>();
        try{
            String sql = "select*from reply where boardno = ? order by replyno;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardno);
            rs = ps.executeQuery();
            while (rs.next()){// 한 줄(레코드) 단위로 소환.
                Map<String, String> map1 = new HashMap<>();
                // 리플 하나 하나당 있을 데이터 , 작성자, 부서, 쓴 날짜, 내용
                map1.put("eno", rs.getString("eno"));
                map1.put("replyno", rs.getString("replyno"));
                map1.put("replycontent" , rs.getString("replycontent"));
                map1.put("replydate", rs.getString("replydate"));
                result.add(map1); // 댓글 하나 어레이리스트에 넣기
            }
            for(int i=0 ; i<result.size(); i++){
                EmployeeDTO employeeDTO = enoSearch(Integer.parseInt((String)(result.get(i).get("eno"))));
                // 어레이리스트 i번쨰 인덱스 map 키값 eno의 String을 정수로 변환해서 받아온 회원 정보들을 employeeDTO에 저장.
                result.get(i).put("ename",employeeDTO.getEname()); // 그 놈은 그 i번쨰 어레이리스트(맵) 하나에 ename으로 저장.
                result.get(i).put("partname",partnoSearch(employeeDTO.getPartno()).getPartname()); // 이 놈은 그 i번쨰 어레이리스트(맵) 하나에 partname으로 저장.
            }


        }catch (Exception e){
            System.out.println(e);
        }
        return result;
    } // 리뷰 보이기 로직

    public boolean replyWrite(ReplyDTO replyDTO){
        try {
            String sql = "insert into reply(eno, boardno, replycontent) values (?,?,?);";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, replyDTO.getEno());
            ps.setInt(2, replyDTO.getBoardno());
            ps.setString(3, replyDTO.getReplycontent());
            if(ps.executeUpdate()==1){
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean replyDelete(int replyno){
        try {
            String sql = "delete from reply where replyno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, replyno);
            if(ps.executeUpdate()==1){ // 업데이트 된건가 확인할 것.
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean replyUpdate(ReplyDTO replyDTO){
        try {
            String sql = "update reply set replycontent =? where replyno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, replyDTO.getReplycontent());
            ps.setInt(2, replyDTO.getReplyno());
            if(ps.executeUpdate()==1){ // 업데이트 된건가 확인할 것.
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

}
