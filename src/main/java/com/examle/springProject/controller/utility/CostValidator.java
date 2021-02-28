package com.examle.springProject.controller.utility;

import com.examle.springProject.exceptions.CostValidateException;


public class CostValidator {
    public static final Integer MAX_COST=1000;
    public static boolean isCostsCorrect(Integer costs){
        return costs<=MAX_COST && costs>0;
    }
    public static void  validateCost(Integer costs){
        if (!isCostsCorrect(costs)) {
            throw new CostValidateException(
                    " Pin is incorrect ");
        }
    }
}
