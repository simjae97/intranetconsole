package view;

import controller.EmployController;
import controller.ReportController;
import model.dto.*;

import java.util.*;

public class ReportView {
    MainView application = new MainView();

    public void allReport() {
        while (true) {
            TreeMap<ReportDTO, Boolean> reportDTOS2 = ReportController.getInstance().allReport2(); //컨트롤러의 받은 전체글 보기 함수 호출: 반환값은 리포트하나-상태로 이루어진 트리맵(해시맵의 경우 순서를 보장하지 않아 트리맵 사용, 정렬기준은 ReportDTO에 compareto 메소드를 오버라이딩 해서 지정)
            ArrayList<Integer> reportS = allsend(reportDTOS2); //VIEW의 전체글 출력 함수 호출:앞서 받아온 트리맵을 출력하고 그에 다른 게시물 번호를 받아와서 유효성 검사 실행
            System.out.println("0.뒤로가기 1.개별보고서 보기");
            System.out.print("입력> ");
            int ch = application.scanner.nextInt();

            if (ch == 0) {
                return;
            }
            else if (ch == 1) {
                System.out.println("보고싶은 게시물 번호");
                System.out.print("입력> ");
                int ch2 = application.scanner.nextInt();
                if (reportS.contains(ch2)) { //앞서 받아온 어레이리스트에 비교해 고른 리포트 번호가 존재하지않는지 검사, 존재하지않으면 읽을 권한이 없다고 판단
                    ReportDTO reportDTO = ReportController.getInstance().specificreport(ch2); //컨트롤러의 개별글 보기 함수 호출: 레포트번호를 입력받아 그에 해당하는 reportDTO를 리턴받아옴
                    boolean run = true;
                    while (run) {
                        run = subview(reportDTO);
                    }
                }
                else {
                    System.out.println("볼 권한이 존재하지 않는 보고서 번호입니다");
                }
            }
        }
    }

    public void goReport() {
        TreeMap<ReportDTO, Boolean> reportDTOS = ReportController.getInstance().goReport(); //컨트롤러의 보낸 보낸전체글 보기 함수 호출: 반환값은 리포트하나-상태로 이루어진 트리맵(해시맵의 경우 순서를 보장하지 않아 트리맵 사용, 정렬기준은 ReportDTO에 compareto 메소드를 오버라이딩 해서 지정)
        ArrayList<Integer> reportS = allsend(reportDTOS); //VIEW의 전체글 출력 함수 호출:앞서 받아온 트리맵을 출력하고 그에 다른 게시물 번호를 받아와서 유효성 검사 실행
        System.out.println("0.뒤로가기 1.게시물확인");
        System.out.print("입력> ");
        int ch = application.scanner.nextInt();

        if (ch == 1) {
            System.out.println("보고싶은 게시물 번호");
            System.out.print("입력> ");
            int ch2 = application.scanner.nextInt();
            if (reportS.contains(ch2)) { //유효성검사:만약 고른 번호가 reports안에 존재하는지 확인
                ReportDTO reportDTO = ReportController.getInstance().specificreport(ch2);
                boolean run = true; //개별글 보기 함수 실행시 무한루프 조건문: default는 true
                while (run) {
                    run = subview(reportDTO); //개별글 보기 함수 출력, 만약 글삭제,뒤로가기시에는 false로 탈출
                }
            }
            else {
                System.out.println("볼 권한이 없는 게시물입니다");
            }
        }
    }

