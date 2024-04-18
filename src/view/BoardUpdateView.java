package view;

import controller.BoardUpdateController;
import model.dto.BoardDTO;


import java.util.Scanner;

public class BoardUpdateView {

    public void boardUpdate(int boardno){ // 글 수정 실행 함수 boardno로 받음
       //다오에서 일단 로그인한 eno가 글쓴 사람과 같은지 확인.
        if(!(BoardUpdateController.getInstance().boardnoSearch(boardno))){//다른 사람일때
            System.out.println("글 수정 권한이 없습니다.");
            return;
        }
        System.out.println("글 수정할 내용을 입력하세요.");
        Scanner scanner = new MainView().scanner;
        System.out.print("수정할 글 제목 : ");
        String title = scanner.nextLine();
        System.out.print("수정할 글 내용 : ");
        String content = scanner.nextLine(); //수정 내용 입력 받기
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardtitle(title); // 수정 제목
        boardDTO.setBoardcontent(content); // 수정 내용
        boardDTO.setBoardno(boardno); //넘버
        if(BoardUpdateController.getInstance().boardUpdate(boardDTO)) {//DTO 넘기고 boolean으로 받기
            System.out.println("업데이트 완료");;
        }else{
            System.out.println("공백은 입력할 수 없습니다.");// 공백아니면 오류 뜰 리가 없다
            boardUpdate(boardno); // 함수 다시 실행.
        }
    } // 글 수정 끝

    public void boardDelete(int boardno){ // 글 삭제 실행 함수
        //다오에서 일단 로그인한 eno가 글쓴 사람과 같은지 확인.
        if(!(BoardUpdateController.getInstance().boardnoSearch(boardno))){//다른 사람일때
            System.out.println("글 삭제 권한이 없습니다.");
            return;
        }
        //해당 보드 글 삭제.
        if(BoardUpdateController.getInstance().boardDelete(boardno)){
            System.out.println("글 삭제 완료");
        }else{
            System.out.println("<관리자 문의> 글 삭제 불가능");
        }
    } // 글 삭제 끝
}
