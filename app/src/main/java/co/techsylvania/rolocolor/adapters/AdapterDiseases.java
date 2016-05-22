package co.techsylvania.rolocolor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.techsylvania.rolocolor.R;

/**
 * Created by tavi on 22/05/16.
 */
public class AdapterDiseases extends RecyclerView.Adapter<AdapterDiseases.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_diseaase, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDisease;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
