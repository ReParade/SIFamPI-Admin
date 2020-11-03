package java.hospital.adminhosptial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText Ecorreo, Econtra;
    Button Mbutton;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ecorreo = findViewById(R.id.correo);
        Econtra = findViewById(R.id.contra);
        progressBar = findViewById(R.id.progressBar);
        Mbutton = findViewById(R.id.button);
        fAuth = FirebaseAuth.getInstance();

        //Darle la funcionalidad al boton para que incie sesion
        Mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Tomar los valores de los EditText y guardar en variables
                String email = Ecorreo.getText().toString().trim();
                String password = Econtra.getText().toString().trim();

                //Validar que los EditText este llenos o mandar un error
                if (TextUtils.isEmpty(email)) {
                    Ecorreo.setError("Correo Requerido");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Ecorreo.setError("Contraseña Requerida");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Verificar usuario
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful() ){
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), InfoAdmin.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Contraseña o correo incorrecto", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }//OnCreate


    //No cerrar la sesion
    @Override
    protected void onStart() {
        super.onStart();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), InfoAdmin.class));
            finish();
        }

    }


}//Main