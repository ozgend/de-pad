package com.denolk.depad.core.socket;

import java.io.OutputStream;
import java.net.Socket;

import android.os.Handler;

import com.denolk.depad.core.DPadData;
import com.denolk.depad.util.Logger;

public class DSocketClient {

	private DSocketHandler	_socketHandler;
	private Handler			_threadHandler;
	private Socket			_socket;
	private OutputStream	_outputStream;

	private int				_port	= 666;
	private String			_hostAddress;

	public DSocketClient(DSocketHandler socketHandler, String hostAddress) {
		this._threadHandler = new Handler();
		this._socketHandler = socketHandler;
		this._hostAddress = hostAddress;
	}

	public void start() {
		try {
			_socket = new Socket(_hostAddress, _port);
			_outputStream = _socket.getOutputStream();
		}
		catch (Exception ex) {
			Logger.e(ex);
		}
	}

	public void stop() {
		try {
			_socket.close();
		}
		catch (Exception e) {
			Logger.e(e);
		}
	}

	public void send(DPadData data) {
		final byte[] bytes = data.toString().getBytes();
		_threadHandler.post(new Runnable() {
			
			@Override
			public void run() {
				try {
					_outputStream.write(bytes);
					postOnDataSent();
				}
				catch (Exception e) {
					if (_outputStream == null) {
						Logger.i("outputStream is null");
					}
					Logger.e(e);
				}

			}
		});
	}

	private void postOnDataSent() {
		if (_socketHandler != null) {
			_socketHandler.onDataSent();
		}
		else {
			Logger.i("Socket not attached.");
		}
	}

}
