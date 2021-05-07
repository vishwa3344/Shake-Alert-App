package com.example.antitheft;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity implements OnClickListener

{
	
Button aboutus,location,map,nearestpolice;
final Context context = this;
String bb;
//GPSTracker class
	GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		aboutus=(Button)findViewById(R.id.AboutUS);
		location=(Button)findViewById(R.id.Location);
		map=(Button)findViewById(R.id.Map);
		nearestpolice=(Button)findViewById(R.id.NearestPoliceMap);
		aboutus.setOnClickListener(this);
		location.setOnClickListener(this);
		map.setOnClickListener(this);
		nearestpolice.setOnClickListener(this);
		
		 gps = new GPSTracker(Home.this);
		 
		 

			// check if GPS enabled		
	        if(gps.canGetLocation()){
	        	
	        	double latitude = gps.getLatitude();
	        	double longitude = gps.getLongitude();
	        	
	        	// \n is for new line
	        	//Toast.makeText(getApplicationContext(), "got it your location", Toast.LENGTH_LONG).show();	
	        }else{
	        	// can't get location
	        	// GPS or Network is not enabled
	        	// Ask user to enable GPS/network in settings
	        	//gps.showSettingsAlert();
	        	//Toast.makeText(getApplicationContext(), "Your Location is unable to find ", Toast.LENGTH_LONG).show();	
	        }
	}
	        

           
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {

	       
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);

	        builder.setTitle("Quit");
	        builder.setIcon(R.drawable.appicon);
	        builder.setMessage("Do you Want to Exit ?");

	        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which)
	            {
	                // Do nothing but close the dialog

	            	
	                finish();
	            }

	        });

	        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // Do nothing
	                dialog.dismiss();
	            }
	        });

	        AlertDialog alert = builder.create();
	        alert.show();
	    }
	    return false;
	}
	
	@Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.home, menu);//Menu Resource, Menu  
        return true;  
    }  
   


	@SuppressLint("CommitPrefEdits")
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		 switch(v.getId()) 
		 {
         case R.id.AboutUS:
             // Do something
        	 AlertDialog.Builder builder = new AlertDialog.Builder(this);

 	        builder.setTitle("About Us");
 	        builder.setIcon(R.drawable.appicon);
 	        builder.setMessage("Information !!! App is used ");

 	        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

 	            public void onClick(DialogInterface dialog, int which) {
 	                // Do nothing but close the dialog

 	            	
 	               dialog.dismiss();
 	            }

 	        });

 	       

 	        AlertDialog alert = builder.create();
 	        alert.show();
        	 
             break;
         case R.id.Location:
             // Do something
        	   SharedPreferences pref =context. getSharedPreferences("MyPref", MODE_PRIVATE); 
			   //String aa= pref.getString("num", null);
			    bb= pref.getString("msg",null);
			 Toast.makeText(getApplicationContext(), bb, Toast.LENGTH_LONG).show();
             break;
            
             case R.id.Map:
                 // Do something
            	// Toast.makeText(getApplicationContext(), "HEllo lock now", Toast.LENGTH_LONG).show();
           	 String value = bb;
          	 String value_list[] = value.split(",");
           	 String location = value_list[0];
            	 String latitude = value_list[1];
           	 String longitude = value_list[2];
           	 
           	 SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
           	 Editor editor = pref1.edit();
          	 editor.putString("lat", latitude);
          	 editor.putString("lon", longitude);
           	  editor.commit();
            	
            	 Intent i = new Intent(Home.this, Sarmap.class);
            	
//            	 Bundle b = new Bundle();
//            	 b.putString("latitude", latitude);
//            	 b.putString("longitude",longitude);
//            	 i.putExtra("personBdl", b);
            	  
            	 startActivity(i);
            	 
            	 
                 break;
             case R.id.NearestPoliceMap:
            	 Intent i11 = new Intent(Home.this, NearestPoliceStation.class);
            	 startActivity(i11);
            	 break;
          
		
		 }
	
}
}
