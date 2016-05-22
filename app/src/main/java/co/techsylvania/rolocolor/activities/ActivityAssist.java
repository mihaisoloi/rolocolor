package co.techsylvania.rolocolor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import co.techsylvania.rolocolor.R;
import co.techsylvania.rolocolor.adapters.AdapterDiseases;

/**
 * Created by tavi on 22/05/16.
 */
public class ActivityAssist extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist);
        RecyclerView rvDiseases = (RecyclerView) findViewById(R.id.rvDiseases);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDiseases.setLayoutManager(llManager);
        AdapterDiseases diseaseAdapter = new AdapterDiseases();
        rvDiseases.setAdapter(diseaseAdapter);

    }
}
