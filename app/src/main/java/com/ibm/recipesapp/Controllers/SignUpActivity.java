package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ibm.recipesapp.R;
import com.ibm.recipesapp.User.User;

// SignUpActivity

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText nameInput, emailInput, passwordInput;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.name_TextInputEditText_SignUpActivity);
        emailInput = findViewById(R.id.email_TextInputEditText_SignUpActivity);
        passwordInput = findViewById(R.id.password_TextInputEditText_SignUpActivity);
    }

    public void signUp(View view){
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        System.out.println(String.format("Sign Up - Email: %s, Password: %s", email, password));

        if(password.length() < 6)
        {
            Toast.makeText(this, "Password is too short", Toast.LENGTH_LONG).show();
        }

        if (!name.equals("") && !email.equals("") && !password.equals(""))
        {
            try
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("SIGN UP", "createUserWithEmail:success");
                                FirebaseUser currUser = mAuth.getCurrentUser();
                                String uid = currUser.getUid();

                                user = new User(uid, name, email);
                                firestore.collection("users").document(uid).set(user);
                                goPetCreationActivity(user);

                                Toast.makeText(this, "Sign up successful", Toast.LENGTH_LONG).show();
                            } else
                            {
                                Log.w("SIGN UP", "createUserWithEmail:failure",
                                        task.getException());
                                Toast.makeText(SignUpActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else
        {
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void goSignInActivity(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void goPetCreationActivity(User user)
    {
        Intent intent = new Intent(this, PetCreationActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}