package GUI;

import Core.DownloadXMLThreadTask;
import Core.FindAndDownloadTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class Controller implements ActionListener
{
    private MainView mainView;
    private FindAndDownloadTask findDownloadTask;
    private String savePath;

    public Controller(MainView mainView)
    {
        this.mainView = mainView;
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
        else if(source == this.mainView.getBtnStop())
        {
//            this.stopDownloads();
        }
        else
        {
            chooseFilePath();
        }
    }

    /*
     * Starts the process off, when enter button is pressed
     */
    private void enterPressed()
    {
//        String strURL = "http://www.sem-o.com/marketdata/Pages/PricingAndScheduling.aspx";
        String strURL = this.mainView.getTxtInput().getText();
        String strRegex = "<a href=\"([^>]+?\\.(XML|xml))\">";

        this.findDownloadTask = new FindAndDownloadTask("Downloads all the XML files of the provided URL",
                strURL, strRegex, 60000);
        this.findDownloadTask.setSavePath(this.savePath);
        this.findDownloadTask.start();
        
        ArrayList<DownloadXMLThreadTask> threadList = findDownloadTask.getThreadList();
        this.mainView.getTxtFileDisplay().append("Number of files: " + threadList.size());
        
        //adds threads to the table model
        for(DownloadXMLThreadTask thread : threadList)
        {
            this.mainView.getTableModel().addDownload(thread);
        }
        
        this.mainView.getTxtFileDisplay().append("\nFinished!!!!!");
        //this.t.stop();
    }
    
    private void stopDownloads()
    {
        this.mainView.getTxtFileDisplay().append("Downloads Stopped!");
        this.findDownloadTask.stop();
    }

    /*
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
}
