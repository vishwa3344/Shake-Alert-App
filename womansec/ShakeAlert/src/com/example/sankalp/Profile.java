package com.example.sankalp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends Activity
{
	private final int SELECT_PHOTO = 1;
	private ImageView imageView;
	Button addcontacts;
	String result;
	Editor editor;
	EditText a;
	 SharedPreferences pref;
	 String imagesdpath;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		 imageView = (ImageView)findViewById(R.id.imageView);

	        Button pickImage = (Button) findViewById(R.id.btn_pick);
	      addcontacts = (Button) findViewById(R.id.con);
	      a=(EditText)findViewById(R.id.name);
	      
	       pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
			 editor = pref.edit();
			 String num11=pref.getString("sd", "notfound");

	        Bitmap bMap = BitmapFactory.decodeFile(num11);
//	        Toast.makeText(getApplicationContext(), num11, Toast.LENGTH_LONG).show();
	        imageView.setImageBitmap(bMap);
	        imageView.setImageURI(Uri.parse(num11));
	        a.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) 
				{
			
					// TODO Auto-generated method stub
					String b=a.getText().toString();
					editor.putString("name", b); 
			        editor.commit();
					
					
				}
			});

	      
	      addcontacts.setOnClickListener(new OnClickListener() 
	      {
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Favouritecontacts.class) );
				
			}
		});
	        pickImage.setOnClickListener(new OnClickListener() 
	        {

				@Override
				public void onClick(View view) 
				{				
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				}
			});
	    }
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	        super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	        switch(requestCode) { 
	        case SELECT_PHOTO:
	            if(resultCode == RESULT_OK)
	            {
					try {
						final Uri imageUri = imageReturnedIntent.getData();
						
						final InputStream imageStream = getContentResolver().openInputStream(imageUri);
						final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
						File imageFile = new File(getRealPathFromURI(imageUri));
						imageView.setImageBitmap(selectedImage);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

	            }
	        }
	    }
	 private String getRealPathFromURI(Uri contentURI) {
		    
		    Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		    if (cursor == null) { // Source is Dropbox or other similar local file path
		        result = contentURI.getPath();
		    } else { 
		        cursor.moveToFirst(); 
		        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
		        result = cursor.getString(idx);
//		        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		        editor.putString("sd", result); 
		        editor.commit();
		        cursor.close();
		    }
		    return result;
		}
	
	
	 @Override
	 public void onBackPressed() {
		 super.onBackPressed();
	        String num11=pref.getString("sd", "notfound");
			 String num111=pref.getString("name", "notfound");
			
	
	 }

	 // Before 2.0
	 @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 String num11=pref.getString("sd", "notfound");
			 String num111=pref.getString("name", "notfound");
		
			startActivity(new Intent(getApplicationContext(),Home.class));
	        
	         return true;
	     }
	     return super.onKeyDown(keyCode, event);
	 }
}
