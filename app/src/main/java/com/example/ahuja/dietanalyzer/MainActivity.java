package com.example.ahuja.dietanalyzer;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.DateHandler;
import com.example.ahuja.dietanalyzer.Adapters_Fragments.NotificationReceiver;
import com.example.ahuja.dietanalyzer.Database.Contract;
import com.example.ahuja.dietanalyzer.Database.DbOpenHelper;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public  String CHANNEL_ID="1";
    long time;

    private DecimalFormat decimalFormat;
    private TextView action_bar_day_no,wt_change,chs,calorie,exercise,water;
    private ImageView wt_change_image,chs_image;
    private CircularProgressBar circularProgressBar;

    private SharedPreferences sharedPreferences;
    private DbOpenHelper dbOpenHelper;
    private Cursor cursor;
    private String cdate_show,cdate_store,cdatesplit[];
    private float ccalories,cexercise,cwater,cwtmax,cwttoday,cwtgoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        CreateNotificationchannel();
        time=System.currentTimeMillis()+60000;
        scheduleNotification(this,time);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        action_bar_day_no=findViewById(R.id.main_act_actionbar_day_no);
        wt_change=findViewById(R.id.act_main_tv_wt_change);
        calorie=findViewById(R.id.act_main_tv_calorie);
        exercise=findViewById(R.id.act_main_tv_exercise);
        water=findViewById(R.id.act_main_tv_water_intake);
        chs=findViewById(R.id.act_main_tv_chs);


        wt_change_image=findViewById(R.id.act_main_iv_wt_change);
        chs_image=findViewById(R.id.act_main_iv_chs);
        circularProgressBar=findViewById(R.id.act_main_pb_cal_count);

        decimalFormat=new DecimalFormat("#.##");
        sharedPreferences=getSharedPreferences(String.valueOf(R.string.preference_file_name),Context.MODE_PRIVATE);
        cdate_show=new DateHandler().getDate_show();
        cdate_store=new DateHandler().getDate_store();
        cdatesplit=cdate_store.split("/");
        dbOpenHelper=new DbOpenHelper(this);
        cursor=null;


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,MyProfile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        action_bar_day_no.setText("Day : "+String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR)-
                sharedPreferences.getInt("Start_Date",Calendar.getInstance().get(Calendar.DAY_OF_YEAR))+1));

        setTodayCalorie();
        setExercise();
        setWater();
        setWtProgress();
        super.onPostResume();
    }

    //Nav bar settings
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Today) {
            Intent intent=new Intent(this,TodaySummaryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_Week) {
            Intent intent=new Intent(this,WeekSummary.class);
            intent.putExtra("Back",0);
            startActivity(intent);


        } else if (id == R.id.nav_Month) {
            Intent intent=new Intent(this,DetailTest.class);
            startActivity(intent);

        } else if (id == R.id.nav_Setting) {
             Intent intent=new Intent(this,MyProfile.class);
             startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void CreateNotificationchannel(){
        CharSequence name="Data Log";
        int importance= NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,name,importance);
        NotificationManager notificationManager=getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
    }
    public void scheduleNotification(Context context,long time){
        Intent intent=new Intent(context,NotificationReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,121234
                ,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        time+=240000;
        AlarmManager alarmManager=(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time,pendingIntent);

    }

    public void ShowToday(View v){
        Intent intent=new Intent(this,TodaySummaryActivity.class);
        startActivity(intent);
    }

    public void GoToProgress(View view){
        Intent intent=new Intent(this,ProgressActivity.class);
        startActivity(intent);
    }

    private void setTodayCalorie(){
        cursor=dbOpenHelper.Fetch_Database(cdatesplit[2],cdatesplit[1],cdatesplit[0], Contract.Dbnutrition.TableName);
        cursor.moveToFirst();
        ccalories=0;
        if(cursor!=null){
            while(!cursor.isAfterLast()){
                ccalories+=cursor.getFloat(cursor.getColumnIndex(Contract.Dbnutrition.Col_Calorie));
                cursor.moveToNext();
            }
        }
        calorie.setText("Calories : "+decimalFormat.format(ccalories)+" Kcal");
        if(ccalories>2000)
             ccalories=2000;
        circularProgressBar.setProgress(ccalories);
    }
    private void setExercise(){
        cursor=dbOpenHelper.Fetch_Database(cdatesplit[2],cdatesplit[1],cdatesplit[0], Contract.DbExercise.TableName);
        cursor.moveToFirst();
        cexercise=0;
        while (!cursor.isAfterLast()){
            cexercise+=cursor.getFloat(cursor.getColumnIndex(Contract.DbExercise.Col_Calorie_Burnt));
            cursor.moveToNext();
        }
        exercise.setText("Exercise : "+decimalFormat.format(cexercise)+" Kcal");
    }
    private void setWater(){
        cursor=dbOpenHelper.Fetch_Database(cdatesplit[2],cdatesplit[1],cdatesplit[0], Contract.DbWater.TableName);
        cursor.moveToFirst();
        cwater=0;
        while (!cursor.isAfterLast()){
            cwater+=cursor.getFloat(cursor.getColumnIndex(Contract.DbWater.Col_Intake_Ml));
            cursor.moveToNext();
        }
        water.setText("Water : "+ decimalFormat.format(cwater)+" mL");
    }
    private void setWtProgress(){
        cursor=dbOpenHelper.Fetch_Database( Contract.DbWeight.TableName);
        cursor.moveToFirst();
        cwtmax=Float.MIN_NORMAL;
        while (!cursor.isAfterLast()){
            cwttoday=cursor.getFloat(cursor.getColumnIndex(Contract.DbWeight.Col_Weight));
            cursor.moveToNext();
        }
        cwtmax=sharedPreferences.getFloat("Weight",cwttoday);
        cwtgoal=sharedPreferences.getFloat("Goal_Weight",cwttoday);
        float diff=cwtmax-cwttoday;
        wt_change.setText("Weight Lost : "+String.valueOf(diff)+" Kg");
        if(diff<0)
            wt_change_image.setImageResource(R.drawable.pos);
        else
            wt_change_image.setImageResource(R.drawable.neg);
        diff=cwttoday-cwtgoal;
        chs_image.setImageResource(R.drawable.weighingscale);
        chs.setText("Remaining : "+String.valueOf(diff)+" Kg");
    }

}
