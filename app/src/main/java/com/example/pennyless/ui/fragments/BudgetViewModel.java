package com.example.pennyless.ui.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pennyless.entities.Income;
import com.example.pennyless.entities.Database;

import java.util.ArrayList;
import java.util.List;

public class BudgetViewModel extends AndroidViewModel {

    private MutableLiveData<List<Income>> budgetData;
    private List<Income> budgetList;
    private Database databaseHelper;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        budgetData = new MutableLiveData<>();
        init();
    }

    MutableLiveData<List<Income>> getBudgetMutableLiveData() {
        return budgetData;
    }

    private void init(){
        fetchData();
        budgetData.setValue(budgetList);
    }

    private void fetchData(){
        databaseHelper = new Database(getApplication().getApplicationContext());

   /*     Budget new1 = new Budget(1,500, Constants.BUDGET_CATEGORY.SALARY.getName(), "");
        Budget new2 = new Budget(1,300, Constants.BUDGET_CATEGORY.INHERITANCE.getName(), "");
        databaseHelper.saveBudget(new1);
        databaseHelper.saveBudget(new2);*/
        budgetList = new ArrayList<>();
        budgetList = databaseHelper.getIncomeList();
    }
}
