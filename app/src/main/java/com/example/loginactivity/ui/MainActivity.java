package com.example.loginactivity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText emailLoginEdit;
    private EditText passLoginEdit;
    private Button buttonLogin;
    private TextView esqueceuLoginTxt;
    private TextView cadastroLoginTxt;
    private FirebaseAuth mAuth;
    private static final String TAG = "Erro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        carregaCampos();
        goToCadastro();


    }

    private void goToCadastro() {
        cadastroLoginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateUI (FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, NavActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailLoginEdit.getText().toString();
                String password = passLoginEdit.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }

        });
    }

    private void carregaCampos() {
        emailLoginEdit = findViewById(R.id.emailLoginEditTxt);
        passLoginEdit = findViewById(R.id.passLoginEditTxt);
        buttonLogin = findViewById(R.id.loginLoginButton);
        esqueceuLoginTxt = findViewById(R.id.esqueceLoginTxtView);
        cadastroLoginTxt = findViewById(R.id.cadastroLoginTxtView);
    }

}