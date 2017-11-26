package com.example.gerar.mrmoto.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.gerar.mrmoto.R;
import com.example.gerar.mrmoto.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class FinishSignupFormActivity extends AppCompatActivity {

    //referencia de la bd de firebase
    private DatabaseReference myRef;

    //variables de uso
    private String email, password, uID;

    //elementos ui
    private TextInputEditText editNombre, editCelular, editFechaNac, editDireccion;
    private Button btnGuardar, btnFechaNac;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_signup_form);
        showToolbar("Finalizar registro", true);

        //Recuperamos datos del activity
        Bundle bundle = this.getIntent().getExtras();
        email = bundle.getString("email");
        password = bundle.getString("password");
        uID = bundle.getString("uID");

        //obtenemos una referencia de la base de datos
        myRef = FirebaseDatabase.getInstance().getReference();

        //referencia a elementos de la ui
        editNombre = (TextInputEditText) findViewById(R.id.name);
        editCelular = (TextInputEditText) findViewById(R.id.cellphone);
        editFechaNac = (TextInputEditText) findViewById(R.id.fechaNac);
        editFechaNac.setEnabled(false);
        editDireccion = (TextInputEditText) findViewById(R.id.address);
        btnGuardar = (Button) findViewById(R.id.btnSave);
        btnFechaNac = (Button) findViewById(R.id.btnFechaNac);

        //escuchador eventos del boton guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombre.getText().toString();
                String celular = editCelular.getText().toString();
                String fechaNacimiento = editFechaNac.getText().toString();
                String direccion = editDireccion.getText().toString();

                if(nombre.equals("")){
                    Toast.makeText(getApplicationContext(), "Es necesario escribir un nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(celular.equals("")){
                    Toast.makeText(getApplicationContext(), "Numero de celular necesario", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fechaNacimiento.equals("")){
                    Toast.makeText(getApplicationContext(), "Es necesario seleccionar tu fecha de nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(direccion.equals("")){
                    Toast.makeText(getApplicationContext(), "Es necesario escribir tu dirección", Toast.LENGTH_SHORT).show();
                    return;
                }

                //escribimos los datos del usuario en la base de datos
                writeNewUser(uID, email, password, nombre, celular, fechaNacimiento, direccion);
                goHome();

            }
        });

        //escuhador de eventos para el boton de fecha
        btnFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment timePickerFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editFechaNac.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                });

                timePickerFragment.show(getSupportFragmentManager(), "FinishSignupFormActivity");
            }
        });



    }

    private void writeNewUser(String userId, String email, String contrasena, String nombre, String celular, String fechaNacimiento, String direccion) {
        Usuario usuario = new Usuario(email, contrasena, nombre, celular, fechaNacimiento, direccion, "cliente");
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("usuarios").child(userId).setValue(usuario);
    }

    public void goHome(){
        startActivity(new Intent(FinishSignupFormActivity.this, HomeActivity.class));
        finish();
    }


    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }

    public void showToolbar(String title, boolean upButton){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
