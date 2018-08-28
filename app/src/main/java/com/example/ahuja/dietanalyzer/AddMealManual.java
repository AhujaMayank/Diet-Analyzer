package com.example.ahuja.dietanalyzer;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Nutrition_Object;

public class AddMealManual extends AppCompatActivity {
    Bundle bundle;
    DbOpenHelper dbOpenHelper;
    String cdate_store,ctype_of_meal,cdate_show;
    String[] cdate;
    String citem_name;
    float ccal,ccarbs,cfats,cproteins,csodium;
    TextInputEditText item_name,cal,carbs,fats,proteins,sodium;
    AppCompatTextView type_of_meal;
    CheckBox addtoFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_manual);
        item_name=findViewById(R.id.add_meal_manual_item_name);
        cal=findViewById(R.id.add_meal_manual_cal);
        carbs=findViewById(R.id.add_meal_manual_carbs);
        fats=findViewById(R.id.add_meal_manual_fats);
        proteins=findViewById(R.id.add_meal_manual_proteins);
        sodium=findViewById(R.id.add_meal_manual_sodium);
        addtoFood=findViewById(R.id.add_meal_manual_radiobutton);
        type_of_meal=findViewById(R.id.add_meal_meal_type);
        dbOpenHelper=new DbOpenHelper(this);
        bundle=getIntent().getExtras();
        cdate_store=bundle.getString("cdate_store",new DateHandler().getDate_store());
        cdate_show=bundle.getString("cdate_show",new DateHandler().getDate_show());
        cdate=cdate_store.split("/");
        ctype_of_meal=bundle.getString("ctype_of_meal");
        setData();
    }

    public void Save_Meal(View view){
        getData();
        Nutrition_Object nutrition_object=new Nutrition_Object(cdate[2],cdate[1],cdate[0],ctype_of_meal,citem_name
                ,ccal,ccarbs,cfats,cproteins,csodium);
        dbOpenHelper.insert_nutrition_data(nutrition_object);

        if(addtoFood.isChecked())
              dbOpenHelper.insert_Food_data(nutrition_object);

        Intent intent=new Intent(this,TodaySummaryActivity.class);
        intent.putExtra("ctype_of_meal",ctype_of_meal);
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        startActivity(intent);
 }
    public void Discard_Meal(View view){
        Intent intent=new Intent(view.getContext(),TodaySummaryActivity.class);
        intent.putExtra("cdate_store",cdate_store);
        intent.putExtra("cdate_show",cdate_show);
        startActivity(intent);
    }

    public void setData(){
        ccal=0;
        ccarbs=0;
        cfats=0;
        cproteins=0;
        csodium=0;
        type_of_meal.setText(ctype_of_meal);

    }
    public void getData(){
        citem_name=item_name.getText().toString();
        ccal=Float.parseFloat(cal.getText().toString());
        ccarbs=Float.parseFloat(carbs.getText().toString());
        cfats=Float.parseFloat(fats.getText().toString());
        cproteins=Float.parseFloat(proteins.getText().toString());
        csodium=Float.parseFloat(sodium.getText().toString());
    }
}
