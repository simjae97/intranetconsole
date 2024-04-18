package view;

import controller.EmployController;
import controller.MailController;
import model.dto.EmployeeDTO;
import model.dto.MailDTO;
import model.dto.PartDTO;

import java.util.*;

public class MailView {
    Scanner scanner = new MainView().scanner;
    public void run() {
        while (true) {
            System.out.println("0.뒤로 가기 | 1.메일 보내기 | 2.받은 메일 | 3.보낸 메일 | 4.휴지통");
            System.out.print("입력 > ");
            int ch = scanner.nextInt();
            if (ch == 0) { // 뒤로 가기
                return;
            } else if (ch == 1) { // 메일 보내기
                System.out.println("0.뒤로 가기 | 1.일반 메일보내기 | 2.부서 메일보내기 ");
                System.out.print("입력 > ");
                int ch2 = scanner.nextInt();
                scanner.nextLine();
                if(ch2==0){continue; // 뒤로 가기
                } else if (ch2==1) { // 일반 메일
                    System.out.print("보낼 사람 수를 선택하세요 : ");
                    int ch3 = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<Map<String, String>> sendemailarr = new ArrayList<>();
                    for (int i = 0; i < ch3; i++) { // 보낼 사람 수만큼
                        System.out.print("보낼 사람 이메일 : ");
                        String sendemail = scanner.nextLine();
                        // 입력하는 순간 DB 유효성 검사로 보내기.
                        if (MailController.getInstance().emailCheck(sendemail)) {// false로 나오면 db에 없는 메일주소
                            System.out.println("없는 이메일입니다. 다시 입력해주세요."); // 이거 왜 안되냐
                            i = i-1; // 다시 입력 받으러 되돌아가면서 i 증감식 전 -1 해주기.
                            continue;
                        }
                        Map<String, String> map = new HashMap<>();
                        map.put("email", sendemail);
                        sendemailarr.add(map);
                        //어레이리스트에 값을 집어넣을때에는 add,get의 경우 값을 return받을때, set의 경우 값을 "변경" 할 때
                        // 따라서 sendmailarr에 값을 반복해서 집어넣어야하므로 add사용
                    } //for문 끝
                    System.out.print("보낼 메일 제목 : ");
                    String sendEtitle = scanner.nextLine();
                    Map<String, String> map = new HashMap<>();
                    map.put("sendEtitle", sendEtitle);
                    System.out.print("보낼 메일 내용 : ");
                    String sendEContent = scanner.nextLine();
                    map.put("sendEContent", sendEContent);
                    sendemailarr.add(map); // 어레이리스트 마지막 Map에 title 과 content 넣기
                    if (MailController.getInstance().sendMail(sendemailarr)) {
                        System.out.println("메일 전송 완료.");
                    } else {
                        System.out.println("메일 전송 실패.");
                    }

                } else if (ch2==2) { // 부서 메일보내기 시작
                    int ch3;
                    while (true){
                        ArrayList<PartDTO> partArr = MailController.getInstance().partView();// part 테이블 어레이리스트로 받음
                        for(int i = 0; i<partArr.size();i++){ //part 테이블 저장된 레코드들 빼내기 로직
                            System.out.print(partArr.get(i).getPartno()+". "+partArr.get(i).getPartname()+" | ");
                        }
                        System.out.println();
                        System.out.print("보낼 부서 입력 > ");
                        ch3 = scanner.nextInt();
                        scanner.nextLine();
                        if(MailController.getInstance().sendpartMail(ch3)==null) {// 입력받은 partno로 배열로 뽑아내기. 유효성 검사
                            System.out.println("잘못된 부서 입력입니다. 다시 입력해주세요");
                            continue;
                        }else{ // 부서 잘못 입력 안했으면 반복 끝
                            break;
                        }
                    }
                    int[] enoarr= MailController.getInstance().sendpartMail(ch3); //partno로 알아낸 eno 배열
                    ArrayList<Map<String, String>> sendemailarr = new ArrayList<>();
                    for (int i = 0; i < enoarr.length; i++) { // 보낼 사람 수만큼
                        Map<String, String> map = new HashMap<>();
                        map.put("eno", ""+enoarr[i]);
                        sendemailarr.add(map);
                        //어레이리스트에 값을 집어넣을때에는 add,get의 경우 값을 return받을때, set의 경우 값을 "변경" 할 때
                        // 따라서 sendmailarr에 값을 반복해서 집어넣어야하므로 add사용
                    } //for문 끝
                    System.out.print("보낼 메일 제목 : ");
                    String sendEtitle = scanner.nextLine();
                    Map<String, String> map = new HashMap<>();
                    map.put("sendEtitle", sendEtitle);
                    System.out.print("보낼 메일 내용 : ");
                    String sendEContent = scanner.nextLine();
                    map.put("sendEContent", sendEContent);
                    sendemailarr.add(map); // 어레이리스트 마지막 Map에 title 과 content 넣기
                    if (MailController.getInstance().sendpartMail(sendemailarr)) {
                        System.out.println("메일 전송 완료.");
                    } else {
                        System.out.println("메일 전송 실패.");
                    }
                }
            } else if (ch == 2) { // 받은 메일함 // 안 읽은 메일 / 읽은 메일 들어가서 세부 구분할 것
                System.out.println("받은 메일 함");
                // 전체 메일로그의 내 eno 로직 출력
                ArrayList<Map<String,String>> a = MailController.getInstance().receiveMail(EmployController.loginEno.getEno());// ArrayList로 Map을 받기.
                Collections.reverse(a); // 어레이리스트 반대로 뒤집기
                for(int i =0 ; i < a.size() ; i++){
                    String sendeno = a.get(i).get("sendeno");
                    EmployeeDTO employeeDTO = MailController.getInstance().enoSearch(Integer.parseInt(sendeno)); // DTO 받기
                    String sendenoView = employeeDTO.getEname();
                    String rmno = a.get(i).get("mailno");
                    String rmtitle = a.get(i).get("mailtitle");
                    String rmstate = a.get(i).get("mailstate");
                    String rmstateView = "";
                    if(rmstate.equals("0")){ rmstateView = "읽지 않음";
                    } else if (rmstate.equals("1")) { rmstateView = "읽음";
                    } else if (rmstate.equals("2")) { continue;
                    } else if (rmstate.equals("3")) { continue;
                    }

                    System.out.printf("%-5s | 보낸 사람:%-5s | 제목 : %-50s | %s\n",rmno,sendenoView,rmtitle,rmstateView);
                }
                System.out.println("0.뒤로가기 | 1.메일 보기 | 2.안 읽은 메일 | 3.읽은 메일 | 4.휴지통 보내기");
                // 안 읽은 메일 로그의 인트 0 / 읽은 메일 로그의 인트 1
                System.out.print("입력 > ");
                int ch2 = scanner.nextInt();
                if(ch2==0){ // 뒤로가기
                    continue;
                } else if (ch2==1) { // 메일 보기
                    System.out.println("볼 메일 번호를 입력하세요");
                    System.out.print("입력 > ");
                    int ch3 = scanner.nextInt();
                    int i = 0; // i번째를 구할 변수.
                    for(i = 0; i< a.size(); i++){ // 개별 메일 출력
                        String rmstate = a.get(i).get("mailstate");
                        String rmno = a.get(i).get("mailno");
                        if(ch3 == Integer.parseInt(rmno) && (rmstate.equals("0")||rmstate.equals("1"))){ // 읽음 안읽음 메일만 나오게.
                            String rmtitle = a.get(i).get("mailtitle");
                            String rmcontetnt = a.get(i).get("mailcontetnt");
                            String rmdate = a.get(i).get("maildate");
                            String rmsendeno = a.get(i).get("sendeno");
                            System.out.println();
                            System.out.println("메일 번호 : "+rmno);
                            System.out.println("제목 : "+rmtitle);
                            System.out.println("보낸 사람 : " + MailController.getInstance().enoSearch(Integer.parseInt(rmsendeno)).getEname()); // eno가 나올게 아니라 ename이 나오게
                            System.out.print("받는 사람 : ");
                            for(int i2 = 0 ; i2 < MailController.getInstance().receiveEnoSearch(ch3).size() ; i2++){ // maillog에서 빼온 애들 for문 돌려 출력
                                if(i2!=MailController.getInstance().receiveEnoSearch(ch3).size()-1){System.out.print(MailController.getInstance().receiveEnoSearch(ch3).get(i2)+ ", ");}
                                else{System.out.print(MailController.getInstance().receiveEnoSearch(ch3).get(i2));}


                            }
                            System.out.println();
                            System.out.println();
                            System.out.println("내용 : "+rmcontetnt);
                            System.out.println();
                            System.out.println("보낸 날짜 : "+rmdate);
                            System.out.println();
                            MailController.getInstance().changeEmailState(1 , Integer.parseInt(rmno)); // 본 메일 상태 바꾸기 (읽음)
                            System.out.println("===============================================================================");
                            System.out.println("0.뒤로 가기 | 1.휴지통으로 보내기");
                            System.out.print("입력 > ");
                            int ch4 = scanner.nextInt();
                            if(ch4 ==0){break;}
                            else if(ch4 == 1){//휴지통 보내기
                                MailController.getInstance().changeEmailState(2 , Integer.parseInt(rmno));
                                System.out.println("휴지통 보내기 완료");
                            }
                            break;
                        }
                        if(ch3 != Integer.parseInt(rmno) && i==a.size()-1){ // 마지막 인덱스까지 왔는데도 안같으면.
                            System.out.println("잘못된 입력입니다.");
                        }
                    }


                    // 메일 개별 보기 끝
                } else if(ch2==2) { // 안 읽은 메일
                    for (int i = 0; i < a.size(); i++) {
                        String sendeno = a.get(i).get("sendeno");
                        EmployeeDTO employeeDTO = MailController.getInstance().enoSearch(Integer.parseInt(sendeno)); // DTO 받기
                        String sendenoView = employeeDTO.getEname();
                        String rmno = a.get(i).get("mailno");
                        String rmtitle = a.get(i).get("mailtitle");
                        String rmstate = a.get(i).get("mailstate");
                        String rmstateView = "";
                        if (rmstate.equals("0")) {
                            rmstateView = "읽지 않음";
                        } else if (rmstate.equals("1")) {
                            continue;
                        } else if (rmstate.equals("2")) { continue;
                        } else if (rmstate.equals("3")) { continue;
                        }
                        System.out.printf("%-5s | 보낸 사람:%-5s | 제목 : %-50s | %s\n", rmno, sendenoView, rmtitle, rmstateView);
                    }
                    System.out.println("0.뒤로가기 | 1. 메일 보기 ");
                    // 안 읽은 메일 로그의 인트 0 / 읽은 메일 로그의 인트 1
                    System.out.print("입력 > ");
                    int ch4 = scanner.nextInt();
                    if(ch4==0){ // 뒤로가기
                        continue;
                    } else if (ch4==1) { // 개별 메일 보기
                        System.out.println("볼 메일 번호를 입력하세요");
                        System.out.print("입력 > ");
                        int ch3 = scanner.nextInt();
                        int i = 0; // i번째를 구할
                        for (i = 0; i < a.size(); i++) { // 개별 메일 출력
                            String rmstate = a.get(i).get("mailstate");
                            String rmno = a.get(i).get("mailno");
                            if (ch3 == Integer.parseInt(rmno) && rmstate.equals("0")) { // 안읽음 메일만 나오게.
                                String rmtitle = a.get(i).get("mailtitle");
                                String rmcontetnt = a.get(i).get("mailcontetnt");
                                String rmdate = a.get(i).get("maildate");
                                String rmsendeno = a.get(i).get("sendeno");
                                System.out.println();
                                System.out.println("메일 번호 : " + rmno);
                                System.out.println("제목 : " + rmtitle);
                                System.out.println("보낸 사람 : " + MailController.getInstance().enoSearch(Integer.parseInt(rmsendeno)).getEname()); // eno가 나올게 아니라 ename이 나오게
                                System.out.print("받는 사람 : ");
                                for (int i2 = 0; i2 < MailController.getInstance().receiveEnoSearch(ch3).size(); i2++) { // maillog에서 빼온 애들 for문 돌려 출력
                                    System.out.print(MailController.getInstance().receiveEnoSearch(ch3).get(i2));
                                }
                                System.out.println();
                                System.out.println();
                                System.out.println("내용 : " + rmcontetnt);
                                System.out.println();
                                System.out.println("보낸 날짜 : " + rmdate);
                                System.out.println();
                                MailController.getInstance().changeEmailState(1 , Integer.parseInt(rmno)); // 본 메일 상태 바꾸기 (읽음)
                                System.out.println("===============================================================================");
                                System.out.println("0.뒤로 가기 | 1.휴지통으로 보내기");
                                int ch5 = scanner.nextInt();
                                if(ch5 ==0){break;}
                                else if(ch5 == 1){//휴지통 보내기
                                    MailController.getInstance().changeEmailState(2 , Integer.parseInt(rmno));
                                    System.out.println("휴지통 보내기 완료");
                                }
                                break;
                            }
                            if (ch3 != Integer.parseInt(rmno) && i == a.size()-1) { // 마지막 인덱스까지 왔는데도 안같으면.
                                System.out.println("잘못된 입력입니다.");
                            }
                        }
                    }

                    } else if (ch2 ==3) { // 읽은 메일
                    for (int i = 0; i < a.size(); i++) {
                        String sendeno = a.get(i).get("sendeno");
                        EmployeeDTO employeeDTO = MailController.getInstance().enoSearch(Integer.parseInt(sendeno)); // DTO 받기
                        String sendenoView = employeeDTO.getEname();
                        String rmno = a.get(i).get("mailno");
                        String rmtitle = a.get(i).get("mailtitle");
                        String rmstate = a.get(i).get("mailstate");
                        String rmstateView = "";
                        if (rmstate.equals("0")) {
                            continue;
                        } else if (rmstate.equals("1")) {
                            rmstateView = "읽음";
                        } else if (rmstate.equals("2")) { continue;
                        } else if (rmstate.equals("3")) { continue;
                        }
                        System.out.printf("%-5s | 보낸 사람:%-5s | 제목 : %-50s | %s\n", rmno, sendenoView, rmtitle, rmstateView);
                    }

                    System.out.println("0.뒤로가기 | 1. 메일 보기 ");
                    // 안 읽은 메일 로그의 인트 0 / 읽은 메일 로그의 인트 1
                    System.out.print("입력 > ");
                    int ch4 = scanner.nextInt();
                    if(ch4==0){ // 뒤로가기
                        continue;
                    } else if (ch4==1) { // 메일 보기
                        System.out.println("볼 메일 번호를 입력하세요");
                        System.out.print("입력 > ");
                        int ch3 = scanner.nextInt();
                        int i = 0; // i번째를 구할
                        for (i = 0; i < a.size(); i++) { // 개별 메일 출력
                            String rmstate = a.get(i).get("mailstate");
                            String rmno = a.get(i).get("mailno");
                            if (ch3 == Integer.parseInt(rmno) && rmstate.equals("1")) { // 읽음 메일만 나오게.
                                String rmtitle = a.get(i).get("mailtitle");
                                String rmcontetnt = a.get(i).get("mailcontetnt");
                                String rmdate = a.get(i).get("maildate");
                                String rmsendeno = a.get(i).get("sendeno");
                                System.out.println();
                                System.out.println("메일 번호 : " + rmno);
                                System.out.println("제목 : " + rmtitle);
                                System.out.println("보낸 사람 : " + MailController.getInstance().enoSearch(Integer.parseInt(rmsendeno)).getEname()); // eno가 나올게 아니라 ename이 나오게
                                System.out.print("받는 사람 : ");
                                for (int i2 = 0; i2 < MailController.getInstance().receiveEnoSearch(ch3).size(); i2++) { // maillog에서 빼온 애들 for문 돌려 출력
                                    if(i2 == MailController.getInstance().receiveEnoSearch(ch3).size()-1){
                                        System.out.print(MailController.getInstance().receiveEnoSearch(ch3).get(i2));
                                    }
                                    else {
                                    System.out.print(MailController.getInstance().receiveEnoSearch(ch3).get(i2)+", ");}
                                }
                                System.out.println();
                                System.out.println();
                                System.out.println("내용 : " + rmcontetnt);
                                System.out.println();
                                System.out.println("보낸 날짜 : " + rmdate);
                                System.out.println();
                                MailController.getInstance().changeEmailState(1 , Integer.parseInt(rmno)); // 본 메일 상태 바꾸기 (읽음)
                                System.out.println("===============================================================================");
                                System.out.println("0.뒤로 가기 | 1.휴지통으로 보내기");
                                int ch5 = scanner.nextInt();
                                if(ch5 ==0){break;}
                                else if(ch5 == 1){//휴지통 보내기
                                    MailController.getInstance().changeEmailState(2 , Integer.parseInt(rmno));
                                    System.out.println("휴지통 보내기 완료");
                                }
                                break;
                            }
                            if (ch3 != Integer.parseInt(rmno) && i == a.size() - 1) { // 마지막 인덱스까지 왔는데도 안같으면.
                                System.out.println("잘못된 입력입니다.");
                            }
                        }
                    }

                } else if (ch2==4) {// 받은 메일함에서 휴지통으로 보내기. state 2번으로 바꾸기.

                        System.out.println("휴지통 보낼 메일 번호를 입력하세요.");
                        System.out.print("입력 > ");
                        int ch3 = scanner.nextInt();

                        int i = 0; // i번째를 구할
                        for (i = 0; i < a.size(); i++) { // 개별 메일 출력
                            String rmstate = a.get(i).get("mailstate");
                            String rmno = a.get(i).get("mailno");
                            if (ch3 == Integer.parseInt(rmno) && (rmstate.equals("0") || rmstate.equals("1"))) {
                                MailController.getInstance().changeEmailState(2, Integer.parseInt(rmno)); // 영구 삭제 state로 바꾸기.
                                break;
                            }
                            if (ch3 != Integer.parseInt(rmno) && i == a.size() - 1) { // 마지막 인덱스까지 왔는데도 안같으면.
                                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                            }
                        }

                }
            } else if (ch == 3) { // 보낸 메일함
                while (true) {
                    System.out.println("보낸 메일함");
                    // 내가 보낸 메일만 로직 출력 (메일 테이블)
                    TreeMap<MailDTO, ArrayList<String>> a = MailController.getInstance().sendMailsView(EmployController.loginEno.getEno());//Map<MailDTO, ArrayList<String>> 받아옴
                    // 트리맵 거꾸로 출력하게 만들기
                    for (Map.Entry<MailDTO, ArrayList<String>> i : a.entrySet()) {// 전체 보낸 메일 출력
                        int mailno = i.getKey().getMailno();
                        String mailtitle = i.getKey().getMailtitle();
                        System.out.printf("%-5s | 제목 : %-50s \n", mailno, mailtitle);
                    }
                    System.out.println("0.뒤로가기 | 1.보낸 메일 보기 ");
                    System.out.print("입력 > ");
                    int ch2 = scanner.nextInt();
                    scanner.nextLine();
                    if (ch2 == 0) {// 뒤로 가기
                        break;
                    } else if (ch2==1) { //보낸 메일 개별 보기
                        System.out.println("볼 메일 넘버를 입력하세요.");
                        System.out.print("입력 > ");
                        int ch3 = scanner.nextInt();
                        scanner.nextLine();
                        int i2 = 0; // i2번째를 구할 value값 가져오기위해서
                        for (Map.Entry<MailDTO, ArrayList<String>> i : a.entrySet()) { // 개별 메일 출력
                            int mailno = i.getKey().getMailno();
                            if (ch3 == mailno && EmployController.loginEno.getEno() == i.getKey().getEno()){
                                int eno = i .getKey().getEno();
                                String rmtitle = i.getKey().getMailtitle();
                                String rmcontetnt = i.getKey().getMailcontetnt();
                                String rmdate = i.getKey().getMaildate();
                                ArrayList<String> recevArr = i.getValue();
                                System.out.println();
                                System.out.println("메일 번호 : " + mailno);
                                System.out.println("제목 : " + rmtitle);
                                System.out.println("보낸 사람 : " + MailController.getInstance().enoSearch(eno).getEname()); // eno가 나올게 아니라 ename이 나오게
                                System.out.print("받는 사람 : ");
                                for (int i3 = 0; i3 < recevArr.size(); i3++) { // maillog에서 빼온 애들 for문 돌려 출력
                                    if(i3!= recevArr.size()-1){
                                        System.out.print(recevArr.get(i3)+", ");
                                    }else{
                                        System.out.print(recevArr.get(i3));
                                    }

                                }
                                System.out.println();
                                System.out.println();
                                System.out.println("내용 : " + rmcontetnt);
                                System.out.println();
                                System.out.println("보낸 날짜 : " + rmdate);
                                System.out.println();
                                System.out.println("===============================================================================");
                                break;
                            }
                            if (ch3 != mailno && i2 == a.size()-1) { // 마지막 인덱스까지 왔는데도 안같으면.
                                System.out.println("잘못된 입력입니다.");
                            }
                            i2++;
                        }

                    } else{
                        System.out.println();
                        System.out.println("잘못된 입력입니다.");
                        System.out.println();
                    }
                }

            } else if (ch == 4) { // 휴지통
                while (true) {
                    // 내 메일로그의 인트만 2번으로 바뀌기 // 여기서 삭제하면 3번으로 바뀌기
                    System.out.println("메일 휴지통");
                    // 2번인 로그 불러오기
                    ArrayList<Map<String,String>> a = MailController.getInstance().receiveMail(EmployController.loginEno.getEno());// ArrayList로 Map을 받기.
                    Collections.reverse(a); // 어레이리스트 반대로 뒤집기
                    for(int i =0 ; i < a.size() ; i++){
                        String sendeno = a.get(i).get("sendeno");
                        EmployeeDTO employeeDTO = MailController.getInstance().enoSearch(Integer.parseInt(sendeno)); // DTO 받기
                        String sendenoView = employeeDTO.getEname();
                        String rmno = a.get(i).get("mailno");
                        String rmtitle = a.get(i).get("mailtitle");
                        String rmstate = a.get(i).get("mailstate");
                        String rmstateView = "";
                        if(rmstate.equals("0")){ continue;
                        } else if (rmstate.equals("1")) { continue;
                        } else if (rmstate.equals("2")) {
                        } else if (rmstate.equals("3")) { continue;
                        }

                        System.out.printf("%-5s | 보낸 사람:%-5s | 제목 : %-50s | %s\n",rmno,sendenoView,rmtitle,rmstateView);
                    }

                    System.out.println("0. 뒤로가기 | 1. 메일 보기 | 2. 삭제 하기 |");
                    // 안 읽은 메일 로그의 인트 0 / 읽은 메일 로그의 인트 1
                    System.out.print("입력 > ");
                    int ch2 = scanner.nextInt();
                    if (ch2 == 0) {//뒤로 가기
                        break;
                    } else if (ch2 == 1) { // 메일 보기 (휴지통)
                        System.out.println("볼 메일 번호를 입력하세요");
                        System.out.print("입력 > ");
                        int ch3 = scanner.nextInt();
                        int i = 0; // i번째를 구할
                        for (i = 0; i < a.size(); i++) { // 개별 메일 출력
                            String rmstate = a.get(i).get("mailstate");
                            String rmno = a.get(i).get("mailno");
                            if (ch3 == Integer.parseInt(rmno) && rmstate.equals("2")) { // 휴지통의 메일만 나오게.
                                String rmtitle = a.get(i).get("mailtitle");
                                String rmcontetnt = a.get(i).get("mailcontetnt");
                                String rmdate = a.get(i).get("maildate");
                                String rmsendeno = a.get(i).get("sendeno");
                                System.out.println();
                                System.out.println("메일 번호 : " + rmno);
                                System.out.println("제목 : " + rmtitle);
                                System.out.println("보낸 사람 : " + MailController.getInstance().enoSearch(Integer.parseInt(rmsendeno)).getEname()); // eno가 나올게 아니라 ename이 나오게
                                System.out.print("받는 사람 : ");
                                for (int i2 = 0; i2 < MailController.getInstance().receiveEnoSearch(ch3).size(); i2++) { // maillog에서 빼온 애들 for문 돌려 출력
                                    System.out.print(MailController.getInstance().receiveEnoSearch(ch3).get(i2));
                                }
                                System.out.println();
                                System.out.println();
                                System.out.println("내용 : " + rmcontetnt);
                                System.out.println();
                                System.out.println("보낸 날짜 : " + rmdate);
                                System.out.println();
                                MailController.getInstance().changeEmailState(1 , Integer.parseInt(rmno)); // 본 메일 상태 바꾸기 (읽음)
                                System.out.println("===============================================================================");
                                System.out.println("0.뒤로 가기 | 1.영구 삭제하기");
                                int ch4 = scanner.nextInt();
                                if(ch4 ==0){break;}
                                else if(ch4 == 1){//삭제하기
                                    MailController.getInstance().changeEmailState(3 , Integer.parseInt(rmno));
                                }else{
                                    System.out.println("잘못된 입력입니다.");
                                }
                                break;
                            }
                            if (ch3 != Integer.parseInt(rmno) && i == a.size() - 1) { // 마지막 인덱스까지 왔는데도 안같으면.
                                System.out.println("잘못된 입력입니다.");
                            }
                        }

                    } else if (ch2 == 2) { // 삭제 하기 (영구 안보이게)
                        System.out.println("영구 삭제할 메일 번호를 입력하세요");
                        System.out.print("입력 > ");
                        int ch3 = scanner.nextInt();
                        int i = 0; // i번째를 구할
                        for (i = 0; i < a.size(); i++) { // 개별 메일 출력
                            String rmstate = a.get(i).get("mailstate");
                            String rmno = a.get(i).get("mailno");
                            if (ch3 == Integer.parseInt(rmno) && rmstate.equals("2")){
                                MailController.getInstance().changeEmailState(3 , Integer.parseInt(rmno)); // 영구 삭제 state로 바꾸기.
                                break;
                            }

                            if (ch3 != Integer.parseInt(rmno) && i == a.size() - 1) { // 마지막 인덱스까지 왔는데도 안같으면.
                                System.out.println("잘못된 입력입니다.");
                            }
                        }
                    } else {
                        System.out.println("잘못된 입력입니다.");
                    }
                }


            } else {
                System.out.println();
                System.out.println("잘못된 입력입니다.");
                System.out.println();
            }
        }
    }


}
