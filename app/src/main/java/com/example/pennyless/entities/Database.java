package com.example.pennyless.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pennyless.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DB_DIR = "/data/data/android.example/databases/";
    private static String DB_NAME = "pennyless.sqlite";
    private static String DB_PATH = DB_DIR + DB_NAME;
    private final Context myContext;

    public Database(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        myContext = context;
        // Get the path of the database that is based on the context.
        DB_PATH = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    try {
        String createExpensesTable = "CREATE TABLE IF NOT EXISTS expenses(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, sum DOUBLE NOT NULL, add_date DATETIME NOT NULL, category TEXT NOT NULL, details TEXT)";
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL)";
        String createIncomesTable = "CREATE TABLE IF NOT EXISTS budgets(id INTEGER PRIMARY KEY AUTOINCREMENT, sum DOUBLE NOT NULL, category TEXT NOT NULL, details TEXT)";

        db.execSQL(createExpensesTable);
        db.execSQL(createUsersTable);
        db.execSQL(createIncomesTable);
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void saveIncome(Income income) {
        try {
            SQLiteDatabase db  = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("sum", income.getSum());
            values.put("category",income.getCategory());
            values.put("details", income.getDetails());
            db.insert("budgets", null, values);
        } catch(Exception ex){
            Log.e("saveBudget","Error saving the new budget.", ex);
        }
    }

    public void saveExpense(Expense expense) {
        try {
            SQLiteDatabase db  = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", expense.getName());
            values.put("sum", expense.getSum());
            values.put("add_date", Constants.getStringFromDate(expense.getAddDate()));
            values.put("category", expense.getCategory());
            values.put("details", expense.getDetails());
            db.insert("expenses", null, values);
        } catch(Exception ex){
            Log.e("saveExpense","Error saving the new Expense.", ex);
        }
    }

    public List<Expense> getExpensesByCategory(String category){
        List<Expense> expenses = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM expenses WHERE category = '" + category + "' ORDER BY add_date DESC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();
                    expense.setId((long) cursor.getInt(0));
                    expense.setName(cursor.getString(1));
                    expense.setSum(cursor.getDouble(2));
                    expense.setAddDate(Constants.getDateFromString(cursor.getString(3)));
                    expense.setCategory(cursor.getString(4));
                    expense.setDetails(cursor.getString(5));
                    expenses.add(expense);
                } while (cursor.moveToNext());
            }
        }catch(Exception ex){
            Log.e("getExpensesByCategory","Error getting the Expense list.", ex);
        }
        return expenses;
    }

    public List<Expense> getExpenseList(){
        List<Expense> expenses = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM expenses ORDER BY add_date DESC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Expense expense = new Expense();
                    expense.setId((long) cursor.getInt(0));
                    expense.setName(cursor.getString(1));
                    expense.setSum(cursor.getDouble(2));
                    expense.setAddDate(Constants.getDateFromString(cursor.getString(3)));
                    expense.setCategory(cursor.getString(4));
                    expense.setDetails(cursor.getString(5));
                    expenses.add(expense);
                } while (cursor.moveToNext());
            }
        }catch(Exception ex){
            Log.e("getExpenses","Error getting the Expense list.", ex);
        }
        return expenses;
    }

    public List<Income> getIncomeList(){
        List<Income> incomeList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM budgets";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Income income = new Income();
                    income.setId((long) cursor.getInt(0));
                    income.setSum(cursor.getDouble(1));
                    income.setCategory(cursor.getString(2));
                    income.setDetails(cursor.getString(3));
                    incomeList.add(income);
                } while (cursor.moveToNext());
            }
        }catch(Exception ex){
            Log.e("getIncomeList","Error getting the income list.", ex);
        }
        return incomeList;
    }

    public void deleteExpense(Long id) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("expenses", "id= ?", new String[]{String.valueOf(id)});
        }catch(Exception ex){
            Log.e("deleteExpense","Error deleting the expense with id:" + id, ex);
        }
    }

    public void deleteIncome(Long id) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete("budgets", "id= ?", new String[]{String.valueOf(id)});
        }catch(Exception ex){
            Log.e("deleteBudget","Error deleting the budget with id:" + id, ex);
        }
    }
    public void saveUser(User user) {
        try {
            SQLiteDatabase db  = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", user.getId());
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            db.insert("users", null, values);
        } catch(Exception ex){
            Log.e("saveUser","Error saving the new user.", ex);
        }
    }

    public User getUser(String username){
        User user = new User();
        try {
            String selectQuery = "SELECT * FROM users WHERE username = '" + username + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    user.setId((long) cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }catch(Exception ex){
            Log.e("getUser","Error getting the user.", ex);
        }
        return user;
    }

    public double getTotalSum() {
        double sum = 0;
        List<Income> incomeList = getIncomeList();
        List<Expense> expenseList = getExpenseList();
        for (Income income:incomeList) {
            sum = sum + income.getSum();
        }
        for(Expense expense:expenseList){
            sum = sum - expense.getSum();
        }

        return sum;
    }
}
