using depad_core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace depad_app
{
    class Program
    {
        static void Main(string[] args)
        {
            var host = Dns.GetHostEntry(Dns.GetHostName()).AddressList.FirstOrDefault(ip => ip.AddressFamily == AddressFamily.InterNetwork).ToString();
            var port = 666;
            var application = new DSocketApplication(host, port);
            application.Start();

            Console.Write("press a key to exit...");
            Console.Read();
        }
    }
}
