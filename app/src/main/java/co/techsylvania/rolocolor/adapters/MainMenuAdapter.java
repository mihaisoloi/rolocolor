package co.techsylvania.rolocolor.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.techsylvania.rolocolor.R;
import co.techsylvania.rolocolor.activities.ColorBlindnessCorrectionActivity;
import co.techsylvania.rolocolor.model.MainMenuItem;

/**
 * Created by tavi on 22/05/16.
 */
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {
    public static final String tag = MainMenuAdapter.class.getSimpleName();
    private ArrayList<MainMenuItem> items;

    public MainMenuAdapter(ArrayList<MainMenuItem> allItems) {
        items = (allItems != null ? allItems : new ArrayList<MainMenuItem>());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_menu, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MainMenuItem item = items.get(position);
        if (item != null) {
            holder.tvItemName.setText(item.getItemName());
            holder.ivItem.setImageResource(item.getItemDrawable());
            switch (item.getItemType()) {
                case MainMenuItem.ItemTypeColorCorrection:
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent activityColorCorrection = new Intent(holder.itemView.getContext().getApplicationContext(), ColorBlindnessCorrectionActivity.class);
                            activityColorCorrection.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            holder.itemView.getContext().getApplicationContext().startActivity(activityColorCorrection);
                        }
                    });
                    break;
                case MainMenuItem.ItemTypeWorldEnhance:
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent activityColorCorrection = new Intent(holder.itemView.getContext().getApplicationContext(), ColorBlindnessCorrectionActivity.class);
                            activityColorCorrection.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            holder.itemView.getContext().getApplicationContext().startActivity(activityColorCorrection);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private ImageView ivItem;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
            this.ivItem = (ImageView) itemView.findViewById(R.id.ivItem);
        }
    }
}
