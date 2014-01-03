package com.denolk.depad.core;

import org.json.JSONObject;

import com.denolk.depad.util.Logger;

public class DPadData {

	public final int	PTYPE_GYROSCOPE	= 1;
	public final int	PTYPE_ENGINE	= 2;

	public DPadData(float[] values) {
		this.Roll = values[1];
		this.Pitch = values[2];
		this.PType = PTYPE_GYROSCOPE;
	}

	public DPadData(float throttle) {
		this.Throttle = throttle;
		this.PType = PTYPE_ENGINE;
	}

	public float	Roll = 0;
	public float	Pitch= 0;
	public float	Throttle= 0;
	public int		PType= 0;

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		try {
			json.put("Roll", this.Roll);
			json.put("Pitch", this.Pitch);
			json.put("Throttle", this.Throttle);
			json.put("PType", this.PType);
		}
		catch (Exception e) {
			Logger.e(e);
		}
		return json.toString();
	}

}
