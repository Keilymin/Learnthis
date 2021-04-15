package burlakov.learnthis.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import burlakov.learnthis.R;
import burlakov.learnthis.contracts.CreateMaterialManager;
import burlakov.learnthis.models.Material;

/**
 * Адаптер для материалов
 */
public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {
    private Context context;
    private List<Material> materials;
    private CreateMaterialManager.View view;

    public MaterialAdapter(Context context, List<Material> materials, CreateMaterialManager.View view) {
        this.context = context;
        this.materials = materials;
        this.view = view;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.material_item, parent, false);
        return new MaterialAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Material material = materials.get(position);

        holder.materialName.setText(material.getFileName());

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            MenuItem Download = menu.add(Menu.NONE, 1, 1, context.getResources().getString(R.string.download));
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, context.getResources().getString(R.string.delete));
            Download.setOnMenuItemClickListener(item -> {
                //todo
                return true;
            });
            Delete.setOnMenuItemClickListener(item -> {
               //todo if уже создан
                materials.remove(material);
                view.setIntoAdapter(materials);
                return true;
            });
        });
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView materialName;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            materialName = itemView.findViewById(R.id.materialName);
        }
    }
}
