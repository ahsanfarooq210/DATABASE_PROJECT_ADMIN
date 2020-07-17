package com.example.database_project_admin.Target.RecyclerViewContent.Adapter;

import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_project_admin.Entity.SalesmanId;
import com.example.database_project_admin.R;
import com.example.database_project_admin.RvAdapters.ShowSalesmanRvAdapter;
import com.example.database_project_admin.Target.Entity.Target;
import com.forcelain.awesomelayoutmanager.AwesomeViewHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Target> targetList;
    private Activity context;
    //private OnItemClickListener itemClickListener;
   // private View.OnLayoutChangeListener onLayoutChangeListener;

  /*  public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }*/


    public RecyclerViewAdapter(ArrayList<Target> targetList, Activity context) {
        this.targetList=targetList;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_target, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder viewHolder, final int position) {

       // final  Target target=targetList.get(position);
       // viewHolder.sku_edit_target.setText(targetList.get(position).getSKU().getProductName());
        viewHolder.salesman_edit_target.setText(targetList.get(position).getSalesmenEmail());
        viewHolder.target_edit_target.setText(String.valueOf(  targetList.get(position).getTARGET()));
        viewHolder.start_date_edit_target.setText(targetList.get(position).getStartDateString());
        viewHolder.end_date_edit_target.setText(targetList.get(position).getEndDateString());
//sku
        viewHolder.company_sku_expandable.setText(targetList.get(position).getSKU().getCompany().getName());
        viewHolder.category_sku_expandable.setText(targetList.get(position).getSKU().getCatagory().getName());
        viewHolder.product_name_sku_expandable.setText(targetList.get(position).getSKU().getProductName());
        viewHolder.product_size_sku_expandable.setText(String.valueOf(targetList.get(position).getSKU().getSize()));


       /* View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(viewHolder.getAdapterPosition());
                }
            }
        };
        viewHolder.readMoreAction.setOnClickListener(clickListener);
        viewHolder.itemView.setOnClickListener(clickListener);*/
    }
/*
    @Override
    public void onViewDetachedFromWindow(RecyclerViewAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
       // holder.itemView.removeOnLayoutChangeListener(onLayoutChangeListener);
    }

    private void closeQuietly(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }*/

    @Override
    public int getItemCount() {
        //targetList == null ? 0 : targetList.size();
        return targetList.size();

    }

    /*public void setArticles(List<Target> targetList) {
        this.targetList = targetList;
        notifyDataSetChanged();
    }
*/
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView salesman_edit_target,target_edit_target,start_date_edit_target,end_date_edit_target;
        Button cancel_button_edit_target,edit_button_edit_target;
        View readMore;
        View readMoreAction;
       //sku
        TextView company_sku_expandable,category_sku_expandable,product_name_sku_expandable, product_size_sku_expandable;
        ViewHolder(View itemView) {
            super(itemView);

            salesman_edit_target = (TextView) itemView.findViewById(R.id.salesman_edit_target);
            target_edit_target = (TextView) itemView.findViewById(R.id.target_edit_target);
            start_date_edit_target=(TextView) itemView.findViewById(R.id.start_date_edit_target);
            end_date_edit_target=(TextView) itemView.findViewById(R.id.end_date_edit_target);
            cancel_button_edit_target=(Button) itemView.findViewById(R.id.cancel_button_edit_target);
            edit_button_edit_target=(Button) itemView.findViewById(R.id.edit_button_edit_target);

            //sku
            company_sku_expandable= (TextView) itemView.findViewById(R.id.company_sku_expandable);
            category_sku_expandable= (TextView) itemView.findViewById(R.id.category_sku_expandable);
            product_name_sku_expandable= (TextView) itemView.findViewById(R.id.product_name_sku_expandable);
            product_size_sku_expandable= (TextView) itemView.findViewById(R.id.product_size_sku_expandable);
           // readMore = itemView.findViewById(R.id.read_more);
          //  readMoreAction = itemView.findViewById(R.id.read_more_action);
        }

        //@Override
       // public void onStateChanged(float progress) {
        //    readMore.setAlpha(1 - progress);
       // }
    }

    public interface OnItemClickListener {
        void onItemClicked(int pos);
    }

}
