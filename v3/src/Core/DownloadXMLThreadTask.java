package Core;

import Enums.DownloadStatus;
import GUI.Model;
import Utilities.FileIOUtility;
import Utilities.HTTPData;
import Utilities.HTTPUtility;
import Utilities.RegexUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DownloadXMLThreadTask extends Task implements Runnable
{
	private String url, xmlURL, rootSavePath, actualPath;
	private int id, fileSize, downloaded;
	private Thread t;
	private DownloadStatus status = DownloadStatus.NONE;
	private volatile boolean bStop = false;

	private Model model;

	// timer, delayInMs, id, msg, url, xmlUrl, downloadPath);
	public DownloadXMLThreadTask(String description, int id, String url,
			String xmlURL, String rootSavePath)
	{
		super(description);

		this.url = url;
		this.xmlURL = xmlURL;
		this.rootSavePath = rootSavePath;
		this.id = id;
		this.fileSize = 0;
		this.downloaded = 0;
	}

	public DownloadXMLThreadTask(String description, int id, String url,
			String xmlURL, String rootSavePath, Model model)
	{
		super(description);

		this.url = url;
		this.xmlURL = xmlURL;
		this.rootSavePath = rootSavePath;
		this.id = id;
		this.fileSize = 0;
		this.downloaded = 0;

		this.model = model;
	}

	@Override
	public void execute()
	{
		this.t = new Thread(this);
		this.t.setDaemon(false); // with this, the thread wont continue past the
									// MainApp
		this.status = DownloadStatus.DOWNLOADING;
		this.t.start();
	}

	public DownloadStatus getStatus()
	{
		return status;
	}

	public void setStatus(DownloadStatus status)
	{
		this.status = status;
	}

	public String getStatusStr()
	{
		return status.getValue();
	}

	public int getFileSize()
	{
		return fileSize;
	}

	public String getXmlURL()
	{
		return xmlURL;
	}

	@Override
	public void run()
	{
		if (!bStop)
		{
			System.out.println(" -Thread " + id + " downloading...");
			this.xmlURL = RegexUtility.replaceAll("\\s", xmlURL, "%20");

			// method to get header length (file size)
			this.fileSize = HTTPUtility.httpRequestSize(this.url + this.xmlURL);
			// passes thread in for progress details
			HTTPData httpData = HTTPUtility.download(url + xmlURL, this);

			this.fileSize = httpData.getLength();

			this.xmlURL = RegexUtility.replaceAll("[/\\\\]", xmlURL, "+");

			// create the root dir if not existing already
			File root = new File(rootSavePath);
			if (!root.exists())
				root.mkdir();

			// create a child dir, name it by the date the xml's were downloaded
			File saveFile = new File(rootSavePath + httpData.getDownloadDateAsString());
			if (!saveFile.exists())
				saveFile.mkdir();

			this.actualPath = saveFile.getAbsolutePath() + "\\" + xmlURL;
			try
			{
				FileIOUtility.writeFileBuffer(httpData.getHTMLData().toString(), actualPath, FileIOUtility.UTF_8);
			}
			catch (FileNotFoundException e)
			{
				this.status = DownloadStatus.FAILED;
				System.out.println("Error(FNF): " + e.getMessage());
			}
			catch (IOException e)
			{
				this.status = DownloadStatus.FAILED;
				System.out.println("Error(IO): " + e.getMessage());
			}

			this.status = DownloadStatus.COMPLETED;
			System.out.println("Thread " + id + " finished!");
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

	public float getProgress()
	{
		return ((float) downloaded / this.fileSize) * 100;
	}

	public void setDownloaded(int downloaded)
	{
		this.downloaded = downloaded;
		this.model.notifyProgressObserver(this);
	}

	public int getDownloaded()
	{
		return this.downloaded;
	}

	@Override
	public String toString()
	{
		return super.toString() + "\nStatus: " + getStatusStr() + "\nURL: "
				+ this.url + "\nXML URL: " + this.xmlURL + "\nRoot folder: "
				+ this.rootSavePath + "\n";
	}
}