    public boolean writeReport() {
        ArrayList <Integer> reportlist = findCategories(); //카테고리 찾기 함수,찾아온 카테고리의 cno를 동적배열에 할당
        System.out.println("보고서 카테고리 번호");
        System.out.print("입력> ");
        int ch = application.scanner.nextInt();

        if(!reportlist.contains(ch)){
            System.out.println("없는 카테고리입니다");
            return false;
        }

        System.out.println("============"+ReportController.getInstance().findType(ch)+"========="); // ch번호에 따른 카테고리 이름 호출
        ReportDTO reportDTO = null; //조건문에 따른 다른 reportDTO를 할당해 주어야 하기때문에 넣어줄 reportdto를 조건문 밖에 선언
        if(ch == 1){ //cno 가 1이다 <- WorkreportDTO를 할당해줌
            System.out.println("제목");
            System.out.print("입력> ");
            String title = application.scanner.next();
            System.out.println("업무 내용");
            System.out.print("입력> ");
            String content1 = application.scanner.next();
            System.out.println("업무 결과");
            System.out.print("입력> ");
            String content2 = application.scanner.next();
            reportDTO = new WorkreportDTO(title,content1,content2); //업캐스팅해서 값을 reportDTO에 넣어줌 <? workdto,vdto,pdto는 전부 reportdto를 상속받는 관계이기 때문에 한번에 배열처리 하기 위함
        }

        if(ch == 2){ //cno가 2가 <- VacationDTO 할당
            System.out.println("제목");
            System.out.print("입력> ");
            String title = application.scanner.next();
            System.out.println("휴가사유");
            System.out.print("입력> ");
            String content = application.scanner.next();
            System.out.println("휴가 시작일");
            System.out.print("입력> ");
            String startdate = application.scanner.next();
            System.out.println("휴가 종료일");
            System.out.print("입력> ");
            String enddate = application.scanner.next();
            reportDTO = new VacationreportDTO(title,content,startdate,enddate);
        }
        if(ch == 3){ //cno가 3다 <- purchaseDTO 할당
            System.out.println("제목");
            System.out.print("입력> ");
            String title = application.scanner.next();
            System.out.println("구매사유");
            System.out.print("입력> ");
            String content = application.scanner.next();
            System.out.println("품목/개수/개별가격");
            System.out.print("입력> ");
            String itemlist = application.scanner.next();
            System.out.println("총가격");
            System.out.print("입력> ");
            int totalprice = application.scanner.nextInt();
            reportDTO = new PurchasereportDTO(title,content,itemlist,totalprice);
        }

        ArrayList<Integer> superiors = findSuperior(); //보낼수있는 사람(grade번호가 나보다 높고 파트번호가 같은 인물 출력 후 유효성검사에 이용)
        System.out.println("보낼 사람 수");
        System.out.print("입력> ");
        int users = application.scanner.nextInt();
        if (users > superiors.size()) { //선택한 유저수가 상사의 수보다 많을 경우
            System.out.println("너무 많습니다");
            return false;
        }

        ArrayList<Integer> userArray = new ArrayList<>(); //현재 선택한 상사들의 넘버를 받아줄 어레이리스트를 선언후 초기화

        for (int i = 0; i < users; i++) {
            System.out.println("보내고싶은 사람의 번호");
            System.out.print("입력> ");
            int user = application.scanner.nextInt();

            if (superiors.contains(user) && !userArray.contains(user)) { //만약 보낼수 있는 유저의 cno= 상사의 cno이고 아직 선택하지 않은 상사의 cno이면
                userArray.add(user); //userArray에 선택한 상사의 cno 등록
            }
            else {
                System.out.println("보낼 권한이 없거나 이미 선택한 상사입니다");
                return false;
            }

        }
        return ReportController.getInstance().reportWrite(reportDTO, userArray); //컨트롤러에 작성한 reportDTO와 reportlog에 넣을 eno를 arraylist에 담아 전송

    }

    public ArrayList<Integer> findSuperior(){
        ArrayList<EmployeeDTO> superior = ReportController.getInstance().findSuperior(); //상사찾기
        System.out.println("===============보낼수 있는 사람===================");
        ArrayList<Integer> superiors = new ArrayList<>();

        for (EmployeeDTO i : superior) {
            System.out.println("번호 :" + i.getEno());
            System.out.println("성함 :" + i.getEname());
            superiors.add(i.getEno());
        }
        System.out.println("============================================================");
        return superiors;
    }

