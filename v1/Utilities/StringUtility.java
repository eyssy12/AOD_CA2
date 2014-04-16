package Utilities;

/**
 * Provides common string processing functions
 *
 * @author mcguinnn
 * @version 1.0
 * @since 14/1/14
 */
public class StringUtility 
{
	/****************************************** Validate methods - used to test string for valid length & non-null ******************************************/
	
	/**
	 * Validates a string i.e. not null and with length > 0
	 * @param str 	String data to test
	 * @return 		True if non-null and length > 0
	 */
	public static boolean isValid(String str)
	{		
		return ((str != null) && (str.length() > 0));
	}
	
	/** Validates two strings
	 * @param strA	String data to test
	 * @param strB	String data to test
	 * @return 		True if non-null and length > 0
	 */
	public static boolean isValid(String strA, String strB)
	{		
		return isValid(strA) && isValid(strB);
	}
	
	/****************************************** Pad/Unpad methods - used to create fixed length strings ******************************************/
	
	/** Un-pads a string be removing the pad characters
	 * @param data			String to be un-padded
	 * @param padCharacter	Character used to originally pad the string (should not occur in the string itself)
	 * @return 				Un-padded string data
	 */
	public static String unpad(String data, char padCharacter)
	{
		int indexPad = data.indexOf(padCharacter);
		if(indexPad != -1)
			return data.substring(0, indexPad);
		else
			return data;
	}
	
	/** Pads a string to a fixed length with a user-defined character
	 * @param data			String to be padded
	 * @param padCharacter	Character used to pad the string (should not occur in the string itself)
	 * @return Padded string data with length == padLength
	 */
	public static String pad(String data, int length, char padCharacter)
	{
		//using length == 10 and padCharacter == '*' the string "porsche" pads to "porsche***"
		StringBuilder strBuilder = new StringBuilder(length);
		
		for(int i = 0; i < length; i++)
		{
			if(i < data.length())
				strBuilder.append(data.charAt(i));
			else
				strBuilder.append(padCharacter);
		}
		
		return strBuilder.toString();
	}

}
