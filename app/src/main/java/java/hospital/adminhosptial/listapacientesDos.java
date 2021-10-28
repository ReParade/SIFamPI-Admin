package java.hospital.adminhosptial;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class listapacientesDos extends FirestoreRecyclerAdapter<contenido_lista_dos, listapacientesDos.ViewHolder> {

    private Activity activity;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth fAuth;

    Bundle datos;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public listapacientesDos(@NonNull FirestoreRecyclerOptions<contenido_lista_dos> options, Activity activity) {
        super(options);
        this.activity = activity;
        mFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
    }

    //Se establecen los valores que tendra la lista
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull contenido_lista_dos articulo) {
        DocumentSnapshot PacienteListas = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = PacienteListas.getId();

        holder.txtNombre.setText(articulo.getNombre());
        holder.txtID.setText(articulo.getID());
        holder.txtEstado.setText(articulo.getEstado());

        //Ver Paciente
        holder.buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //////////////////////////////////////////////////
                AlertDialog.Builder ventana = new AlertDialog.Builder(activity);
                ventana.setMessage("Ingrese el ID del Usuario");
                ventana.setTitle(("ID del Usuario"));
                final EditText ET_id = new EditText(activity);
                ventana.setView(ET_id);

                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idUsuario = ET_id.getText().toString().trim();

                        //Esto pasa el id de los internados
                        Intent intent = new Intent(activity, ModInfo.class);
                        intent.putExtra("pacientesid2", id);
                        intent.putExtra("pacientes", idUsuario);
                        System.out.println(id);

                        activity.startActivity(intent);

                    }
                });

                ventana.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity, "Cancelado", Toast.LENGTH_SHORT);
                    }
                });
                ventana.show();
//////////////////////////////////////////////

            }
        });

    }

    //Metodo que creara cada una de las vistas de la lista
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listapacientesdos, parent, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre;
        TextView txtID;
        TextView txtEstado;

        Button buttonEditar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtID = itemView.findViewById(R.id.txtID);
            txtEstado = itemView.findViewById(R.id.txtEstado);


            buttonEditar = itemView.findViewById(R.id.btnEditar);

        }

    }//Fin  public class ViewHolder

}//Fin public class listapacientesDos
