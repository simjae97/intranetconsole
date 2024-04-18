package model.dao;

import controller.EmployController;
import model.dto.EmployeeDTO;
import view.EmployeeView;

import java.util.ArrayList;

public class EmployeeDao extends SuperDao{
    //싱글톤
    private EmployeeDao(){}
    private static EmployeeDao employeeDao = new EmployeeDao();
    public static EmployeeDao getInstance(){return  employeeDao;}

    public int signUp(EmployeeDTO employeeDTO){
        try {
            String sql = "insert into employee(eid,epw,ename,ephone,eemail) values (?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEid());
            ps.setString(2,employeeDTO.getEpw());
            ps.setString(3,employeeDTO.getEname());
            ps.setString(4,employeeDTO.getEphone());
            ps.setString(5, employeeDTO.getEemail());

            int count =ps.executeUpdate(); //count => 실행된 레코드 수
            if(count==1){ // 실행된 레코드 수가 1 이면 성공
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1; //실패
    }

    public boolean checkId(String eid){
        try{
            String sql = "select eid from employee where eid = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eid);
            rs = ps.executeQuery();
            if(rs.next()){ return true; }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkEmail(String eeamil){
        try {
            String sql = "select eemail from employee where eemail =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,eeamil);
            rs = ps.executeQuery();
            if(rs.next()){return true;}
        }catch (Exception e){
            e.printStackTrace();
        }
         return  false;
    }

    public boolean logIn(EmployeeDTO employeeDTO){
        try{
            String sql = "select eno from employee where eid = ? and epw = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEid());
            ps.setString(2,employeeDTO.getEpw());
            rs= ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int[] findEPGno(EmployeeDTO employeeDTO){ // Eno Partno gradeno 가져오기
        try {
            String sql = "select eno,partno,gradeno from employee where eid = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEid());
            rs= ps.executeQuery();
            if(rs.next()){
                return new int[]{rs.getInt("eno"),rs.getInt("partno"),rs.getInt("gradeno")};
            }
            int[] result = new int[3];
            result[0] = rs.getInt("eno");
            result[1] = rs.getInt("partno");
            result[2] = rs.getInt("gradeno");
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String findEid(EmployeeDTO employeeDTO){ //eno을 가지고 eid 찾기
        try {
            String sql = "select eid from employee where eno = ?";
            ps= conn.prepareStatement(sql);
            ps.setInt(1,employeeDTO.getEno());
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getString("eid");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean exit(EmployeeDTO employeeDTO, int i){

        try { //id 값과 pw 값을 가지고 employee테이블에서 eno를 가져온다.
            String sql = "select eno from employee where eid = ? and epw = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEid());
            ps.setString(2,employeeDTO.getEpw());

            rs = ps.executeQuery(); // eno를 rs에 대입

            if(rs.next()){//rs를 하나씩 비교한다.
                if(rs.getInt("eno")==i){ //rs 에 들어간 eno 값이 i 와 같으면
                    String sqld = "delete from employee where eno = ?"; // employee 테이블에서 eno 번호가 i인 것을 삭제 하는 구문
                    ps= conn.prepareStatement(sqld);
                    ps.setInt(1,i);

                    ps.executeUpdate();
                    return true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<EmployeeDTO> employeeInfo(EmployeeDTO employeeDTO){ //arraylist 에 값을 담아 주기!
        ArrayList<EmployeeDTO> result = new ArrayList<>();
        try{
            String sql = "select * from employee"; // employee 테이블 을 가져온다.
            ps=conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                employeeDTO = new EmployeeDTO(); // 빈 employeeDto 생성
                employeeDTO.setEno(rs.getInt("eno"));               //값 넣기
                employeeDTO.setGradeno(rs.getInt("gradeno"));       //값 넣기
                employeeDTO.setEid(rs.getString("eid"));            //값 넣기
                employeeDTO.setEpw(rs.getString("epw"));            //값 넣기
                employeeDTO.setEname(rs.getString("ename"));        //값 넣기
                employeeDTO.setPartno(rs.getInt("partno"));         //값 넣기
                employeeDTO.setEphone(rs.getString("ephone"));      //값 넣기
                employeeDTO.setEemail(rs.getString("eemail"));      //값 넣기
                employeeDTO.setEdate(rs.getString("edate"));        //값 넣기
                result.add(employeeDTO); //arraylist에 employDto 넣기
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean changegradeno(EmployeeDTO employeeDTO){
        try{
            String sql = "update employee set gradeno = ? where ename = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeDTO.getGradeno());
            ps.setString(2,employeeDTO.getEname());

            ps.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean changepartno(EmployeeDTO employeeDTO){
        try{
            String sql = "update employee set partno = ? where ename = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,employeeDTO.getPartno());
            ps.setString(2,employeeDTO.getEname());

            ps.executeUpdate();
            return  true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean fire(EmployeeDTO employeeDTO){
        try{
            String sql = "delete from employee where ename = ?"; //employee 테이블에서 ename 값이 일치하면 지운다.
            ps = conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEname());
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateInfopw(EmployeeDTO employeeDTO){
        try{
            String sql = "update employee set epw = ? where eid = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEpw());
            ps.setString(2,employeeDTO.getEid());

            ps.executeUpdate();
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }


        return false;
    }
    public boolean updateInfophone(EmployeeDTO employeeDTO){
        try{
            String sql = "update employee set ephone = ? where eid = ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEphone());
            ps.setString(2,employeeDTO.getEid());
            ps.executeUpdate();
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateInfoEmail(EmployeeDTO employeeDTO){
        try {
            String sql = "update employee set eemail = ? where eid =?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,employeeDTO.getEemail());
            ps.setString(2,employeeDTO.getEid());
            ps.executeUpdate();
            return true;


        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
