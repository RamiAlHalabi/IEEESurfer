/**
 * IEEESurfer
 * 
 * Copyright (c) 2014 Rami Al Halabi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package com.ieeemadcandroid.ieeesurfer.main;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.ieeemadcandroid.ieeesurfer.R;

public class SocialMedia extends Activity {

	private GridView gridview;
	private List<SocialItem> menuitems;
	private SocialItem item;
	private ObjectAnimator show;
	private static final boolean POSTGINGERBREAD = !Build.VERSION.RELEASE
			.startsWith("1") && !Build.VERSION.RELEASE.startsWith("2");
	private WindowManager mWindowManager;
	private Display mDisplay;
	private SharedPreferences prefs;
	private ProgressBar pb;
	
	@Override
	protected void onPause() {
		super.onPause();
		pb.setVisibility(View.GONE);
	}


	@Override
	protected void onStop() {
		super.onStop();
		pb.setVisibility(View.GONE);
	}


	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.socialmedia);
		pb=(ProgressBar) findViewById(R.id.progressBar);
		gridview = (GridView) findViewById(R.id.socialgrid);
		if (POSTGINGERBREAD) {
			gridview.setAlpha(0f);
			gridview.setVisibility(View.VISIBLE);
			show = ObjectAnimator.ofFloat(gridview, "alpha", 0f, 1f);
			show.setDuration(400);
			show.setInterpolator(new LinearInterpolator());
			show.start();
		} else {
			gridview.setVisibility(View.VISIBLE);
		}

		prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		gridview.setBackgroundColor(prefs.getInt("menu.background", 0xFF000000));
		
		menuitems = new ArrayList<SocialItem>();
		for (int i = 0; i < icons.length; i++) {
			item = new SocialItem(icons[i]);
			menuitems.add(item);
		}
		SocialAdapter adapter = new SocialAdapter(this, R.layout.social_media_item,
				menuitems);

		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();
		getResources().getConfiguration();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
				|| mDisplay.getOrientation() == 1
				|| mDisplay.getOrientation() == 3
				|| mDisplay.getRotation() == 1 || mDisplay.getRotation() == 3) {
			gridview.setNumColumns(4);
		}
		

		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				choose(arg2 + 1);
			}
		});
	}


	public void choose(int item) {
		pb.setVisibility(View.VISIBLE);
		Intent open = new Intent();
		switch (item) {
		case 1:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("https://www.facebook.com/IEEE.org"));
			startActivity(open);
			break;
		case 2:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://twitter.com/IEEEorg"));
			startActivity(open);
			break;
		case 3:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://plus.google.com/110847308612303935604"));			
			startActivity(open);
			break;
		case  4:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("https://www.linkedin.com/company/ieee"));
			startActivity(open);
			break;
		case 5:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://www.youtube.com/user/IEEETV"));
			startActivity(open);
			break;
		case 6:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://en.wikipedia.org/wiki/Institute_of_Electrical_and_Electronics_Engineers"));
			startActivity(open);
			break;
		case 8:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://www.flickr.com/groups/ieee"));
			startActivity(open);
			break;
		case 9:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://foursquare.com/ieeexplore"));
			startActivity(open);
			break;
		case 7:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://www.tumblr.com/tagged/ieee"));
			startActivity(open);
			break;

		}

	}
	private int[] icons = { 
			R.drawable.facebook,
			R.drawable.twitter,
			R.drawable.googleplus,
			R.drawable.linkedin,
			R.drawable.youtube,
			R.drawable.wiki,
			R.drawable.tumblr,
			R.drawable.flickr,
			R.drawable.foursquare
			
	};

	

}
