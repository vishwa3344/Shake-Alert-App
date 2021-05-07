package com.example.sankalp;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
	
	private ShakeListener mShaker;
	Editor editor;
	TextView t1;
	ImageView im1;
	Button h1;
	SharedPreferences pref ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		t1=(TextView)findViewById(R.id.pfname);
		im1=(ImageView)findViewById(R.id.pfphoto);
		h1=(Button)findViewById(R.id.button1);
		pref= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
		h1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(Home.this); 
				final EditText edittext = new EditText(getApplicationContext());
				alert.setMessage("Enter Your Message");
				alert.setTitle("Alert Title");

				alert.setView(edittext);
				edittext.setTextColor(Color.BLACK);

				alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				        //What ever you want to do with the value
//				        Editable YouEditTextValue = edittext.getText();
//				        //OR
				        String YouEditTextValue1 = edittext.getText().toString();
				
				        
				        h1.setText(YouEditTextValue1);
				        String b=h1.getText().toString();
				    editor.putString("msg",b);
				    editor.commit();
				        
				        
				    }
				});

				alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				        // what ever you want to do with No option.
				    }
				});

				alert.show();
			}
		});
		
	
		 editor = pref.edit();
		 
		
		 String num11=pref.getString("sd", "notfound");
		 String num111=pref.getString("name", "notfound");

        Bitmap bMap = BitmapFactory.decodeFile(num11);
//        Toast.makeText(getApplicationContext(), num11, Toast.LENGTH_LONG).show();
       im1.setImageBitmap(bMap);
       t1.setText(num111);
        
        
		mShaker = new ShakeListener(this);
		mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
			public void onShake() {
				startActivity(new Intent(Home.this,
						GpsInfoDetails.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

    @Override  
    public boolean onOptionsItemSelected(MenuItem item) 
    {  
        switch (item.getItemId()) {  
            case R.id.item1:  
            	startActivity(new Intent(Home.this,Profile.class));
              Toast.makeText(getApplicationContext(),"Profile Selected",Toast.LENGTH_LONG).show();  
            return true;     
          
              default:  
                return super.onOptionsItemSelected(item);  
        }  
    }  

}
