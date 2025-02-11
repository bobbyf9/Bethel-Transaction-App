package com.example.betheltransactionapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<TransactionModel> transactionList;

    // Constructor
    public TransactionAdapter(List<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionModel transaction = transactionList.get(position);

        // Format date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String formattedDate = (transaction.getTimestamp() != null) ? sdf.format(transaction.getTimestamp()) : "N/A";

        holder.amountTextView.setText(String.format("$%.2f", transaction.getAmount()));
        holder.typeTextView.setText(transaction.getType());
        holder.descriptionTextView.setText(transaction.getDescription());
        holder.dateTextView.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    // ViewHolder class
    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView amountTextView, typeTextView, descriptionTextView, dateTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.text_amount);
            typeTextView = itemView.findViewById(R.id.text_type);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            dateTextView = itemView.findViewById(R.id.text_date);
        }
    }
}
