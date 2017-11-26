package com.example.gerar.mrmoto.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gerar.mrmoto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    TextInputEditText editEmail, editNombre, editUsuario, editCelular, editPassword, editConfirmPassword;
    Button btnJoinUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_title_createaccount), true);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = (TextInputEditText) findViewById(R.id.email);
        editPassword = (TextInputEditText) findViewById(R.id.password_createaccount);
        editConfirmPassword = (TextInputEditText) findViewById(R.id.confirmPassword);

        btnJoinUs = (Button) findViewById(R.id.joinUs);

        btnJoinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                /*String nombre = editNombre.getText().toString().trim();
                String usuario = editUsuario.getText().toString().trim();
                String celular = editCelular.getText().toString().trim();*/
                String password = editPassword.getText().toString().trim();
                String confirmPassword = editConfirmPassword.getText().toString().trim();

                if(email.equals("")){
                    Toast.makeText(getApplicationContext(), "Ingrese un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(confirmPassword)){
                    if(password.equals("")){
                        Toast.makeText(getApplicationContext(), "Contraseña invalida", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(CreateAccountActivity.this, "Error al crear cuenta", Toast.LENGTH_SHORT).show();;
                                } else {
                                    startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });


    }


    public void showToolbar(String title, boolean upButton){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
