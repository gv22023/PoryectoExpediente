package com.example.proyectoexpediente;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder> {

    private List<Materia> listaMaterias;

    public MateriaAdapter(List<Materia> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_materia, parent, false);
        return new MateriaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        Materia materia = listaMaterias.get(position);
        holder.tvNombre.setText("Nombre: " + materia.getNombre());
        holder.tvCodigo.setText("Código: " + materia.getCodigo());
        holder.tvUV.setText("UV: " + materia.getUnidadesValorativas());
    }

    @Override
    public int getItemCount() {
        return listaMaterias.size();
    }

    public static class MateriaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCodigo, tvUV;

        public MateriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreMateria);
            tvCodigo = itemView.findViewById(R.id.tvCodigoMateria);
            tvUV = itemView.findViewById(R.id.tvUVMateria);
        }
    }
}
