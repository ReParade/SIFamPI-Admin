package java.hospital.adminhosptial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class listapacientes extends FirestoreRecyclerAdapter<contenido_lista, listapacientes.ViewHolder> {

    private Activity activity;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth fAuth;
    String userID;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public listapacientes(@NonNull FirestoreRecyclerOptions<contenido_lista> options,  Activity activity) {
        super(options);
        this.activity = activity;
        mFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
    }

    //Se establecen los valores que tendra la lista
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull contenido_lista articulo) {
        DocumentSnapshot PacienteListas = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = PacienteListas.getId();

        holder.txtNombre.setText(articulo.getNombre());
        holder.txtID.setText(articulo.getID());

        //Ver Paciente
        holder.buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Esto pasa el id del usuario a MostrarListaDos "JnyEXKdtn6SGmkBJOykjJA6HZKn1"
                Intent intent = new Intent(activity, MostrarListaDos.class);
                intent.putExtra("pacientesid", id);

                activity.startActivity(intent);
            }
        });

        holder.buttunCopiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ID", id);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(activity, "ID copiado", Toast.LENGTH_SHORT).show();
            }
        });

     /*   //Eliminar
        holder.buttunBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("ELIMINAR");
                builder.setMessage("¿Desea eliminar a esta persona?");

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        userID = fAuth.getCurrentUser().getUid();
                        mFirestore.collection("Administrador").document(userID).collection("Pacientes").document(id).delete();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cerrar el arlet dialog
                    }
                });

                builder.create().show();
            }
        }); */

    }

    //Metodo que creara cada una de las vistas de la lista
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pacientes, parent, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre;
        TextView txtID;

        Button buttonVer;
        Button buttunCopiar;
        Button buttunBorrar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtID = itemView.findViewById(R.id.txtID);
            txtNombre = itemView.findViewById(R.id.txtNombre);

            buttonVer = itemView.findViewById(R.id.btnVer);
            buttunCopiar = itemView.findViewById(R.id.btnCopiar);
            /*buttunBorrar = itemView.findViewById(R.id.btnEiminar);*/

        }

    }//Fin  public class ViewHolder

}//Fin public class listapacientes
