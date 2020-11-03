package java.hospital.adminhosptial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InfoAdmin extends AppCompatActivity {

    Spinner genero;

    //Variables de fecha
    private int ano;
    private int mes;
    private int dia;
    TextView Fechaingreso;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;

    EditText NumRegistro;
    EditText NomPaciente;
    EditText edad;
    EditText alergias;
    EditText NomDoc;
    EditText especial;
    EditText NoPiso;
    EditText NoCama;
    EditText estado;
    EditText aviso;
    Button btnActualizar;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    private static final String TAG = "Registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_admin);

        //Fechas
        Fechaingreso = findViewById(R.id.Ingreso);
        Calendar calendario = Calendar.getInstance();
        ano = calendario.get(Calendar.YEAR);
        mes = calendario.get(Calendar.MONTH);
        dia = calendario.get(Calendar.DAY_OF_MONTH);

        //Campos a rellenar
        NumRegistro = findViewById(R.id.ID);
        NomPaciente = findViewById(R.id.Paciente);
        edad = findViewById(R.id.Edad);
        alergias = findViewById(R.id.Alergias);
        NomDoc = findViewById(R.id.Doctor);
        especial = findViewById(R.id.Especialidad);
        NoPiso = findViewById(R.id.Piso);
        NoCama = findViewById(R.id.Cama);
        estado = findViewById(R.id.Estado);
        aviso = findViewById(R.id.Avisos);
        btnActualizar = findViewById(R.id.actualizar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Seleccionar la fecha del boton
        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ano = year;
                mes = month;
                dia = dayOfMonth;
                Fechaingreso.setText(dia + "/" + (mes + 1) + "/" + ano);
            }
        };

        // Rellenar el Spinner
        genero = (Spinner) findViewById(R.id.Genero);

        ArrayAdapter< CharSequence > adapter = ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner);
        genero.setAdapter(adapter);


        //Ingresar los datos a firebase
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = NumRegistro.getText().toString();
                final String paciente = NomPaciente.getText().toString();
                final String Edad = edad.getText().toString();
                final String alergia = alergias.getText().toString();
                final String doctor = NomDoc.getText().toString();
                final String especialidad = especial.getText().toString();
                final String piso = NoPiso.getText().toString();
                final int cama = NoCama.getInputType();
                final String Estado = estado.getText().toString();
                final String Aviso = aviso.getText().toString();
                final String fechaingreso = Fechaingreso.getText().toString();
                final String selec = genero.getSelectedItem().toString();

                userID = fAuth.getCurrentUser().getUid();
                //Registrar en el apartado del doctor
                DocumentReference documentReference = fStore.collection("Administrador").document(userID).collection("Pacientes").document(id);
                //Registrar en el apartado del paciente
                DocumentReference documentReference2 = fStore.collection("Pacientes").document(id);
                Map<String,Object> user = new HashMap<>();

                user.put("id", id);
                user.put("Nombre", paciente);
                user.put("Edad", Edad);
                user.put("Alergias", alergia);
                user.put("Doctor", doctor);
                user.put("Especialidad", especialidad);
                user.put("Piso", piso);
                user.put("Cama", cama);
                user.put("Estado", Estado);
                user.put("Aviso", Aviso);
                user.put("Ingreso", fechaingreso);
                user.put("Genero", selec);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoAdmin.this, "Registrado con Exito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                Toast.makeText(InfoAdmin.this, "Registrado con Exito", Toast.LENGTH_SHORT).show();

                documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoAdmin.this, "Registrado con Exito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                Toast.makeText(InfoAdmin.this, "Registrado con Exito", Toast.LENGTH_SHORT).show();
            }
        });


    }// Fin del OnCreate


    //Crear el calendario
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                return new DatePickerDialog(this, oyenteSelectorFecha, ano, mes, dia);
        }
        return null;
    }

    public void mostrarCalendario(View control){
        showDialog(TIPO_DIALOGO);
    }



    //Metodo para cerrar sesion
    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    //Crear el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflate = getMenuInflater();
        menuInflate.inflate(R.menu.menu,menu);
        return true;
    }
    //Darle funcionalidad al menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.cerrar:
                logout();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }


}//InfoAdmin