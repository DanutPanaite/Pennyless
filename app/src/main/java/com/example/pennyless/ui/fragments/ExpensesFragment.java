package com.example.pennyless.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennyless.R;
import com.example.pennyless.adapters.ExpenseRecyclerAdapter;
import com.example.pennyless.adapters.RecyclerClickHandler;
import com.example.pennyless.adapters.RecyclerTouchAdapter;
import com.example.pennyless.entities.Database;
import com.example.pennyless.entities.Expense;
import com.example.pennyless.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class ExpensesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<Expense> expensesList = new ArrayList<>();
    private ExpenseRecyclerAdapter customRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private Database databaseHelper;
    private ExpensesViewModel pageViewModel;

    public static ExpensesFragment newInstance(int index) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.expenses_main, container, false);
        List<Expense> expensesListCopy = expensesList;
        databaseHelper = new Database(getActivity().getApplicationContext());
        customRecyclerViewAdapter = new ExpenseRecyclerAdapter(expensesListCopy, getActivity().getApplicationContext());
        recyclerView = view.findViewById(R.id.expense_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        pageViewModel = new ViewModelProvider(requireActivity()).get(ExpensesViewModel.class);
        pageViewModel.getExpensesMutableLiveData().observe(getViewLifecycleOwner(), expensesListUpdateObserver);

        recyclerView.addOnItemTouchListener(new RecyclerTouchAdapter(getActivity().getApplicationContext(), recyclerView, new RecyclerClickHandler() {
            @Override
            public void onClick(View view, int position) {
                if(expensesList != null) {
                    Expense selectedEntity = expensesList.get(position);
                    Intent in = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
                    in.putExtra("expense", selectedEntity);
                    startActivity(in);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(expensesList != null) {
                    Expense selectedEntity = expensesList.get(position);
                    databaseHelper.deleteExpense(selectedEntity.getId());
                    if(customRecyclerViewAdapter != null) {
                        expensesList = databaseHelper.getExpenseList();
                        customRecyclerViewAdapter.refresh(expensesList);
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Item deleted.", Toast.LENGTH_LONG).show();
                }
            }
        }));

        return view;
    }

    private Observer<List<Expense>> expensesListUpdateObserver = new Observer<List<Expense>>() {
        @Override
        public void onChanged(List<Expense> expensesArrayList) {

            if(customRecyclerViewAdapter != null)
                customRecyclerViewAdapter.refresh(expensesArrayList);
            expensesList = expensesArrayList;
        }
    };
}