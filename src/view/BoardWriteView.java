package view;

import controller.BoardWriteController;
import controller.EmployController;
import model.dto.BoardDTO;

import java.util.Scanner;

public class BoardWriteView {

    Scanner scanner = new MainView().scanner;
    public void BoardWrite(){
        while (true) {
            try {
                // 현제 로그인된 아이디 관리자 비교
                System.out.println((EmployController.loginEno.getEno() == 5 ? "0.공지 " : "") + "1.전체게시판 2.부서별게시판 3.직급별게시판");
                System.out.print("입력> ");
                int bcno = scanner.nextInt() + 1; scanner.nextLine();
                if (EmployController.loginEno.getEno() < 5 && bcno == 1) { // 관리자가 아니면 공지 X
                    System.out.println("권한이 없습니다.");
                    break;
                }
                int bcno2 = 0;
                if (bcno == 3) {
                    bcno2 = EmployController.loginEno.getPartno();  // 부서게시판일땐 현재 로그인 부서번호 가져오기
                } else if (bcno == 4) {
                    bcno2 = EmployController.loginEno.getGradeno(); // 직급게시판일땐 직급번호 가져오기
                } else if (bcno > 4) {
                    System.out.println("없는 번호입니다.");
                    break;
                }
                System.out.print("제목 : ");
                String boardTitle = scanner.nextLine();
                System.out.print("내용 : ");
                String boardContent = scanner.nextLine();

                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setEno(EmployController.loginEno.getEno());
                boardDTO.setBoardtitle(boardTitle);
                boardDTO.setBoardcontent(boardContent);
                boardDTO.setBcno(bcno);
                boardDTO.setBcno2(bcno2);
                if (BoardWriteController.getInstance().boardWrite(boardDTO)) {
                    System.out.println("게시물 등록 성공");
                    return;
                } else {
                    System.out.println("게시물 등록 실패");
                    return;
                }
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
