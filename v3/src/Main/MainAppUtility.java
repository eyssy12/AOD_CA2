package Main;

import java.util.ArrayList;

import Utilities.HTTPData;
import Utilities.HTTPUtility;
import Utilities.RegexOutput;
import Utilities.RegexUtility;

public class MainAppUtility
{
    /**
     * Finds all XML file paths in a HTML page
     *
     * @param strURL The webpage to perform the Regex on
     * @param strRegex Regex statement to fish out the data
     * @return ArrayList<RegexOutput> results
     */
    public static ArrayList<RegexOutput> findXmlFilePaths(String strURL, String strRegex)
    {
    	if(!HTTPUtility.checkURL(strURL))
    		return null;
    	
        HTTPData httpData = HTTPUtility.download(strURL);
        return RegexUtility.findAll(
                strRegex,
                httpData.getHTMLData().toString(),
                1, 0);
    }
}
