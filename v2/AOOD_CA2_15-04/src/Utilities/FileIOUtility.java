package Utilities;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileIOUtility 
{
	public static void writeXML(String data, String path) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(path);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-16");  //ISO-8859-1 (Latin ASCII), US-ASCII, UTF-8
		BufferedWriter bw = new BufferedWriter(osw);
		
		bw.write(data);
		
		bw.close();
		osw.close();
		fos.close();
	}
}
