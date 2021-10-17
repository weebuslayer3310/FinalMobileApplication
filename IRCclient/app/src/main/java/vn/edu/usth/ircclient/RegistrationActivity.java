package vn.edu.usth.ircclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Uri imageUri;
    CircleImageView profile_image;
    String imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        TextView txt_Signin = findViewById(R.id.Signin);
        profile_image = findViewById(R.id.profile_image);
        EditText RegistrationName = findViewById(R.id.RegistrationName);
        EditText RegistrationEmail = findViewById(R.id.RegistrationEmail);
        EditText RegistrationPassword = findViewById(R.id.RegistrationPassword);
        EditText RegistrationConfirmPassword = findViewById(R.id.RegistrationConfirmPassword);
        TextView SignupButton = findViewById(R.id.SignupButton);

        txt_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=RegistrationName.getText().toString();
                String email=RegistrationEmail.getText().toString();
                String password=RegistrationPassword.getText().toString();
                String ConfirmPassword=RegistrationConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(ConfirmPassword)){
                    Toast.makeText(RegistrationActivity.this, "Please fill the blank", Toast.LENGTH_SHORT).show();
                }
                else if(!email.matches(emailPattern)){
                    RegistrationEmail.setError("Please Enter valid email");
                    Toast.makeText(RegistrationActivity.this, "please enter valid email", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(ConfirmPassword)){
                    Toast.makeText(RegistrationActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 6){
                    Toast.makeText(RegistrationActivity.this, "Enter 6 character password", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                DatabaseReference reference=database.getReference().child("user").child(auth.getUid());
                                StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

                                if(imageUri!=null){
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageURI=uri.toString();
                                                        Users users = new Users(auth.getUid(),name,email,imageURI);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                                                }
                                                                else{
                                                                    Toast.makeText(RegistrationActivity.this, "Error in Creating User", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                                else{

                                    imageURI="https://firebasestorage.googleapis.com/v0/b/mobileapplication-edf3b.appspot.com/o/profileicon.png?alt=media&token=ccb1ef8a-fdb4-4c9d-897d-5275cf083d9f";
                                    Users users = new Users(auth.getUid(),name,email,imageURI);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                            }
                                            else{
                                                Toast.makeText(RegistrationActivity.this, "Error in Creating User", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                imageUri = data.getData();
                profile_image.setImageURI(imageUri);
            }
        }
    }
}