package com.example.ahuja.dietanalyzer;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Nutrition_Object;

import java.text.DecimalFormat;

public class AddMeal extends AppCompatActivity {

    boolean editable;

    //item name
    String citem_name;
    TextView item_name;

    //Date Handlers
    String ctype_of_meal;
    String date[],dstore;

    //qty card
    TextInputEditText quantity;
    TextView measureunit;
    float cquantity,divfactor;


    //Composition Card
    CheckBox addToMyFood;
    TextView cal_count,cal_share,carbs,fats,proteins;
    float ccal_count,ccal_share,ccarbs,cfats,cproteins,csodium;
    TextInputEditText sodium;
    Bundle bundle;

    //Db Save Material
    DbOpenHelper dbOpenHelper;
    Nutrition_Object nutrition_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        dbOpenHelper=new DbOpenHelper(this);

        item_name=findViewById(R.id.add_meal_item_name);

        quantity=findViewById(R.id.add_meal_etext_qty);
        measureunit=findViewById(R.id.add_meal_text_measure);

        addToMyFood=findViewById(R.id.add_meal_radiobutton);
        cal_count=findViewById(R.id.add_meal_text_cal_count);
        cal_share=findViewById(R.id.add_meal_text_cal_share);
        carbs=findViewById(R.id.add_meal_text_carbs);
        fats=findViewById(R.id.add_meal_text_fats);
        sodium=findViewById(R.id.add_meal_text_sodium);
        proteins=findViewById(R.id.add_meal_text_proteins);
        bundle=getIntent().getExtras();
            setItem_name();
            cquantity = 100;
            divfactor=100;
            setqty_card();
            setcomp_card();
            dstore = bundle.getString("cdate_store");
            date = dstore.split("/");
            ctype_of_meal = bundle.getString("ctype_of_meal", "Breakfast");
            divfactor=bundle.getFloat("Quantity",100);



    }

    //Buttons OnClick
    public void inc_qty(View view){
        cquantity=Float.parseFloat(quantity.getText().toString());
        cquantity++;
        setqty_card();
        setcomp_card();
    }
    public void dec_qty(View view){
        cquantity=Float.parseFloat(quantity.getText().toString());
        cquantity--;
        setqty_card();
        setcomp_card();
    }
    public void save_meal(View view){
        csodium=Float.parseFloat(sodium.getText().toString());
        nutrition_object=new Nutrition_Object(date[2],date[1],date[0],ctype_of_meal,citem_name,ccal_count
                                        ,ccarbs,cfats,cproteins,csodium);
        long id=dbOpenHelper.insert_nutrition_data(nutrition_object);
        if(addToMyFood.isChecked())
            dbOpenHelper.insert_Food_data(nutrition_object);
        Intent intent=new Intent(view.getContext(),AddMealOption.class);
        intent.putExtra("ctype_of_meal",ctype_of_meal);
        intent.putExtra("cdate_store",dstore);
        intent.putExtra("cdate_show",bundle.getString("cdate_show"));
        startActivity(intent);
    }
    public void discard_meal(View view){
        cquantity=-1;
        setqty_card();
        setcomp_card();
        Toast.makeText(this,"Record will not be Save",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(view.getContext(),AddMealOption.class);
        intent.putExtra("ctype_of_meal",ctype_of_meal);
        intent.putExtra("cdate_store",dstore);
        intent.putExtra("cdate_show",bundle.getString("cdate_show"));
        startActivity(intent);
    }

    //Data Set Methods
    public void setItem_name(){
        citem_name=bundle.getString("Label");
        item_name.setText(citem_name);
    }
    public void setqty_card(){
        if(cquantity==-1)
           cquantity=100;
        quantity.setText(String.valueOf(cquantity));
    }
    public void setcomp_card(){
        if(bundle.get("Calories")!=null)
          ccal_count=Float.parseFloat(bundle.get("Calories").toString())/divfactor*cquantity;
        else
           ccal_count=0;
        if(bundle.get("Carbs")!=null)
        ccarbs=Float.parseFloat(bundle.get("Carbs").toString())/divfactor*cquantity;
        else
            ccarbs=0;
        if(bundle.get("Fats")!=null)
            cfats= Float.parseFloat(bundle.get("Fats").toString())/divfactor*cquantity;
        else
            cfats=0;
        if(bundle.get("Proteins")!=null)
            cproteins= Float.parseFloat(bundle.get("Proteins").toString())/divfactor*cquantity;
        else
             cproteins=0;
        if(bundle.get("Sodium")!=null)
            csodium=Float.parseFloat(bundle.get("Sodium").toString())/divfactor*cquantity;
        else
        csodium=0;
        ccal_share=(ccal_count)/20;
        DecimalFormat format=new DecimalFormat("0.00");
        cal_count.setText(String.valueOf(format.format(ccal_count)));
        cal_share.setText(String.valueOf(format.format(ccal_share)));
        carbs.setText(String.valueOf(format.format(ccarbs)));
        fats.setText(String.valueOf(format.format(cfats)));
        proteins.setText(String.valueOf(format.format(cproteins)));
        sodium.setText(String.valueOf(csodium));
    }

}
