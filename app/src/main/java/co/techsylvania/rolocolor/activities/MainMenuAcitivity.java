package co.techsylvania.rolocolor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import co.techsylvania.rolocolor.R;
import co.techsylvania.rolocolor.adapters.MainMenuAdapter;
import co.techsylvania.rolocolor.model.MainMenuItem;
import jp.epson.moverio.bt200.DisplayControl;
import jp.epson.moverio.bt200.SensorControl;

/**
 * Created by tavi on 22/05/16.
 */
public class MainMenuAcitivity extends ActivityBase {
    boolean is3d = false;
    private SensorControl mSensorControl;
    private DisplayControl mDisplayControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        RecyclerView rvMenu = (RecyclerView) findViewById(R.id.rvMenu);
        final LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenu.setLayoutManager(llManager);
        ArrayList<MainMenuItem> items = new ArrayList<>();
        MainMenuItem i1 = new MainMenuItem(MainMenuItem.ItemTypeColorCorrection, "Color blindness correction", R.drawable.ic_launcher);
//        MainMenuItem i2 = new MainMenuItem(MainMenuItem.ItemTypeWorldEnhance, "World helper", R.drawable.ic_launcher);
        MainMenuItem i3 = new MainMenuItem(MainMenuItem.ItemTypeSettings, "Settings", R.drawable.ic_launcher);
        items.add(i1);
//        items.add(i2);
        items.add(i3);

        MainMenuAdapter adapter = new MainMenuAdapter(items);
        rvMenu.setAdapter(adapter);
    }

    private void toggle(boolean is3d) {

    }

    public AppCompatActivity getActivity() {
        return this;
    }
}
