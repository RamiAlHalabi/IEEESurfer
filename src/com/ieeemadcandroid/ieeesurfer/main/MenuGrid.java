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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.ieeemadcandroid.ieeesurfer.R;

@SuppressLint("NewApi")
public class MenuGrid extends Activity {
	private final int SHORTMENU = 9;
	private GridView gridview;
	private List<MainMenuItem> menuitems;
	private MainMenuItem item;
	private ObjectAnimator show;
	private static final boolean POSTGINGERBREAD = !Build.VERSION.RELEASE
			.startsWith("1") && !Build.VERSION.RELEASE.startsWith("2");
	private WindowManager mWindowManager;
	private Display mDisplay;
	private String columns, def_col;
	private boolean extended_menu;
	private int items_to_show;
	private SharedPreferences prefs;
	private static int bg; 

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menugrid);

		gridview = (GridView) findViewById(R.id.gridview);
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
		bg = prefs.getInt("menu.background", 0x7c000000);
		gridview.setBackgroundColor(bg);
				
		
		extended_menu = prefs.getBoolean("menu.extended", true);
		if (extended_menu)
			items_to_show = icons.length;
		else
			items_to_show = SHORTMENU;

		menuitems = new ArrayList<MainMenuItem>();
		for (int i = 0; i < items_to_show; i++) {
			item = new MainMenuItem(icons[i], names[i]);
			menuitems.add(item);
		}
		MenuAdapter adapter = new MenuAdapter(this, R.layout.menuitem,
				menuitems);

		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();
		getResources().getConfiguration();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
				|| mDisplay.getOrientation() == 1
				|| mDisplay.getOrientation() == 3
				|| mDisplay.getRotation() == 1 || mDisplay.getRotation() == 3) {
			def_col = "4";
		} else
			def_col = "3";
		columns = prefs.getString("menu.columns", def_col);
		gridview.setNumColumns(Integer.parseInt(columns));

		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				choose(arg2 + 1);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		 SharedPreferences prefs=
		 PreferenceManager.getDefaultSharedPreferences(this);
		 gridview.setBackgroundColor
		 (prefs.getInt("menu.background",0xFF000000));
		 
	}

	private void choose(int c) {
		Intent open = new Intent();
		switch (c) {
		case 1:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://www.ieee.org"));
			startActivity(open);
			break;
		case 2:			
			 open.setAction(Intent.ACTION_VIEW);
			 open.addCategory(Intent.CATEGORY_BROWSABLE);
			 open.setData(Uri.parse
			 ("https://www.ieee.org/portal/myieee/index.html"));
			 startActivity(open);
			break;
		case 3:
			startActivity(new Intent(
					"com.ieeemadcandroid.ieeesurfer.rss.RSSMAIN"));
			break;
		case 4:
			startActivity(new Intent(
					"com.ieeemadcandroid.ieeesurfer.societies.SOCIETIES"));
			break;
		case 5:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://ieeexplore.ieee.org"));
			startActivity(open);
			break;
		case 6:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://spectrum.ieee.org"));
			startActivity(open);
			break;
		case 7:
			startActivity(new Intent(
					"com.ieeemadcandroid.ieeesurfer.societies.REGIONS"));
			break;
		case 8:
			startActivity(new Intent(
					"com.ieeemadcandroid.ieeesurfer.main.SOCIALMEDIA"));
			break;
		case 9:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://standards.ieee.org"));
			startActivity(open);
			break;
		case 10:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://ieeetv.ieee.org"));
			startActivity(open);
			break;
		case 11:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://www.ieee.org/organizations/foundation"));
			startActivity(open);
			break;
		case 12:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://careers.ieee.org"));
			startActivity(open);
			break;
		case 13:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse(prefs.getString("menu.sbaddr", "https://www.facebook.com/ieee.lufeiii")));
			startActivity(open);
			break;
		case 14:
			open.setAction(Intent.ACTION_VIEW);
			open.addCategory(Intent.CATEGORY_BROWSABLE);
			open.setData(Uri.parse("http://www.ieee.org/conferences_events"));
			startActivity(open);
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.press_about:
			startActivity(new Intent(
					"com.ieeemadcandroid.ieeesurfer.main.ABOUT"));
			return true;
		case R.id.press_help:
			startActivity(new Intent("com.ieeemadcandroid.ieeesurfer.main.HELP"));
			return true;
		case R.id.press_prefs:
			startActivity(new Intent(
					"com.ieeemadcandroid.ieeesurfer.main.PREFERENCES"));
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	

	private int[] icons = { R.drawable.menu_icon_1, R.drawable.menu_icon_2,
			R.drawable.menu_icon_3, R.drawable.menu_icon_4,
			R.drawable.menu_icon_5, R.drawable.menu_icon_6,
			R.drawable.menu_icon_7, R.drawable.menu_icon_8,
			R.drawable.menu_icon_9, R.drawable.menu_icon_10,
			R.drawable.menu_icon_11, R.drawable.menu_icon_12,
			R.drawable.menu_icon_13, R.drawable.menu_icon_14 };
	private int[] names = { R.string.menu1, R.string.menu2, R.string.menu3,
			R.string.menu4, R.string.menu5, R.string.menu6, R.string.menu7,
			R.string.menu8, R.string.menu9, R.string.menu10, R.string.menu11,
			R.string.menu12, R.string.menu13, R.string.menu14 };
	
	public static int color(Context context) {
		// This method was added to correct for background color changing
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		int bg = prefs.getInt("menu.background", 0x7c000000);
		int alpha =	((bg>>24)&0xFF);
		int red = 	((bg>>16)&0xFF);
		int green = ((bg>>8)&0xFF);
		int blue = 	((bg)&0xFF);
		double lum = ((0.2126*(red)+0.7152*(green)+0.0722*(blue))*((alpha)/255.0))/255.0;
		if(lum>0.35)
			return 0xFF000000;//black
		else return 0xFFFFFFFF;//white
	}
	public static int color(int bg) {
		// This method was added to correct for background color changing and is a variant of the method above
		int alpha =	((bg>>24)&0xFF);
		int red = 	((bg>>16)&0xFF);
		int green = ((bg>>8)&0xFF);
		int blue = 	((bg)&0xFF);
		double lum = ((0.2126*(red)+0.7152*(green)+0.0722*(blue))*((alpha)/255.0))/255.0;
		if(lum>0.35)
			return 0xFF000000;//black
		else return 0xFFFFFFFF;//white
	}
	public static void setEnabled(boolean state, TextView view){
		view.setEnabled(state);
		int textcolor=color(bg);
		if(state){
			view.setTextColor(textcolor);
		}
		else{
			if(textcolor==0xFF000000){//black text becomes gray
				view.setTextColor(0x7C393939);
			}
			else view.setTextColor(0x7CC1C1C1);//white becomes less white
		}
			
	}

}
