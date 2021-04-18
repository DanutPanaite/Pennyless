package com.example.pennyless.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Constants {

    public enum EXPENSE_CATEGORY {

        GROCERIES( 1, "Groceries"),
        TRANSPORT(2, "Transport"),
        CLOTHING(3, "Clothing"),
        UTILITIES(4, "Utilities"),
        OTHERS(5, "Others");

        private int type;
        private String name;
        private static final Map<String, EXPENSE_CATEGORY> labelMap = new HashMap<String, EXPENSE_CATEGORY>();

        static {
            for (EXPENSE_CATEGORY a : values()) {
                labelMap.put(a.name, a);
            }
        }

        private EXPENSE_CATEGORY(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static EXPENSE_CATEGORY valueOfLabel(String label) {
            return labelMap.get(label);
        }

        public static String getNameByCode(int code) {
            for (EXPENSE_CATEGORY s : EXPENSE_CATEGORY.values()) {
                if (code == s.type) {
                    return s.name;
                }
            }
            return null;
        }
    }

    public enum INCOME_CATEGORY {

        SALARY( 1, "Salary"),
        SAVINGS(2, "Savings"),
        INVESTMENT(3, "Investment"),
        INHERITANCE(4, "Inheritance"),
        OTHERS(5, "Others");

        private int type;
        private String name;
        private static final Map<String, INCOME_CATEGORY> labelMap = new HashMap<String, INCOME_CATEGORY>();

        static {
            for (INCOME_CATEGORY a : values()) {
                labelMap.put(a.name, a);
            }
        }

        private INCOME_CATEGORY(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static INCOME_CATEGORY valueOfLabel(String label) {
            return labelMap.get(label);
        }

        public static String getNameByCode(int code) {
            for (INCOME_CATEGORY s : INCOME_CATEGORY.values()) {
                if (code == s.type) {
                    return s.name;
                }
            }
            return null;
        }
    }

    public static String getStringFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getDateFromString(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date convertedDate = null;
        try {
            convertedDate = sdf.parse(date);
        } catch (ParseException e) {
            Log.e("getDateFromString", "Can't parse the string date." + e.getMessage());
        }
        return convertedDate;
    }
}
