package com.example.remoteController;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HelpDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Voice Commands?\n")
                .setMessage("Hold the mic button and speak any of the following commands:\n\n"+
                        "1) 'Play/Pause': Play or Pause a video\n"+
                        "2) 'Stop': Stop a video\n"+
                        "3) 'Next': Next video track\n"+
                        "4) 'Previous': Previous video track\n"+
                        "5) 'Mute/Unmute': Mute or Unmute volume\n"+
                        "6) 'Volume Up': Increase volume\n"+
                        "7) 'Volume Down': Decrease volume\n"+
			            "8) 'Open Camera': Open the camera\n"+
                        "9) 'Open Video': Open camera or Switch to video\n"+
                        "10) 'Capture': Take a photo or start recording / stop recording a video\n"+
                        "11) 'Close camera': Close the camera\n")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }
}