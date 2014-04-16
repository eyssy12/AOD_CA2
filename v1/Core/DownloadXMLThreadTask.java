package Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Enums.DownloadStatus;
import Utilities.FileIOUtility;
import Utilities.HTTPData;
import Utilities.HTTPUtility;
import Utilities.RegexUtility;

public class DownloadXMLThreadTask extends Task implements Runnable
{
	private String url, xmlURL, rootSavePath, actualPath;
	private int id;
	private Thread t;
	private DownloadStatus status = DownloadStatus.NONE;
	
	private volatile boolean bStop = false;
	
	// timer, delayInMs, id, msg, url, xmlUrl, downloadPath);
	public DownloadXMLThreadTask(String description, int id,
			String url, String xmlURL, String rootSavePath) 
	{
		super(description);
		
		this.url = url;
		this.xmlURL = xmlURL;
		this.rootSavePath = rootSavePath;
		this.id = id;
	}

	@Override
	public void execute()
	{
		this.t = new Thread(this);
		this.t.setDaemon(false); // with this, the thread wont continue past the MainApp
		this.status = DownloadStatus.DOWNLOADING;
		this.t.start();
	}
	
	public DownloadStatus getStatus() {
		return status;
	}

	public void setStatus(DownloadStatus status) {
		this.status = status;
	}
	
	public String getStatusStr() {
		return status.getValue();
	}

	@Override
	public void run() 
	{
		if(!bStop)
		{
			System.out.println(" -Thread " + id + " downloading...");
			xmlURL = RegexUtility.replaceAll("\\s", xmlURL, "%20");
			
			HTTPData httpData = HTTPUtility.download(url+xmlURL);
			xmlURL = RegexUtility.replaceAll("[/\\\\]", xmlURL, "+");
			
			// create the root dir if not existing already
			File root = new File(rootSavePath);
			if(!root.exists())
				root.mkdir();
			
			// create a child dir, name it by the date the xml's we're downloaded
			File saveFile = new File(rootSavePath + httpData.getDownloadDateAsString());
			if(!saveFile.exists())
				saveFile.mkdir();

			this.actualPath = saveFile.getAbsolutePath() + "\\" + xmlURL;
			try
			{
				FileIOUtility.writeXML(httpData.getHTMLData().toString(), actualPath);
			}
			catch (FileNotFoundException e)
			{
				this.status = DownloadStatus.FAILED;
				System.out.println("Error(FNF): " + e.getMessage());
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				this.status = DownloadStatus.FAILED;
				e.printStackTrace();
			}
			
			this.status = DownloadStatus.COMPLETED;
			System.out.println("Thread " + id +" finished!");
		}
		else
		{
			this.status = DownloadStatus.CANCELED;
		}
	}
	
	public void kill()
	{
		this.bStop = true;
	}
	
	@Override
	public String toString()
	{
		return super.toString() +
				"\nStatus: " + getStatusStr() +
				"\nURL: " + this.url +
				"\nXML URL: " + this.xmlURL +
				"\nRoot folder: " + this.rootSavePath +
				"\n";
	}
}
