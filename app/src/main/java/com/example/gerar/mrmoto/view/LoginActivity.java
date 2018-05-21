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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    //elementos para la autenticacion en firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    //elementos de la ui
    TextInputEditText editEmail, editPassword;
    Button btnLogin;

    //referencia de la bd  de firebase para verificacion del usuario que se va a loguear
    DatabaseReference myRef;

    //variable de uso
    private String email, password, uID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = (TextInputEditText) findViewById(R.id.username);
        editPassword = (TextInputEditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();

                if(email.equals("")){
                    Toast.makeText(getApplicationContext(), "Proporcione un correo válido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(getApplicationContext(), "Proporcione una contraseña válida", Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Error al iniciar sesión.", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    //recuperamos la sesion del usuario y su id
                                    user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        //recuperamos el id del usuario
                                        uID = user.getUid();
                                        signIn(email, password, uID);
                                    }

                                }
                            }
                        });
            }
        });
    }

    public void signIn(String email, String password, String uID){
        //obtenemos una referencia de la base de datos
        myRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child(uID).child("email");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String correo = String.valueOf(dataSnapshot.getValue());
                if(correo != "null"){
                    finish();
                    goHome();
                } else {
                    finish();
                    goFinishSignupFormAccount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void goHome(){
        startActivity(new Intent(LoginActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT));
        finish();
    }

    public void goFinishSignupFormAccount(){
        Intent intent = new Intent(LoginActivity.this, FinishSignupFormActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("uID", uID);
        startActivity(intent);
    }

    public void goCreateAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }


}

