package Utilities;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileIOUtility 
{
	public static final String UTF_16 = "UTF-16";
	public static final String UTF_8 = "UTF-16";
	
	public static void writeFileBuffer(String data, String path, String encoding) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(path);
		OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);  //ISO-8859-1 (Latin ASCII), US-ASCII, UTF-8
		BufferedWriter bw = new BufferedWriter(osw);
		
		bw.write(data);
		
		bw.close();
		osw.close();
		fos.close();
	}
}
