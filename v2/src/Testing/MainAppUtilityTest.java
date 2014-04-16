package Testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.MainAppUtility;
import Utilities.RegexOutput;
import Utilities.StringUtility;

public class MainAppUtilityTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
//		MainAppUtility.setProxy("proxy.dkit.ie", "true", "3128");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getAllXMLTest() 
	{
		String strURL = "http://www.sem-o.com/marketdata/Pages/PricingAndScheduling.aspx";
		String strRegex = "<a href=\"([^>]+?\\.(XML|xml))\">";
		
		ArrayList<RegexOutput> list;
		int result;
		boolean bResult;
		
		//pass
		list = MainAppUtility.findXmlFilePaths(strURL, strRegex);
		result = list.size();
		assertEquals(20, result);
		
		// fail
		list = MainAppUtility.findXmlFilePaths(strURL, strRegex);
		result = list.size();
		assertNotSame(10, result);
		
		//invalid http page
		strURL = "htttttp://www.sem-o.com";
		list = MainAppUtility.findXmlFilePaths(strURL, strRegex);
		assertEquals(null, list);
		
		//invalid URL
		strURL = "";
		bResult = StringUtility.isValid(strURL);
		assertEquals(false, bResult);
		
		//invalid Regex
		strRegex = "";
		bResult = StringUtility.isValid(strRegex);
		assertEquals(false, bResult);
	}
}
