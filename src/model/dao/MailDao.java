package model.dao;

import controller.EmployController;
import model.dto.MailDTO;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MailDao extends SuperDao{
    // 싱글톤
    private static MailDao mailDao = new MailDao();
    private MailDao(){};
    public static MailDao getInstance(){
        return mailDao;
    }

    public int emailSearch(String eemail){ // email으로 eno 찾기
        try{
            String sql = "select eno from employee where eemail = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, eemail);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("eno"); // 1명 찾으면 땡.
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return -1;
    }

    public void partnoSearch(){ // 파트넘버로 검색해서 사람들 구하기
        return;
    }

    public int[] sendpartMail(int partno){ // 부서번호 받아서. // 부서 전체 보내기기 때문에.
        try{
            String sql = "select eno from employee where partno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, partno);
            rs = ps.executeQuery(); // 파트넘버 받은 eno 애들 찾아서 뽑아내기
            int i2 = 0; // 강사님이 executeUpdate로 영향 받은 수 된다고 했지만 rs 쓰는 구문 (select 등에는
            // executeUpdate를 쓰는 순간 안됨. SQLException : Can not issue executeUpdate() or executeLargeUpdate() with statements that produce result sets
            while (rs.next()){
                i2 = i2+1; // 개수만큼 인트수에 더하기
            }
            int[] arr = new int[i2]; // 영향 받은 수만큼 배열 만들어서 저장할 배열만들기
            if(arr.length == 0){ return null;}
            rs = ps.executeQuery(); //다시 넥스트 하기전의 rs를 대입해서.
            // 안하면 오류남 오류 내용 SQLException :// After end of result set
            for(int i =0 ; i < arr.length ; i++){
                rs.next();
                arr[i] = rs.getInt("eno"); // next 된 eno의 값을 arr에 순차적으로 저장.
            }// for 문 끝

            return arr;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean sendMail(ArrayList<Map<String,String>> sendemailarr){
        try{
            String sql1 = "insert into mail(eno, mailtitle, mailcontetnt) values (?,?,?)";
            ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS); // 제너레이션 키 쓰기위해
            ps.setInt(1, EmployController.loginEno.getEno()); // 로그인 중인 사람 번호 넣기
            ps.setString(2, sendemailarr.get(sendemailarr.size()-1).get("sendEtitle"));
            ps.setString(3, sendemailarr.get(sendemailarr.size()-1).get("sendEContent"));
            // 2, 3번째에 마지막 인덱스에서 가져온 제목 내용 넣기

            if(ps.executeUpdate()==1){ // mail table에 데이터 넣고, 이제 maillog에도 넣어야한다.(들어갈 값 : 받는사람들eno, mailno(fk))
                rs = ps.getGeneratedKeys();
                rs.next();
                int mailno = rs.getInt(1); // 방금 넣은 레코드의 mailno 저장.

                int[] arr = new int[sendemailarr.size()-1]; // 마지막 전까지 eno 넣을 배열 미리 만들기
                for(int i = 0 ; i<sendemailarr.size()-1 ; i++){
                    arr[i] = emailSearch(sendemailarr.get(i).get("email"));  // 빼온 email 값으로 eno 추출하기
                }
                String sql2 = "insert into maillog(mailno, eno) values (?,?)";
                for(int i = 0; i<sendemailarr.size()-1 ; i++) { // 마지막 전까지 email값 뽑아내서 넣기
                    ps = conn.prepareStatement(sql2);
                    ps.setInt(1, mailno);
                    ps.setInt(2, arr[i]);
                    ps.executeUpdate(); // 한 줄 한 줄 업데이트.
                }
                return true; // 완료되면 트루
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public boolean sendpartMail(ArrayList<Map<String,String>> sendemailarr){ // 실제로 보내기
        try{
            String sql1 = "insert into mail(eno, mailtitle, mailcontetnt) values (?,?,?)";
            ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, EmployController.loginEno.getEno()); // 로그인 중인 사람 번호 넣기
            ps.setString(2, sendemailarr.get(sendemailarr.size()-1).get("sendEtitle"));
            ps.setString(3, sendemailarr.get(sendemailarr.size()-1).get("sendEContent"));
            // 2, 3번째에 마지막 인덱스에서 가져온 제목 내용 넣기
            // 이까진 같다
            if(ps.executeUpdate()==1){ // mail table에 데이터 넣고, 이제 maillog에도 넣어야한다.(들어갈 값 : 받는사람들eno, mailno(fk))
                rs = ps.getGeneratedKeys();
                rs.next();
                int mailno = rs.getInt(1); // 방금 넣은 레코드의 mailno 저장. // 방금 넣은 레코드의 mailno 저장.

                String sql2 = "insert into maillog(mailno, eno) values (?,?)";
                for(int i = 0; i<sendemailarr.size()-1 ; i++) { // 마지막 전까지 email값 뽑아내서 넣기
                    ps = conn.prepareStatement(sql2);
                    ps.setInt(1, mailno);
                    ps.setInt(2, Integer.parseInt(sendemailarr.get(i).get("eno")));//받아온 eno 를 인트로 변환해서 다시 넣어주기
                    ps.executeUpdate(); // 한 줄 한 줄 업데이트.
                }
                return true; // 완료되면 트루
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public TreeMap<MailDTO, ArrayList<String>> sendMailsView(int loginEno){
        try{
            String sql = "select * from mail where eno = ? order by mailno; ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, loginEno);
            rs = ps.executeQuery();
            TreeMap<MailDTO, ArrayList<String>> sendMailsarr = new TreeMap<>();
            while(rs.next()){ // 보낸 메일 테이블 정보
                MailDTO mailDTO = new MailDTO();
                mailDTO.setEno(rs.getInt("eno"));//보낸 사람
                mailDTO.setMailno(rs.getInt("mailno"));// 보낸 메일 넘버
                mailDTO.setMailtitle(rs.getString("mailtitle"));
                mailDTO.setMailcontetnt(rs.getString("mailcontetnt"));
                mailDTO.setMaildate(rs.getString("maildate"));
                String sql2 = "select * from maillog join employee on maillog.eno = employee.eno where mailno = ? order by mailno;";
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, rs.getInt("mailno"));
                rs2 = ps.executeQuery();
                ArrayList<String> enameArr = new ArrayList<>();
                while (rs2.next()){
                    enameArr.add(rs2.getString("ename"));
                } // 보낸 mailno에 맞는 ename 받아오기
                sendMailsarr.put(mailDTO, enameArr);
            } // while 끝
            return sendMailsarr;
        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public ArrayList<Map<String,String>> receiveMail(int loginEno){
        try{
            String sql = "select *from maillog join mail on maillog.mailno = mail.mailno where maillog.eno = ? order by mail.mailno;"; // 조인해서 mail.eno 검색해서 mail.mailno로 정렬하기
            // maillog가 받은 eno 가 내가 되야한다.
            ps = conn.prepareStatement(sql);
            ps.setInt(1, loginEno);
            rs = ps.executeQuery();
            ArrayList<Map<String,String>> recMailarr = new ArrayList<>();
            while(rs.next()){
//                if(rs.getInt("mailstate")==2 || rs.getInt("mailstate")==3 ){ continue;}
                // 상태가 2나 3이면 다음으로 넘어가기 하려고 했는데 휴지통 생각해서 그냥 다 넣기.
                Map<String, String> map = new HashMap<>();
                map.put("sendeno",rs.getString("mail.eno")); //보낸 사람
                map.put("mailno", ""+rs.getInt("mail.mailno"));
                map.put("mailstate", rs.getInt("mailstate")+""); // 일단 혹시 모르니 state도 저장
                map.put("mailtitle", rs.getString("mailtitle"));
                map.put("mailcontetnt", rs.getString("mailcontetnt"));
                map.put("maildate", rs.getString("maildate"));
                recMailarr.add(map); // map 하나씩 recMailarr에 넣기
            }

            return recMailarr;
        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public ArrayList<String> receiveEnoSearch(int mailno){ // 메일 받은 사람들 배열보이기
        try{
            String sql = "select * from maillog join employee on maillog.eno = employee.eno where mailno = ?"; // 조인해서 mail.eno 검색해서 mail.mailno로 정렬하기
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mailno);
            rs = ps.executeQuery();
            ArrayList<String> recMailarr = new ArrayList<>();
            while(rs.next()){
                recMailarr.add(rs.getString("ename"));
            }
            return recMailarr;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<String> sendEnoSearch(int mailno) { // 메일 보낸 사람들 배열보이기
        try{
            String sql = "select * from maillog join employee on maillog.eno = employee.eno where mailno = ?"; // 조인해서 mail.eno 검색해서 mail.mailno로 정렬하기
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mailno);
            rs = ps.executeQuery();
            ArrayList<String> sendMailarr = new ArrayList<>();
            while(rs.next()){
                sendMailarr.add(rs.getString("ename"));
            }
            return sendMailarr;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean changeEmailState(int state, int mailno){ // state 바꾸기.
        try{
            String sql = "update maillog set mailstate = ?  where mailno = ? and eno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, mailno);
            ps.setInt(3, EmployController.loginEno.getEno());
            int count = ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }



}
