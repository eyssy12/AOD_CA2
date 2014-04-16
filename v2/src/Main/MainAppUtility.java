package Main;

import java.util.ArrayList;

import Utilities.HTTPData;
import Utilities.HTTPUtility;
import Utilities.RegexOutput;
import Utilities.RegexUtility;

public class MainAppUtility
{
    //public static final String HOME_USER_PATH = System.getProperty("user.home");
    //public static final String URL = "http://www.sem-o.com";

    /**
     * Finds all XML file paths in a HTML page
     *
     * @param strURL The webpage to perform the Regex on
     * @param strRegex Regex statement to fish out the data
     * @return ArrayList<RegexOutput> results
     */
    public static ArrayList<RegexOutput> findXmlFilePaths(String strURL, String strRegex)
    {
        HTTPData httpData = HTTPUtility.download(strURL);

        if(httpData != null)
        {
            return RegexUtility.findAll(
                    strRegex,
                    httpData.toString(),
                    1, 0);
        }

        return null;
    }

    public static void setProxy(String proxyHost, String proxySet, String proxyPort)
    {
        System.getProperties().put("proxyHost", proxyHost);
        System.getProperties().put("proxySet", proxySet);
        System.getProperties().put("proxyPort", proxyPort);
    }
}
