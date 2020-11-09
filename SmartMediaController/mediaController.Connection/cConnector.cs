using System;
using System.Collections.Generic;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Sockets;
using System.Text;

namespace mediaController.Connection
{
    public sealed class cConnector
    {
        private Socket _listener, _clientSocket;
        private string _ip;
        private int _port;
        private bool _isConnected;

        private static readonly cConnector instance = new cConnector();

        // Explicit static constructor to tell C# compiler  
        // not to mark type as beforefieldinit  
        static cConnector()
        {
        }
        private cConnector()
        {
            _clientSocket = null;
            _ip = Dns.GetHostByName(Dns.GetHostName()).AddressList[0].ToString();
            _port = GetOpenPort();
            _isConnected = false;
        }
        public static cConnector Instance
        {
            get
            {
                return instance;
            }
        }


        //public Connector()
        //{
        //    _clientSocket = null;
        //    _ip= Dns.GetHostByName(Dns.GetHostName()).AddressList[0].ToString();
        //    _port = GetOpenPort();
        //    _isConnected = false;
        //}

        private int GetOpenPort()
        {
            int PortStartIndex = 1000;
            int PortEndIndex = 2000;
            IPGlobalProperties properties = IPGlobalProperties.GetIPGlobalProperties();
            IPEndPoint[] tcpEndPoints = properties.GetActiveTcpListeners();

            List<int> usedPorts = new List<int>();
            for (int i = 0; i < tcpEndPoints.Length; i++)
            {
                usedPorts.Add(tcpEndPoints[i].Port);
            }

            int unusedPort = 0;
            for (int port = PortStartIndex; port < PortEndIndex; port++)
            {
                if (!usedPorts.Contains(port))
                {
                    unusedPort = port;
                    break;
                }
            }
            return unusedPort;
        }

        private void WaitForConnection()
        {
            Console.WriteLine("Waiting connection ... ");
            _clientSocket = _listener.Accept();
            //Check if Connected
            if (_clientSocket != null)
            {
                _isConnected = true;
                Console.WriteLine("Connected!!!");
            }
            else
            {
                _isConnected = false;
                Console.WriteLine("Not connected!!!");
            }
        }

        public void ExecuteServer()
        {
            IPAddress ipAddr = IPAddress.Parse(_ip);
            IPEndPoint localEndPoint = new IPEndPoint(ipAddr, _port);
 
            _listener = new Socket(ipAddr.AddressFamily,
                         SocketType.Stream, ProtocolType.Tcp);
            try
            {
                _listener.Bind(localEndPoint);
                Console.WriteLine(" IP Address:" + localEndPoint.Address + "\n Port:" + localEndPoint.Port);
               
                _listener.Listen(10);
                WaitForConnection();
            }

            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        public string ReadStream()
        {
            //while (_clientSocket != null)
            //{
                // Data buffer 
                byte[] bytes = new Byte[1024];
                string data = null;

                while (true)
                {

                    int numByte = _clientSocket.Receive(bytes);

                    data += Encoding.ASCII.GetString(bytes,
                                               0, numByte);
                    //Console.WriteLine(data);
                    if (data.IndexOf("<EOF>") > -1)
                        break;
                }

                //Performing a required action and then returning response
                //string response = Controller(data.Trim().ToUpper());
                Console.WriteLine("From Client: " + data.Substring(0,data.IndexOf("<EOF>")));

                if (data.Equals("close<EOF>"))
                {
                    Disconnect();
                    WaitForConnection();
                }
            return data;
            //}
        }

        public void sendResponse(string res)
        {
            byte[] message = Encoding.ASCII.GetBytes(res);
            _clientSocket.Send(message);
        }

        public bool IsConnected()
        {
            return _isConnected;
        }

        public void Disconnect() {
            _isConnected = false;
            _clientSocket.Shutdown(SocketShutdown.Both);
            _clientSocket.Close();
            Console.WriteLine("Disconnected.");
        }

    }
}
