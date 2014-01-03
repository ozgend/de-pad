using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace depad_core
{
    internal class DSocketListener
    {
        TcpListener _tcpListener = null;
        ISocketDataHandler _handler;

        const int _sleep = 10;

        public bool IsRunning { get; set; }

        public DSocketListener(ISocketDataHandler handler)
        {
            _handler = handler;
            IPAddress ipAddress = Dns.GetHostEntry(handler.Host).AddressList.FirstOrDefault(a => a.AddressFamily == AddressFamily.InterNetwork);
            _tcpListener = new TcpListener(ipAddress, handler.Port);
            _tcpListener.Server.NoDelay = true;
            Console.WriteLine("++ DSocketListener - initialized on {0}:{1} ", handler.Host, handler.Port);
        }

        public void Start()
        {
            IsRunning = true;

            try
            {
                _tcpListener.Start();
                Console.WriteLine("++ DSocketListener - awaiting connection");
            }
            catch (Exception ex)
            {
                Console.WriteLine("++ DSocketListener - start error: " + ex.ToString());
            }


            while (IsRunning)
            {
                try
                {
                    Thread.Sleep(_sleep);
                    //var client = _tcpListener.AcceptTcpClient();
                    var client = _tcpListener.AcceptSocket();

                    var bytes = new byte[256];
                    //var stream = client.GetStream();
                    var stream = client.Receive(bytes);
                    //stream.Read(bytes, 0, bytes.Length);
                    var serialized = Encoding.ASCII.GetString(bytes, 0, bytes.Length).Trim('\0');
                    var data = JsonConvert.DeserializeObject<DPadData>(serialized);
                    _handler.DataReceived(data);
                }
                catch (Exception ex)
                {
                    Console.WriteLine("++ DSocketListener - receive error: " + ex.ToString());
                }
            }
            _tcpListener.Stop();
            Console.WriteLine("++ DSocketListener - stopped");
        }

        public void Stop()
        {
            IsRunning = false;
        }

    }

}
