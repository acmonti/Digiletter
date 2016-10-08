package com.mlabs.bbm.digiletter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends android.app.Activity {
    LoginDatabaseAdapter LoginDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText NewFname = (EditText) findViewById(R.id.txt_FName);
        final EditText NewLname = (EditText) findViewById(R.id.txt_LName);
        final EditText NewUname = (EditText) findViewById(R.id.txt_UName);
        final EditText NewEmail = (EditText) findViewById(R.id.txt_Email);
        final EditText NewPassword = (EditText) findViewById(R.id.txt_Password);
        final EditText ConfirmPassword = (EditText) findViewById(R.id.txt_CPassword);

        final TextView show1 = (TextView) findViewById(R.id.showCPW);
        final TextView show2 = (TextView) findViewById(R.id.textView2);
        final TextView member = (TextView) findViewById(R.id.link_member);

        final Button CreateAcct = (Button) findViewById(R.id.btn_CreateAcct);

        LoginDatabaseAdapter = new LoginDatabaseAdapter(this);
        LoginDatabaseAdapter = LoginDatabaseAdapter.open();

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class );
                startActivity(intent);
            }
        });

        //Show "password"
        show1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int cursor= NewPassword.getSelectionStart();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        NewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        NewPassword.setSelection(cursor);
                        break;

                    case MotionEvent.ACTION_UP:
                        NewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        NewPassword.setSelection(cursor);
                        break;
                }
                return true;
            }
        });

        //Show "confirm password"
        show2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int cursor= ConfirmPassword.getSelectionStart();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        ConfirmPassword.setSelection(cursor);
                        break;

                    case MotionEvent.ACTION_UP:
                        ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        ConfirmPassword.setSelection(cursor);
                        break;
                }
                return true;
            }
        });

        assert CreateAcct != null;
        CreateAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nFname = NewFname.getText().toString();
                String nLname = NewLname.getText().toString();
                String nUname = NewUname.getText().toString();
                String nEmail = NewEmail.getText().toString();
                String nPass = NewPassword.getText().toString();
                String conPass = ConfirmPassword.getText().toString();

                String checkSUUname = LoginDatabaseAdapter.getSignUpUsername(nUname);
                String checkSUEmail = LoginDatabaseAdapter.getSignUpEmail(nEmail);

                if(nFname.equals("")||nLname.equals("")||nUname.equals("")||nEmail.equals("")||nPass.equals("")||conPass.equals(""))
                {Toast.makeText(getApplicationContext(), "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return;}

                //VALIDATION
                if (!validateFName(NewFname.getText().toString())){
                    NewFname.setError("Invalid First Name");
                    NewFname.requestFocus();
                }
                else if (!validateLName(NewLname.getText().toString())){
                    NewLname.setError("Invalid Last Name");
                    NewLname.requestFocus();
                }
                else if (!validateEmail(NewEmail.getText().toString())){
                    NewEmail.setError("Invalid Email");
                    NewEmail.requestFocus();
                }
                else if(!validatePassword(NewPassword.getText().toString()))
                {NewPassword.setError("Invalid Password");
                    NewPassword.requestFocus();
                }
                else if (!NewPassword.getText().toString().equals(ConfirmPassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else if(nUname.equals(checkSUUname)){
                    NewUname.setError("Username exists");
                    NewUname.requestFocus();
                }
                else if(nEmail.equals(checkSUEmail)){
                    NewEmail.setError("Email exists");
                    NewEmail.requestFocus();
                }
                else {
                    LoginDatabaseAdapter.insertEntry(nFname, nLname, nUname, nEmail, nPass);
                    Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class );
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        LoginDatabaseAdapter.close();
    }

    //VALIDATION FUNCTIONS
    private boolean validateFName(String fname){
        String fnamePattern = "^([A-Za-z] *)+$";
        Pattern pattern = Pattern.compile(fnamePattern);
        Matcher matcher = pattern.matcher(fname);
        return matcher.matches();
    }
    private boolean validateLName(String lname){
        String lnamePattern = "^([A-Za-z] *)+$";
        Pattern pattern = Pattern.compile(lnamePattern);
        Matcher matcher = pattern.matcher(lname);
        return matcher.matches();
    }
    private boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean validatePassword(String password){
        if (password != null && password.length() >= 8) {
            return true;
        }
        return false;
    }
}
