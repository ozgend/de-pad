using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace depad_core
{
    internal interface ISocketDataHandler
    {
        string Host { get; set; }
        int Port { get; set; }
        void DataReceived(DPadData data);
    }
}
