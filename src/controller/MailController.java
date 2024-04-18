package controller;


import com.sun.source.tree.Tree;
import model.dao.MailDao;
import model.dao.SuperDao;
import model.dto.EmployeeDTO;
import model.dto.MailDTO;
import model.dto.PartDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MailController {
    // 싱글톤
    private static MailController mailController = new MailController();
    private MailController(){};
    public static MailController getInstance(){
        return mailController;
    }

    public ArrayList<PartDTO> partView(){ // 파트 가져오기
        return MailDao.getInstance().partView();
    }

    public EmployeeDTO enoSearch(int eno){ //슈퍼다오에 있는 enoSearch 쓰기위해
        return MailDao.getInstance().enoSearch(eno);
    }

    public ArrayList<String> receiveEnoSearch(int mailno) { // 메일 받은 사람들 배열보이기
        return MailDao.getInstance().receiveEnoSearch(mailno);
    }
    public ArrayList<String> sendEnoSearch(int mailno) { // 메일 보낸 사람들 배열보이기
        return MailDao.getInstance().sendEnoSearch(mailno);
    }

    public boolean emailCheck(String email){
        if(MailDao.getInstance().emailSearch(email)==-1){ //eno가 있으면 true
            return true;
        }
        return false;
    }
    public int[] sendpartMail(int partno){ // 부서별 eno 검색
        return MailDao.getInstance().sendpartMail(partno);
    }

    public boolean sendpartMail(ArrayList<Map<String,String>> sendemailarr){ // 부서별 실제 보내기
        return MailDao.getInstance().sendpartMail(sendemailarr);
    }

    public boolean sendMail(ArrayList<Map<String,String>> sendemailarr){ // 직접 입력
        return MailDao.getInstance().sendMail(sendemailarr);
    }

    public TreeMap<MailDTO, ArrayList<String>> sendMailsView(int loginEno){
        return MailDao.getInstance().sendMailsView(loginEno);
    }

    public ArrayList<Map<String,String>> receiveMail(int loginEno){
        return MailDao.getInstance().receiveMail(loginEno);
    }

    public boolean changeEmailState(int state, int mailno){
        return MailDao.getInstance().changeEmailState(state, mailno);
    }

}
