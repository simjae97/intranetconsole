package view;

import controller.EmployController;
import model.dao.SuperDao;
import model.dto.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EmployeeView {
    Scanner scanner = new MainView().scanner;
    List<String> employeeList = new ArrayList<>();

    //회원가입
    public void signUp(){

        //입력
        System.out.print("아이디를 입력하세요 : ");            String id = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");          String pw = scanner.nextLine();
        System.out.print("이름을 입력하세요 :");              String name = scanner.nextLine();
        System.out.print("전화번호를 입력하세요 : ");          String phone = scanner.nextLine();
        System.out.print("이메일을 입력하세요 : ");            String email = scanner.nextLine();

        //객체화
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEid(id);             employeeDTO.setEpw(pw);        employeeDTO.setEname(name);
        employeeDTO.setEphone(phone);       employeeDTO.setEemail(email);

        //컨트롤러에 전달/결과 받기
        int result = EmployController.getInstance().signUp(employeeDTO);

        if(result ==0){
            System.out.println("<안내> 회원가입에 성공하였습니다.");

        }else if (result == 1){
            System.out.println("<안내> 회원가입에 실패했습니다.");
        }else if (result == 2){
            System.out.println("<안내> 중복된 아이디 입니다.");
        }else if(result ==3 ){
            System.out.println("<안내> 중복된 이메일 입니다.");
        }
    }

    public boolean logIn(){
        //입력
        System.out.print("아이디 : ");       String id = scanner.nextLine();
        System.out.print("비밀번호 : ");      String pw = scanner.nextLine();

        //객체화
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEid(id); employeeDTO.setEpw(pw);
        //전달
        boolean result = EmployController.getInstance().logIn(employeeDTO);
        //출력
        if(result){
            System.out.println("<안내> 로그인 성공");
        }else{
            System.out.println("<안내> 로그인 실패");
        }
        return result;
    }

    public void exit(){//회원 탈퇴
        //입력
        System.out.println("회원 탈퇴 아이디 : ");     String id = scanner.nextLine();
        System.out.println("회원 탈퇴 비밀번호 : ");    String pw = scanner.nextLine();
        //객체화
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEid(id);     employeeDTO.setEpw(pw);

        //전달
        boolean result = EmployController.getInstance().exit(employeeDTO);
        //결과
        if(result){
            System.out.println("<안내>회원 탈퇴를 성공했습니다.");
        }else {
            System.out.println("<안내>회원 탈퇴에 실패했습니다.");
        }

    }

    public void employeeInfo(){ //회원 정보 출력
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                                                    직원 정보                                                                          ");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s \t %-5s \t %-5s \t %-7s \t %-18s \t %-20s \t %-10s \t %-10s \t %-20s \n","회원번호","직급","부서","이름","전화번호","email","id","pw","date");

        //System.out.println("회원번호 \t 직급 \t 부서 \t 이름 \t 전화번호 \t\t email \t\t id \t\t pw \t\t date");
        //객체화
        EmployeeDTO employeeDTO = new EmployeeDTO();

        ArrayList<EmployeeDTO> result = EmployController.getInstance().employeeInfo(employeeDTO);


        //결과 출력
        for(int i=0; i<result.size();i++) {

            int employeeno = result.get(i).getEno();
            String partname = EmployController.getInstance().managerpartView(result.get(i).getPartno()).getPartname();// result 어레이리스트에 i번째 partno 으로 출력하는 구문
            String gradename = EmployController.getInstance().managergradeView(result.get(i).getGradeno()).getGradename();
            String ename = result.get(i).getEname();
            String ephone = result.get(i).getEphone();
            String eemail = result.get(i).getEemail();
            String eid = result.get(i).getEid();
            String epw = result.get(i).getEpw();
            String edate = result.get(i).getEdate();

            System.out.printf("%-8d \t %-5s \t %-5s \t %-7s \t %-20s \t %-20s \t %-10s \t %-10s \t %-20s \n",employeeno,(gradename.equals("전체")?"미지정":gradename),partname,ename,ephone,eemail,eid,epw,edate);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void changegradeno(){ //직책 변경
        System.out.print("대상자 : ");
        String changename = scanner.nextLine();
        System.out.println("1.사원, 2.대리, 3.팀장, 4.부장, 5.임원");
        System.out.println("바뀔 직급 : ");
        int changegrade = scanner.nextInt();
        scanner.nextLine();

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEname(changename);
        employeeDTO.setGradeno(changegrade);

        boolean result = EmployController.getInstance().changegradeno(employeeDTO);

        if(result){
            System.out.println("직급이 변경되었습니다.");
        }else{
            System.out.println("직급 변경에 실패했습니다.");
        }
    }

    public void changepartno(){ //부서 변경
        //입력
        System.out.println("대상자 : ");
        String changename = scanner.nextLine();
        System.out.println("1.총무 , 2.회계 , 3.개발");
        System.out.println("바뀔 팀 번호: ");
        int changepart = scanner.nextInt();
        scanner.nextLine();
        //객체
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEname(changename);
        employeeDTO.setPartno(changepart);

        //컨트롤러
        boolean result = EmployController.getInstance().changepartno(employeeDTO);

        //결과
        if(result){
            System.out.println("부서를 변경했습니다.");
        }else{
            System.out.println("부서 변경 실패 ");
        }

    }

    public void fire(){ // 해고
        System.out.println("대상자 : ");
        String firename = scanner.nextLine();

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEname(firename);

        boolean result = EmployController.getInstance().fire(employeeDTO);

        if(result){
            System.out.println("해고!!!");
        }else {
            System.out.println("해고 실패!!!");
        }

    }

    //개인정보 수정
    public void updateInfopw(){

        System.out.println("회원정보를 수정합니다. ");
        System.out.println("본인 아이디를 입력하세요");
        String id = scanner.nextLine();

        // 유효성 검사 -> id값이 EmployController.getInstance().findEid() 에서 나온 값과 같지 않으면 실행
        if(!id.equals(EmployController.getInstance().findEid())){
            System.out.println("아이디가 틀렸습니다.");
            return;
        }

        System.out.print("비밀번호를 입력하세요 : ");
        String newpw = scanner.nextLine();
        //객체화
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEpw(newpw); employeeDTO.setEid(id);


        //전달
        boolean result = EmployController.getInstance().updateInfopw(employeeDTO);
        //출력
        if(result){
            System.out.println("비밀번호 변경");
        }else {
            System.out.println("실패");
        }

    }
    public void updateInfophone(){

        System.out.println("회원정보를 수정합니다. ");
        System.out.println("본인 아이디를 입력하세요");
        String id = scanner.nextLine();

        // 유효성 검사 -> id값이 EmployController.getInstance().findEid() 에서 나온 값과 같지 않으면 실행
        if(!id.equals(EmployController.getInstance().findEid())){
            System.out.println("아이디가 틀렸습니다.");
            return;
        }

        System.out.print("변경할 번호를 입력하세요 : ");
        String newphone = scanner.nextLine();

        //객체화
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEid(id);
        employeeDTO.setEphone(newphone);

        //전달
        boolean result = EmployController.getInstance().updateInfophone(employeeDTO);
        //출력
        if(result){
            System.out.println("전화번호 변경");
        }else {
            System.out.println("실패");
        }

    }

    public void updateInfoEmail(){
        System.out.println("회원정보를 수정합니다.");
        System.out.println("본인 아이디를 입력하세요");
        String id = scanner.nextLine();

        // 유효성 검사 -> id값이 EmployController.getInstance().findEid() 에서 나온 값과 같지 않으면 실행
        if(!id.equals(EmployController.getInstance().findEid())){
            System.out.println("아이디가 틀렸습니다.");
            return;
        }
        System.out.println("변경할 이메일을 입력하세요");
        String newEmail = scanner.nextLine();

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEid(id);
        employeeDTO.setEemail(newEmail);

        boolean result = EmployController.getInstance().updateInfoEmail(employeeDTO);

        if(result){
            System.out.println("이메일 변경");
        }else{
            System.out.println("실패");
        }

    }

}
