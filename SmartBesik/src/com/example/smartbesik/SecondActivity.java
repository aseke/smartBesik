package com.example.smartbesik;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class SecondActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.second_layout);
	    
    ImageButton facebookButton = ((ImageButton)findViewById(R.id.facebookButton));
    facebookButton.setOnClickListener(new OnClickListener() {
		
		public void onClick(View arg0) {

			String url = "https://facebook.com/smartbesik";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
			
		}
	});
    ImageButton twitterButton = ((ImageButton)findViewById(R.id.twitterButton));
    twitterButton.setOnClickListener(new OnClickListener() {
		public void onClick(View arg0) {
			String url2 = "https://twitter.com/Serikboldan";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url2));
			startActivity(i);
		}
	});
    ImageButton websiteButton = ((ImageButton)findViewById(R.id.websiteButton));
    websiteButton.setOnClickListener(new OnClickListener() {
		public void onClick(View arg0) {
			String url3 = "https://www.smartbesik.kz";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url3));
			startActivity(i);
		}
	});
    
}
}
