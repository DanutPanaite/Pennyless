package com.example.pennyless.ui.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pennyless.R;
import com.example.pennyless.entities.Income;
import com.example.pennyless.entities.Expense;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView type = findViewById(R.id.typeLbl);
        TextView name = findViewById(R.id.nameInfo);
        TextView nameLbl = findViewById(R.id.nameText);
        TextView sum = findViewById(R.id.sumText);
        TextView details = findViewById(R.id.detailsText);
        TextView category = findViewById(R.id.categoryText);

        if(getIntent() != null) {
            if(getIntent().getSerializableExtra("budget") != null) {
                Income budget = (Income) getIntent().getSerializableExtra("budget");
                if (budget != null) {
                    type.setText("BUDGET");
                    name.setVisibility(View.INVISIBLE);
                    nameLbl.setVisibility(View.INVISIBLE);
                    sum.setText(String.valueOf(budget.getSum()));
                    if(budget.getDetails() == null || budget.getDetails().isEmpty())
                        details.setText("None.");
                    else
                        details.setText(budget.getDetails());
                    category.setText(budget.getCategory());
                }
            }else{
                Expense expense = (Expense) getIntent().getSerializableExtra("expense");
                if (expense != null) {
                    type.setText("EXPENSE");
                    name.setVisibility(View.VISIBLE);
                    nameLbl.setVisibility(View.VISIBLE);
                    name.setText(expense.getName());
                    sum.setText(String.valueOf(expense.getSum()));
                    if(expense.getDetails() == null || expense.getDetails().isEmpty())
                        details.setText("None.");
                    else
                        details.setText(expense.getDetails());
                    category.setText(expense.getCategory());
                }
            }
        }
    }
}