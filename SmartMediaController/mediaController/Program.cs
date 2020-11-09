using mediaController.Camera;
using mediaController.Connection;
using mediaController.MediaPlayer;

namespace mediaController
{
  class Program
  {
        private static cCamera camera;
        private static cMediaPlayer mediaPlayer;
        private static string clientRequest = null;
        private static string response = null;

        static void Main(string[] args)
        {   
               camera = new cCamera();
               mediaPlayer = new cMediaPlayer();

               //Excuting server
               cConnector.Instance.ExecuteServer();

               //Reading Client Commmands and then responding back
               while (cConnector.Instance.IsConnected())
               {
                   //Reading stream
                   clientRequest=cConnector.Instance.ReadStream();
                   //Getting a response against each action
                   response=Controller(clientRequest);
                   //Forwarding that response to the client
                   cConnector.Instance.sendResponse(response);
               }

        }

        public static string Controller(string data)
        {
            if (data.Equals("OPEN_CAM<EOF>"))
            {
               return camera.OpenCamera();
            }
            else if (data.Equals("CLOSE_CAM<EOF>"))
            {
                return camera.CloseCamera();
            }
            else if (data.ToUpper().Trim().Equals("CAPTURE<EOF>"))
            {
                return camera.Capture();
            }
            else if (data.ToUpper().Trim().Equals("PLAY_PAUSE<EOF>"))
            {
                return mediaPlayer.PlayOrPause();
            }
            else if (data.ToUpper().Trim().Equals("STOP<EOF>"))
            {
                return mediaPlayer.Stop();
            }
            else if (data.ToUpper().Trim().Equals("PREV<EOF>"))
            {
                return mediaPlayer.Prev();
            }
            else if (data.ToUpper().Trim().Equals("NEXT<EOF>"))
            {
                return mediaPlayer.Next();
            }
            else if (data.ToUpper().Trim().Equals("MUTE<EOF>"))
            {
                return mediaPlayer.MuteOrUnmute();
            }
            else if (data.ToUpper().Trim().Equals("UNMUTE<EOF>"))
            {
                return mediaPlayer.MuteOrUnmute();
            }
            else if (data.ToUpper().Trim().Equals("VOL_DOWN<EOF>"))
            {
                return mediaPlayer.VolDown();
            }
            else if (data.ToUpper().Trim().Equals("VOL_UP<EOF>"))
            {
                return mediaPlayer.VolUp();
            }
            else if (data.ToUpper().Trim().Equals("OPEN_VIDEO<EOF>"))
            {
                return camera.OpenVideo();
            }
            else
            {
                return "No Command is Found!";
            }
        }
    }
}
