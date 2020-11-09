using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using WindowsInput;

namespace MediaController.VirtualKeyboard
{
    public static class cVirtualKeyboard
    {
        private static InputSimulator inputStimulator = new InputSimulator();

        public static string CAPTURE()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.RETURN);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }
	//Switch to camera
        public static void TO_CAMERA()
        {
            inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.DOWN);
        }

        public static string OPEN_VID()
        {
            try
            {
                //Console command
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.UP);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string VOLUME_MUTE()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.VOLUME_MUTE);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string VOLUME_DOWN()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.VOLUME_DOWN);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string VOLUME_UP()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.VOLUME_UP);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string MEDIA_NEXT_TRACK()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.MEDIA_NEXT_TRACK);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string MEDIA_PREV_TRACK()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.MEDIA_PREV_TRACK);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string MEDIA_PLAY_PAUSE()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.MEDIA_PLAY_PAUSE);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }

        public static string MEDIA_STOP()
        {
            try
            {
                inputStimulator.Keyboard.KeyPress(WindowsInput.Native.VirtualKeyCode.MEDIA_STOP);
                return "ok!<EOF>";
            }
            catch (Exception e)
            {
                return "Fail: " + e.Message + "<EOF>";
            }
        }
    }
}
