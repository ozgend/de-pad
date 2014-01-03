package com.denolk.depad.core.sensor;

import com.denolk.depad.core.DPadData;

public interface DSensorHandler {

	void notifyAccelerometerChanged(DPadData data);
	void notifyGyroscopeChanged(DPadData data);
	void notifyProximityChanged(DPadData data);
}
