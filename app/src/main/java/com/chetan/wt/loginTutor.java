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

public class loginTutor extends Activity {

    private String Password;
    private String Mail;
    private Matcher matcher;
    FirebaseAuth mAuth;
    Button bu;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_tutor);

        Intent intent = getIntent();

        final TextView mail = (TextView) findViewById(R.id.email);
        final TextView pass = (TextView) findViewById(R.id.pass);
        final Button signup =(Button)findViewById(R.id.signIn);
        bu=(Button)findViewById(R.id.button4);
        pb=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;

                pb.setMessage("Logging In...");
                pb.show();

                Mail = mail.getText().toString();
                Password = pass.getText().toString();

                if(TextUtils.isEmpty(Mail)){
                    mail.setError("Please Enter Email ID");
                    Toast.makeText(loginTutor.this, "Please Fill all the details!!", Toast.LENGTH_LONG).show();
                    flag = 1;
                    pb.dismiss();
                }

                if(TextUtils.isEmpty(Password)){
                    pass.setError("Please Enter Password");
                    Toast.makeText(loginTutor.this, "Please Fill all the details!!", Toast.LENGTH_LONG).show();
                    flag = 1;
                    pb.dismiss();

                }

                String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(Mail);

                if (flag == 0) {

                    //if(mAuth.getCurrentUser().)
                    mAuth.signInWithEmailAndPassword(Mail, Password).addOnCompleteListener(loginTutor.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pb.dismiss();


                                Intent intent = new Intent(loginTutor.this, ListOfCourseTutor.class);

                                Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else
                            {
                                if (!matcher.matches()) {
                                    mail.setError("invalid e-mail");
                                    Toast.makeText(loginTutor.this, "Invalid E-Mail ID!!", Toast.LENGTH_SHORT).show();
                                }
                                else if (Password.length() <= 8) {
                                    pass.setError("password not long enough");
                                    Toast.makeText(loginTutor.this, "Invalid Password!!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(loginTutor.this, "Invalid Email/Password!!", Toast.LENGTH_SHORT).show();
                                }
                                pb.dismiss();

                            }
                        }
                    });

                }

            }
        });
    }

    public void forgot_pass(View view){
        Intent i = new Intent(loginTutor.this, forgotPassword.class);
        startActivity(i);
    }

    public void register_student(View view) {
        Intent intent = new Intent(loginTutor.this, TutorRegistration.class);
        startActivity(intent);
    }
}
