package view;

import controller.Board1Controller;
import controller.EmployController;
import controller.ReplyController;
import model.dto.ReplyDTO;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Board1View { // 개별 글 뷰

    public void board1(int boardno){ // 직접 보드넘버를 받아온다.
            System.out.println("========================== 개별 글 출력 실행 ==========================");
            // 글 하나 하나 출력 되는 공간 // Controller로 인트값 넘기기.
            Map<String, String> map = Board1Controller.getInstance().board1(boardno);
            System.out.println("번호 : "+map.get("boardno"));
            System.out.println("제목 : "+map.get("boardtitle"));
            System.out.println("작성자 : "+map.get("ename"));
            System.out.println();
            System.out.println("내용 : "+map.get("boardcontent"));
            System.out.println();
            System.out.println("작성일 : "+map.get("boarddate"));
            System.out.println("댓글 ================================================================");
            ArrayList<Map> map2 = ReplyController.getInstance().replyview(boardno); // 리플 담은 배열 출력
            for(int i =0 ; i<map2.size(); i++){ // 댓글 출력 포문
                    System.out.print(i+1 +"번째 댓글 : ");
                    System.out.print(map2.get(i).get("partname")+"팀/");
                    System.out.printf("%3s %35s\n", map2.get(i).get("ename"), map2.get(i).get("replydate"));
                    System.out.println(map2.get(i).get("replycontent"));
                    System.out.println("--------------------------------------------------------------------");
            }
            System.out.println("0.뒤로 가기 | 1.댓글 입력 | 2.댓글 수정 | 3.댓글 삭제 | 4.글 수정 | 5.글 삭제");

            Scanner scanner = new MainView().scanner;
            int ch = scanner.nextInt();
            scanner.nextLine();
            if(ch==0){return;} // 뒤로 가기
            else if (ch==1) { // 댓글 등록
                    System.out.print("댓글 입력 : ");

                    String newreplycontent = scanner.nextLine(); // 적을 댓글 받기
                    ReplyDTO replyDTO = new ReplyDTO();
                    replyDTO.setEno(EmployController.loginEno.getEno()); // 로그인 중인 Eno DTO에 넣기
                    replyDTO.setReplycontent(newreplycontent);  // newreplycontent를 DTO에 넣기
                    replyDTO.setBoardno(boardno); // 받아온 보드넘버도 같이 넣기.
                    if(ReplyController.getInstance().replyWrite(replyDTO)){// DTO 보내기 boolean값으로 리턴 받기
                            System.out.println("댓글 등록 완료.");
                            board1(boardno);
                    }else{
                            System.out.println("댓글 등록이 되지 않았습니다.");
                    }
            } else if (ch==2) { // 댓글 수정 실행
                    System.out.print("수정할 댓글 번호 입력 : ");
                    int ch2 = scanner.nextInt()-1;
                    scanner.nextLine();
                    if(EmployController.loginEno.getEno() != Integer.parseInt((String) map2.get(ch2).get("eno"))){// map2의 ch2번째 eno를 받아와서 검증
                            System.out.println("수정 권한이 없습니다.");
                            board1(boardno);
                    }else{
                            System.out.print("수정 할 댓글 내용 :");
                            String newreplycontent = scanner.nextLine();
                            ReplyDTO replyDTO = new ReplyDTO();
                            replyDTO.setReplycontent(newreplycontent);
                            replyDTO.setReplyno(Integer.parseInt((String) map2.get(ch2).get("replyno")));
                            boolean i = ReplyController.getInstance().replyUpdate(replyDTO);
                            if(i){
                                    System.out.println("댓글 수정 완료.");
                            }else {
                                    System.out.println("댓글 수정 실패");
                            }
                            board1(boardno);
                    }
                    board1(boardno); // 다시 출력
            } else if (ch==3) { // 댓글 삭제 실행
                    System.out.print("삭제할 댓글 번호 입력 : ");
                    int ch2 = scanner.nextInt()-1;
                    if(EmployController.loginEno.getEno() != Integer.parseInt((String) map2.get(ch2).get("eno"))){// map2의 ch2번째 eno를 받아와서 검증
                            System.out.println("삭제 권한이 없습니다.");
                            board1(boardno);
                    }else{
                            boolean i = ReplyController.getInstance().replyDelete(Integer.parseInt((String) map2.get(ch2).get("replyno")));
                            if(i){
                                    System.out.println("댓글 삭제 완료");
                            }else{
                                    System.out.println("댓글 삭제 실패");
                            }
                            board1(boardno); // 다시 출력
                    }
            } else if (ch==4) { // 글 수정 실행
                    new BoardUpdateView().boardUpdate(boardno);
                    board1(boardno); //함수 다시 실행되서 수정된 글 보이게.
            } else if (ch==5) { // 글 삭제 실행
                    new BoardUpdateView().boardDelete(boardno);
                    return; // 함수 나가기.
            }


    }
}
