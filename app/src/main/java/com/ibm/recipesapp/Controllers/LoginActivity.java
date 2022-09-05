package com.ibm.recipesapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ibm.recipesapp.R;


// LoginActivity
public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        emailInput = findViewById(R.id.email_TextInputEditText_LoginActivity);
        passwordInput = findViewById(R.id.password_TextInputEditText_LoginActivity);
    }
    
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        
        if(currentUser != null){
            String email = currentUser.getEmail();
            System.out.println(String.format("Current User - email: %s", email));
            goTaskActivity();
        }
    }

    public void logIn(View v)
    {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        System.out.println(String.format("Log In - Email: %s, Password: %s", email, password));

        if (!email.equals("") && !password.equals(""))
        {
            try
            {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SIGN IN", "signInWithEmail:success");
                                goTaskActivity();
                                Toast.makeText(this, "Successful login. Welcome back", Toast.LENGTH_LONG).show();
                            } else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w("SIGN IN", "signInWithEmail:failure",
                                        task.getException());
                                Toast.makeText(LoginActivity.this,
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

    public void goToSignUpActivity(View view)
    {
        Intent goToSignUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(goToSignUpActivity);
    }

    private void goTaskActivity() {
        Intent goToTasksActivity = new Intent(this, RecipesActivity.class);
        startActivity(goToTasksActivity);
    }

//    public void Test(){
//        User user1 = new User("Bob");
//        User user2 = new User("Sara");
//        User user3 = new User("Harry");
//        firestore.collection("test").document("test1").set(user1);
//        firestore.collection("test").document("test2").set(user2);
//        firestore.collection("test").document("practice").set(user3);
//    }
}