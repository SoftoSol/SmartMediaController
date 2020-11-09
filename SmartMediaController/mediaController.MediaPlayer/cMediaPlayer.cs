using MediaController.VirtualKeyboard;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mediaController.MediaPlayer
{
    public class cMediaPlayer
    {
        public string PlayOrPause()
        {
            return cVirtualKeyboard.MEDIA_PLAY_PAUSE();
        }
        public string Stop()
        {
            return cVirtualKeyboard.MEDIA_STOP();
        }
        public string MuteOrUnmute()
        {
            return cVirtualKeyboard.VOLUME_MUTE();
        }
        public string Next()
        {
            return cVirtualKeyboard.MEDIA_NEXT_TRACK();
        }
        public string Prev()
        {
            return cVirtualKeyboard.MEDIA_PREV_TRACK();
        }
        public string VolUp()
        {
            return cVirtualKeyboard.VOLUME_UP();
        }
        public string VolDown()
        {
            return cVirtualKeyboard.VOLUME_DOWN();
        }
    }
}
