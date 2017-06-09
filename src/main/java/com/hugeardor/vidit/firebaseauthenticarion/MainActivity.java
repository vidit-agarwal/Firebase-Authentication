package com.hugeardor.vidit.firebaseauthenticarion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnLogin ;
    EditText input_email , input_pass ;
    TextView btnSignUp , btnForgotPass;
    RelativeLayout activity_main ;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view
        btnLogin=(Button)findViewById(R.id.login_btn_login);
        input_email=(EditText)findViewById(R.id.login_email);
        input_pass=(EditText)findViewById(R.id.login_password);
        btnSignUp=(TextView)findViewById(R.id.login_btn_signup);
        btnForgotPass=(TextView)findViewById(R.id.login_btn_forget_password);
        activity_main=(RelativeLayout)findViewById(R.id.activity_main);


        btnSignUp.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        //initialise firebase authentication

        auth=FirebaseAuth.getInstance();

        //check already session . if ok-> dashboard

        if(auth.getCurrentUser() != null)
        {

            startActivity(new Intent(MainActivity.this ,Dashboard.class ));

        }


    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.login_btn_forget_password)
        {

            startActivity(new Intent(MainActivity.this , forgotPassword.class));
            finish();
        }
        else if(v.getId()== R.id.login_btn_signup)
        {

            startActivity(new Intent(MainActivity.this , SignUp.class));
            finish();
        }
        else if(v.getId()== R.id.login_btn_login)
        {


            if(input_email.getText().toString().length() == 0 ) {



                input_email.setError( "Email is required!" );

            }
            else if(input_pass.getText().toString().length()==0)
            {
                input_pass.setError( "Password is required!" );
            }
            else{

            loginUser(input_email.getText().toString() , input_pass.getText().toString()); }

        }

    }

    private void loginUser(String email, final String pass) {

        auth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful())
                {
                    if(pass.length() <6)
                    {
                        Snackbar snackbar =Snackbar.make(activity_main , "Password Length Must be over 6" , Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else
                    {
                        Snackbar snackbar =Snackbar.make(activity_main , "Email Id or Password is not Coreect !" , Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }

                }
                else
                {


                    startActivity(new Intent(MainActivity.this , Dashboard.class));
                    finish();

                }

            }
        }) ;




    }

}
