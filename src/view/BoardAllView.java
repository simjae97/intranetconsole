package view;


import controller.BoadAllViewController;
import controller.Board1Controller;
import model.dto.BoardDTO;


import java.util.ArrayList;
import java.util.Scanner;

public class BoardAllView {

    // Application에서 스케너 받아오기
    Scanner scanner = new MainView().scanner;
    private static final int PAGE_NATION = 15;  //게시판 1페이당 공지 제외 최대 게시글 수

    public void boardAllView(){
        while (true){
            try {
                System.out.println("0.뒤로가기 1.전체게시판 2.부서게시판 3.직급게시판");
                System.out.print("입력> "); int ch = scanner.nextInt();
                ArrayList<BoardDTO> boardDTOArrayList = new ArrayList<>();  // [1]글 출력을 위한 배열 생성
                ArrayList<BoardDTO> notiBardDTOArrayList = new ArrayList<>();  // [1]글 출력을 위한 배열 생성
                if(ch == 0){
                  return;
                } else if(ch > 3){ // [2] 입력받은 숫자 확인
                    System.out.println("숫자를 다시 입력해주세요.");
                } else {    // [3] 입력받은 숫자를 매개 변수로 BoadAllViewController View 메소드 실행
                    int nowpage = 1;    // 현제 페이지
                    while (true){
                        // 1. 공지글 먼저 상위로 출력 - 공지는 항상 유지
                        notiBardDTOArrayList = BoadAllViewController.getInstance().boardAllView(0);
                        System.out.println("========================================== 게시판 ==========================================");
                        System.out.printf("%-5s \t %-25s \t %-10s \t %-10s \n","번호","제목\t","작성자","작성일");
                        for(int i=0; i<notiBardDTOArrayList.size(); i++){
                            System.out.printf("%-5s \t %-25s \t %-10s \t %-10s \n", "공지"+(i+1), notiBardDTOArrayList.get(i).getBoardtitle(), BoadAllViewController.getInstance().enoSearch(notiBardDTOArrayList.get(i).getEno()).getEname(), notiBardDTOArrayList.get(i).getBoarddate().split(" ")[0]);
                        }
                        // 공지글 출력 종료

                        // 2. 카테고리별 게시판 출력 15개씩
                        boardDTOArrayList = BoadAllViewController.getInstance().boardAllView(ch);
                        if(boardDTOArrayList.size() == 0){
                            System.out.println("표시할 게시글이 없습니다.");
                        } else {
                            for(int i=0+(nowpage-1)*PAGE_NATION; i<PAGE_NATION*nowpage; i++){
                                if(i >= boardDTOArrayList.size()){break;}
                                System.out.printf("%-5d \t %-25s \t %-10s \t %-10s \n", i + 1, boardDTOArrayList.get(i).getBoardtitle(), BoadAllViewController.getInstance().enoSearch(boardDTOArrayList.get(i).getEno()).getEname(), boardDTOArrayList.get(i).getBoarddate().split(" ")[0]);
                            }
                        }
                        System.out.println("=========================================================================================");
                        // 카테고리별 게시판 출력 종료
                        System.out.println("0.뒤로가기1.개별글보기 2.글쓰기 3.이전페이지 4.다음페이지");
                        System.out.print("입력> ");
                        int ch4 = scanner.nextInt();
                        if(ch4 == 0){
                            break;
                        } else if(ch4 == 1){
                            System.out.println("0.공지 1.일반게시물");
                            System.out.print("입력> ");
                            int notino = scanner.nextInt();
                            int boardno;
                            System.out.print("보고싶은 게시물을 선택하시오.> ");
                            int boardIndex = scanner.nextInt() - 1;
                            if(notino == 0){    // 공지 게시판 보기
                                boardno = notiBardDTOArrayList.get(boardIndex).getBoardno();
                            } else if (notino == 1) {   // 일반 게시판볼때
                                boardno = boardDTOArrayList.get(boardIndex).getBoardno();
                            } else {
                                System.out.println("없는 번호입니다.");
                                break;
                            }
                            new Board1View().board1(boardno);
                        } else if(ch4 == 2){    // 글쓰기
                            new BoardWriteView().BoardWrite();
                        } else if(ch4 == 3){    // 이전 페이지
                            if(nowpage == 1 ){  // 현재 페이지가 1이면 이전페이지 X
                                System.out.println("이전페이지가 없습니다.");
                            } else {
                                --nowpage;  // nowpage(현재 페이지 --)
                            }
                        } else if(ch4 == 4){    // 다음 페이지
                            // 게시물 총 수를 삼항연산자를 2번 사용해서 최대페이지 계산 후 현제페이지랑 비교
                            if(boardDTOArrayList.size()%15 == 0 ? boardDTOArrayList.size()/15 == nowpage : boardDTOArrayList.size()/15+1 == nowpage){
                                System.out.println("다음페이지가 없습니다."); // 현재 페이지랑 최대페이지 같으면
                            } else {
                                ++nowpage;  // nowpage(현재 페이지 ++)
                            }
                        } else {
                            System.out.println("숫자를 다시 입력해주세요.");
                        }
                    }
                }
            } catch (Exception e){ // 입력 유효성 검사
                scanner.nextLine();
                System.out.println(e);
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
