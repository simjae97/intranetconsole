package model.dao;

import controller.EmployController;
import controller.ReportController;
import model.dto.*;

import javax.annotation.processing.Generated;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ReportDAO extends SuperDao {

    private ReportDAO(){}
    private static ReportDAO reportDAO = new ReportDAO();
    public static ReportDAO getInstance(){return reportDAO;}

//    public ArrayList<ReportDTO> allReport() { //폐기
//        try {
//            String sql = "SELECT report.* FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.eno = ? and reportlog.confirm = true;";
//            int loginnum = EmployController.loginEno.getEno();
//            ps = conn.prepareStatement(sql);
//            ps.setInt(1, loginnum);
//            rs = ps.executeQuery();
//            ArrayList<ReportDTO> reportDTOS = new ArrayList<>();
//            while (rs.next()) {
//                ReportDTO reportDTO = new ReportDTO();
//                reportDTO.setReporttitle(rs.getString("reporttitle"));
//                reportDTO.setReportno(rs.getInt("reportno"));
//                reportDTOS.add(reportDTO);
//            }
//            return reportDTOS;
//        } catch (Exception e) {
//
//        }
//        return null;
//    }

    public TreeMap<ReportDTO, Boolean> allReport2() { //받은 보고서 테이블 출력,현재 reportlog의 결재상태도 같이 가져와야하므로 맵구조로 가져옴,다만 cno기준의 정렬을 보장해야하므로 Hashmap이 아닌 treemap 사용

        try {
            String sql = "SELECT report.*,reportlog.confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.eno = ?;"; //조인해서 로그에 남은 로그인넘버기준으로 값을 가져옴,상태는 단순히 결재상태로 받아오면 됨
            int loginnum = EmployController.loginEno.getEno();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, loginnum);
            rs = ps.executeQuery();
            TreeMap<ReportDTO, Boolean> reportDTOS = new TreeMap<>();
            while (rs.next()) {
                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setReporttitle(rs.getString("reporttitle"));
                reportDTO.setReportno(rs.getInt("reportno"));
                reportDTO.setCno(rs.getInt("cno"));
                boolean state = rs.getBoolean("confirm");
                reportDTOS.put(reportDTO, state);
            }
            return reportDTOS;
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

//    public HashMap<ReportDTO,Boolean> allReport2(){
//        try {
//            String sql = "SELECT report.*,reportlog.confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.eno = ?;";
//            int loginnum = ReportController.loginemployee.getEno();
//            ps = conn.prepareStatement(sql);
//            ps.setInt(1, loginnum);
//            rs = ps.executeQuery();
//            HashMap<ReportDTO,Boolean> reportDTOS = new HashMap<>();
//            while(rs.next()){
//                ReportDTO reportDTO = new ReportDTO();
//                reportDTO.setReporttitle(rs.getString("reporttitle"));
//                reportDTO.setReportno(rs.getInt("reportno"));
//                boolean state = rs.getBoolean("confirm");
//                reportDTOS.put(reportDTO,state);
//            }
//            return reportDTOS;
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
//        return null;
//    }

    public boolean reportWrite(ReportDTO dto, ArrayList<Integer> array) { //cno추가,cno에 따른 다운캐스팅후 개별테이블에 입력

        try {
            int cno = dto instanceof WorkreportDTO? 1: dto instanceof VacationreportDTO? 2:dto instanceof PurchasereportDTO?3:0; //받아온 dto의 원래 형태를 돌려받음
            if (cno == 0){
                System.out.println("잘못된 형식의 보고서입니다");
                return false;
            }
            String sql = " insert into Report(eno,reporttitle, reportcontent, cno) values (?, ?,?,?);";
            int loginnum = EmployController.loginEno.getEno();
            // 2. SQL 기재
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //생성된 기본키를 리턴받기
            // ? 매개변수 대입
            ps.setInt(1, loginnum); // 기재된 SQL내 첫번째 ?에 값 대입
            ps.setString(2, dto.getReporttitle()); // 기재된 SQL내 2번째 ?에 값 대입
            ps.setString(3, dto.getReportcontent()); // 기재된 SQL내 3번째 ?에 값 대입
            ps.setInt(4, cno);

            // 3. SQL 실행 // 4. SQL 결과
            int count = ps.executeUpdate();//executeUpdate() 기재된 sql 실행하고 insert된 레코드 개수 반환.

            rs = ps.getGeneratedKeys(); //바로 primary key 가져오기 //getGeneratedKeys로 생성된 generatedKey를 돌려받음
            rs.next(); // rs.실행
            int pk = rs.getInt(1); //출력된 primary key를 pk에 저장

            if (count == 1) { // 1개가 영향을 받았다는 소리니까 혹시 0개일때를 대비해서 유효성

                if (cno == 1){
                    WorkreportDTO workreportDTO = (WorkreportDTO) dto;
                    sql = "insert into workreport(reportno,content2) values(?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1,pk);
                    ps.setString(2,workreportDTO.getContent2());
                    int count2 = ps.executeUpdate();
                    if(count2 != 1){
                        sql = "delete from report where reportno = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1,pk);
                        ps.executeUpdate();
                        return false;
                    }
                }

                if (cno == 2){
                    VacationreportDTO vacationreportDTO = (VacationreportDTO) dto;
                    sql = "insert into vacationreport(reportno,startdate,enddate) values(?,?,?)";
                    String startdate =vacationreportDTO.getStartdate();
                    String enddate = vacationreportDTO.getEnddate();

                    if(startdate.compareTo(enddate) >= 0){
                        sql = "delete from report where reportno = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1,pk);
                        ps.executeUpdate();
                        return false;
                    }

                    ps = conn.prepareStatement(sql);
                    ps.setInt(1,pk);
                    ps.setString(2,vacationreportDTO.getStartdate());
                    ps.setString(3,vacationreportDTO.getEnddate());
                    int count2 = ps.executeUpdate();
                    if(count2 != 1){
                        sql = "delete from report where reportno = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1,pk);
                        ps.executeUpdate();
                        return false;
                    }

                }

                if (cno == 3){
                    PurchasereportDTO purchasereportDTO = (PurchasereportDTO) dto;
                    sql = "insert into purchasereport(reportno,itemlist,totalprice) values(?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1,pk);
                    ps.setString(2,purchasereportDTO.getItemlist());
                    ps.setInt(3,purchasereportDTO.getTotalprice());
                    int count2 = ps.executeUpdate();
                    if(count2 != 1){
                        sql = "delete from report where reportno = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1,pk);
                        ps.executeUpdate();
                        return false;
                    }
                }

                for (int i : array) {
                    sql = "insert into reportlog(reportno, eno) values(?,?);";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1, pk);
                    ps.setInt(2, i);
                    int check = ps.executeUpdate();
                    if (check != 1) {
                        sql = "delete from report where reportno = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1,pk);
                        ps.executeUpdate();
                        sql = "delete from reportlog where reportno = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setInt(1,pk);
                        ps.executeUpdate();
                        return false;
                    }
                }

                return true;

            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        // 5. 함수 종료
        return false; // 실패 샘플
    }

    public TreeMap<ReportDTO, Boolean> goReport() {

        try {
            String sql = "SElect report.reportno,report.reporttitle,cno,Count(*) as count FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE report.eno = ? group by report.reportno;";
            String sql2 = "SELECT report.reportno,Count(*) as count FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE report.eno = ? and reportlog.confirm =true group by report.reportno, reportlog.confirm;";
            int loginnum = EmployController.loginEno.getEno();

            ps = conn.prepareStatement(sql);
            ps.setInt(1, loginnum);
            rs = ps.executeQuery();

            ps = conn.prepareStatement(sql2);
            ps.setInt(1, loginnum);
            rs2 = ps.executeQuery();
            TreeMap<ReportDTO, Boolean> reportDTOS = new TreeMap<>();

            HashMap<Integer, Integer> compare = new HashMap<>();

            while (rs2.next()) {
                compare.put(rs2.getInt("reportno"), rs2.getInt("count"));
            }

            while (rs.next()) {
                ReportDTO reportDTO = new ReportDTO();
                int reportno = rs.getInt("reportno");
                reportDTO.setReporttitle(rs.getString("reporttitle"));
                reportDTO.setReportno(reportno);
                reportDTO.setCno(rs.getInt("cno"));
                int count = rs.getInt("count");
                int result = 0;

                for (Map.Entry<Integer, Integer> entry : compare.entrySet()) {
                    int compareReportNo = entry.getKey();
                    int compareCount = entry.getValue();
                    if (compareReportNo == reportno && count == compareCount) {
                        reportDTOS.put(reportDTO, true);
                        result = 1;
                        break;
                    }
                }

                if (result == 0) {
                    reportDTOS.put(reportDTO, false);
                }
            }

            return reportDTOS;

        }
        catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    public ReportDTO specificreport(int num) {
        String sql = "select * from report where reportno = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            rs = ps.executeQuery();
            if(rs.next()){
                int cno = rs.getInt("cno");

                if(cno == 1){
                    WorkreportDTO reportDTO = new WorkreportDTO();
                    reportDTO.setReportcontent(rs.getString("reportcontent"));
                    reportDTO.setReporttitle(rs.getString("reporttitle"));
                    reportDTO.setEno(rs.getInt("eno"));
                    reportDTO.setReportno(rs.getInt("reportno"));
                    reportDTO.setReportdate(rs.getString("reportdate"));
                    sql = "select * from workreport where reportno =?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1,num);
                    rs2 = ps.executeQuery();

                    if(rs2.next()){
                        reportDTO.setContent2(rs2.getString("content2"));
                    }

                    return reportDTO;
                }

                if(cno == 2){
                    VacationreportDTO reportDTO = new VacationreportDTO();
                    reportDTO.setReportcontent(rs.getString("reportcontent"));
                    reportDTO.setReporttitle(rs.getString("reporttitle"));
                    reportDTO.setEno(rs.getInt("eno"));
                    reportDTO.setReportno(rs.getInt("reportno"));
                    reportDTO.setReportdate(rs.getString("reportdate"));
                    sql = "select * from Vacationreport where reportno =?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1,num);
                    rs2 = ps.executeQuery();

                    if(rs2.next()){
                        reportDTO.setStartdate(rs2.getString("startdate"));
                        reportDTO.setEnddate(rs2.getString("enddate"));
                    }

                    return reportDTO;
                }

                if(cno == 3){
                    PurchasereportDTO reportDTO = new PurchasereportDTO();
                    reportDTO.setReportcontent(rs.getString("reportcontent"));
                    reportDTO.setReporttitle(rs.getString("reporttitle"));
                    reportDTO.setEno(rs.getInt("eno"));
                    reportDTO.setReportno(rs.getInt("reportno"));
                    reportDTO.setReportdate(rs.getString("reportdate"));
                    sql = "select * from Purchasereport where reportno = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setInt(1,num);
                    rs2 = ps.executeQuery();

                    if(rs2.next()){
                        reportDTO.setItemlist(rs2.getString("itemlist"));
                        reportDTO.setTotalprice(rs2.getInt("totalprice"));
                    }

                    return reportDTO;
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public boolean accept(int num){
        String sql = "update reportlog set confirm = true where reportno = ? and eno = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setInt(2, EmployController.loginEno.getEno());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return false;
    }

    public ArrayList<EmployeeDTO> findSuperior(){ //변동없음(아직)

      try {
          String sql = "SELECT * FROM Employee WHERE gradeno>? and partno = ?;";
          int logingrade = EmployController.loginEno.getGradeno();
          int loginpart = EmployController.loginEno.getPartno();
          ps = conn.prepareStatement(sql);
          ps.setInt(1, logingrade);
          ps.setInt(2, loginpart);
          rs = ps.executeQuery();
          ArrayList<EmployeeDTO> reportDTOS = new ArrayList<>();

          while (rs.next()) {
              EmployeeDTO employeeDTO = new EmployeeDTO();
              employeeDTO.setEno(rs.getInt("eno"));
              employeeDTO.setEname(rs.getString("ename"));
              reportDTOS.add(employeeDTO);
          }

          return reportDTOS;
      }
      catch (Exception e) {
          System.out.println(e);
      }

      return null;
    }
    public boolean reportDelete(int num){ //변동없음(아직)

        try {
            String sql = "DELETE from report WHERE reportno = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        return false;
    }
    public TreeMap<Integer, Boolean > findSuperiors(int num){ //변동없음 (아직)

        try {
            TreeMap<Integer, Boolean > superiors = new TreeMap<>();
            String sql = "select eno, confirm from reportlog where reportno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            rs = ps.executeQuery();

            while(rs.next()) {
                superiors.put(rs.getInt("eno"),rs.getBoolean("confirm"));
            }

            return superiors;
        }
        catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public String findType(int cno){ //신규 추가

        try {
            String sql = "select cname from reportcategory where cno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cno);
            rs = ps.executeQuery();

            if(rs.next()){
                return rs.getString("cname");
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public ArrayList<HashMap<Integer,String >> findCategories(){
        ArrayList<HashMap<Integer,String >> result = new ArrayList<>();

        try {
            String sql = "select * from reportcategory";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                HashMap<Integer, String> input = new HashMap<>();
                input.put(rs.getInt("cno"),rs.getString("cname"));
                result.add(input);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        return result;
    }
}

