package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth pauth;
    private DatabaseReference user;
    EditText Phone1,  Phone2,  Phone3,  Phone4;
    Button Submit;
    String User;
    String times = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pauth = FirebaseAuth.getInstance();
        User = pauth.getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference();

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                times = snapshot.child("User").child(User).child("times").getValue(String.class);
                Log.d("t",times);
                if(times.equals("A"))
                {Log.d("t","Fetch");
                    Phone1 = findViewById(R.id.Phone1);
                    Phone2 = findViewById(R.id.Phone2);
                    Phone3 = findViewById(R.id.Phone3);
                    Phone4 = findViewById(R.id.Phone4);
                    Submit = findViewById(R.id.submit);
                    Submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String ph1 = Phone1.getText().toString().trim();
                            String ph2 = Phone2.getText().toString().trim();
                            String ph3 = Phone3.getText().toString().trim();
                            String ph4 = Phone4.getText().toString().trim();
                            int f = 0;
                            if(!Pattern.matches("[a-zA-Z]+", ph1)){
                                if(ph1.length() < 10 && ph1.length() > 12)
                                {
                                    Phone1.setError("Invalid Email");
                                    f = 1;
                                }

                            }
                            if(!Pattern.matches("[a-zA-Z]+", ph2)){
                                if(ph1.length() < 10 && ph1.length() > 12)
                                {
                                    Phone2.setError("Invalid Email");
                                    f = 1;
                                }

                            }
                            if(!Pattern.matches("[a-zA-Z]+", ph3)){
                                if(ph1.length() < 10 && ph1.length() > 12)
                                {
                                    Phone3.setError("Invalid Email");
                                    f = 1;
                                }

                            }
                            if(!Pattern.matches("[a-zA-Z]+", ph4)){
                                if(ph1.length() < 10 && ph1.length() > 12)
                                {
                                    Phone4.setError("Invalid Email");
                                    f = 1;
                                }

                            }
                            if (f == 1) {
                                return;
                            }
                            else
                            {

                                //String n = User.getDisplayName();


                                //String uid = user.push().getKey();
                                Map<String, Object> u = new HashMap<>();
                                //u.put("Userid", Userid);
                                user.child("User").child(User).child("times").setValue("B");
                                user.child("User").child(User).child("Emergency_Contact1").setValue(ph1);

                                user.child("User").child(User).child("Emergency_Contact2").setValue(ph2);
                                user.child("User").child(User).child("Emergency_Contact3").setValue(ph3);
                                user.child("User").child(User).child("Emergency_Contact4").setValue(ph4);

                                times = "B";
                                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
                if(times.equals("B"))
                {startActivity(new Intent(getApplicationContext(), dashboard.class));
                    finish();}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Network Error ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cuser = pauth.getCurrentUser();
        if(cuser != null)
        {

        }
        else
        {
            startActivity(new Intent(getApplicationContext(), login_page.class));
            finish();
        }
    }



}