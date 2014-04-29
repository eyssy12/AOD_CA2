package GUI;

import Core.DownloadXMLThreadTask;
import Core.FindAndDownloadTask;
import Interfaces.IMyObserver;
import Utilities.GUIUtility;
import Utilities.HTTPUtility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Controller implements ActionListener, IMyObserver
{
	private static int DATE_NOT_SELECTED = 0;
	private static int TIME_BELOW_ALLOWED = 1;
	private static int TIME_IN_THE_PAST = 2;
	private static int LOWEST_REPEAT_TIME_MS = 10000;
	
	private Model model;
    private MainView mainView;
    private FindAndDownloadTask findDownloadTask;
    private String savePath;

    public Controller(Model model)
    {
    	this.model = model;
    	this.model.addObserver(this);
    	
        this.mainView = new MainView(this, model);
//        GUIUtility.setLookAndFeel(this.mainView);
        
        this.mainView.setResizable(false);
        this.mainView.setVisible(true);
        setSavePath(FindAndDownloadTask.DEFAULT_SAVE_PATH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        final Object source = e.getSource();

        if(source == this.mainView.getBtnEnter())
        {
            this.enterPressed();
        }
        if(source == this.mainView.getBtnStop())
        {
        	this.stopDownloads();
        }
        if(source == this.mainView.getBtnSelectPath())
        {
            chooseFilePath();
        }
    }

    /**
     * Starts the process off, when the enter button is pressed
     */
    private void enterPressed()
    {
    	//http://www.sem-o.com/marketdata/Pages/PricingAndScheduling.aspx
        String strURL = this.mainView.getTxtInput().getText();
        
        // test the url
        if(!HTTPUtility.checkURL(strURL))
        {
        	this.displayMessageDialog("Error - ULR does not exist!");
        	return;
        }
        
        // check if a date has been chosen
        long delayInMs = this.getDelayTime();
        if(delayInMs == TIME_IN_THE_PAST)
        {
        	this.displayMessageDialog("*** You can't select a date in the past! ***");
        	return;
        }
        else if(delayInMs == TIME_BELOW_ALLOWED)
        {
        	this.displayMessageDialog("*** You must allow the program to perform the task"
        			+ " for more than "+(LOWEST_REPEAT_TIME_MS/1000)+ " seconds! ***");
        	return;
        }
        else if(delayInMs == DATE_NOT_SELECTED)
        {
        	this.displayMessageDialog("*** You must select a date! ***");
        	return;
        }

        // we're ok by this stage
        String strRegex = "<a href=\"([^>]+?\\.(XML|xml))\">";
        this.findDownloadTask = new FindAndDownloadTask("Downloads all the XML files of the provided URL",
                strURL, strRegex, delayInMs, this.model);
        this.findDownloadTask.setSavePath(this.savePath);
        this.findDownloadTask.start();
        
        ArrayList<DownloadXMLThreadTask> threadList = this.findDownloadTask.getThreadList();
        JTextArea txtFileDisplay = this.mainView.getTxtFileDisplay();
        txtFileDisplay.append("Website URL: " + strURL);
        txtFileDisplay.append("\nNumber of files: " + threadList.size());
        txtFileDisplay.append("\nTimer set to " + delayInMs + "milliseconds.");
        txtFileDisplay.append("\nDownloads have started.");
    }
    
    private void stopDownloads()
    {
        this.mainView.getTxtFileDisplay().append("Downloads Stopped!");
        this.displayMessageDialog("Download Process has been stopped");
        this.findDownloadTask.stop();
    }

    /**
     * Lets user choose path and sets the save path of the downloads
     */
    private void chooseFilePath()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(this.savePath));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        File chosenFilePath = chooser.getSelectedFile();
        StringBuilder temp = new StringBuilder();
        if(chosenFilePath != null)
        {
            temp.append(chosenFilePath.getPath());
            if(!temp.substring(temp.length()-1).equalsIgnoreCase("\\"))
            {
                temp.append("\\");
                System.out.println("TEMP: " + temp);
            }
            setSavePath(temp.toString());
        }
    }
    
    /**
     * Sets values for views components on startup
     */
    private void setSavePath(String savePath)
    {
        this.savePath = savePath;
        this.mainView.getLblPathDisplay().setText(this.savePath);
    }
    
    /**
     * Converts the user's input to milliseconds
     * 
     * @return The delay time from the user's input or -1 if none inputed
     */
    private long getDelayTime()
    {
    	if(this.mainView.getDatePicker().getDate() == null)
    		return 0;
    		
    	Calendar currDate = Calendar.getInstance();
    	Calendar targetDate = Calendar.getInstance();
    	
    	int hour = (int)this.mainView.getSpnrHour().getValue();
    	int min = (int)this.mainView.getSpnrMinute().getValue();
    	
    	targetDate.setTime(this.mainView.getDatePicker().getDate());
    	targetDate.set(Calendar.HOUR, hour);
    	targetDate.set(Calendar.MINUTE, min);
    	
    	long delayTime = getDateDiff(currDate.getTime(), targetDate.getTime(), TimeUnit.MILLISECONDS);
    	System.out.println(delayTime);
    	
    	// for our purposes, we cant allow the task to perform less than every 10 seconds
    	// because the downloads can take a lot longer than that
    	
    	// check if not less than 10 secs
    	if(delayTime > 0 && delayTime <= LOWEST_REPEAT_TIME_MS)
    		return 1;
    	
    	// if the date is in the past
    	if(delayTime <= 0)
    		return 2;
    	
    	// otherwise, we're ok
    	return delayTime;
    }
    
    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit format in which you want the diff calculated in to
     * @return the diff value, in the provided unit
     */
    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit)
    {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    
    /**
     * Adds download threads to the table
     */
    private void updateTableDisplay()
    {
    	this.findDownloadTask = this.model.getLastDownloadTask();
    	ArrayList<DownloadXMLThreadTask> threadList = this.findDownloadTask.getThreadList();
    	
        //adds threads to the table model
        for(DownloadXMLThreadTask thread : threadList)
        {
            this.mainView.getTableModel().addDownload(thread);
        }
        this.displayMessageDialog("Downloads have started!");
    }
    
    /**
     * Creates a message dialog with passed in message.
     * 
     * @param message
     */
    private void displayMessageDialog(String message)
    {
    	JOptionPane.showMessageDialog(null, message);
    }

	@Override
	public void update()
	{
		updateTableDisplay();
	}

	@Override
	public void update(Object o) 
	{	
	}
}
