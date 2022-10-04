package com.example.mess_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class PollingActivity extends AppCompatActivity {

    public static int sum;
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;

    TextView textView;

    RecyclerView pollingRecyclerView;
    PollingAdapter pollingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = (DatabaseReference) firebaseDatabase.getReference().child("meals");//.child("m1").child("count");

        getdata();

        setContentView(R.layout.activity_polling);



        pollingRecyclerView = (RecyclerView) findViewById(R.id.Polling_rv);
        pollingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("meals"), MainModel.class)
                        .build();

        pollingAdapter = new PollingAdapter(options);
        pollingRecyclerView.setAdapter(pollingAdapter);

    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.

                    sum=0;
                    for(DataSnapshot ds: snapshot.getChildren()){

                        Map<String,Object> map = (Map<String,Object>) ds.getValue();
                        Object count = map.get("count");
                        int cValue = Integer.parseInt(String.valueOf(count));
                        sum = sum + cValue;

                        Log.d("Sum",String.valueOf(sum));

                    }
                   // textView.setText(String.valueOf(sum));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(PollingActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        pollingAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pollingAdapter.stopListening();
    }

}