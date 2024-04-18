package view;

import controller.EmployController;
import model.dto.EmployeeDTO;

import java.util.Scanner;

public class MainView {

    public static Scanner scanner = new Scanner(System.in);
    public void run(){
        while (true) {
            System.out.println("1.회원가입  2.로그인 ");
            System.out.print("입력 > ");
            int ch = scanner.nextInt();
            scanner.nextLine();
            if (ch == 1) {
                new EmployeeView().signUp();
            } else if (ch == 2) {

                boolean run = new EmployeeView().logIn();

                while (run) {  // 로그인 되면
                    System.out.println("1.보고서  2.게시판 3.로그아웃 4.회원탈퇴"+" 5.메일"+(EmployController.loginEno.getGradeno()==5?" 6.관리자권한 실행":" 6.회원정보 수정" ));
                    System.out.print("입력 > ");
                    int ch2 = scanner.nextInt();
                    scanner.nextLine();
                    if (ch2 == 1){
                        while (true) {
                            System.out.println("0.뒤로가기 1.받은 보고서 2.보낸 보고서 3.보고서 작성");
                            System.out.print("입력 > ");
                            int ch3 = scanner.nextInt();
                            if (ch3 == 1) {
                                new ReportView().allReport();
                            } else if (ch3 == 2) {
                                new ReportView().goReport();
                            } else if (ch3 == 0) {
                                break;
                            }

                            else if(ch3 == 3){
                                if(new ReportView().writeReport()){
                                    System.out.println("작성완료");
                                }
                                else {
                                    System.out.println("작성실패");
                                }
                            }
                        }
                    } else if(ch2 == 2){
                        new BoardAllView().boardAllView();
                    } else if(ch2 == 3){
                        run = false;
                        EmployController.getInstance().logOut();
                    } else if(ch2 == 4){
                        new EmployeeView().exit();
                        EmployController.getInstance().logOut();
                        run = false;

                    } else if (ch2==5) {
                        new MailView().run();
                    }
                    else if(ch2==6 && EmployController.loginEno.getGradeno()==5&& EmployController.loginEno.getPartno()==1){

                        while (true) {
                            new EmployeeView().employeeInfo();
                            System.out.println("0.뒤로가기 1.직책변환 2.부서변환 3.해고");
                            System.out.print("입력 > ");
                            int manageemployee = scanner.nextInt();
                            scanner.nextLine();

                            if (manageemployee == 1) {
                                new EmployeeView().changegradeno();
                            } else if (manageemployee == 2) {
                                new EmployeeView().changepartno();
                            } else if (manageemployee == 3) {
                                new EmployeeView().fire();
                            } else if (manageemployee == 0) {
                                break;
                            }else{
                                System.out.println("잘못된 입력번호 입니다.");
                            }
                        }

                    }else if (ch2==6&&EmployController.loginEno.getGradeno()==5) { // 관리자 권한 부분
                        new EmployeeView().employeeInfo();
                    }else if(ch2==6){
                        System.out.println("회원 정보를 수정합니다.");
                        System.out.println("1. 비밀번호변경, 2. 전화번호변경, 3. 이메일 변경");
                        System.out.print("입력 > ");
                        int change = scanner.nextInt();
                        scanner.nextLine();
                        if(change==1) {
                            new EmployeeView().updateInfopw();
                        }else if(change==2){
                            new EmployeeView().updateInfophone();
                        }else if(change==3){
                            new EmployeeView().updateInfoEmail();
                        }

                    }
                }
            } else{
                System.out.println("없는 기능 번호 입니다.");
            }
        }
    }
}
