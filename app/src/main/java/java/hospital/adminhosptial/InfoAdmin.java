package java.hospital.adminhosptial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class InfoAdmin extends AppCompatActivity {

    Spinner genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_admin);

        // Rellenar el Spinner
        genero = (Spinner) findViewById(R.id.Genero);

        ArrayAdapter< CharSequence > adapter = ArrayAdapter.createFromResource(this, R.array.genero, R.layout.spinner);
        genero.setAdapter(adapter);

    }// Fin del OnCreate

    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflate = getMenuInflater();
        menuInflate.inflate(R.menu.menu,menu);
        return true;
    }

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