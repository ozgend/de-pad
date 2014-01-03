using depad_core;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace depad_clienttest
{
    class Program
    {
        private static Random _random = new Random();
        private static int _port = 666;
        private static string _host;

        static void Main()
        {
            _host = Dns.GetHostEntry(Dns.GetHostName()).AddressList.FirstOrDefault(ip => ip.AddressFamily == AddressFamily.InterNetwork).ToString();
            
            while (Console.ReadKey().Key != ConsoleKey.Escape)
            {
                var data = RandomData();
                var serialized = JsonConvert.SerializeObject(data);
                Send(serialized);
            }
        }

        static DPadData RandomData()
        {
            return new DPadData
            {
                Pitch = (float)(_random.NextDouble() * 20) - 10,
                Roll = (float)(_random.NextDouble() * 20) - 10,
                Throttle = (float)_random.NextDouble() * 100,
                PType = _random.Next(1, 3)
            };
        }

        static void Send(string message)
        {
            try
            {
                var tcpClient = new TcpClient(_host, _port);

                var bytes = new byte[256];
                bytes = System.Text.Encoding.ASCII.GetBytes(message);

                var stream = tcpClient.GetStream();
                stream.Write(bytes, 0, bytes.Length);
                Console.WriteLine("Sent: " + message);
                stream.Close();
                tcpClient.Close();
            }
            catch (ArgumentNullException e)
            {
                Console.WriteLine("ArgumentNullException: " + e);
            }
            catch (SocketException e)
            {
                Console.WriteLine("SocketException: " + e);
            }
        }


    }
}
