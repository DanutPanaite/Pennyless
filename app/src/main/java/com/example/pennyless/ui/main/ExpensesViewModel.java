package com.example.pennyless.ui.main;

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
        //Expense new1 = new Expense(1l, "test1", 23.0, new Date(System.currentTimeMillis()),Constants.EXPENSE_CATEGORY.CLOTHING.getName(), "");
        //Expense new2 = new Expense(1l, "test2", 45.0, new Date(System.currentTimeMillis()),Constants.EXPENSE_CATEGORY.TRANSPORT.getName(), "");
        //Expense new3 = new Expense(1l, "test3", 35.0, new Date(System.currentTimeMillis()),Constants.EXPENSE_CATEGORY.GROCERIES.getName(), "");
        //Expense new4 = new Expense(1l, "test4", 36.0, new Date(System.currentTimeMillis()),Constants.EXPENSE_CATEGORY.UTILITIES.getName(), "");
        //Expense new5 = new Expense(1l, "test5", 36.0, new Date(System.currentTimeMillis()),Constants.EXPENSE_CATEGORY.OTHERS.getName(), "");

        //databaseHelper.saveExpense(new1);
        //databaseHelper.saveExpense(new2);
        //databaseHelper.saveExpense(new3);
        //databaseHelper.saveExpense(new4);
        //databaseHelper.saveExpense(new5);

        expenses = new ArrayList<>();
        expenses = databaseHelper.getExpenseList();
    }
}