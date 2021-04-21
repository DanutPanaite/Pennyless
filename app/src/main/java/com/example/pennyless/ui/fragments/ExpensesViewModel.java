package com.example.pennyless.ui.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pennyless.entities.Database;
import com.example.pennyless.entities.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpensesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Expense>> expensesData;
    private List<Expense> expenses;
    private Database databaseHelper;

    public ExpensesViewModel(@NonNull Application application) {
        super(application);
        expensesData = new MutableLiveData<>();
        init();
    }

    MutableLiveData<List<Expense>> getExpensesMutableLiveData() {
        return expensesData;
    }

    private void init(){
        fetchData();
        expensesData.setValue(expenses);
    }

    private void fetchData(){
        databaseHelper = new Database(getApplication().getApplicationContext());


        expenses = new ArrayList<>();
        expenses = databaseHelper.getExpenseList();
    }
}