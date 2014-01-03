using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace depad_core
{
    public class DSocketApplication : ISocketDataHandler
    {
        DSocketListener _listener;
        private string _host;
        private int _port;
        public string Host { get { return this._host; } set { this._host = value; } }
        public int Port { get { return this._port; } set { this._port = value; } }

        public DSocketApplication(string host, int port)
        {
            Host = host;
            Port = port;
            this._listener = new DSocketListener(this);
        }

        public void Start()
        {
            _listener.Start();
        }

        public void Stop()
        {
            _listener.Stop();
        }

        public void DataReceived(DPadData data)
        {
            switch (data.PType)
            {
                case DPadData.PTYPE_GYROSCOPE:
                    Console.WriteLine("application - gyroscope = {0}", JsonConvert.SerializeObject(data.Gyroscope()));
                    break;
                case DPadData.PTYPE_ENGINE:
                    Console.WriteLine("application - engine = {0}", JsonConvert.SerializeObject(data.Engine()));
                    break;
                default:
                    Console.WriteLine("application - unknown data received = {0}", JsonConvert.SerializeObject(data));
                    break;
            }
        }


    }
}
