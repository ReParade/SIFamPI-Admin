package java.hospital.adminhosptial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarListaDos extends AppCompatActivity {

    Bundle datos;

    RecyclerView recyclerViewLista;
    listapacientesDos mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth fAuth;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_lista_dos);

        recyclerViewLista = findViewById(R.id.recyclerLista);
        recyclerViewLista.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        datos = getIntent().getExtras();
        String base = datos.getString("pacientesid"); //JnyEXKdtn6SGmkBJOykjJA6HZKn1 id
        System.out.println(base);

        Query query = mFirestore.collection("Administrador").document(userID).collection("Pacientes").document(base).collection("Internados");

        FirestoreRecyclerOptions<contenido_lista_dos> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<contenido_lista_dos>().setQuery(query, contenido_lista_dos.class).build();

        mAdapter = new listapacientesDos( firestoreRecyclerOptions, this );
        mAdapter.notifyDataSetChanged();
        recyclerViewLista.setAdapter(mAdapter);

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public void boton(View view){
        Intent med = new Intent(this, InfoAdmin.class);
        datos = getIntent().getExtras();
        String base2 = datos.getString("pacientesid"); //JnyEXKdtn6SGmkBJOykjJA6HZKn1 id
        med.putExtra("base3", base2);
        System.out.println(base2);
        startActivity(med);
    }

}//public class