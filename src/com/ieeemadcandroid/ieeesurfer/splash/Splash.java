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
package com.ieeemadcandroid.ieeesurfer.splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.ieeemadcandroid.ieeesurfer.R;

public class Splash extends View {
	private boolean initialized = false;
	private Bitmap part1;
	private Bitmap part2;
	private Bitmap part3;
	private Bitmap part4;
	private Bitmap part5;
	private Bitmap part6;
	private Bitmap part7;
	private int x[] = new int[10];
	private int y[] = new int[10];
	private int z[] = new int[10]; // alpha value
	private int width, height, pwidth, pheight; // screen and part sizes
	private Rect bg = new Rect();// background rectangle
	private int centerx, centery;// screen centers
	private Paint pBlack = new Paint();// background paint
	private Paint paint = new Paint(); // object paint
	private int step = 10;
	protected boolean splash_done=false;
	private final Context context;
	
	
	public Splash(Context context) {
		super(context);
		this.context=context;
		paint.setStyle(Paint.Style.FILL);
		part1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_logo1);
		part2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_logo2);
		part3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_logo3);
		part4 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_logo4);
		part5 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_logo5);
		part6 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_logo6);
		part7 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ieee_text_w);
	}

	private void initialize(Canvas canvas) {
		splash_done=false;
		width = canvas.getWidth();
		height = canvas.getHeight();
		pwidth = part1.getWidth();
		pheight = part1.getHeight();
		// initial object placement and alpha setup
		x[1] =pwidth/2;
		x[2] = width-3*pwidth/2;
		y[1] = y[2] = height / 2 - pheight;
		z[1] = z[2] = 0;
		x[3] = x[6] = width / 2 - pwidth / 2;
		y[3] = y[6] = height / 2 - pheight;
		z[3] = 0;
		y[4] =pheight/2; //0
		y[5] = height-2*pheight-pheight/2;//without the last
		x[4] = x[5] = width / 2 - pwidth / 2;
		z[4] = z[5] = 255;
		x[7] = width / 2 - pwidth / 2;
		y[7] = height / 2;
		z[7] = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// do the initialization once only
		if (!initialized) {
			initialize(canvas);
			initialized = true;
		}

		centerx = (int) (width / 2.0);
		centery = (int) (height / 2.0);

		// set background
		bg.set(0, 0, width, height);
		pBlack.setStyle(Paint.Style.FILL);
		pBlack.setColor(0xff000000);
		canvas.drawRect(bg, pBlack);

		// draw parts
		if (x[1] < (centerx - pwidth / 2)) {
			drawpart(part1, x[1], y[1], z[1], canvas);
			drawpart(part2, x[2], y[2], z[2], canvas);
			x[1] += step;
			x[2] -= step;
			z[1] += 2*step;
			if (z[1] > 255)
				z[1] = 255;
			z[2] = z[1];
			invalidate();
		} else {
			drawpart(part1, width / 2 - pwidth / 2, y[1], 255, canvas);
			drawpart(part2, width / 2 - pwidth / 2, y[2], 255, canvas);

			if (z[3] < 255) {
				drawpart(part3, x[3], y[3], z[3], canvas);
				z[3] += 2 * step;
				invalidate();
			} else {
				drawpart(part3, x[3], y[3], 255, canvas);

				if (y[4] < (centery - pheight)) {
					drawpart(part4, x[4], y[4], z[4], canvas);
					drawpart(part5, x[5], y[5], z[5], canvas);
					y[4] += 1.5*step;
					y[5] -= 1.5*step;
					invalidate();
				} else {
					drawpart(part4, x[4], height / 2 - pheight, 255, canvas);
					drawpart(part5, x[5], height / 2 - pheight, 255, canvas);
					drawpart(part6, x[6], y[6], 255, canvas);
					if (z[7] < 255) {
						drawpart(part7, x[7], y[7], z[7], canvas);
						z[7] += 2 * step;
						invalidate();
					} else {
						drawpart(part7, x[7], y[7], 255, canvas);
						splash_done= true;
						context.startActivity(new Intent("com.ieeemadcandroid.ieeesurfer.MAINMENU"));
						try {
							finalize();
						} catch (Throwable e) {
							e.printStackTrace();
						}
						return;
					}
				}

			}
		}
		
		
		
		return;
	}

	private void drawpart(Bitmap part, int x, int y, int z, Canvas canvas) {
		paint.setAlpha(z);
		canvas.drawBitmap(part, x, y, paint);

	}

}
