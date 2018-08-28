package com.example.ahuja.dietanalyzer.Database;

import android.provider.BaseColumns;

public class Contract {
  public Contract(){}

    public static class Dbnutrition implements BaseColumns{

        public static final String TableName="meal_table";
        public static final String Col_Day="day";
        public static final String Col_Month="month";
        public static final String Col_Year="year";
        public static final String Col_Desc="description";
        public static final String Col_Type_meal="type";
        public static final String Col_Calorie="calories";
        public static final String Col_Carbs="carbohydrates";
        public static final String Col_Fats="fats";
        public static final String Col_Proteins="proteins";
        public static final String Col_Sodium="sodium";

    }
    public static class DbFood implements BaseColumns{

        public static final String TableName="my_food";
        public static final String Col_Desc="description";
        public static final String Col_Type_meal="type";
        public static final String Col_Calorie="calories";
        public static final String Col_Carbs="carbohydrates";
        public static final String Col_Fats="fats";
        public static final String Col_Proteins="proteins";
        public static final String Col_Sodium="sodium";

    }
    public static class DbWater implements BaseColumns{
      public static final String TableName="water_table";
        public static final String Col_Day="day";
        public static final String Col_Month="month";
        public static final String Col_Year="year";
        public static final String Col_Intake_L="litres";
        public static final String Col_Intake_Ml="millilitres";
        public static final String Col_Intake_Glasses="glasses";



    }

    public static class DbExercise implements BaseColumns{

        public static final String TableName="exercise_table";
        public static final String Col_Day="day";
        public static final String Col_Month="month";
        public static final String Col_Year="year";
        public static final String Col_Desc="description";
        public static final String Col_Calorie_Burnt="caloriesBurnt";
    }

    public static class DbWeight implements BaseColumns{
        public static final String TableName="weight_table";
        public static final String Col_Day="day";
        public static final String Col_Month="month";
        public static final String Col_Year="year";
        public static final String Col_Weight="weight";
        public static final String Col_Body_Fat="bodyfat";
        public static final String Col_Body_Muscle="muscle";
    }


}
