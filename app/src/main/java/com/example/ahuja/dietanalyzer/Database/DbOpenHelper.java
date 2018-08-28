package com.example.ahuja.dietanalyzer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DbName="Diet_Tracker.db";
    private static final int Dbversion=3;
    public DbOpenHelper(Context context){
        super(context,DbName,null,Dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

          String Db_Create_Nutrition="CREATE TABLE "+ Contract.Dbnutrition.TableName +" ("
                  + Contract.Dbnutrition._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                  + Contract.Dbnutrition.Col_Day +" TEXT,"
                  + Contract.Dbnutrition.Col_Month + " TEXT,"
                  + Contract.Dbnutrition.Col_Year + " TEXT,"
                  + Contract.Dbnutrition.Col_Type_meal + " TEXT,"
                  + Contract.Dbnutrition.Col_Desc +" TEXT,"
                  + Contract.Dbnutrition.Col_Calorie+" REAL,"
                  + Contract.Dbnutrition.Col_Carbs+" REAL,"
                  + Contract.Dbnutrition.Col_Fats + " REAL,"
                  + Contract.Dbnutrition.Col_Proteins+" REAL,"
                  + Contract.Dbnutrition.Col_Sodium+" REAL );";
        String Db_Create_Food="CREATE TABLE "+ Contract.DbFood.TableName +" ("
                + Contract.DbFood._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contract.DbFood.Col_Type_meal + " TEXT,"
                + Contract.DbFood.Col_Desc +" TEXT,"
                + Contract.DbFood.Col_Calorie+" REAL,"
                + Contract.DbFood.Col_Carbs+" REAL,"
                + Contract.DbFood.Col_Fats + " REAL,"
                + Contract.DbFood.Col_Proteins+" REAL,"
                + Contract.DbFood.Col_Sodium+" REAL );";

        String Db_Create_exercise="CREATE TABLE "+ Contract.DbExercise.TableName +" ("
                  + Contract.DbExercise._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                  + Contract.DbExercise.Col_Day +" TEXT,"
                  + Contract.DbExercise.Col_Month + " TEXT,"
                  + Contract.DbExercise.Col_Year + " TEXT,"
                  //+ Contract.DbExercise.Col_Type_meal + " TEXT,"
                  + Contract.DbExercise.Col_Desc +" TEXT,"
                  + Contract.DbExercise.Col_Calorie_Burnt+" REAL );";
          String Db_Create_weight="CREATE TABLE "+ Contract.DbWeight.TableName +" ("
                  + Contract.DbWeight._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                  + Contract.DbWeight.Col_Day +" TEXT,"
                  + Contract.DbWeight.Col_Month + " TEXT,"
                  + Contract.DbWeight.Col_Year + " TEXT,"
                  + Contract.DbWeight.Col_Weight + " REAL,"
                  + Contract.DbWeight.Col_Body_Fat +" REAL,"
                  + Contract.DbWeight.Col_Body_Muscle+" REAL );";
        String Db_Create_water="CREATE TABLE "+ Contract.DbWater.TableName +" ("
                + Contract.DbWater._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contract.DbWater.Col_Day +" TEXT,"
                + Contract.DbWater.Col_Month + " TEXT,"
                + Contract.DbWater.Col_Year + " TEXT,"
                + Contract.DbWater.Col_Intake_L + " REAL,"
                + Contract.DbWater.Col_Intake_Ml +" REAL,"
                + Contract.DbWater.Col_Intake_Glasses+" REAL );";

        db.execSQL(Db_Create_Nutrition);
          db.execSQL(Db_Create_exercise);
          db.execSQL(Db_Create_weight);
          db.execSQL(Db_Create_water);
          db.execSQL(Db_Create_Food);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String del_nutrition=" DROP TABLE IF EXISTS " + Contract.Dbnutrition.TableName;
        String del_exercise=" DROP TABLE IF EXISTS " + Contract.DbExercise.TableName;
        String del_weight=" DROP TABLE IF EXISTS " + Contract.DbWeight.TableName;
        String del_water=" DROP TABLE IF EXISTS "+ Contract.DbWater.TableName;
        String del_food=" DROP TABLE IF EXISTS "+ Contract.DbFood.TableName;
        db.execSQL(del_food);
        db.execSQL(del_nutrition);
        db.execSQL(del_exercise);
        db.execSQL(del_weight);
        db.execSQL(del_water);
        onCreate(db);

    }
    //insert data in the given table
    public long insert_nutrition_data(Nutrition_Object nutrition_object){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.Dbnutrition.Col_Day,nutrition_object.getDay());
        contentValues.put(Contract.Dbnutrition.Col_Month,nutrition_object.getMonth());
        contentValues.put(Contract.Dbnutrition.Col_Year,nutrition_object.getYear());
        contentValues.put(Contract.Dbnutrition.Col_Type_meal,nutrition_object.getMeal_type());
        contentValues.put(Contract.Dbnutrition.Col_Desc,nutrition_object.getDesc());
        contentValues.put(Contract.Dbnutrition.Col_Calorie,nutrition_object.getCalories());
       // Log.d("Cal Value !!!!!!!!!! ",contentValues.getAsString(Contract.Dbnutrition.Col_Calorie));
        contentValues.put(Contract.Dbnutrition.Col_Carbs,nutrition_object.getCarbs());
        contentValues.put(Contract.Dbnutrition.Col_Fats,nutrition_object.getFats());
        contentValues.put(Contract.Dbnutrition.Col_Proteins,nutrition_object.getProteins());
        contentValues.put(Contract.Dbnutrition.Col_Sodium,nutrition_object.getSodium());
        return sqLiteDatabase.insert(Contract.Dbnutrition.TableName,null,contentValues);

    }
    public long insert_Food_data(Nutrition_Object nutrition_object){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.DbFood.Col_Type_meal,nutrition_object.getMeal_type());
        contentValues.put(Contract.DbFood.Col_Desc,nutrition_object.getDesc());
        contentValues.put(Contract.DbFood.Col_Calorie,nutrition_object.getCalories());
        contentValues.put(Contract.DbFood.Col_Carbs,nutrition_object.getCarbs());
        contentValues.put(Contract.DbFood.Col_Fats,nutrition_object.getFats());
        contentValues.put(Contract.DbFood.Col_Proteins,nutrition_object.getProteins());
        contentValues.put(Contract.DbFood.Col_Sodium,nutrition_object.getSodium());
        return sqLiteDatabase.insert(Contract.DbFood.TableName,null,contentValues);

    }
    public long insert_exercise_data(Exercise_Object exercise_object){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.DbExercise.Col_Day,exercise_object.getDay());
        contentValues.put(Contract.DbExercise.Col_Month,exercise_object.getMonth());
        contentValues.put(Contract.DbExercise.Col_Year,exercise_object.getYear());
        contentValues.put(Contract.DbExercise.Col_Desc,exercise_object.getDesc());
        contentValues.put(Contract.DbExercise.Col_Calorie_Burnt,exercise_object.getCalories());
        return sqLiteDatabase.insert(Contract.DbExercise.TableName,null,contentValues);

    }
    public long insert_weight_data(Weight_Object weight_object){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.DbWeight.Col_Day,weight_object.getDay());
        contentValues.put(Contract.DbWeight.Col_Month,weight_object.getMonth());
        contentValues.put(Contract.DbWeight.Col_Year,weight_object.getYear());
        contentValues.put(Contract.DbWeight.Col_Weight,weight_object.getWeight());
        contentValues.put(Contract.DbWeight.Col_Body_Fat,weight_object.getBody_fat());
        contentValues.put(Contract.DbWeight.Col_Body_Muscle,weight_object.getBody_muscle());
        return sqLiteDatabase.insert(Contract.DbWeight.TableName,null,contentValues);

    }
    public long insert_water_data(Water_Object water_object){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.DbWater.Col_Day,water_object.getDay());
        contentValues.put(Contract.DbWater.Col_Month,water_object.getMonth());
        contentValues.put(Contract.DbWater.Col_Year,water_object.getYear());
        contentValues.put(Contract.DbWater.Col_Intake_L,water_object.getLitre());
        contentValues.put(Contract.DbWater.Col_Intake_Ml,water_object.getMillilitres());
        contentValues.put(Contract.DbWater.Col_Intake_Glasses,water_object.getGlasses());
        return sqLiteDatabase.insert(Contract.DbWater.TableName,null,contentValues);

    }
    public Cursor Fetch_Database(String table_name){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        return sqLiteDatabase.query(table_name,
                null,
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor Fetch_Database(String query,String table_name){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        return sqLiteDatabase.query(table_name,
                null,
                Contract.DbFood.Col_Desc+" LIKE ?",
                new String[]{"%"+query+"%"},
                null,
                null,
                null);
    }

    public Cursor Fetch_Database(String Day, String Month, String Year,String table_name){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        return sqLiteDatabase.query(table_name,
                null,
                Contract.Dbnutrition.Col_Day + "=? AND "+
                        Contract.Dbnutrition.Col_Month +"=? AND "+
                        Contract.Dbnutrition.Col_Year +"=?",
                new String[]{Day, Month, Year},
                null,
                null,
                null,
                null);
    }
    public Cursor Fetch_DatabaseMeal(String Day, String Month, String Year,String typ,String table_name){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        return sqLiteDatabase.query(table_name,
                null,
                Contract.Dbnutrition.Col_Day + "=? AND "+
                        Contract.Dbnutrition.Col_Month +"=? AND "+
                        Contract.Dbnutrition.Col_Year +"=? AND "+
                        Contract.Dbnutrition.Col_Type_meal+"=?",
                new String[]{Day, Month, Year,typ},
                null,
                null,
                null,
                null);
    }

    public boolean DeleteEntry(long id,String table_name){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        int ans=sqLiteDatabase.delete(table_name, " _ID=?", new String[]{String.valueOf(id)});
        return  ans>0?true:false;
    }
    public Nutrition_Object DeleteNutritionEntry(long id,String table_name,boolean isFood){
        Nutrition_Object nutrition_object;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(table_name,
                null,
                " _ID=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor==null)
              return null;
        if(isFood){
                     nutrition_object=new Nutrition_Object("","",""
                    ,cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Type_meal))
                    ,cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Desc))
                    ,cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie))
                    ,cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Carbs))
                    ,cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Fats))
                    ,cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Proteins))
                    ,cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Sodium)));
        }
        else {
            nutrition_object = new Nutrition_Object(cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Day))
                    , cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Month))
                    , cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Year))
                    , cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Type_meal))
                    , cursor.getString(cursor.getColumnIndex(Contract.Dbnutrition.Col_Desc))
                    , cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie))
                    , cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Carbs))
                    , cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Fats))
                    , cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Proteins))
                    , cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Sodium)));
        }
        sqLiteDatabase.delete(table_name, " _ID=?", new String[]{String.valueOf(id)});
        return nutrition_object;
    }


}
