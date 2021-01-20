package com.example.audientesprojekt.presetlogik;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.audientesprojekt.R;
import com.google.gson.Gson;
import java.util.ArrayList;

public class PresetRecAdapter extends RecyclerView.Adapter<PresetRecAdapter.ViewHolder> {

    private ArrayList<SoundInput> soundInputs;
    private Context context;


    public PresetRecAdapter(Context context, ArrayList<SoundInput> presets) {
        this.context = context;
        this.soundInputs = presets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_recyclerlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SoundInput preset = soundInputs.get(position);
        holder.audioName.setText(preset.getSoundName());
        holder.audioDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundInputs.remove(preset);
                Gson gson = new Gson();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                preferences.edit().remove("sounds").apply();
                preferences.edit().putString("sounds", gson.toJson(soundInputs)).apply();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return soundInputs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView audioImage;
        private TextView audioName;
        private ImageView audioDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            audioImage = itemView.findViewById(R.id.audioImage);
            audioName = itemView.findViewById(R.id.audioName);
            audioDelete = itemView.findViewById(R.id.audioDelete);
        }
    }
}
