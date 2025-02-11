package com.example.betheltransactionapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    private FloatingActionButton addTransactionButton;
    private RecyclerView recyclerView;
    private TextView noTransactionsText;
    private TransactionAdapter transactionAdapter;
    private List<TransactionModel> transactionList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        noTransactionsText = findViewById(R.id.no_transactions_text);

        if (currentUser == null) {
            // Redirect to Login if user is not authenticated
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize Firebase Database reference for the user's transactions
        databaseReference = FirebaseDatabase.getInstance().getReference("transactions").child(currentUser.getUid());

        // Find views from XML
        recyclerView = findViewById(R.id.recycler_view);
        addTransactionButton = findViewById(R.id.add_transaction_button);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionList = new ArrayList<>();
        transactionAdapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(transactionAdapter);

        // Load transactions from Firebase
        loadTransactions();

        // Set listener for Floating Action Button to add transactions
        addTransactionButton.setOnClickListener(v -> {
            Intent intent = new Intent(TransactionActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });
    }

    private void loadTransactions() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TransactionModel transaction = dataSnapshot.getValue(TransactionModel.class);
                    if (transaction != null) {
                        transactionList.add(transaction);
                    }
                }
                transactionAdapter.notifyDataSetChanged();

                if (transactionList.isEmpty()) {
                    noTransactionsText.setVisibility(View.VISIBLE);
                } else {
                    noTransactionsText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TransactionActivity.this, "Failed to load transactions: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
