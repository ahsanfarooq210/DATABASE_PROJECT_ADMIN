package com.example.database_project_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_salesman_activity extends AppCompatActivity
{
    private EditText usernameTf,passwordTf,confirmPasswordTf;
    private FirebaseAuth mAuth;

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
    private DatabaseReference salesmanReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_salesman_activity);

        usernameTf=findViewById(R.id.username_tf);
        passwordTf=findViewById(R.id.password_tf);
        confirmPasswordTf=findViewById(R.id.confirm_password_tf);
        mAuth = FirebaseAuth.getInstance();

        rellay1 = findViewById(R.id.rellay1);
        rally2=findViewById(R.id.bottom_rally2);
        rellay2=findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        progressBar=findViewById(R.id.my_progress_bar);
        progressBarh.postDelayed(runnable1,100);
        salesmanReference= FirebaseDatabase.getInstance().getReference("SALESMAN_USERNAME");
        salesmanReference.keepSynced(true);

    }

    public void createSlaesman(View view)
    {
        if (usernameTf.getText().toString().length() == 0)
        {
            usernameTf.setError("username cannot be empty");
            return;
        }
        if (passwordTf.getText().toString().length() == 0)
        {
            passwordTf.setError("password cannot be empty");
            return;
        }
        if (confirmPasswordTf.getText().toString().length() == 0)
        {
            confirmPasswordTf.setError("please onfirm password");
            return;
        }

        if(!passwordTf.getText().toString().equals(confirmPasswordTf.getText().toString()))
        {
            passwordTf.setError("Passwords donot match");
            confirmPasswordTf.setError("Passwords donot match");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        String username=usernameTf.getText().toString().trim(),password=passwordTf.getText().toString().trim();
        createSalesman(username,password);


    }

    public void createSalesman(final String email, final String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBarh.postDelayed(runnable1,100);
                            usernameTf.setText("");
                            passwordTf.setText("");
                            confirmPasswordTf.setText("");
                            Toast.makeText(add_salesman_activity.this, "salesman created successfully", Toast.LENGTH_SHORT).show();
                            String id=salesmanReference.push().getKey();
                            SalesmanId salesman=new SalesmanId(id,email,password);
                            salesmanReference.child(id).setValue(salesman);


                        } else {
                            // If sign in fails, display a message to the user.
                            progressBarh.postDelayed(runnable1,100);
                            Toast.makeText(add_salesman_activity.this, "error in creating the salesman\nEnter again", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
