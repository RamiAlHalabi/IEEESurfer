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
package com.ieeemadcandroid.ieeesurfer.societies;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ieeemadcandroid.ieeesurfer.R;
import com.ieeemadcandroid.ieeesurfer.main.MenuGrid;

@SuppressLint("NewApi")
public class RegionList extends Activity implements OnItemClickListener {
	private ListView LV;
	private ImageView IV;
	private TextView TV;
	private ImageButton web;
	private String url = new String();
	private ObjectAnimator show, hide;
	private LinearLayout lo;
	private List<Item> menuitems;
	private Item item;
	private int textcolor;
	
	public static final boolean POSTGINGERBREAD = !Build.VERSION.RELEASE
			.startsWith("1") && !Build.VERSION.RELEASE.startsWith("2");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regions);
		lo = (LinearLayout) findViewById(R.id.regionsbg);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		lo.setBackgroundColor(prefs.getInt("menu.background", 0xFF000000));
		textcolor=MenuGrid.color(this);
		IV = (ImageView) findViewById(R.id.logoregions);
		IV.setImageResource(R.drawable.regions);
		TV = (TextView) findViewById(R.id.nameregion);
		TV.setText(getResources().getText(R.string.list_regions));
		TV.setTextColor(textcolor);
		LV = (ListView) findViewById(R.id.listregions);
		LV.setBackgroundColor(0x7c000000);
		
		menuitems = new ArrayList<Item>();
		for (int i = 0; i < RegionsID.length; i++) {
			item = new Item(RegionsID[i]);
			menuitems.add(item);
		}
		RegionAdapter adapter = new RegionAdapter(this, R.layout.singlesociety,
				menuitems);
		LV.setAdapter(adapter);
		LV.setOnItemClickListener(this);
		
		
		
		
		if (POSTGINGERBREAD) {
			show = ObjectAnimator.ofFloat(IV, "alpha", 0f, 1f);
			hide = ObjectAnimator.ofFloat(IV, "alpha", 1f, 0f);
		}

		web = (ImageButton) findViewById(R.id.goto_site2);
		web.setVisibility(View.INVISIBLE);
		IV.setVisibility(View.VISIBLE);
		web.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent open = new Intent();
				open.setAction(Intent.ACTION_VIEW);
				open.addCategory(Intent.CATEGORY_BROWSABLE);
				open.setData(Uri.parse("http://" + url));
				startActivity(open);
			}

		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TV.setText(Regions2[arg2]);
		if (POSTGINGERBREAD) {
			show.setDuration(100);
			hide.setDuration(100);
			show.setInterpolator(new LinearInterpolator());
			hide.setInterpolator(new LinearInterpolator());
			hide.start();
			IV.setImageResource(id[arg2]);
			show.start();
		} else {
			IV.setImageResource(id[arg2]);
		}
		web.setVisibility(View.VISIBLE);
		url = URL[arg2];
		arg1.requestFocus();

	}

	private String[] Regions2 = { "Region 1\n(Northeastern U.S.)",
			"Region 2\n(Eastern U.S.)", "Region 3\n(Southern U.S.)",
			"Region 4\n(Central U.S.)", "Region 5\n(Southwestern U.S.)",
			"Region 6\n(Western U.S.)", "Region 7\n(Canada)",
			"Region 8\n(Africa,Europe, Middle East)",
			"Region 9\n(Latin America)", "Region 10\n(Asia and Pacific)" };
	private String[] URL = { "sites.ieee.org/r1", "www.ewh.ieee.org/reg/2",
			"www.ewh.ieee.org/reg/3", "www.ewh.ieee.org/reg/4",
			"www.ieeer5.org", "www.ieee-region6.org", "www.ieee.ca",
			"www.ieeer8.org", "sites.ieee.org/r9", "www.ieeer10.org" };
	private int[] id = { R.drawable.region1, R.drawable.region2,
			R.drawable.region3, R.drawable.region4, R.drawable.region5,
			R.drawable.region6, R.drawable.region7, R.drawable.region8,
			R.drawable.region9, R.drawable.region10

	};
	private int[] RegionsID={
			R.string.reg1,
			R.string.reg2,
			R.string.reg3,
			R.string.reg4,
			R.string.reg5,
			R.string.reg6,
			R.string.reg7,
			R.string.reg8,
			R.string.reg9,
			R.string.reg10
	
	};
}