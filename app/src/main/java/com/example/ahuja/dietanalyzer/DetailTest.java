package com.example.ahuja.dietanalyzer;

import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DataShowFrag;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.Recycler_View_Fragment;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;

public class DetailTest extends AppCompatActivity {
    DataShowFrag Rv_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_test);

        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        Rv_frag=(DataShowFrag) fragmentManager.findFragmentById(R.id.fragment_detail_test);
        Rv_frag.setContext(this);
        Rv_frag.setCurr_date(new DateHandler().getDate_store());
        Rv_frag.setCtype_of_meal("Breakfast");
        Rv_frag.setCdate_show(new DateHandler().getDate_show());
        Rv_frag.setPath("DetailTest");
        Rv_frag.setView(findViewById(R.id.detail_test_coordinate));
    }
}
