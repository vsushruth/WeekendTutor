package com.chetan.wt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity {
    int flag = 0;
    private String UserID = null;
    private FirebaseAuth fa;
    private FirebaseUser User;
    private DatabaseReference refStud, refTut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        fa = FirebaseAuth.getInstance();
        User = fa.getCurrentUser();

        refStud = FirebaseDatabase.getInstance().getReference("Students");
        refTut = FirebaseDatabase.getInstance().getReference("users");
        if (User != null) {

            UserID = User.getUid();
            if(UserID != null)
            {
                refStud.child(UserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //UserClass = dataSnapshot.child("userClass").getValue().toString();
                        if(dataSnapshot.exists())
                        {
                            Intent intent = new Intent(Welcome.this, CourseList.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                refTut.child(UserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            //UserClass = dataSnapshot.child("userClass").getValue().toString();
                            Intent intent = new Intent(Welcome.this, CourseList.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        }
    }

    public void login_tutor(View view){
        Intent intent = new Intent(this, loginTutor.class);
        startActivity(intent);
    }

    public void login_student(View view){
        Intent intent = new Intent(this, loginStudent.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(flag==1){
            finishAffinity();
        }
        else {
            Toast.makeText(this,"Press once again to exit",Toast.LENGTH_SHORT).show();
            flag++;
        }
    }

}
