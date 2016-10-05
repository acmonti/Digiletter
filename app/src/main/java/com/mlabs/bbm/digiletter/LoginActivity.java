package com.mlabs.bbm.digiletter;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;


import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    LoginDatabaseAdapter LoginDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R .layout.activity_login);

        final EditText email_uname = (EditText) findViewById(R.id.txt_EmailUName);
        final EditText password_tu = (EditText) findViewById(R.id.txt_PWord);

        final TextView show = (TextView) findViewById(R.id.show_password);
        final TextView SignUp = (TextView) findViewById(R.id.link_signup);

        final Button go = (Button) findViewById(R.id.btn_LogIn);

        LoginDatabaseAdapter = new LoginDatabaseAdapter(this);
        LoginDatabaseAdapter = LoginDatabaseAdapter.open();

        assert go != null;
        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email = email_uname.getText().toString();
                String uname = email_uname.getText().toString();
                String password = password_tu.getText().toString();

                String checkUname = LoginDatabaseAdapter.getSingleEntryUname(uname);
                String checkEmail = LoginDatabaseAdapter.getSingleEntryEmail(email);

                if(password.equals(checkUname)|password.equals(checkEmail))
                {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intentSignUP = new Intent(getApplicationContext(),OnTouchActivity.class);
                    startActivity(intentSignUP);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        show.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int cursor= password_tu.getSelectionStart();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        password_tu.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        password_tu.setSelection(cursor);
                        break;

                    case MotionEvent.ACTION_UP:
                        password_tu.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password_tu.setSelection(cursor);
                        break;
                }
                return true;
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class );
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginDatabaseAdapter.close();
    }
}