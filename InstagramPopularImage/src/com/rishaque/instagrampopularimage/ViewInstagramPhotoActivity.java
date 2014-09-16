package com.rishaque.instagrampopularimage;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ViewInstagramPhotoActivity extends Activity {
	
	public static final String CLIENT_ID = "23cd605acb1a4047b23393ec6b2c40cb";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;

	//{"data" => [x] => "user" => "username" }
	//{"data => [x] => "images" => "standard_resolution" => "url"}
	//{"data" => [x] => "caption" => "text"} 
	
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instagram_photo);
        fetchPopularPhotos();
    }

    
    private void fetchPopularPhotos(){
    	photos = new ArrayList<InstagramPhoto>();
    	
    	//create adapter
    	aPhotos = new InstagramPhotosAdapter(this,photos);
    	ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
    	lvPhotos.setAdapter(aPhotos);
    	
    	//https://api.instagram.com/v1/media/popular?client_id=<client_id>
    	//{"data" => [x] => "images" => "standard_resolution" => "url"}
    	//setup popular endpoint
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
    	//create the network clinet
    	AsyncHttpClient client = new AsyncHttpClient();
    	
    	//trigger the network request
    	client.get(popularUrl, new JsonHttpResponseHandler(){
    		//define success and failure callbacks
    		//handle the successful response(popular photos JSOn)
    		
    		
    

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			//Log.i("INFO", response.toString());
			
			
			JSONArray photosJSON = null;
			try {
				photos.clear();
				photosJSON = response.getJSONArray("data");
				for(int i = 0; i <photosJSON.length(); i++){
					JSONObject photoJSON = photosJSON.getJSONObject(i);//1,2,3,
					InstagramPhoto photo = new InstagramPhoto();
					if(photoJSON.getJSONObject("user").getString("username") != null){
						photo.username = photoJSON.getJSONObject("user").getString("username");
					}
					
					if(photoJSON.getJSONObject("user").getString("profile_picture") != null)
					{
						photo.user_image = photoJSON.getJSONObject("user").getString("profile_picture");
					}
					
					
					if(photoJSON.getJSONObject("caption").getString("text") != null){
						photo.caption = photoJSON.getJSONObject("caption").getString("text");
					}
					
					if(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url") !=null){
						photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
					}
					
				
						
						photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						
					
	
					
					photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
					System.out.println("like count" + photo.likesCount);
					//Log.i("DEBUG", photo.toString());
					photos.add(photo);		
				}
				aPhotos.notifyDataSetChanged();
			}catch(JSONException e){
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			super.onFailure(statusCode, headers, responseString, throwable);
		}

	
		
		
		
    	});
    	
    	//handle the successful response 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_instagram_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
