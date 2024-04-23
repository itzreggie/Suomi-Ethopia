package com.example.suomiethopia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {

    private List<String> dataList;
    private List<String> dataListFiltered;
    private OnItemClickListener listener;
    private Context context;
    private TextView noResultsTextView;
    public SearchAdapter(List<String> dataList) {
        this.dataList = dataList;
        this.dataListFiltered = new ArrayList<>(dataList);
    }

    public interface OnItemClickListener {
        void onItemClick(String municipalityName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    public void setNoResultsTextView(TextView textView) {
        this.noResultsTextView = textView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        if (dataListFiltered.size() == 0) {

            if (noResultsTextView != null) {
                noResultsTextView.setVisibility(View.VISIBLE);
            }
            return 0;
        } else {

            if (noResultsTextView != null) {
                noResultsTextView.setVisibility(View.GONE);
            }
            return dataListFiltered.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        String municipalityName = dataListFiltered.get(position);
                        listener.onItemClick(municipalityName);
                        hideKeyboard(itemView);
                    }
                }
            });
        }

        private void hideKeyboard(View view) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = new ArrayList<>(dataList);
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String data : dataList) {
                        if (data.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(data);
                        }
                    }
                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                filterResults.count = dataListFiltered.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataListFiltered = (ArrayList<String>) results.values;
                notifyDataSetChanged();
                if (results.count == 0) {

                    if (noResultsTextView != null) {
                        noResultsTextView.setVisibility(View.VISIBLE);
                    }
                } else {

                    if (noResultsTextView != null) {
                        noResultsTextView.setVisibility(View.GONE);
                    }
                }
            }
        };
    }

}
