package vn.edu.usth.irc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText RegistrationName = findViewById(R.id.RegistrationName);
        EditText RegistrationEmail = findViewById(R.id.RegistrationEmail);
        EditText RegistrationPassword = findViewById(R.id.RegistrationPassword);
        EditText RegistrationConfirmPassword = findViewById(R.id.RegistrationConfirmPassword);
        TextView SignupButton = findViewById(R.id.SignupButton);
        TextView txt_Signin = findViewById(R.id.txt_Signin);

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=RegistrationName.getText().toString();
                String email=RegistrationName.getText().toString();
                String password=RegistrationPassword.getText().toString();
                String ConfirmPassword=RegistrationConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(ConfirmPassword)){
                    Toast.makeText(RegistrationActivity.this, "Please fill the blank", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(ConfirmPassword)){
                    Toast.makeText(RegistrationActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6){
                    Toast.makeText(RegistrationActivity.this, "PLease enter 6 character password", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(RegistrationActivity.this, InterfaceActivity.class));
                    Toast.makeText(RegistrationActivity.this, "Succesfully created account!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        txt_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }
}