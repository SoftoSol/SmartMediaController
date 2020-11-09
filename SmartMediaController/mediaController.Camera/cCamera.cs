using mediaController.CommandPrompt;
using MediaController.VirtualKeyboard;
using System;

namespace mediaController.Camera
{
  public class cCamera
  {
        private string _command;

        public cCamera(){
            _command = null;
        }

        public string OpenCamera()
        {
            _command = "start microsoft.windows.camera:";
            try {
                cVirtualKeyboard.TO_CAMERA();
                cCommandPrompt.ExecuteCommand(_command);
                return "ok!<EOF>";
            }
            catch(Exception e)
            {
                return "Fail:"+e.Message+"<EOF>";
            }
        }

        public string CloseCamera()
        {
            _command = "Taskkill /IM WindowsCamera.exe /F";
            try
            {
                cCommandPrompt.ExecuteCommand(_command);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail:" + e.Message + "<EOF>";
            }
        }

        public string Capture()
        {
            //OpenCamera();
            return cVirtualKeyboard.CAPTURE();
        }

        public string OpenVideo()
        {
            OpenCamera();
            return cVirtualKeyboard.OPEN_VID();
        }

    }
}
