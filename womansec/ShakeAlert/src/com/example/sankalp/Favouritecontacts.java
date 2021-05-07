package com.example.sankalp;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Favouritecontacts extends Activity {
	Button contact1,contact2,contact3,save;
	final int PICK_CONTACT=1,PICK_CONTACT1=2,PICK_CONTACT2=3;
	EditText contactnum1,contactnum2,contactnum3;
	String num1,num2,num3;
	Editor editor;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favouritecontacts);
		contact1=(Button)findViewById(R.id.button1);
		contact2=(Button)findViewById(R.id.button2);
		contact3=(Button)findViewById(R.id.button3);
		save=(Button)findViewById(R.id.savecontacts);
		contactnum1=(EditText)findViewById(R.id.editText1);
		contactnum2=(EditText)findViewById(R.id.editText2);
		contactnum3=(EditText)findViewById(R.id.editText3);
		
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
		 editor = pref.edit();
		
		String num11=pref.getString("num1", "notfound");
		String num22=pref.getString("num2", "notfound");
		String num33=pref.getString("num3", "notfound");
		
		contactnum1.setText(num11);
		contactnum2.setText(num22);
		contactnum3.setText(num33);
		
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				
			
				
				num1=contactnum1.getText().toString();
				num2=contactnum2.getText().toString();
				num3=contactnum3.getText().toString();
				
				
				editor.putString("num1", num1); 
				editor.putString("num2", num2); 
				editor.putString("num3", num3); 
				editor.commit();
				
				
			}
		});
		contact3.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT2);
				
			}
		});
		contact2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT1);
			}
		});
		
		contact1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
				
			
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});
	}
		
	
	 public void onActivityResult(int reqCode, int resultCode, Intent data)
	   {
	     super.onActivityResult(reqCode, resultCode, data);

	        switch(reqCode){
	           case (PICK_CONTACT):
	             if (resultCode == Activity.RESULT_OK)
	             {
	                 Uri contactData = data.getData();
	                 Cursor c = managedQuery(contactData, null, null, null, null);
	              if (c.moveToFirst()) 
	              {
	              String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

	              String hasPhone =
	              c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

	              if (hasPhone.equalsIgnoreCase("1")) 
	              {
	             Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null);
	                phones.moveToFirst();
	                String phn_no = phones.getString(phones.getColumnIndex("data1"));
	                String name = c.getString(c.getColumnIndex(StructuredPostal.DISPLAY_NAME));
	               contactnum1.setText(phn_no);
	               contactnum2.setText("");
	               contactnum3.setText("");


	              }
	                }
	             }
	           case (PICK_CONTACT1):
		             if (resultCode == Activity.RESULT_OK)
		             {
		                 Uri contactData = data.getData();
		                 Cursor c = managedQuery(contactData, null, null, null, null);
		              if (c.moveToFirst()) 
		              {
		              String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

		              String hasPhone =
		              c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

		              if (hasPhone.equalsIgnoreCase("1")) 
		              {
		             Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null);
		                phones.moveToFirst();
		                String phn_no = phones.getString(phones.getColumnIndex("data1"));
		                String name = c.getString(c.getColumnIndex(StructuredPostal.DISPLAY_NAME));
		               contactnum2.setText(phn_no);
              
		               contactnum3.setText("");

		              }
		                }
		             }
	           case (PICK_CONTACT2):
		             if (resultCode == Activity.RESULT_OK)
		             {
		                 Uri contactData = data.getData();
		                 Cursor c = managedQuery(contactData, null, null, null, null);
		              if (c.moveToFirst()) 
		              {
		              String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

		              String hasPhone =
		              c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

		              if (hasPhone.equalsIgnoreCase("1")) 
		              {
		             Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null);
		                phones.moveToFirst();
		                String phn_no = phones.getString(phones.getColumnIndex("data1"));
		                String name = c.getString(c.getColumnIndex(StructuredPostal.DISPLAY_NAME));
		              
//		          
		               contactnum3.setText(phn_no);
//		               
		              }
		                }
		             }
	      }}
}  


	


