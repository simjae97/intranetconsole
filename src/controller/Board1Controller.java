package controller;

import model.dao.Board1Dao;

import java.util.Map;

public class Board1Controller { // 개별 글 출력 컨트롤러

    // 싱글톤 만들기
    private Board1Controller(){};
    private static Board1Controller board1Controller = new Board1Controller();
    public static Board1Controller getInstance(){
        return board1Controller;
    }

    //개별글 출력 컨트롤러 // Map으로 받아서 출력을 map.get으로 하기.
    public Map<String, String> board1(int boardno){
        Map<String,String> result = Board1Dao.getInstance().board1(boardno);
        return result;
    }

    public boolean board1Checking(int boaradno){
        return Board1Dao.getInstance().board1Checking(boaradno, EmployController.loginEno);
    }
}
