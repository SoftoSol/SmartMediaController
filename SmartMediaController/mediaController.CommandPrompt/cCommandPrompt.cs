using System;
using System.Diagnostics;

namespace mediaController.CommandPrompt
{
    public static class cCommandPrompt
    {
        public static bool ExecuteCommand(string command)
        {
            try
            {
                Process proc = new Process();
                proc.StartInfo.FileName = "cmd.exe";
                proc.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
                proc.StartInfo.CreateNoWindow = true;
                proc.StartInfo.RedirectStandardInput = true;
                proc.StartInfo.RedirectStandardOutput = true;
                proc.StartInfo.UseShellExecute = false;
                proc.Start();
                proc.StandardInput.WriteLine(command);
                proc.StandardInput.Flush();
                proc.StandardInput.Close();
                proc.WaitForExit();
                return true;
            }
            catch (Exception objException)
            {
                Console.WriteLine(objException.Message);
                return false;
            }
        }
    }
}
