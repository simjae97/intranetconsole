package controller;

import model.dao.BoardUpdateDao;
import model.dao.SuperDao;
import model.dto.BoardDTO;

public class BoardUpdateController {

    private BoardUpdateController(){};
    private static BoardUpdateController boardUpdateController = new BoardUpdateController();
    public static BoardUpdateController getInstance(){
        return boardUpdateController;
    }

    public boolean boardnoSearch(int boardno){
        return BoardUpdateDao.getInstance().boardnoSearch(boardno)==EmployController.loginEno.getEno();
        //로그인 놈과 보드넘버의 eno를 비교해서 같은 사람이면, true 아니면 false 리턴
    }

    public boolean boardUpdate(BoardDTO boardDTO){ // 글 수정 실행 함수 DTO로 받기
        return BoardUpdateDao.getInstance().boardUpdate(boardDTO);
    } // 글 수정 끝

    public boolean boardDelete(int boardno){ // 글 삭제 실행 함수
        return BoardUpdateDao.getInstance().boardDelete(boardno);
    } // 글 삭제 끝
}
