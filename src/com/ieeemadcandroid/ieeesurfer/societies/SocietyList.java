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

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ieeemadcandroid.ieeesurfer.R;
import com.ieeemadcandroid.ieeesurfer.main.MenuGrid;

@SuppressLint("NewApi")
public class SocietyList extends Activity implements OnItemClickListener {
	private ListView LV;
	private ImageView IV;
	private TextView TV;
	private ImageButton web;
	private EditText filter;
	private ArrayAdapter<String> adapter;
	private Button clear;
	private String url = new String();
	private ObjectAnimator show, hide;
	private LinearLayout lo;
	private int textcolor;
	
	private static final boolean POSTGINGERBREAD = !Build.VERSION.RELEASE
			.startsWith("1") && !Build.VERSION.RELEASE.startsWith("2");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.societies);

		lo = (LinearLayout) findViewById(R.id.societiesbg);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		lo.setBackgroundColor(prefs.getInt("menu.background", 0x7c000000));
		textcolor=MenuGrid.color(this);
		IV = (ImageView) findViewById(R.id.logosocieties);
		IV.setImageResource(R.drawable.ieee_text_w);
		
		clear = (Button) findViewById(R.id.clearfilter);
		clear.setVisibility(View.INVISIBLE);
		clear.setTextColor(textcolor);
		
		TV = (TextView) findViewById(R.id.namesocieties);
		TV.setText(getResources().getText(R.string.list_societies));
		TV.setTextColor(textcolor);
		LV = (ListView) findViewById(R.id.listsocieties);
		LV.setBackgroundColor(0x7c000000);
		adapter = new ArrayAdapter<String>(this, R.layout.singlesociety,
				Societies);
		LV.setAdapter(adapter);
		LV.setTextFilterEnabled(true);
		LV.setOnItemClickListener(this);
		web = (ImageButton) findViewById(R.id.goto_site);
		web.setVisibility(View.INVISIBLE);
		if (POSTGINGERBREAD) {
			IV.setAlpha(0f);
			show = ObjectAnimator.ofFloat(IV, "alpha", 0f, 1f);
			hide = ObjectAnimator.ofFloat(IV, "alpha", 1f, 0f);
		}

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
		filter = (EditText) findViewById(R.id.filter);
		filter.setHintTextColor((textcolor==0xFF000000)?0xAA000000:0xAAFFFFFF);
		filter.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				SocietyList.this.adapter.getFilter().filter(arg0);
				if (POSTGINGERBREAD) {
					if (arg0.toString().isEmpty())
						clear.setVisibility(View.INVISIBLE);
					else
						clear.setVisibility(View.VISIBLE);
				} else {
					if (arg0.toString().matches(""))
						clear.setVisibility(View.INVISIBLE);
					else
						clear.setVisibility(View.VISIBLE);
				}
			}

		});
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				filter.setText("");
			}

		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		web.setVisibility(View.VISIBLE);
		TV.setText(Societies[filtered(arg2)]);
		arg1.requestFocus();
		if (POSTGINGERBREAD) {
			show.setDuration(100);
			hide.setDuration(100);
			show.setInterpolator(new LinearInterpolator());
			hide.setInterpolator(new LinearInterpolator());
			hide.start();
			IV.setImageResource(id[filtered(arg2)]);
			show.start();
		} else {
			IV.setImageResource(id[filtered(arg2)]);
		}
		url = URL[filtered(arg2)];

	}

	private String[] Societies = {
			"IEEE Aerospace and Electronic Systems Society ",
			"IEEE Antennas and Propagation Society ",
			"IEEE Broadcast Technology Society ",
			"IEEE Circuits and Systems Society ",
			"IEEE Communications Society ",
			"IEEE Components, Packaging, and Manufacturing Technology Society ",
			"IEEE Computational Intelligence Society ",
			"IEEE Computer Society ", "IEEE Consumer Electronics Society ",
			"IEEE Control Systems Society ",
			"IEEE Dielectrics and Electrical Insulation Society ",
			"IEEE Education Society ", "IEEE Electron Devices Society ",
			"IEEE Electromagnetic Compatibility Society ",
			"IEEE Engineering in Medicine and Biology Society ",
			"IEEE Geoscience and Remote Sensing Society ",
			"IEEE Industrial Electronics Society ",
			"IEEE Industry Applications Society ",
			"IEEE Information Theory Society ",
			"IEEE Instrumentation and Measurement Society ",
			"IEEE Intelligent Transportation Systems Society ",
			"IEEE Magnetics Society ",
			"IEEE Microwave Theory and Techniques Society ",
			"IEEE Nuclear and Plasma Sciences Society ",
			"IEEE Oceanic Engineering Society ", "IEEE Photonics Society ",
			"IEEE Power Electronics Society ", "IEEE Power & Energy Society ",
			"IEEE Product Safety Engineering Society ",
			"IEEE Professional Communication Society ",
			"IEEE Reliability Society ",
			"IEEE Robotics and Automation Society ",
			"IEEE Signal Processing Society ",
			"IEEE Society on Social Implications of Technology ",
			"IEEE Solid-State Circuits Society ",
			"IEEE Systems, Man, and Cybernetics Society ",
			"IEEE Ultrasonics, Ferroelectrics, and Frequency Control Society ",
			"IEEE Vehicular Technology Society " };
	private String[] URL = { "ieee-aess.org", "www.ieeeaps.org",
			"bts.ieee.org", "www.ieeecss.org", "www.comsoc.org",
			"www.cpmt.org", "cis.ieee.org", "www.computer.org",
			"cesoc.ieee.org", "www.ieeecss.org", "sites.ieee.org/deis",
			"sites.ieee.org/edsocsac", "eds.ieee.org", "www.emcs.org",
			"www.embs.org", "www.grss-ieee.org", "ieee-ies.org",
			"ias.ieee.org", "www.itsoc.org", "ieee-ims.org",
			"sites.iee.org/itss", "www.ieeemagnetics.org", "www.mtt.org",
			"ewh.ieee.org/soc/nps", "www.oceanicengineering.org",
			"www.photonicssociety.org", "www.ieee-pels.org",
			"www.ieee-pes.org", "ewh.ieee.org/soc/pses", "pcs.ieee.org",
			"rs.ieee.com", "www.ieee-ras.org",
			"www.signalprocessingsociety.org", "www.ieeessit.org",
			"sscs.ieee.org", "www.ieeesmc.org", "www.ieee-uffc.org",
			"www.vtsociety.org" };
	private int[] id = { R.drawable.aess, R.drawable.aps, R.drawable.bts,
			R.drawable.cas, R.drawable.comsoc, R.drawable.cmpt, R.drawable.cis,
			R.drawable.compsoc, R.drawable.ces, R.drawable.controlsys,
			R.drawable.deis, R.drawable.edsoc, R.drawable.eds, R.drawable.emc,
			R.drawable.emb, R.drawable.grss, R.drawable.ies, R.drawable.ias,
			R.drawable.its, R.drawable.ims, R.drawable.itsc, R.drawable.mag,
			R.drawable.mtts, R.drawable.npss, R.drawable.oes, R.drawable.ps,
			R.drawable.pels, R.drawable.pes, R.drawable.pses, R.drawable.pcs,
			R.drawable.reliability, R.drawable.ras, R.drawable.sps,
			R.drawable.ssit, R.drawable.sscs, R.drawable.smc, R.drawable.uffc,
			R.drawable.vts

	};

	private int filtered(int n) {
		String txt = SocietyList.this.adapter.getItem(n);
		for (int i = 0; i < Societies.length; i++) {
			if (Societies[i].equals(txt)) {
				return i;
			}
		}
		return 0;
	}
}