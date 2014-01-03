package com.denolk.depad.core.socket;

import java.io.PrintWriter;
import java.net.Socket;

import android.os.AsyncTask;

import com.denolk.depad.core.DPadData;
import com.denolk.depad.util.Logger;

public class DSocketWriterTask extends AsyncTask<DPadData, Void, Void> {

	private final int	_port	= 666;
	private String		_hostAddress;

	public DSocketWriterTask(String hostAddress){
		this._hostAddress = hostAddress;
	}

	@Override
	protected Void doInBackground(DPadData... params) {
		try {
			DPadData data = params[0];
			Socket socket = new Socket(_hostAddress, _port);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(data.toString());
			writer.flush();
			writer.close();
		}
		catch (Exception ex) {
			Logger.e(ex);
		}

		return null;
	}
}