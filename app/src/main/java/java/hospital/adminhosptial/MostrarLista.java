package java.hospital.adminhosptial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarLista extends AppCompatActivity {

    RecyclerView recyclerViewLista;
    listapacientes mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth fAuth;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_lista);

        recyclerViewLista = findViewById(R.id.recyclerLista);
        recyclerViewLista.setLayoutManager(new LinearLayoutManager(this));
        mFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        Query query = mFirestore.collection("Administrador").document(userID).collection("Pacientes");

        FirestoreRecyclerOptions<contenido_lista> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<contenido_lista>().setQuery(query, contenido_lista.class).build();

        mAdapter = new listapacientes( firestoreRecyclerOptions, this );
        mAdapter.notifyDataSetChanged();
        recyclerViewLista.setAdapter(mAdapter);

    }//Fin del onCreate

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
        startActivity(med);
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


}//Fin del public class