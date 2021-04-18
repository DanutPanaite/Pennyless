package com.example.pennyless.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.pennyless.R;
import com.example.pennyless.entities.Expense;
import com.example.pennyless.utils.Constants;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.List;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.MyViewHolder> {

    private List<Expense> expenses;
    private Context appContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, category, addDate, sum;
        public MaterialIconView categoryImage;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            sum = (TextView) view.findViewById(R.id.sum);
            category = (TextView) view.findViewById(R.id.category);
            addDate = (TextView) view.findViewById(R.id.add_date);
            categoryImage = (MaterialIconView)view.findViewById(R.id.category_img);
        }
    }

    public ExpenseRecyclerAdapter(List<Expense> expenses, Context context) {
        this.expenses = expenses;
        this.appContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Expense entityDetail = expenses.get(position);
        holder.addDate.setText(Constants.getStringFromDate(entityDetail.getAddDate()));
        holder.sum.setText(String.valueOf(entityDetail.getSum()));
        holder.name.setText(entityDetail.getName());
        holder.category.setText(entityDetail.getCategory());
        if(entityDetail.getCategory().equalsIgnoreCase(Constants.EXPENSE_CATEGORY.GROCERIES.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.APPLE);
        } else if(entityDetail.getCategory().equalsIgnoreCase(Constants.EXPENSE_CATEGORY.TRANSPORT.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.BUS_SCHOOL);
        }else if(entityDetail.getCategory().equalsIgnoreCase(Constants.EXPENSE_CATEGORY.CLOTHING.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.SHOPPING);
        }else if(entityDetail.getCategory().equalsIgnoreCase(Constants.EXPENSE_CATEGORY.UTILITIES.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.BANK);
        }else if(entityDetail.getCategory().equalsIgnoreCase(Constants.EXPENSE_CATEGORY.OTHERS.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.STAR);
        }
        holder.categoryImage.setColorResource(R.color.purple_700);
    }

    @Override
    public int getItemCount() {
        if(expenses != null) {
            return expenses.size();
        }
        return 0;
    }

    public void refresh(List<Expense> expenseList)
    {
        this.expenses = expenseList;
        notifyDataSetChanged();
    }
}
