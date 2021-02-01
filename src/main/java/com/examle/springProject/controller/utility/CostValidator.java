package com.examle.springProject.controller.utility;

public class CostValidator {
    public static final Integer MAX_COST=1000;
    public static boolean validateCost(Integer costs){
        if(costs==null){
            return false;
        }
        if(costs>MAX_COST){
            return false;
        }
        return true;
    }
}
