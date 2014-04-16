package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Scanner;

/**
 * @author mcguinnn
 * @version 1.0
 * @since September 2013
 */
public class MyScanner 
{
	
	/**
	 * A handle to the input buffer from the keyboard now available to all the get methods below
	 */
	private static Scanner kb = new Scanner(System.in);
	
	public static String getString(String prompt)
	{
		BufferedReader buffIn
		= new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print(prompt);
		String value = null;
		try {
			value = buffIn.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;		
	}
	
	/**
	 * Returns a byte entered from the keyboard, limited to the range lo - hi
	 * @param prompt String to prompt the user 
	 * @param lo Minimum accepted input value
	 * @param hi Minimum accepted input value
	 * @return Float value
	 */
	public static byte getByte(String prompt, int lo, int hi)
	{
		byte value = 0;
		do
		{
			value = getByte(prompt);
		}
		//repeat until the value is in the range lo - hi
		while((value < lo) || (value > hi));
		
		return value;
	}
	
	/**
	 * Returns a byte entered from the keyboard
	 * @param prompt String to prompt the user
	 * @return Float value
	 */
	public static byte getByte(String prompt)
	{
		System.out.print(prompt);
		byte value = kb.nextByte();
		return value;		
	}
	
	/**
	 * Returns a short entered from the keyboard, limited to the range lo - hi
	 * @param prompt String to prompt the user 
	 * @param lo Minimum accepted input value
	 * @param hi Minimum accepted input value
	 * @return Integer value
	 */
	public static short getShort(String prompt, int lo, int hi)
	{
		short value = 0;
		do
		{
			value = getShort(prompt);
		}
		//repeat until the value is in the range lo - hi
		while((value < lo) || (value > hi));
		
		return value;
	}
	
	/**
	 * Returns a short entered from the keyboard
	 * @param prompt String to prompt the user
	 * @return Float value
	 */
	public static short getShort(String prompt)
	{
		System.out.print(prompt);
		short value = kb.nextShort();
		return value;		
	}

	/**
	 * Returns an int entered from the keyboard, limited to the range lo - hi
	 * @param prompt String to prompt the user 
	 * @param lo Minimum accepted input value
	 * @param hi Minimum accepted input value
	 * @return Integer value
	 */
	public static int getInt(String prompt, int lo, int hi)
	{
		int value = 0;
		do
		{
			value = getInt(prompt);
		}
		//repeat until the value is in the range lo - hi
		while((value < lo) || (value > hi));
		
		return value;
	}
	
	/**
	 * Returns an int entered from the keyboard
	 * @param prompt String to prompt the user
	 * @return Float value
	 */
	public static int getInt(String prompt)
	{
		System.out.print(prompt);
		int value = kb.nextInt();
		return value;		
	}
	
	/**
	 * Returns a float entered from the keyboard, limited to the range lo - hi
	 * @param prompt String to prompt the user 
	 * @param lo Minimum accepted input value
	 * @param hi Minimum accepted input value
	 * @return Float value
	 */
	public static float getFloat(String prompt, float lo, float hi)
	{
		float value = 0;
		do
		{
			value = getFloat(prompt);
		}
		//repeat until the value is in the range lo - hi
		while((value < lo) || (value > hi));
		
		return value;
	}
	
	/**
	 * Returns a float entered from the keyboard
	 * @param prompt String to prompt the user 
	 * @return Float value
	 */
	public static float getFloat(String prompt)
	{
		System.out.print(prompt);
		float value = kb.nextFloat();
		return value;		
	}
	
	/**
	 * Returns a double entered from the keyboard, limited to the range lo - hi
	 * @param prompt String to prompt the user 
	 * @param lo Minimum accepted input value
	 * @param hi Minimum accepted input value
	 * @return Float value
	 */
	public static double getDouble(String prompt, float lo, float hi)
	{
		double value = 0;
		do
		{
			value = getDouble(prompt);
		}
		//repeat until the value is in the range lo - hi
		while((value < lo) || (value > hi));
		
		return value;
	}
	
	/**
	 * Returns a double entered from the keyboard
	 * @param prompt String to prompt the user 
	 * @return Float value
	 */
	public static double getDouble(String prompt)
	{
		System.out.print(prompt);
		double value = kb.nextDouble();
		return value;		
	}
	

}
