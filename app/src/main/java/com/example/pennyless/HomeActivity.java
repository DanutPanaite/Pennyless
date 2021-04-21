package com.example.pennyless;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennyless.entities.Database;
import com.example.pennyless.entities.Expense;
import com.example.pennyless.entities.Income;
import com.example.pennyless.ui.fragments.SectionsPagerAdapter;
import com.example.pennyless.ui.login.LoginActivity;
import com.example.pennyless.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Database database;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private static final String appPreferences = "PennylessAppPreferences";
    private SharedPreferences sharedpreferences;
    private List<Income> incomeList = new ArrayList<>();
    private List<Expense> expenseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().subscribeToTopic("PersonalBudgeting")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                    Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.i(TAG, msg);
                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        database = new Database(getApplicationContext());
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        Button logout = findViewById(R.id.logout);


        sharedpreferences = getSharedPreferences(appPreferences, Context.MODE_PRIVATE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("loggedOut", true);
                editor.apply();
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAddDialog();
            }
        });
    }

    private void generateAddDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
        alertDialog.setTitle(Html.fromHtml("<font color='#FF000000'>Add</font>"));
        Drawable dialogDrawable = getResources().getDrawable(R.drawable.outline_add_24);
        dialogDrawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.purple_700), PorterDuff.Mode.SRC_ATOP));
        alertDialog.setIcon(dialogDrawable);
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = li.inflate(R.layout.add_dialog, null);
        alertDialog.setView(dialogView);
        TextView nameTxt = (TextView)dialogView.findViewById(R.id.nameLabel);
        EditText name = (EditText)dialogView.findViewById(R.id.nameTxt);
        EditText details = (EditText)dialogView.findViewById(R.id.detailsTxt);
        EditText sum = (EditText)dialogView.findViewById(R.id.sumTxt);
        Spinner type = (Spinner)dialogView.findViewById(R.id.typeTxt);
        Spinner category = (Spinner)dialogView.findViewById(R.id.categoryList);

        List<String> types = Arrays.asList("INCOME", "EXPENSE");
        type.setAdapter(new ArrayAdapter<String>(HomeActivity.this, R.layout.spinner_dropdown_item, types));
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.teal_700));
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equalsIgnoreCase("INCOME")) {
                    category.setAdapter(new ArrayAdapter<Constants.INCOME_CATEGORY>(HomeActivity.this, R.layout.spinner_dropdown_item, Constants.INCOME_CATEGORY.values()));
                    name.setVisibility(View.INVISIBLE);
                    nameTxt.setVisibility(View.INVISIBLE);
                }else if(selectedItem.equalsIgnoreCase("EXPENSE")){
                    category.setAdapter(new ArrayAdapter<Constants.EXPENSE_CATEGORY>(HomeActivity.this, R.layout.spinner_dropdown_item, Constants.EXPENSE_CATEGORY.values()));
                    name.setVisibility(View.VISIBLE);
                    nameTxt.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    TextView typeTextView = (TextView)type.getSelectedView();
                    String typeSelected = typeTextView.getText().toString();
                    TextView categoryTextView = (TextView)category.getSelectedView();
                    String categorySelected = categoryTextView.getText().toString();

                    if(typeSelected.equalsIgnoreCase("INCOME")){
                        Income newIncome = new Income();
                        newIncome.setSum(Double.valueOf(sum.getText().toString()));
                        newIncome.setCategory(categorySelected);
                        if (!TextUtils.isEmpty(details.getText()))
                            newIncome.setDetails(details.getText().toString());
                        database.saveIncome(newIncome);
                    } else if(typeSelected.equalsIgnoreCase("EXPENSE")){
                        Expense newExpense = new Expense();
                        newExpense.setAddDate(new Date(System.currentTimeMillis()));
                        newExpense.setSum(Double.valueOf(sum.getText().toString()));
                        newExpense.setName(name.getText().toString());
                        newExpense.setCategory(categorySelected);
                        if (!TextUtils.isEmpty(details.getText()))
                            newExpense.setDetails(details.getText().toString());
                        database.saveExpense(newExpense);
                    }
                    Toast.makeText(getApplicationContext(), "Information saved.", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    if (TextUtils.isEmpty(sum.getText()))
                        Toast.makeText(getApplicationContext(), "Please fill in the sum.", Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(name.getText()))
                        Toast.makeText(getApplicationContext(), "Please fill in the name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        alert.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}