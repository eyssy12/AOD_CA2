package Main;

import Core.FindAndDownloadTask;
import GUI.Controller;
import GUI.MainView;
import GUI.Model;
import Utilities.MyScanner;

public class MainApp
{

    public static void main(String[] args)
    {
//    	HTTPUtility.setProxy("proxy.dkit.ie", "true", "3128");

    	/* http://www.javalobby.org/java/forums/t45447.html
    	 * 
    	 * try this for picking a date
    	 * 
    	 * 
    	 */
    	
        MainApp theApp = new MainApp();
//        theApp.start();
        theApp.guiStart();

        System.out.println();
        System.out.println("Main App is finished...");
        System.out.println();
    }
    
    private void guiStart()
    {
    	Model model = new Model();
    	Controller controller = new Controller(model);
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
