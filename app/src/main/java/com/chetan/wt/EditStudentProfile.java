package com.chetan.wt;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStudentProfile extends AppCompatActivity {
    String StudentID;
    Integer flag=0;
    EditText name, qualification, city;
    TextView mail;
    Button back, save;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);
        setTitle("Edit Profile");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.startblue1)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StudentID =  getIntent().getStringExtra("StudentID");
        name = (EditText) findViewById(R.id.editSname);
        qualification = (EditText) findViewById(R.id.editSqualification);
        city = (EditText) findViewById(R.id.editScity);
        mail = (TextView) findViewById(R.id.editSemail);
        save = (Button) findViewById(R.id.save);
        reff = FirebaseDatabase.getInstance().getReference().child("Students").child(StudentID);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                mail.setText(dataSnapshot.child("mail").getValue().toString());
                qualification.setText(dataSnapshot.child("qualification").getValue().toString());
                city.setText(dataSnapshot.child("city").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( name.getText().toString().trim().length() == 0 )
                    name.setError( "Student Name is required!" );
                else
                    flag++;
                if( qualification.getText().toString().trim().length() == 0 )
                    qualification.setError( "Qualification is required!" );
                else
                    flag++;
                if( city.getText().toString().trim().length() == 0 )
                    city.setError( "Address is required!" );
                else
                    flag++;
//                if( mail.getText().toString().trim().length() == 0 )
//                    mail.setError( "Areas of Interests are required!" );
//                else
//                    flag++;
                if (flag==3) {
                    reff.child("name").setValue(name.getText().toString().trim());
                    reff.child("qualification").setValue(qualification.getText().toString().trim());
                    reff.child("city").setValue(city.getText().toString().trim());
                    reff.child("mail").setValue(mail.getText().toString().trim());
                    finish();
                }
                else
                    flag=0;
            }
        });

        //Toast.makeText(this,StudentID,Toast.LENGTH_SHORT).show();
//        back = (Button)findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
