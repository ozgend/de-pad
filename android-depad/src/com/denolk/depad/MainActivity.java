package com.denolk.depad;

import android.app.Activity;
import android.hardware.Sensor;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.denolk.depad.core.DPadData;
import com.denolk.depad.core.sensor.DSensorHandler;
import com.denolk.depad.core.sensor.DSensorListener;
import com.denolk.depad.core.socket.DSocketClient;
import com.denolk.depad.core.socket.DSocketHandler;
import com.denolk.depad.core.views.DGyroView;
import com.denolk.depad.core.views.ThrottleControlBar;
import com.denolk.depad.R;

public class MainActivity extends Activity implements DSensorHandler, DSocketHandler, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

	EditText			_txtHost;
	TextView			_txtRoll, _txtPitch, _txtThrottle;
	ToggleButton		_btnToggle;
	DGyroView			_drawingView;
	ThrottleControlBar	_barThrottle;
	DSensorListener		_sensorListener;
	DSocketClient		_socketClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_drawingView = (DGyroView) findViewById(R.id.drawingView);
		_txtRoll = (TextView) findViewById(R.id.axis_y);
		_txtPitch = (TextView) findViewById(R.id.axis_z);
		_txtHost = (EditText) findViewById(R.id.txtserver);
		_txtThrottle = (TextView) findViewById(R.id.txtThrottle);
		_barThrottle = (ThrottleControlBar) findViewById(R.id.throttleBar);
		_btnToggle = (ToggleButton) findViewById(R.id.toggleListener);

		_btnToggle.setOnCheckedChangeListener(this);
		_barThrottle.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			start();
		}
		else {
			stop();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		sendEngineData(progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyAccelerometerChanged(DPadData data) {
		sendGyroData(data);
	}

	@Override
	public void notifyGyroscopeChanged(DPadData data) {
		sendGyroData(data);
	}

	@Override
	public void notifyProximityChanged(DPadData data) {
		//todo: on-off maybe?
	}

	private void sendGyroData(DPadData data) {
		_txtRoll.setText(data.Roll + "");
		_txtPitch.setText(data.Pitch + "");
		_drawingView.update(data);

		if (_socketClient != null) {
			_socketClient.send(data);
		}
	}

	private void sendEngineData(int value) {
		if (_socketClient != null) {
			_txtThrottle.setText(value + "");
			DPadData data = new DPadData(value);
			_socketClient.send(data);
		}
	}

	@Override
	public void onDataSent() {

	}

	private void start() {
		if (_txtHost.getText().toString().length() > 7) {
			_sensorListener = new DSensorListener(this, this);
			_socketClient = new DSocketClient(this, _txtHost.getText().toString());
			_sensorListener.start(Sensor.TYPE_ACCELEROMETER);
//			_socketClient.start();
		}
	}

	private void stop() {
//		_socketClient.stop();
		_sensorListener.stop();
		_sensorListener = null;
		_socketClient = null;
	}

}
