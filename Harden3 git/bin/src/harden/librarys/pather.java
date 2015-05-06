package harden.librarys;

import java.io.File;
import javax.swing.JOptionPane;

public class pather
{

    public pather()
    {
    }

    public static void createMainPath(String gamename)
    {
        String user = System.getProperty("user.name");
        String os = System.getProperty("os.name");
        if(os.contains("Windows")) {
            mainpath = gamename + "/";
        } else if(os.equalsIgnoreCase("Mac OS X")) {
        	mainpath = gamename+"/";
        } else
        if(os.equalsIgnoreCase("Linux"))
        {
            mainpath = gamename+"/";
        } else
        {
            JOptionPane.showMessageDialog(null, (new StringBuilder("Sorry your operating system(os) is not supported for this application!\nPlease post this in the forms:\nError: Os Not Supported osname:")).append(os).toString());
            System.exit(0);
        }
        File f = new File(mainpath);
        if(!f.exists())
            f.getPath();
        System.out.println("Info: Mainpath set to " + mainpath);
    }

    public static String mainpath = "mainpath";

}
