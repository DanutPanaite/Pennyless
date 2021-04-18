package com.example.pennyless.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pennyless.entities.Income;
import com.example.pennyless.entities.Database;

import java.util.ArrayList;
import java.util.List;

public class IncomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Income>> incomeData;
    private List<Income> incomeList;
    private Database databaseHelper;

    public IncomeViewModel(@NonNull Application application) {
        super(application);
        incomeData = new MutableLiveData<>();
        init();
    }

    MutableLiveData<List<Income>> getBudgetMutableLiveData() {
        return incomeData;
    }

    private void init(){
        fetchData();
        incomeData.setValue(incomeList);
    }

    private void fetchData(){
        databaseHelper = new Database(getApplication().getApplicationContext());


        incomeList = new ArrayList<>();
        incomeList = databaseHelper.getIncomeList();
    }
}
