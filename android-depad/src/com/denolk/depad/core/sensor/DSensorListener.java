package com.denolk.depad.core.sensor;

import com.denolk.depad.core.DPadData;
import com.denolk.depad.util.Logger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class DSensorListener implements SensorEventListener {

	private DSensorHandler	_handler;
	private SensorManager	_sensorManager;

	public DSensorListener(Context context, DSensorHandler handler) {
		_handler = handler;
		_sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	public void start(int sensorType) {
		_sensorManager.registerListener(this, _sensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_GAME);
	}

	public void stop() {
		_sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int value) {
		Logger.i("onAccuracyChanged %s data: %d", sensor.getName(), value);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {

		//		String values = java.util.Arrays.toString(event.values);
		//		Logger.i("onSensorChanged %s data: %s", event.sensor.getName(), values);

		DPadData data = null;
		switch (event.sensor.getType()) {

			case Sensor.TYPE_ACCELEROMETER:
				data = new DPadData(event.values);
				_handler.notifyAccelerometerChanged(data);
				break;

			case Sensor.TYPE_ROTATION_VECTOR:
				data = new DPadData(event.values);
				_handler.notifyRotationChanged(data);
				break;

			case Sensor.TYPE_GYROSCOPE:
				data = new DPadData(event.values);
				_handler.notifyGyroscopeChanged(data);
				break;

			case Sensor.TYPE_ORIENTATION:
				data = new DPadData(event.values);
				_handler.notifyOrientationChanged(data);
				break;

			default:
				break;
		}

	}
}
