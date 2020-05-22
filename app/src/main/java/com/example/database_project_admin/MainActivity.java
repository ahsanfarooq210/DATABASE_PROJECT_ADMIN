package com.example.database_project_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private EditText emailet,passwordet;
    private RelativeLayout rellay1,rally2,rellay2;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            rellay1.setVisibility(View.VISIBLE);
            rally2.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);

        }
    };
    private ProgressBar progressBar;
    private Handler progressBarh=new Handler();
    private Runnable runnable1=new Runnable()
    {
        @Override
        public void run()
        {

            progressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        emailet=findViewById(R.id.username_tf);
        passwordet=findViewById(R.id.password_tf);

        rellay1 = findViewById(R.id.rellay1);
        rally2=findViewById(R.id.bottom_rally2);
        rellay2=findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        progressBar=findViewById(R.id.my_progress_bar);
        progressBarh.postDelayed(runnable1,100);
    }

    public void loginButton(View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        if(emailet.getText().toString().length()==0)
        {
            emailet.setError("Enter username");
            progressBarh.postDelayed(runnable1,100);
            return;
        }
        if(passwordet.getText().toString().length()==0)
        {
            passwordet.setError("Enter password");
            progressBarh.postDelayed(runnable1,100);
            return;
        }

        String username=emailet.getText().toString().trim(),password=passwordet.getText().toString().trim();
        signIn(username,password);
    }

    public void signIn(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            progressBarh.postDelayed(runnable1,100);

                        }
                        else
                            {
                            // If sign in fails, display a message to the user.
                                progressBarh.postDelayed(runnable1,100);

                            }

                        // ...
                    }
                });
    }
}
