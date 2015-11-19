package com.geek.geekmall.model;

import com.geek.geekmall.bean.Income;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class IncomeData {
    private int status;
    private String errorMsg;
    private List<Income> monthlyIncome;
    private Income totalIncome;

    public List<Income> getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(List<Income> monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Income getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Income totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
