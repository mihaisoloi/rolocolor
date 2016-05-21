package co.techsylvania.rolocolor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

import co.techsylvania.rolocolor.R;
import co.techsylvania.rolocolor.adapters.MainMenuAdapter;
import co.techsylvania.rolocolor.model.MainMenuItem;

/**
 * Created by tavi on 22/05/16.
 */
public class MainMenuAcitivity extends AppCompatActivity {
    boolean is3d = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button btn3d = (Button) findViewById(R.id.btn3d);
        btn3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is3d = !is3d;
                if (is3d) {

                }
                else {

                }
            }
        });

        RecyclerView rvMenu = (RecyclerView) findViewById(R.id.rvMenu);
        final LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenu.setLayoutManager(llManager);
        ArrayList<MainMenuItem> items = new ArrayList<>();
        MainMenuItem i1 = new MainMenuItem(MainMenuItem.ItemTypeColorCorrection, "Color blindness correction", R.drawable.ic_launcher);
        MainMenuItem i2 = new MainMenuItem(MainMenuItem.ItemTypeWorldEnhance, "World helper", R.drawable.ic_launcher);
        items.add(i1);
        items.add(i2);

        MainMenuAdapter adapter = new MainMenuAdapter(items);
        rvMenu.setAdapter(adapter);

    }

    public AppCompatActivity getActivity() {
        return this;
    }
}
