package Utilities;

import Core.DownloadXMLThreadTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPUtility
{
    public static HTTPData download(String stringURL)
    {
        HTTPData httpData = null;
        String data = null;
        StringBuilder strBuilder = new StringBuilder();

        try
        {
            //create a buffered stream for data from the site
            URL url = new URL(stringURL);
            //connect to the URL and retrieve an input stream, as a stream of bytes
            InputStream is = url.openStream();
            //wrap the input stream in a buffered reader to convert to chars and buffer HTML code - to be read one line at a time
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //read first line
            data = br.readLine();

            //was it the end?
            while(data != null)
            {
                //no, so add the data
                strBuilder.append(data);

                //get the next line
                data = br.readLine();
            }
        } catch (MalformedURLException e)
        {
            System.out.println("URL[" + stringURL + "] was not in the correct format.");
            return null;
        } catch (IOException e)
        {
            System.out.println("An IO exception occured when reading the data from " + stringURL);
            return null;
        }

        //if there was some data at that URL e.g. try a fictitious URL like http://www.sdkfjhdshjashjbasdkgh.com
        if(strBuilder.length() > 0)
        {
            httpData = new HTTPData(stringURL, strBuilder);
        }
        return httpData;
    }
    
    
    //needed to create this so table could give details (progressbar)
    public static HTTPData download(String stringURL, DownloadXMLThreadTask thread)
    {
        HTTPData httpData = null;
        String data = null;
        StringBuilder strBuilder = new StringBuilder();

        try
        {
            //create a buffered stream for data from the site
            URL url = new URL(stringURL);
            //connect to the URL and retrieve an input stream, as a stream of bytes
            InputStream is = url.openStream();
            //wrap the input stream in a buffered reader to convert to chars and buffer HTML code - to be read one line at a time
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //read first line
            data = br.readLine();
            if(data != null)
            {
                thread.setDownloaded((thread.getDownloaded() + data.length()));
            }
            //was it the end?
            while(data != null)
            {
                //no, so add the data
                strBuilder.append(data);

                //get the next line
                data = br.readLine();
                if(data != null)
                {
                    thread.setDownloaded((thread.getDownloaded() + data.length()));
                }
            }
        } catch (MalformedURLException e)
        {
            System.out.println("URL[" + stringURL + "] was not in the correct format.");
            return null;
        } catch (IOException e)
        {
            System.out.println("An IO exception occured when reading the data from " + stringURL);
            return null;
        }

        //if there was some data at that URL e.g. try a fictitious URL like http://www.sdkfjhdshjashjbasdkgh.com
        if(strBuilder.length() > 0)
        {
            httpData = new HTTPData(stringURL, strBuilder);
        }
        return httpData;
    }
}
