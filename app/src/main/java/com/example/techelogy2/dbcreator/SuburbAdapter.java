package com.example.techelogy2.dbcreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;



import java.util.ArrayList;

import database.SuburbModel;

/**
 * Created by techelogy2 on 26/10/16.
 */

public class SuburbAdapter extends BaseAdapter implements Filterable {
    public interface DropdownSelectionListener {
        public abstract void onDropDownSelect(SuburbModel model);
    }

    private ArrayList<SuburbModel> suburbList1 = new ArrayList<>();
    ArrayList<SuburbModel> suggestions = new ArrayList<>();
    Context context;
    private Filter filter = new CustomFilter();
    DropdownSelectionListener dropdownSelectionListener;

    public SuburbAdapter(Context context, ArrayList<SuburbModel> suburbList1, ArrayList<SuburbModel> suburbList2) {

        this.suggestions = suburbList1;
        this.suburbList1 = suburbList2;
        this.context = context;
        dropdownSelectionListener = (DropdownSelectionListener) context;
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ViewHolder {
        TextView autoText;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.suburbrow, parent, false);
            holder = new ViewHolder();
            holder.autoText = (TextView) convertView.findViewById(R.id.autoText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.autoText.setText(suggestions.get(position).getDisplay_title());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("suggestions.get(position).toString() = " + suggestions.get(position).getId());
                dropdownSelectionListener.onDropDownSelect(suggestions.get(position));
            }
        });
        return convertView;
    }


    private class CustomFilter extends Filter {

        public CustomFilter() {
            System.out.println("CustomFilter  called= ");
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            constraint = constraint.toString().toLowerCase().trim();

            // suggestions.clear();
            System.out.println("constraint = " + constraint);
            System.out.println("suburbList1.size = " + suburbList1.size());
            if (suburbList1 != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                ArrayList<SuburbModel> filteredList = new ArrayList<>();
                for (int i = 0; i < suburbList1.size(); i++) {


                    if (suburbList1.get(i).getDisplay_title().toLowerCase().trim().contains(constraint.toString())) { // Compare item in original list if it contains constraints.


                        filteredList.add(suburbList1.get(i));
                        //suggestions.add(suburbList1.get(i)); // If TRUE add item in Suggestions.

                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            System.out.println("results = " + results.values);
            if (results.count > 0) {
                suggestions = (ArrayList<SuburbModel>) results.values;
                notifyDataSetChanged();

            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
