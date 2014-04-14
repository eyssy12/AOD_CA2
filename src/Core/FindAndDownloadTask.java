package Core;

import java.util.ArrayList;
import java.util.Timer;

import Main.MainAppUtility;
import Utilities.RegexOutput;
import Utilities.RegexUtility;

public class FindAndDownloadTask extends Task
{
	public static final String HOME_USER_PATH = System.getProperty("user.home");
	
	private String strURL, strRegex;
	private int delayInMs;
	private Timer timer;	
	private ArrayList<DownloadXMLThreadTask> threadList;
	
	private volatile boolean bStop = false;

	public FindAndDownloadTask(String description, String strURL,
			String strRegex, int delayInMs)
	{
		super(description);
		
		this.strURL = strURL;
		this.strRegex = strRegex;
		this.delayInMs = delayInMs;
		this.timer = new Timer(false);
		this.threadList = new ArrayList<>();
		
	}
	
	public void start()
	{
		execute();
	}
	
	/**
	 * Creates the first repeated task and adds to the timer.
	 * This effectively starts the whole repeat process off
	 * since in the RepeatedTask::execute() method it will
	 * re-insert itself into the timer as long as bStop is false.
	 */
	@Override
	public void execute()
	{
		if(!bStop)
		{
			ArrayList<RegexOutput> list = MainAppUtility.findXmlFilePaths(strURL, strRegex);
			
			// default download path /desktop/download xmls/
			// replace the '\' with '/' so that java can create the directories
			String downloadPath = RegexUtility.replaceAll("\\\\", HOME_USER_PATH + "/Desktop/Downloaded XMLS/", "/");
			int size = list.size();
			for(int i = 0; i < size; i++)
			{
				DownloadXMLThreadTask task = new DownloadXMLThreadTask("Download thread " + i, i,
						MainAppUtility.URL, list.get(i).getData(), downloadPath);

				this.threadList.add(task);
			}
			
			// we reschedule this task again for the future
			addTaskToTimer();
			// start the tasks/threads
			initiateDownloads();
		}
	}
	
	/**
	 * Stops the process when the user wants to exit.
	 * Calls RepeatedTask::setStop() to stop the task adding itself to the timer.
	 * Calls Timer::cancel() to stop the timer thread for the application.
	 */
	public void stop()
	{
		stopDownloads();
		this.bStop = true;
		this.timer.cancel();
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public int getDelayInMs() {
		return delayInMs;
	}

	public void setDelayInMs(int delayInMs) {
		this.delayInMs = delayInMs;
	}
	
	private void addTaskToTimer()
	{
		timer.schedule(new ScheduledTask(new FindAndDownloadTask(super.getDescription(), strURL, strRegex, delayInMs)), delayInMs);
	}
	
	private boolean stopDownloads()
	{
		if(!threadList.isEmpty())
		{
			for(DownloadXMLThreadTask d : threadList)
				d.kill();
			
			return true;
		}
		
		return false;
	}
	
	private boolean initiateDownloads()
	{
		if(!threadList.isEmpty())
		{
			for(DownloadXMLThreadTask d : threadList)
				d.execute();
			
			return true;
		}
		
		return false;
	}

}
