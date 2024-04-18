package controller;

import model.dao.ReportDAO;
import model.dto.EmployeeDTO;
import model.dto.ReportDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class ReportController {
    private ReportController(){}
    private static ReportController reportController = new ReportController();
    public static ReportController getInstance(){return reportController;}

//    public ArrayList<ReportDTO> allReport(){
//        return reportDAO.allReport();
//    }

    public TreeMap<ReportDTO,Boolean> allReport2(){
        return ReportDAO.getInstance().allReport2();
    }


//    public HashMap<ReportDTO,Boolean> allReport2(){
//        return reportDAO.allReport2();
//    }


    public boolean reportWrite(ReportDTO dto, ArrayList<Integer> array){return ReportDAO.getInstance().reportWrite(dto,array);}
    public EmployeeDTO enoSearch(int num){
        return ReportDAO.getInstance().enoSearch(num);
    }
    public TreeMap<ReportDTO,Boolean> goReport(){
        return ReportDAO.getInstance().goReport();
    }

    public ReportDTO specificreport(int num){
        return ReportDAO.getInstance().specificreport(num);
    }

    public boolean accept(int num){
        return ReportDAO.getInstance().accept(num);
    }

    public ArrayList<EmployeeDTO> findSuperior(){
        return ReportDAO.getInstance().findSuperior();
    }

    public boolean reportDelete(int num){return ReportDAO.getInstance().reportDelete(num);}
    public TreeMap<Integer, Boolean > findSuperiors(int num){
        return ReportDAO.getInstance().findSuperiors(num);
    }
    public String findType(int cno){return ReportDAO.getInstance().findType(cno);}

    public ArrayList<HashMap<Integer,String >> findCategories(){return ReportDAO.getInstance().findCategories();}
}
