package co.techsylvania.rolocolor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import co.techsylvania.rolocolor.R;

/**
 * Created by tavi on 22/05/16.
 */
public class ColorEnhancementActivity extends ActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_b);
        View v = LayoutInflater.from(this).inflate(R.layout.layout_diseases, (ViewGroup) findViewById(R.id.rlParent), false);
        ((RelativeLayout) findViewById(R.id.rlParent)).addView(v);

        //Protanopia
        v.findViewById(R.id.item11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Protanomaly
        v.findViewById(R.id.item12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Deuteranopia
        v.findViewById(R.id.item13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Deuteranomaly
        v.findViewById(R.id.item21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Tritanopia
        v.findViewById(R.id.item22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Tritanomaly
        v.findViewById(R.id.item23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Achromatopsia
        v.findViewById(R.id.item31).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Achromatomaly
        v.findViewById(R.id.item32).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Normal RGB
        v.findViewById(R.id.item33).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
