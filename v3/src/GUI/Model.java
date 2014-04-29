package GUI;

import java.util.ArrayList;

import Core.DownloadXMLThreadTask;
import Core.FindAndDownloadTask;
import Interfaces.IModel;
import Interfaces.IMyObserver;

public class Model implements IModel
{
	private final ArrayList<IMyObserver> observers = new ArrayList<IMyObserver>();
	private ThreadTableModel progressObserver;
	private ArrayList<FindAndDownloadTask> downloadTaskList;
	
    public Model()
    {
    	this.downloadTaskList = new ArrayList<FindAndDownloadTask>();
    }

    @Override
    public void addObserver(IMyObserver observer)
    {
        this.observers.add(observer);
    }

    @Override
    public void deleteObserver(IMyObserver observer)
    {
        this.observers.remove(observer);
    }

    @Override
    public void deleteObservers()
    {
        this.observers.clear();
    }

    @Override
    public void notifyObservers()
    {
        for (int i = 0; i < this.observers.size(); i++)
        {
            final IMyObserver observer = this.observers.get(i);
            observer.update();
        }
    }
    
    public void addProgressObserver(ThreadTableModel progressObserver)
    {
    	this.progressObserver = progressObserver;
    }
    
    public void notifyProgressObserver(DownloadXMLThreadTask downloadThread)
    {
    	this.progressObserver.update(downloadThread);
    }

    public void refreshData()
    {
        this.notifyObservers();
    }
    
    public void addDownloadTask(FindAndDownloadTask downloadTask)
    {
    	if(downloadTask != null)
    	{
    		this.downloadTaskList.add(downloadTask);
    		this.refreshData();
    	}
    }
    
    public int getDownloadTaskSize()
    {
    	return this.downloadTaskList.size();
    }
    
    public FindAndDownloadTask getDownloadTask(int index)
    {
    	if(this.downloadTaskList != null)
    	{	
    	    return this.downloadTaskList.get(index);
    	}
    	return null;
    }
    
    public FindAndDownloadTask getLastDownloadTask()
    {
    	return this.getDownloadTask((this.getDownloadTaskSize() - 1));
    }

}
