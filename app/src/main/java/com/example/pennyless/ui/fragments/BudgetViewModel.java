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

    public MutableLiveData<List<Income>> getBudgetMutableLiveData() {
        return budgetData;
    }

    private void init(){
        fetchData();
        budgetData.setValue(budgetList);
    }

    private void fetchData(){
        databaseHelper = new Database(getApplication().getApplicationContext());

        budgetList = new ArrayList<>();
        budgetList = databaseHelper.getIncomeList();
    }
}
