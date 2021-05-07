package com.example.sankalp;

import java.util.List;
import java.util.Locale;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class GpsInfoDetails extends Activity 
{
	double latitude;
	double longitude;
	GPSTracker gps;
	TextView gpsvalue,address;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_info_details);
		gpsvalue=(TextView)findViewById(R.id.gpsvalue);
		address=(TextView)findViewById(R.id.address);
		
		// create class object
		gps = new GPSTracker(GpsInfoDetails.this);

		// check if GPS enabled
		if (gps.canGetLocation())
		{

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

////			// \n is for new line
////			Toast.makeText(getApplicationContext(),
////					"Your Location is - \nLat: " + latitude + "\nLong: "
////							+ longitude + gps.getLocation(), Toast.LENGTH_LONG)
////					.show();
			gpsvalue.setText("Your Location is - \n\nLatitude: " + latitude + "\nLongitude: "+ longitude);
			if(latitude==0.0||longitude==0.0)
			{
				Toast.makeText(getApplicationContext(),"Location Value Not Found ", Toast.LENGTH_LONG).show();
			}
			else
			{
				getCompleteAddressString(latitude,longitude);	
			}
	
			
		} else 
		{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}
	private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) 
                {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
                address.setText("Address :\n\n"+strAdd);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
       		String num11=pref.getString("num1", "notfound");
       		String num22=pref.getString("num2", "notfound");
       		String num33=pref.getString("num3", "notfound");
       	 
       		String sara=pref.getString("msg", "Danger!!!!");
       		String[] no={num11,num22,num33};
       		for(int i=0;i<=no.length;i++)
       		{
       			Log.e("number", String.valueOf(i)+no[i]);

             SmsManager smsManager = SmsManager.getDefault();
    			smsManager.sendTextMessage(String.valueOf(no[i]), null,"Help! I'm here.!\n"+"Latitude: "+latitude+"\nLongitude: "+longitude+"\n"+sara, null, null);
       			//Toast.makeText(getApplicationContext(),"http://maps.google.com/?q="+latitude+","+longitude +"Address"+strAdd, Toast.LENGTH_LONG).show();
       		}
                
            } else
            {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.w("My Current loction address", "Cannot get Address!");
        }
        return strAdd;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gps_info_details, menu);
		return true;
	}

}

//String link = "http://maps.google.com/maps?q=loc:" + String.format("%f,%f", lat, lon);

//http://maps.google.com/maps?q=loc:37.776570,-122.417506


//SmsManager smsManager = SmsManager.getDefault();
//smsManager.sendTextMessage("phoneNo", null, "http://maps.google.com/?q="+lat+","+lng, null, null);
