package controller;

import model.dao.ReplyDao;
import model.dto.ReplyDTO;

import java.util.ArrayList;
import java.util.Map;

public class ReplyController {

    //싱글톤
    private ReplyController(){};
    private static ReplyController replyController = new ReplyController();
    public static ReplyController getInstance(){return replyController;}

    //입력받은 boardno 를 댓글 다오로 보내서 처리결과 받아오기
    public ArrayList<Map> replyview(int boardno){
        ArrayList<Map> result = ReplyDao.getInstance().replyview(boardno);
        return result;
    }

    public boolean replyWrite(ReplyDTO replyDTO){
        return ReplyDao.getInstance().replyWrite(replyDTO);
    }

    public boolean replyDelete(int replyno){
        return  ReplyDao.getInstance().replyDelete(replyno);
    }

    public boolean replyUpdate(ReplyDTO replyDTO){
        return ReplyDao.getInstance().replyUpdate(replyDTO);
    }
}
