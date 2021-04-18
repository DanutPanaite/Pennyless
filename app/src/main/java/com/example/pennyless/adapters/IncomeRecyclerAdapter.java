package com.example.pennyless.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pennyless.R;
import com.example.pennyless.entities.Income;
import com.example.pennyless.utils.Constants;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.List;

public class IncomeRecyclerAdapter extends RecyclerView.Adapter<IncomeRecyclerAdapter.MyViewHolder> {

    private List<Income> incomeList;
    private Context appContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sum, category;
        public MaterialIconView categoryImage;

        public MyViewHolder(View view) {
            super(view);
            sum = (TextView) view.findViewById(R.id.name);
            category = (TextView) view.findViewById(R.id.category_budget);
            categoryImage = (MaterialIconView)view.findViewById(R.id.category_img_budget);
        }
    }

    public IncomeRecyclerAdapter(List<Income> income, Context context) {
        this.incomeList = income;
        this.appContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.income_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Income entityDetail = incomeList.get(position);
        holder.sum.setText(String.valueOf(entityDetail.getSum()));
        holder.category.setText(entityDetail.getCategory());
        if(entityDetail.getCategory().equalsIgnoreCase(Constants.INCOME_CATEGORY.SALARY.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.CASH_MULTIPLE);
        } else if(entityDetail.getCategory().equalsIgnoreCase(Constants.INCOME_CATEGORY.INHERITANCE.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.COINS);
        }else if(entityDetail.getCategory().equalsIgnoreCase(Constants.INCOME_CATEGORY.INVESTMENT.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.CHART_HISTOGRAM);
        }else if(entityDetail.getCategory().equalsIgnoreCase(Constants.INCOME_CATEGORY.SAVINGS.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.BRIEFCASE);
        }else if(entityDetail.getCategory().equalsIgnoreCase(Constants.INCOME_CATEGORY.OTHERS.getName())){
            holder.categoryImage.setIcon(MaterialDrawableBuilder.IconValue.STAR);
        }
        holder.categoryImage.setColorResource(R.color.app_color);
    }

    @Override
    public int getItemCount() {
        if(incomeList != null) {
            return incomeList.size();
        }
        return 0;
    }

    public void refresh(List<Income> incomeList)
    {
        this.incomeList = incomeList;
        notifyDataSetChanged();
    }
}
