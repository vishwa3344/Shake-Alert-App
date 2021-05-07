package com.example.antitheft;




import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver 
{
	private static final int MODE_PRIVATE = 0;
	private SharedPreferences prefs;
	private String prefName = "report1";
	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();
	
	public void onReceive(Context context, Intent intent) {
	
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {
			
			if (bundle != null) 
			{
				
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdusObj.length; i++) {
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
			        String senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();
			        String s=message;

			        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message : " + message);
			        
		        if(s.startsWith("Help!"))
			        {
			        	String StringAll;
			        	  StringAll = message.toString();
			             
			              Toast.makeText(context, "success", Toast.LENGTH_LONG).show();
			              SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE); 
						    Editor editor = pref.edit();
						    
						    //editor.putString("num", senderNum);
						    editor.putString("msg", message);
						    editor.commit();
			          Notification(context, "Alert Intimation");
			          abortBroadcast();
			          
		        }
//			       
			       
				} // end for loop
              } // bundle is null

		} catch (Exception e) 
		{
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
			
		}
	}

	public void Notification(Context context, String message)
	{
		// Set Notification Title
		String strtitle = "Alert Intimation";
		// Open NotificationView Class on Notification Click
		Intent intent = new Intent(context,Home.class);
		// Send data to NotificationView Class
		intent.putExtra("title", strtitle);
		intent.putExtra("text", message);
		// Open NotificationView.java Activity
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		

		// Create Notification using NotificationCompat.Builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
				// Set Icon
				.setSmallIcon(R.drawable.main)
				// Set Ticker Message
				.setTicker(message)
				// Set Title
				.setContentTitle("Alert Intimation")
				// Set Text
				.setContentText(message)
				// Add an Action Button below Notification
				.setContentIntent(pIntent)
				// Dismiss Notification
				.setAutoCancel(true);

		// Create Notification Manager
		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Build Notification with Notification Manager
		notificationmanager.notify(0, builder.build());
		
		// Todo timer start
		

	}

	
	
}