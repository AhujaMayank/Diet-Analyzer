package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahuja.dietanalyzer.AddMeal;
import com.example.ahuja.dietanalyzer.Connect_API.Model.FoodJson;
import com.example.ahuja.dietanalyzer.Connect_API.Model.MainJson;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;
import com.example.ahuja.dietanalyzer.Database.Nutrition_Object;
import com.example.ahuja.dietanalyzer.R;

public class DataShowFrag extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView norecord;
    RvAdapterShowData rv_adapter;
    LinearLayoutManager linearLayoutManager;

    DbOpenHelper dbOpenHelper;
    String curr_date;
    String ctype_of_meal;
    String cdate_show;
    Cursor cursor;
    String path;
    Context context;
    String cdate[];
    Nutrition_Object nutrition_object;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setCurr_date(String curr_date) {
        this.curr_date = curr_date;
    }

    public void setCdate_show(String cdate_show) {
        this.cdate_show = cdate_show;
    }

    public void setCtype_of_meal(String ctype_of_meal) {
        this.ctype_of_meal = ctype_of_meal;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setCursor() {
       cursor=null;
        if(path.equals("AddMealOption")){
        cdate=curr_date.split("/");
        cursor=dbOpenHelper.Fetch_DatabaseMeal(cdate[2],cdate[1],cdate[0],ctype_of_meal,Contract.Dbnutrition.TableName);
       }
       if(path.equals("MyFoodAddSearch"))
             cursor=dbOpenHelper.Fetch_Database(Contract.DbFood.TableName);
       if(path.equals("DetailTest"))
            cursor=dbOpenHelper.Fetch_Database(Contract.Dbnutrition.TableName);
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper=new DbOpenHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     //   dbOpenHelper=new DbOpenHelper(context);
        return inflater.inflate(R.layout.data_show_frag_layout,container,false);
    }

    @Override
    public void onResume() {
        recyclerView=getView().findViewById(R.id.rv_data_show_frag_layout);
        norecord=getView().findViewById(R.id.tv_data_show_frag_no_record);

        setData();
        setlistener();
        swipeManager();
        super.onResume();
    }
    public void setData(){
        setCursor();
        cursor.moveToFirst();
        if(cursor.isAfterLast()){
            recyclerView.setVisibility(View.INVISIBLE);
            norecord.setVisibility(View.VISIBLE);
            norecord.setText("No Record logged! Do it now.");
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            norecord.setVisibility(View.INVISIBLE);
        }
            rv_adapter = new RvAdapterShowData(context);
            linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            rv_adapter.setCursor(cursor);
            recyclerView.setAdapter(rv_adapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

    }

    public boolean Cursor_Changed(Cursor ncursor){
        cursor=ncursor;
        return rv_adapter.SwapCursor(cursor);
    }
    public void setlistener(){
        cdate=curr_date.split("/");
        rv_adapter.setListItemClickListener(new RvAdapterShowData.ShowDataListItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view, Cursor cursor) {
                cursor.moveToPosition(position);
                Intent intent=new Intent(view.getContext(), AddMeal.class);
                if(path.equals("MyFoodAddSearch"))
                    intent.putExtra("Editable",true);
                else
                    intent.putExtra("Editable",false);
                intent.putExtra("cdate_store",curr_date);
                intent.putExtra("cdate_show",cdate_show);
                intent.putExtra("ctype_of_meal",ctype_of_meal);
                intent.putExtra("Label",cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Desc)));
                intent.putExtra("Calories",String.valueOf(cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie))));
                intent.putExtra("Carbs",String.valueOf(cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Carbs))));
                intent.putExtra("Proteins",String.valueOf(cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Proteins))));
                intent.putExtra("Fats",String.valueOf(cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Fats))));
                intent.putExtra("Sodium",String.valueOf(cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Sodium))));
                startActivity(intent);
            }
        });



    }

    //deleting data methods
    public void swipeManager(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos=viewHolder.getAdapterPosition();
                Log.d("Going to Delete :", "onSwiped: "+pos);
                cursor.moveToPosition(pos);
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Delete Record !").setIcon(R.drawable.del_button)
                .setMessage("Are you sure ??")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          deletedata(pos);
                          makeSnackBar();

                    }

                })
                .setNegativeButton("Cancel" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor_Changed(cursor);
                        dialog.cancel();
                    }
                }).setCancelable(true);
                alert.show();
            }
        }).attachToRecyclerView(recyclerView);
    }
    void deletedata(int pos){
        cursor.moveToPosition(pos);
        long id;
        if(path.equals("AddMealOption")){
            id=cursor.getLong(cursor.getColumnIndex(Contract.Dbnutrition._ID));
            Log.d("Deleting :", "deletedata: "+id);
            nutrition_object=dbOpenHelper.DeleteNutritionEntry(id, Contract.Dbnutrition.TableName,false);
            Log.d("Befpre ", "deletedata: "+nutrition_object.getDesc());
        }
        if(path.equals("MyFoodAddSearch")){
           id=cursor.getLong(cursor.getColumnIndex(Contract.DbFood._ID));
            Log.d("Deleting :", "deletedata: "+id);
           nutrition_object=dbOpenHelper.DeleteNutritionEntry(id, Contract.DbFood.TableName,true);
            Log.d("Befpre ", "deletedata: "+nutrition_object.getDesc());
        }
        if(path.equals("DetailTest")) {
            id=cursor.getLong(cursor.getColumnIndex(Contract.Dbnutrition._ID));
            Log.d("Deleting :", "deletedata: "+id);
            nutrition_object=dbOpenHelper.DeleteNutritionEntry(id, Contract.Dbnutrition.TableName,false);
            Log.d("Befpre ", "deletedata: "+nutrition_object.getDesc());
        }
        setCursor();
        Cursor_Changed(cursor);
    }
    void makeSnackBar(){
        if(view==null){
            Log.d("Alert!!!!", "makeSnackBar: null record ");
            return;
        }
        Snackbar snackbar=Snackbar.make(view,"Item Removed",Snackbar.LENGTH_LONG);
        Log.d("Deleted ID :", "makeSnackBar: "+nutrition_object.getDesc());
        if(nutrition_object!=null) {
            snackbar.setAction("RECOVER", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (path.equals("AddMealOption")) {
                        dbOpenHelper.insert_nutrition_data(nutrition_object);
                    }
                    if (path.equals("MyFoodAddSearch")) {
                        dbOpenHelper.insert_Food_data(nutrition_object);
                    }
                    if (path.equals("DetailTest")) {
                        dbOpenHelper.insert_nutrition_data(nutrition_object);
                    }
                    setCursor();
                    Cursor_Changed(cursor);
                }

            });
        }
        snackbar.show();
    }


}
