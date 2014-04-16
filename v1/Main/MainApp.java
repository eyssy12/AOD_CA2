package Main;

import Utilities.MyScanner;
import Core.FindAndDownloadTask;

public class MainApp
{
	
    public static void main(String[] args)
    {
//    	MainAppUtility.setProxy("proxy.dkit.ie", "true", "3128");
    	
    	MainApp theApp = new MainApp();
    	theApp.start();
    	
    	System.out.println();
		System.out.println("Main App is finished...");
		System.out.println();
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
