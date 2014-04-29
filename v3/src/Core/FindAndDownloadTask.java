package Core;

import GUI.Model;
import Main.MainAppUtility;
import Utilities.RegexOutput;
import Utilities.RegexUtility;

import java.util.ArrayList;
import java.util.Timer;

public class FindAndDownloadTask extends Task
{
    public static String DEFAULT_SAVE_PATH = System.getProperty("user.home")
            + "/Desktop/Downloaded XMLS/";
    private String strURL, strRegex, savePath;
    private long delayInMs;
    private Timer timer;
    private ArrayList<DownloadXMLThreadTask> threadList;
    private volatile boolean bStop = false;
    
    private Model model;

    public FindAndDownloadTask(String description, String strURL,
            String strRegex, long delayInMs)
    {
        super(description);

        this.strURL = strURL;
        this.strRegex = strRegex;
        this.delayInMs = delayInMs;
        this.timer = new Timer(false);
        this.threadList = new ArrayList<>();
        this.savePath = DEFAULT_SAVE_PATH;
    }
    
    public FindAndDownloadTask(String description, String strURL,
            String strRegex, long delayInMs, String savePath)
    {
        super(description);
        
        this.strURL = strURL;
        this.strRegex = strRegex;
        this.delayInMs = delayInMs;
        this.timer = new Timer(false);
        this.threadList = new ArrayList<>();
        this.savePath = savePath;
    }
    
    public FindAndDownloadTask(String description, String strURL,
            String strRegex, long delayInMs, Model model)
    {
        super(description);

        this.strURL = strURL;
        this.strRegex = strRegex;
        this.delayInMs = delayInMs;
        this.timer = new Timer(false);
        this.threadList = new ArrayList<>();
        this.savePath = DEFAULT_SAVE_PATH;
        
        this.model = model;
    }
    
    public FindAndDownloadTask(String description, String strURL,
            String strRegex, long delayInMs, String savePath, Model model)
    {
        super(description);

        this.strURL = strURL;
        this.strRegex = strRegex;
        this.delayInMs = delayInMs;
        this.timer = new Timer(false);
        this.threadList = new ArrayList<>();
        this.savePath = savePath;
        
        this.model = model;
    }

    public void start()
    {
        this.execute();
    }

    /**
     * Creates the first repeated task and adds to the timer. This effectively
     * starts the whole repeat process off since in the RepeatedTask::execute()
     * method it will re-insert itself into the timer as long as bStop is false.
     */
    @Override
    public void execute()
    {
        if(!bStop)
        {
            ArrayList<RegexOutput> list = MainAppUtility.findXmlFilePaths(strURL, strRegex);

            // default download path /desktop/download xmls/
            // replace the '\' with '/' so that java can create the directories
            this.savePath = RegexUtility.replaceAll("\\\\", this.savePath, "/");
            
            // gets the start of the url (eg http://www.sem-o.com/)
            String url = RegexUtility.find("^http://([^/]+)", this.strURL);
            
            int size = list.size();
            for(int i = 0; i < size; i++)
            {
                DownloadXMLThreadTask task = new DownloadXMLThreadTask("Download thread " + i, i,
                        url, list.get(i).getData(), this.savePath, this.model);

                this.threadList.add(task);
            }
            
            //add this to the model's list
            this.model.addDownloadTask(this);
            
            // we reschedule this task again for the future
            addTaskToTimer();
            // start the tasks/threads
            initiateDownloads();
        }
    }

    /**
     * Stops the process when the user wants to exit. Calls
     * RepeatedTask::setStop() to stop the task adding itself to the timer.
     * Calls Timer::cancel() to stop the timer thread for the application.
     */
    public void stop()
    {
        stopDownloads();
        this.bStop = true;
        this.timer.cancel();
    }

    public Timer getTimer()
    {
        return timer;
    }

    public void setTimer(Timer timer)
    {
        this.timer = timer;
    }

    public long getDelayInMs()
    {
        return delayInMs;
    }

    public void setDelayInMs(int delayInMs)
    {
        this.delayInMs = delayInMs;
    }

    public String getSavePath()
    {
        return this.savePath;
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }
    

    private void addTaskToTimer()
    {
        timer.schedule(new ScheduledTask(new FindAndDownloadTask(super.getDescription(), 
                strURL, strRegex, delayInMs, savePath, this.model)), delayInMs);
    }
    
    private boolean stopDownloads()
    {
        if(!threadList.isEmpty())
        {
            for(DownloadXMLThreadTask d : threadList)
            {
                d.kill();
            }
            return true;
        }
        return false;
    }

    private boolean initiateDownloads()
    {
        if(!threadList.isEmpty())
        {
            for(DownloadXMLThreadTask d : threadList)
            {
                d.execute();
            }
            return true;
        }
        return false;
    }

    public ArrayList<DownloadXMLThreadTask> getThreadList()
    {
        return threadList;
    }
}
