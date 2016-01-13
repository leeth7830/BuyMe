package kappa.buyme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class Register extends ActionBarActivity implements View.OnClickListener{
    EditText etFName, etLName, etEmail, etUsername, etPassword;
    Button bRegister;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:
                String firstname = ""+etFName.getText().toString();
                String lastname = ""+etLName.getText().toString();
                String username = ""+etUsername.getText().toString();
                String password = ""+etPassword.getText().toString();
                String email = ""+etEmail.getText().toString();

                 user = new User(firstname, lastname, email, username, password);
                registerUser(user);
                break;
        }
    }

    private void registerUser(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                Intent loginIntent = new Intent(Register.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}