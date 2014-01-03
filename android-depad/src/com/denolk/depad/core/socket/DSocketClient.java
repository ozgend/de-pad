package com.denolk.depad.core.socket;

import com.denolk.depad.core.DPadData;

public class DSocketClient {

	private DSocketHandler		_socketHandler;
	private String _hostAddress;

	public DSocketClient(DSocketHandler socketHandler, String hostAddress) {
		this._socketHandler = socketHandler;
		this._hostAddress = hostAddress;
	}

	public void send(DPadData data) {
		new DSocketWriterTask(_hostAddress).execute(data);
		postOnDataSent();
	}

	private void postOnDataSent() {
		_socketHandler.onDataSent();
	}

}
