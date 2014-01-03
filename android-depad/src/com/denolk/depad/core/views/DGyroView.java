package com.denolk.depad.core.views;

import com.denolk.depad.core.DPadData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DGyroView extends View {

	final int	width			= 240;
	final int	height			= 240;
	final int	centerY			= height / 2;

	int			angleStartDiff	= 0;

	RectF		horizonSky;
	RectF		horizonGround;

	Paint		paint			= new Paint();

	public DGyroView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint.setAntiAlias(true);
		horizonSky = new RectF(0, 0, width, height);
		horizonGround = new RectF(0, 0, width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawHorizon(canvas);
		drawLine(canvas);
	}

	private void drawHorizon(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);

		paint.setColor(Color.rgb(150, 150, 255));
		canvas.drawArc(horizonSky, 180 - angleStartDiff, 180, true, paint);

		paint.setColor(Color.rgb(200, 180, 130));
		canvas.drawArc(horizonGround, 0 - angleStartDiff, 180, true, paint);
	}

	private void drawLine(Canvas canvas) {
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(4f);
		paint.setColor(Color.RED);
		paint.setStrokeJoin(Paint.Join.ROUND);
		canvas.drawLine(0, centerY, width, centerY, paint);

		// banking dots
		//DashPathEffect dashPath = new DashPathEffect(new float[] { 175, 4},  8f);
		//paint.setPathEffect(dashPath);
		//paint.setStrokeWidth(25f);
		//paint.setColor(Color.BLACK);
		//canvas.drawCircle(centerY, centerY, centerY/2, paint);
	}

	public void update(DPadData data) {
		angleStartDiff = (int) (data.Roll * 9);

		this.postInvalidate();
	}
}
