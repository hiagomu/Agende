package com.example.loginactivity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private ImageView fotoCadastroImgView;
    private EditText nomeCadastroEditTxt;
    private EditText emailCadastroEditTxt;
    private EditText senhaCadastroEditTxt;
    private EditText confSenhaCadastroEditTxt;
    private Button cadastraCadastroBtn;
    private FirebaseAuth mAuth;
    private static final String TAG = "CreateNewUserActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        mAuth = FirebaseAuth.getInstance();

        carregaViews();
        criaUsuario();
    }

    private void carregaViews() {
        fotoCadastroImgView = findViewById(R.id.fotoCadastroImgView);
        nomeCadastroEditTxt = findViewById(R.id.nomeCadastroEditTxt);
        emailCadastroEditTxt = findViewById(R.id.emailCadastroEditTxt);
        senhaCadastroEditTxt = findViewById(R.id.senhaCadastroEditTxt);
        confSenhaCadastroEditTxt = findViewById(R.id.confSenhaCadastroEditTxt);
        cadastraCadastroBtn = findViewById(R.id.cadastraCadastroBtn);
    }

    private void criaUsuario() {
        cadastraCadastroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailCadastroEditTxt.getText().toString();
                String password = senhaCadastroEditTxt.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
            private void updateUI (FirebaseUser user){
                if (user != null) {
                    Intent intent = new Intent(CadastroActivity.this, NavActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}