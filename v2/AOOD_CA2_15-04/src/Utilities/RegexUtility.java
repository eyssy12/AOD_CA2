package Utilities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides common regular expression functions
 *
 * @author mcguinnn
 * @version 1.0
 * @since 14/1/14
 */
public class RegexUtility 
{
	
	/****************************************** Static regex strings for common file finds ******************************************/

	public static final String FILE_NAME_SUFFIX = "[a-z0-9]{2,3}$";
	
	
	
	
	/****************************************** Match methods - used to test if a string MATCHES a regex ******************************************/
	

	/**
	 * Tests if data matches a regular expression. 
	 * @param strRegex	String regular expression to be applied to the data.
	 * @param strData	String data.
	 * @return			True if match, otherwise false
	 */
	public static boolean matches(String strRegex, String strData)
	{
		if(StringUtility.isValid(strRegex, strData) != false) //if valid then proceed - assumes the integer values are valid
		{
			return strData.matches(strRegex); //test for a match
		}
		
		return false;
	}
		
	/****************************************** Print methods - used to PRINT data matching a regex ******************************************/
	
	/**
	 * Finds and prints the regex output for the specified group, after a user-defined start position (i.e. allows us to "jump over" string content).
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 * @param groupNumber	Integer specifying the group number - if the regex contains group (i.e. bracket pairs) we can specify which component to output.	
	 * @param startPosition	Integer specifying the position in the input data to start applying the regex. 
	 */
	public static void print(String strRegex, String strData, int groupNumber, int startPosition) 
	{
		if(StringUtility.isValid(strRegex, strData) != false) //if valid then proceed - assumes the integer values are valid
		{
			Pattern pattern = Pattern.compile(strRegex);
			Matcher match = pattern.matcher(strData);
			
			while(match.find(startPosition))
			{
				System.out.println(match.group(groupNumber));
			}	
		}
	}
	
	/**
	 * Finds and prints the regex output for the specified group.
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 * @param groupNumber	Integer specifying the group number - if the regex contains group (i.e. bracket pairs) we can specify which component to output.	
	 */
	public static void print(String strRegex, String strData, int groupNumber)
	{			
		print(strRegex, strData, groupNumber, 0);
	}
	
	/**
	 * Performs the find on the input data with a regex
	 * @param strRegex 	String regular expression to be applied to the data.
	 * @param strData 	String data.
	 */
	public static void print(String strRegex, String strData)
	{
		print(strRegex, strData, 0, 0);
	}
	
	
	/****************************************** Find methods - used to RETURN data matching a regex ******************************************/
		
	/*
	 * Performs the find on the input data (with a regex) and returns the first match. Use, for example, to obtain file suffix from file name.
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 */
	public static String find(String strRegex, String strData)
	{
		return find(strRegex, strData, 0, 0);
	}
	
	/**
	 * Performs the find on the input data (with a regex) and returns the first match. Use, for example, to obtain file suffix from file name.
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 * @param groupNumber	Integer specifying the group number - if the regex contains group (i.e. bracket pairs) we can specify which component to output.
	 */
	public static String find(String strRegex, String strData, int groupNumber)
	{
		return find(strRegex, strData, groupNumber, 0);
	}
	
	/**
	 * Performs the find on the input data (with a regex) and returns the first match. Use, for example, to obtain file suffix from file name.
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 * @param groupNumber	Integer specifying the group number - if the regex contains group (i.e. bracket pairs) we can specify which component to output.
	 * @param startPosition	Integer specifying the position in the input data to start applying the regex. 	

	 */
	public static String find(String strRegex, String strData, int groupNumber, int startPosition)
	{
		if(StringUtility.isValid(strRegex, strData) != false) //if valid then proceed - assumes the integer values are valid
		{
			Pattern pattern = Pattern.compile(strRegex);
			Matcher match = pattern.matcher(strData);
			
			if(match.find(startPosition))
				return match.group(groupNumber);
		}
		
		return null;
	}
	
	/**
	 * Performs the find on the input data (with a regex) and populates a user-defined ArrayList (passed as an parameter) with RegexOutput objects.
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 * @param groupNumber	Integer specifying the group number - if the regex contains group (i.e. bracket pairs) we can specify which component to output.	
	 * @param startPosition	Integer specifying the position in the input data to start applying the regex. 
	 */
	public static void findAll(ArrayList<RegexOutput> list, String strRegex, String strData, int groupNumber, int startPosition)
	{
		if(StringUtility.isValid(strRegex, strData) != false) //if valid then proceed - assumes the integer values are valid
		{
			Pattern pattern = Pattern.compile(strRegex);
			Matcher match = pattern.matcher(strData);
			
			int length = strData.length();
			
			while((startPosition < length) && (match.find(startPosition)))
			{
				startPosition = match.end();
				list.add(new RegexOutput(match.group(groupNumber), match.start(),startPosition));
				startPosition++;
			}	
		}
	}
	
	/**
	 * Performs the find on the input data (with a regex) and returns an ArrayList with RegexOutput objects.
	 * @param strRegex 		String regular expression to be applied to the data.
	 * @param strData 		String data.
	 * @param groupNumber	Integer specifying the group number - if the regex contains group (i.e. bracket pairs) we can specify which component to output.	
	 * @param startPosition	Integer specifying the position in the input data to start applying the regex. 
	 * @return				An ArrayList of RegexOutput objects, zero length if no match
	 */
	public static ArrayList<RegexOutput> findAll(String strRegex, String strData, int groupNumber, int startPosition) 
	{
		ArrayList<RegexOutput> list = new ArrayList<RegexOutput>();		
		findAll(list, strRegex, strData, groupNumber, startPosition);
		
		if(list.size() == 0)
			return null;
		else
			return list;
	}
	
	/****************************************** Replace methods - used to REPLACE all regex matches in a source string with a replacement string ******************************************/

	/**
	 * Replaces all instances of the regular expression in the input data with the replacement data.
	 * @param strRegex		String regular expression to be applied to the data.
	 * @param strData		String data.
	 * @param strReplace	String replacement for the matched string data
	 * @return				String with the matched text replaced.
	 */
	public static String replaceAll(String strRegex, String strData, String strReplace)
	{
		if(StringUtility.isValid(strRegex, strData) != false) //if valid then proceed - assumes the integer values are valid
		{
			return strData.replaceAll(strRegex, strReplace); //replace
		}

		return null;
	}

}
