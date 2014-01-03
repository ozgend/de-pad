using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace depad_core
{
    public class DPadData
    {
        public const int PTYPE_GYROSCOPE = 1;
        public const int PTYPE_ENGINE = 2;

        public int PType { get; set; }
        public float Roll { get; set; }
        public float Pitch { get; set; }
        public float Throttle { get; set; }

        public dynamic Gyroscope()
        {
            return new { Roll = Roll, Pitch = Pitch };
        }

        public dynamic Engine()
        {
            return new { Throttle = Throttle };
        }
    }
}