    public ArrayList<Integer> allsend(TreeMap<ReportDTO, Boolean> send){ //전체보고서(보냈을때,받았을때 둘다) 출력
        ArrayList<Integer> result = new ArrayList<>(); //전체보고서에 받아온 reportno를 저장하는 ArrayList 생성(유효성 검사용)
        System.out.println("===============================================================");
        try {
            for (Map.Entry<ReportDTO, Boolean> entry : send.entrySet()) { //매개변수로 받아온 ReportDTO-Boolean으로 이루어진 순서쌍을 차례대로 출력
                if(entry.getKey() == null){
                    System.out.println("삭제된 글입니다");
                    return result;
                }
                System.out.println("리포트 번호: ["+entry.getKey().getReportno()+"] 제목 :[ "+entry.getKey().getReporttitle()+"] 상태 : ["+ (entry.getValue() ? "결재완료" : "결재대기")+"] 타입 :"+ReportController.getInstance().findType(entry.getKey().getCno()) ); //상태가 true면 결재완려,false면 결재대기
                result.add(entry.getKey().getReportno()); //result list에 출력된 ReportDTO의 reportno를 삽입
            }
        }
        catch (Exception e) {
            System.out.println("서류함이 비었습니다");
        }
        System.out.println("===============================================================");
        return result;
    }

    public ArrayList<Integer> findCategories(){
        ArrayList<Integer> result1 = new ArrayList<>();
        ArrayList<HashMap<Integer,String >> result = ReportController.getInstance().findCategories(); //위와 동일,다만 카테고리번호와 카테고리이름을 받아옴
        System.out.println("===============================================================\n");
        for(HashMap<Integer,String >   i : result){
            for(Map.Entry<Integer,String > entry: i.entrySet()){
                System.out.println("카테고리 번호 : "+entry.getKey()+"카테고리 제목: "+entry.getValue()+"\n");
                result1.add(entry.getKey());
            }

        }
        System.out.println("===============================================================");

        return result1;
    }

    public boolean subview(ReportDTO reportDTO){ //개별 보고서 출력
        System.out.println(reportDTO.toString()); //각각 subreportDTO의 toString을 오버라이딩해 원본에 따라 다른 toString을 구현
        TreeMap<Integer, Boolean> superiors = ReportController.getInstance().findSuperiors(reportDTO.getReportno()); //상사의 cno와 결재상태를 가져오는 함수 호출
        System.out.println("");
        for (Map.Entry<Integer, Boolean> entry : superiors.entrySet()) { //cno-state로 이루어진 순서쌍 반복문
            Integer key = entry.getKey();
            Boolean value = entry.getValue();
            // 키와 값을 사용하여 작업 수행
            System.out.println("결재자: " + ReportController.getInstance().enoSearch(key).getEname() + ", 결재상태: " + value);
        }
        System.out.println("\n\n\n===============================================================\n");
        System.out.println("0.뒤로가기" + (reportDTO.getEno() == EmployController.loginEno.getEno() ? "1.보고서삭제 " : " 1.결재승인"));
        System.out.print("입력> ");
        int ch3 = application.scanner.nextInt();

        if (ch3 == 0) {
            return false; //false= 함수 위로 올라가서 run을 종료시켜버림  , true = 함수위로 올라가서 run을 계속 실행하게 만듬
        }
        else if (ch3 == 1 && reportDTO.getEno() == EmployController.loginEno.getEno()) { //순서:만약 ch3==1이고 리포트번호가 현재 로그인상태와 같으면 실행
            System.out.println("삭제함수 실행");
            if (ReportController.getInstance().reportDelete(reportDTO.getReportno())) {
                System.out.println("삭제성공");
                return false;
            } else {
                System.out.println("삭제 실패");
                return true;
            }
        }
        else if (ch3 == 1) { //순서:만약 ch3==1이면 실행이기때문에 위의 코드와 순서를 바꾸면 else에 걸려 삭제함수 실행이 제대로 이루어지지 않음. 현재 보고서에 들어올 수 있는 권한은 결재자 or 자신밖에없으니 본인이 아니면 결재승인이 뜨게함
            if (ReportController.getInstance().accept(reportDTO.getReportno())) {
                System.out.println("결재완료");
            }
            else {
                System.out.println("결재실패");
            }
            return true;
        }

        return true;
    }

}