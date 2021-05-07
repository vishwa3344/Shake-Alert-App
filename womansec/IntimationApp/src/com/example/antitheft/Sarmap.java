package com.example.antitheft;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;








import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class Sarmap extends Activity{
	GoogleMap map;
	ArrayList<LatLng> markerPoints;
	TextView tvDistanceDuration;
	//PlaceDetails placeDetails;
	GPSTracker gps;
	private static LatLng origin;
	private static LatLng dest;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sarmap);
//		tvDistanceDuration = (TextView)findViewById(R.id.tv_distance_time);
		
		initilizeMap();
		gps = new GPSTracker(getApplicationContext());
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude()
					+ ", longitude: " + gps.getLongitude());
		}
		// stop executing code by return
		origin = new LatLng(gps.getLatitude(), gps.getLongitude());
		
		SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
	    Editor editor = pref1.edit();
	
		String lat= pref1.getString("lat", "0");
		
		String lon=  pref1.getString("lon","0");
		
		double latitude=Double.parseDouble(lat);
		double longitude=Double.parseDouble(lon);
		
	dest = new LatLng(latitude, longitude);
		
//		dest = new LatLng("12.9229", "80.1275");

		// Initializing
		markerPoints = new ArrayList<LatLng>();
		

//		SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		
//		 map = fm.getMap();
		
		if(map!=null) {
		  
//		   map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

			// Showing / hiding your current location
		map.setMyLocationEnabled(true);

//			// Enable / Disable zooming controls
//		map.getUiSettings().setZoomControlsEnabled(false);
//
//			// Enable / Disable my location button
//			map.getUiSettings().setMyLocationButtonEnabled(true);
//
//			// Enable / Disable Compass icon
//		map.getUiSettings().setCompassEnabled(true);
//
//			// Enable / Disable Rotate gesture
//			map.getUiSettings().setRotateGesturesEnabled(true);
//
//			// Enable / Disable zooming functionality
//		map.getUiSettings().setZoomGesturesEnabled(true);
		   

		map.addMarker(new MarkerOptions()
	    .position(origin)
	    .title("Current Location")
	    .snippet("Current Location ")
	    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		
		
	

		  
//		    Marker destination = map.addMarker(new MarkerOptions().position(dest).title("Destination"));

		     // Move the camera instantly to hamburg with a zoom of 15.
		     map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));

		     // Zoom in, animating the camera.
		     map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}
		

		String url = getDirectionsUrl(origin, dest);

		DownloadTask downloadTask = new DownloadTask();

		// Start downloading json data from Google Directions API
		downloadTask.execute(url);

	}
	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}


	private void initilizeMap() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (map == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
		
	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination="+dest.latitude+","+dest.longitude;	
		//String str_origin = "origin="+la+","+lan;
		
		// Destination of route
	//	String str_dest = "destination="+latitude+","+longitude;		
		
					
		// Sensor enabled
		String sensor = "sensor=false";			
					
		// Building the parameters to the web service
		String parameters = str_origin+"&"+str_dest+"&"+sensor;
					
		// Output format
		String output = "json";
		
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		
		
		return url;
	}
	  private String downloadUrl(String strUrl) throws IOException
	  {
	        String data = "";
	        InputStream iStream = null;
	        HttpURLConnection urlConnection = null;
	        try{
	                URL url = new URL(strUrl);

	                // Creating an http connection to communicate with url 
	                urlConnection = (HttpURLConnection) url.openConnection();

	                // Connecting to url 
	                urlConnection.connect();

	                // Reading data from url 
	                iStream = urlConnection.getInputStream();

	                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

	                StringBuffer sb  = new StringBuffer();

	                String line = "";
	                while( ( line = br.readLine())  != null){
	                        sb.append(line);
	                }
	                
	                data = sb.toString();

	                br.close();

	        }catch(Exception e){
	                Log.d("Exception while downloading url", e.toString());
	        }finally{
	                iStream.close();
	                urlConnection.disconnect();
	        }
	        return data;
	     }

		private class DownloadTask extends AsyncTask<String, Void, String>
		{			
			
			// Downloading data in non-ui thread
			@Override
			protected String doInBackground(String... url) 
			{
					
				// For storing data from web service
				String data = "";
						
				try{
					// Fetching the data from web service
					data = downloadUrl(url[0]);
				}catch(Exception e){
					Log.d("Background Task",e.toString());
				}
				return data;		
			}
			
			// Executes in UI thread, after the execution of
			// doInBackground()
			@Override
			protected void onPostExecute(String result) {			
				super.onPostExecute(result);			
				
				ParserTask parserTask = new ParserTask();
				
				// Invokes the thread for parsing the JSON data
				parserTask.execute(result);
					
			}		
		}
		/** A class to parse the Google Places in JSON format */
	    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >
	    {
	    	
	    	// Parsing the data in non-ui thread    	
			@Override
			protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
				
				JSONObject jObject;	
				List<List<HashMap<String, String>>> routes = null;			           
	            
	            try{
	            	jObject = new JSONObject(jsonData[0]);
	            	DirectionsJSONParser parser = new DirectionsJSONParser();
	            	
	            	// Starts parsing data
	            	routes = parser.parse(jObject);    
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            return routes;
			}
			
			// Executes in UI thread, after the parsing process
			@Override
			protected void onPostExecute(List<List<HashMap<String, String>>> result) {
				ArrayList<LatLng> points = null;
				PolylineOptions lineOptions = null;
				MarkerOptions markerOptions = new MarkerOptions();
				String distance = "";
				String duration = "";
				
				
				
				if(result.size()<1){
					Toast.makeText(getApplicationContext(), "No Points", Toast.LENGTH_SHORT).show();
					return;
				}
					
				
			
					// Traversing through all the routes
		            for(int i=0;i<result.size();i++){
		                points = new ArrayList<LatLng>();
		                lineOptions = new PolylineOptions();
		 
		                // Fetching i-th route
		                List<HashMap<String, String>> path = result.get(i);
		 
		                // Fetching all the points in i-th route
		                for(int j=0;j<path.size();j++){
		                    HashMap<String,String> point = path.get(j);
		 
		                    if(j==0){    // Get distance from the list
		                        distance = (String)point.get("distance");
		                        continue;
		                    }else if(j==1){ // Get duration from the list
		                        duration = (String)point.get("duration");
		                        continue;
		                    }
		 
		                    double lat = Double.parseDouble(point.get("lat"));
		                    double lng = Double.parseDouble(point.get("lng"));
		                    LatLng position = new LatLng(lat, lng);
		 
		                    points.add(position);
		                }
		 
		                // Adding all the points in the route to LineOptions
		                lineOptions.addAll(points);
		                lineOptions.width(5);
		                lineOptions.color(Color.BLUE);
		           	
					
				}
				
//				tvDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
				
				// Drawing polyline in the Google Map for the i-th route
				map.addPolyline(lineOptions);							
			}			
	    }   
	    
}



