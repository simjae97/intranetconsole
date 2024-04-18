package model.dao;
import model.dto.EmployeeDTO;
import model.dto.GradeDTO;
import model.dto.PartDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SuperDao {
    protected Connection conn;
    protected PreparedStatement ps; //작성된 sql을 가지고있고 실행 담당
    protected ResultSet rs;
    protected ResultSet rs2;

    SuperDao(){
        try {
            //1.MYSQL 회사의 JDBC관련된 객체를 JVM에 로딩한다
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.연동된 결과의 객체를 connetction 인터페이스에 대입한다
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phoenix", "root", "1234");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public EmployeeDTO enoSearch(int eno){ // 없으면 리턴값 null 검색되면 employeeDTO // eno로 자기 정보 받아오기.
        EmployeeDTO employeeDTO;
        try{
            String sql = "select * from employee where eno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, eno);
            rs = ps.executeQuery();
            while (rs.next()) { // rs.next() -> 데이터베이스 테이블에서 한칸이동후 값이 있으면 true 없으면 false
                employeeDTO = new EmployeeDTO();
                employeeDTO.setEno(rs.getInt("eno"));  // eno 가져와 객체에 넣어준다.
                employeeDTO.setEid(rs.getString("eid"));  // eid 가져와 객체에 넣어준다.
                employeeDTO.setEemail(rs.getString("eemail"));  // eemail 가져와 객체에 넣어준다.
                employeeDTO.setEname(rs.getString("ename"));   // ename 가져와 객체에 넣어준다.
                employeeDTO.setGradeno(rs.getInt("gradeno")); // gradeno 가져와서 객체에 넣어준다.
                employeeDTO.setPartno(rs.getInt("partno")); // partno 가져와서 객체에 넣어준다.
                return employeeDTO;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<PartDTO> partView(){ // 전체 Part출력용 Dao기능
        PartDTO partDTO;
        try{
            String sql = "select * from part order by partno";
            ArrayList<PartDTO> partArr = new ArrayList<>();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // rs.next() -> 데이터베이스 테이블에서 한칸이동후 값이 있으면 true 없으면 false
                partDTO = new PartDTO();
                partDTO.setPartname(rs.getString("partname"));  // eno 가져와 객체에 넣어준다.
                partDTO.setPartno(rs.getInt("partno"));  // eid 가져와 객체에 넣어준다.
                partArr.add(partDTO);
            }
            return partArr;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public PartDTO partnoSearch(int partno){ // 없으면 리턴값 null 검색되면 PartDTO // partno로 자기 정보 받아오기.
        PartDTO partDTO;
        try{
            String sql = "select * from part where partno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, partno);
            rs = ps.executeQuery();
            while (rs.next()) { // rs.next() -> 데이터베이스 테이블에서 한칸이동후 값이 있으면 true 없으면 false
                partDTO = new PartDTO();
                partDTO.setPartname(rs.getString("partname"));  // eno 가져와 객체에 넣어준다.
                partDTO.setPartno(rs.getInt("partno"));  // eid 가져와 객체에 넣어준다.
                return partDTO;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public GradeDTO gradenoSearch(int gradeno){ // gradeno로 gradeDTO 값 뽑아내기
        GradeDTO gradeDTO;
        try{
            String sql = "select * from grade where gradeno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gradeno);
            rs = ps.executeQuery();
            while (rs.next()) { // rs.next() -> 데이터베이스 테이블에서 한칸이동후 값이 있으면 true 없으면 false
                gradeDTO = new GradeDTO();
                gradeDTO.setGradename(rs.getString("gradename"));  // 가져와 객체에 넣어준다.
                gradeDTO.setGradeno(rs.getInt("gradeno"));  // e가져와 객체에 넣어준다.
                return gradeDTO;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public int boardnoSearch(int boardno){//보드넘버로 eno 구하기.
        try{
            String sql = "select * from board where boardno = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, boardno);
            rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("eno"); //board넘버에서 가져온 eno return값으로 보내주기
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return 0; // 없으면 0 리턴
    }



}
