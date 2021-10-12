package java.hospital.adminhosptial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ModInfo extends AppCompatActivity {

    Bundle datos;
    Bundle datos2;

    //Variables de fecha
    private int ano;
    private int mes;
    private int dia;
    TextView Fechaingreso;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;

    TextView NumRegistro;
    EditText NomPaciente;
    EditText edad;
    EditText alergias;
    EditText NomDoc;
    EditText especial;
    EditText NoPiso;
    EditText NoCama;
    EditText estado;
    EditText aviso;
    TextView genero;
    Button btnActualizar;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    private static final String TAG = "Registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_info);


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
        genero = findViewById(R.id.Genero2);
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

        obtenerdatos();


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
                final String cama = NoCama.getText().toString();
                final String Estado = estado.getText().toString();
                final String Aviso = aviso.getText().toString();
                final String fechaingreso = Fechaingreso.getText().toString();
                final String selec = genero.getText().toString();

                //Validar que los EditText este llenos o mandar un error
                if (TextUtils.isEmpty(id)) {
                    NumRegistro.setError("Numero de Registro Requerido");
                    return;
                }
                if (TextUtils.isEmpty(paciente)) {
                    NomPaciente.setError("Nombre Requerido");
                    return;
                }
                if (selec.equals("Eliga una opcion")){
                    Toast.makeText(ModInfo.this, "Elija una Opción (Hombre o Mujer)", Toast.LENGTH_SHORT).show();
                    return;
                }

                datos = getIntent().getExtras();
                String base = datos.getString("pacientesid2");

                datos2 = getIntent().getExtras();
                String IdUsuario = datos2.getString("pacientes");

                userID = fAuth.getCurrentUser().getUid();
                //Registrar en el apartado del doctor
                DocumentReference documentReference = fStore.collection("Administrador").document(userID).collection("Pacientes").document(IdUsuario).
                        collection("Internados").document(base);
                //Registrar en el apartado del paciente
                DocumentReference documentReference2 = fStore.collection("Pacientes").document(IdUsuario).collection("Internados").document(base);
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
                        Toast.makeText(ModInfo.this, "Información Actualizada con Exito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                Toast.makeText(ModInfo.this, "Información Actualizada con Exito", Toast.LENGTH_SHORT).show();

                documentReference2.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ModInfo.this, "Información Actualizada con Exito", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                });
                Toast.makeText(ModInfo.this, "Información Actualizada con Exito", Toast.LENGTH_SHORT).show();
            }
        });

    }//Fin del onCreate


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


    private void obtenerdatos(){

        datos = getIntent().getExtras();
        String base = datos.getString("pacientesid2");

        datos2 = getIntent().getExtras();
        String IdUsuario = datos2.getString("pacientes");

        userID = fAuth.getCurrentUser().getUid();
        fStore.collection("Administrador").document(userID).collection("Pacientes").document(IdUsuario).
                collection("Internados").document(base).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 String id = documentSnapshot.getString("id");
                 String paciente = documentSnapshot.getString("Nombre");
                 String Edad = documentSnapshot.getString("Edad");
                 String alergia = documentSnapshot.getString("Alergias");
                 String doctor = documentSnapshot.getString("Doctor");
                 String especialidad = documentSnapshot.getString("Especialidad");
                 String piso = documentSnapshot.getString("Piso");
                 String cama = documentSnapshot.getString("Cama");
                 String Estado = documentSnapshot.getString("Estado");
                 String Aviso = documentSnapshot.getString("Aviso");
                 String fechaingreso = documentSnapshot.getString("Ingreso");
                 String select = documentSnapshot.getString("Genero");

                NumRegistro.setText(id);
                NomPaciente.setText(paciente);
                edad.setText(Edad);
                genero.setText(select);
                alergias.setText(alergia);
                NomDoc.setText(doctor);
                especial.setText(especialidad);
                NoPiso.setText(piso);
                NoCama.setText(cama);
                estado.setText(Estado);
                aviso.setText(Aviso);
                Fechaingreso.setText(fechaingreso);

            }
        });

    }//Fin del obtenerdatos

  /*  private void Dialogo(){

        //////////////////////////////////////////////////
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana.setMessage("Ingrese el ID del Administrador");
        ventana.setTitle(("ID del Administrador"));
        ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ModInfo.this, "Ahi va xd", Toast.LENGTH_LONG);
            }
        });

        ventana.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ModInfo.this, "Cancelado", Toast.LENGTH_SHORT);
                finish();
            }
        });
        ventana.show();
//////////////////////////////////////////////

    } */



}//Fin del class ModInfo