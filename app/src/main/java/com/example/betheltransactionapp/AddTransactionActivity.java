package com.example.betheltransactionapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText editAmount, editDescription, editDate;
    private RadioGroup radioGroupType;
    private Button btnSaveTransaction;
    private DatabaseReference transactionsRef;
    private Calendar calendar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        // Initialize Firebase Database
        transactionsRef = FirebaseDatabase.getInstance().getReference("transactions").child(currentUser.getUid());

        // Bind UI elements
        editAmount = findViewById(R.id.edit_amount);
        editDescription = findViewById(R.id.edit_description);
        editDate = findViewById(R.id.edit_date);
        radioGroupType = findViewById(R.id.radio_group_type);
        btnSaveTransaction = findViewById(R.id.btn_save_transaction);
        calendar = Calendar.getInstance();

        // Set Date Picker Dialog
        editDate.setOnClickListener(v -> showDatePicker());

        // Save Transaction
        btnSaveTransaction.setOnClickListener(v -> saveTransaction());
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    editDate.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void saveTransaction() {
        String amount = editAmount.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String date = editDate.getText().toString().trim();

        // Get selected transaction type
        int selectedTypeId = radioGroupType.getCheckedRadioButtonId();
        if (selectedTypeId == -1) {
            Toast.makeText(this, "Please select a transaction type", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedType = findViewById(selectedTypeId);
        String type = selectedType.getText().toString();

        // Validate inputs
        if (TextUtils.isEmpty(amount) || TextUtils.isEmpty(description) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate unique transaction ID
        String transactionId = transactionsRef.push().getKey();

        double transactionAmount;
        try {
            transactionAmount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show();
            return;
        }
        //
        Date transactionDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            transactionDate = sdf.parse(date);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }



        // Create Transaction Model
        TransactionModel transaction = new TransactionModel(transactionId,type,transactionAmount, description,transactionDate);

        // Save transaction to Firebase
        transactionsRef.child(transactionId).setValue(transaction)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddTransactionActivity.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddTransactionActivity.this, "Failed to add transaction", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
