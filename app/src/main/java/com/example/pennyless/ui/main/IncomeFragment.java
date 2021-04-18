package com.example.pennyless.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pennyless.R;
import com.example.pennyless.adapters.IncomeRecyclerAdapter;
import com.example.pennyless.entities.Income;
import com.example.pennyless.entities.Database;

import java.util.ArrayList;
import java.util.List;

public class IncomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private List<Income> incomeList = new ArrayList<>();
    private IncomeRecyclerAdapter customRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private Database databaseHelper;
    private IncomeViewModel pageViewModel;
    private TextView total_sumLabel, total_sum;
    private static final String TAG = "IncomeFragment";

    public static IncomeFragment newInstance(int index) {
        IncomeFragment fragment = new IncomeFragment();
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
        total_sum = (TextView)view.findViewById(R.id.total_sum);
        total_sumLabel = (TextView)view.findViewById(R.id.total_sumLabel);

        total_sum.setText(getResources().getString(R.string.total));
        List<Income> budgetListCopy = incomeList;
        customRecyclerViewAdapter = new IncomeRecyclerAdapter(budgetListCopy, getActivity().getApplicationContext());
        recyclerView = view.findViewById(R.id.budget_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customRecyclerViewAdapter);
        LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        pageViewModel = new ViewModelProvider(requireActivity()).get(IncomeViewModel.class);
        pageViewModel.getBudgetMutableLiveData().observe(getViewLifecycleOwner(), incomeListUpdateObserver);
        return view;
    }

    private Observer<List<Income>> incomeListUpdateObserver = new Observer<List<Income>>() {
        @Override
        public void onChanged(List<Income> incomeArrayList) {
            if(customRecyclerViewAdapter != null)
                customRecyclerViewAdapter.refresh(incomeArrayList);
        }
    };



}
