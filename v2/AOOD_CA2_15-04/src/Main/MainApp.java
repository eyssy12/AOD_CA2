package Main;

import Core.FindAndDownloadTask;
import GUI.LookAndFeel;
import GUI.MainView;
import Utilities.MyScanner;

public class MainApp
{

    public static void main(String[] args)
    {
//    	MainAppUtility.setProxy("proxy.dkit.ie", "true", "3128");

        MainApp theApp = new MainApp();
//        theApp.start();
        theApp.guiStart();

        System.out.println();
        System.out.println("Main App is finished...");
        System.out.println();
    }
    
    private void guiStart()
    {
        MainView mainView = new MainView();
        final LookAndFeel laf = new LookAndFeel(mainView);
        mainView.setVisible(true);
    }

    private void start()
    {
        String strURL = "http://www.sem-o.com/marketdata/Pages/PricingAndScheduling.aspx";
        String strRegex = "<a href=\"([^>]+?\\.(XML|xml))\">";

        FindAndDownloadTask t = new FindAndDownloadTask("Downloads all the XML files of the provided URL",
                strURL, strRegex, 60000);

        t.start();

        MyScanner.getString("Hit key to stop");

        t.stop();
    }
}
