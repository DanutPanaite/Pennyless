package com.example.pennyless.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennyless.R;
import com.example.pennyless.adapters.IncomeRecyclerAdapter;
import com.example.pennyless.adapters.RecyclerClickHandler;
import com.example.pennyless.adapters.RecyclerTouchAdapter;
import com.example.pennyless.entities.Income;
import com.example.pennyless.entities.Database;
import com.example.pennyless.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<Income> budgetList = new ArrayList<>();
    private IncomeRecyclerAdapter customRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private Database databaseHelper;
    private BudgetViewModel pageViewModel;
    private TextView total_sum, total_sumLabel;

    public static BudgetFragment newInstance(int index) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.income_main, container, false);

        total_sumLabel = view.findViewById(R.id.total_sumLabel);
        total_sumLabel.setText(getResources().getString(R.string.total));

        total_sum = view.findViewById(R.id.total_sum);
        List<Income> budgetListCopy = budgetList;
        databaseHelper = new Database(getActivity().getApplicationContext());
        customRecyclerViewAdapter = new IncomeRecyclerAdapter(budgetListCopy, getActivity().getApplicationContext());
        recyclerView = view.findViewById(R.id.budget_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        pageViewModel = new ViewModelProvider(requireActivity()).get(BudgetViewModel.class);
        pageViewModel.getBudgetMutableLiveData().observe(getViewLifecycleOwner(), budgetListUpdateObserver);

        recyclerView.addOnItemTouchListener(new RecyclerTouchAdapter(getActivity().getApplicationContext(), recyclerView, new RecyclerClickHandler() {
            @Override
            public void onClick(View view, int position) {
                if(budgetList != null) {
                    Income selectedEntity = budgetList.get(position);
                    Intent in = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
                    in.putExtra("budget", selectedEntity);
                    startActivity(in);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(budgetList != null) {
                    Income selectedEntity = budgetList.get(position);
                    databaseHelper.deleteIncome(selectedEntity.getId());
                    if(customRecyclerViewAdapter != null) {
                        budgetList = databaseHelper.getIncomeList();
                        customRecyclerViewAdapter.refresh(budgetList);
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Item deleted.", Toast.LENGTH_LONG).show();
                }
            }
        }));

        return view;
    }

    private Observer<List<Income>> budgetListUpdateObserver = new Observer<List<Income>>() {
        @Override
        public void onChanged(List<Income> budgetArrayList) {
            if(customRecyclerViewAdapter != null)
                customRecyclerViewAdapter.refresh(budgetArrayList);
            budgetList = budgetArrayList;
            total_sum.setText(String.valueOf(databaseHelper.getTotalSum()));
        }
    };
}
