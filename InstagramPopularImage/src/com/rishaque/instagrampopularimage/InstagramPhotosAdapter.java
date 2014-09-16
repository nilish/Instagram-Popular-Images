package com.rishaque.instagrampopularimage;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


//http://guides.codepath.com/android/Android-Bootcamp-Cliffnotes#week-1---fundamentals-and-views
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos){
		super(context, android.R.layout.simple_list_item_1, photos);
		
		//android.R.layout.simple_list_yem_1 is boring built-in template
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//take the data source at position (i.e 0)
		//get the data item
		
		InstagramPhoto photo = getItem(position);
		//check if we are using a recycle view
		
		if(convertView == null){ //guide using arrayadapter with a list view
			//
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false); 
		}
		//lookup the subview within the template
		
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		ImageView imgInstagramUserProfilePic = (ImageView)convertView.findViewById(R.id.imgInstagramUser); 
		TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
		TextView tvLikesCount = (TextView)convertView.findViewById(R.id.tvLikesCount);
		
		//Populate the subviews (textfiled, imageview) with the correct data
		
		tvCaption.setText(photo.caption);
		tvUsername.setText(photo.username);
		int count = photo.likesCount;
		String count_text = Integer.toString(count);
		tvLikesCount.setText(count_text);
		
		
		//main image
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		//Reset the image from the recucled view
		imgPhoto.setImageResource(0);
		//Ask for the photo to be added
		//picasso
		//cache the image for you
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		
		
		
		//Profile Image
		//imgInstagramUserProfilePic.getL
		
		imgInstagramUserProfilePic.setImageResource(0);
		Picasso.with(getContext()).load(photo.user_image).into(imgInstagramUserProfilePic);
		
		//return the view for that data item
		//http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#using-a-basic-arrayadapter
		return convertView;
		
	}
	

	
	

}
