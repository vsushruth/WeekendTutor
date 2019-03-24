package com.chetan.wt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginStudent extends Activity {

    private String Password;
    private String Mail;
    private Matcher matcher;
    FirebaseAuth mAuth;
    private ProgressDialog pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_student);

        Intent intent = getIntent();

        final TextView mail = (TextView) findViewById(R.id.emailStu);
        final TextView pass = (TextView) findViewById(R.id.passStu);
        final Button signup =(Button)findViewById(R.id.signInStu);
        pb=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pb.setMessage("Logging In...");
                pb.show();

                int flag = 0;

                Mail = mail.getText().toString();
                Password = pass.getText().toString();

                if(TextUtils.isEmpty(Mail)){
                    mail.setError("Please Enter Email ID");
                    Toast.makeText(loginStudent.this, "Please Fill all the details!!", Toast.LENGTH_LONG).show();
                    flag = 1;
                    pb.dismiss();
                }
                if(TextUtils.isEmpty(Password)){
                    pass.setError("Please Enter Password");
                    Toast.makeText(loginStudent.this, "Please Fill all the details!!", Toast.LENGTH_LONG).show();
                    flag = 1;
                    pb.dismiss();
                }

                String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(Mail);

                if(flag==0)
                {


//                    if(mAuth.getCurrentUser().)
                    mAuth.signInWithEmailAndPassword(Mail, Password).addOnCompleteListener(loginStudent.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //pb.setMessage("Logging In...");
                                //pb.show();
                               // mAuth.getCurrentUser();
                                pb.dismiss();
                                Intent intent = new Intent(loginStudent.this, CourseList.class);

                                Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                            else
                            {
                                if (!matcher.matches()) {
                                    mail.setError("Invalid E-mail");
                                    Toast.makeText(loginStudent.this, "Invalid E-Mail ID!!", Toast.LENGTH_SHORT).show();
                                } else if (Password.length() <= 8) {
                                    pass.setError("Password Not Long Enough");
                                    Toast.makeText(loginStudent.this, "Invalid Password!!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(loginStudent.this, "Invalid Email/Password!!", Toast.LENGTH_SHORT).show();


                                pb.dismiss();


                            }
                        }
                    });
                }


            }
        });
    }

    public void forgot_pass(View view){
        Intent intent = new Intent(loginStudent.this, forgotPassword.class);
        startActivity(intent);
    }

    public void register_student(View view){
        Intent intent = new Intent(loginStudent.this, registerStudent.class);
        startActivity(intent);
    }

}

