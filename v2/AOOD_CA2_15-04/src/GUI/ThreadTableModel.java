package GUI;


import Core.DownloadXMLThreadTask;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Aidan
 */
public class ThreadTableModel extends AbstractTableModel
{
    private static final String[] columnNames =
    {
        "File Name", "Status", "Size", "Progress"
    };
    private static final Class[] columnClasses =
    {
        String.class, String.class, Integer.class,
        MyProgressBar.class
    };
    private ArrayList<DownloadXMLThreadTask> downloadList 
            = new ArrayList<DownloadXMLThreadTask>();

    public void addDownload(DownloadXMLThreadTask download)
    {
        this.downloadList.add(download);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public DownloadXMLThreadTask getDownload(int row)
    {
        return (DownloadXMLThreadTask) downloadList.get(row);
    }

    public void clearDownload(int row)
    {
        this.downloadList.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public int getColumnCount()
    {
        return this.columnNames.length;
    }

    public String getColumnName(int col)
    {
        return this.columnNames[col];
    }

    public Class getColumnClass(int col)
    {
        return this.columnClasses[col];
    }

    public int getRowCount()
    {
        return this.downloadList.size();
    }

    public Object getValueAt(int row, int col)
    {
        DownloadXMLThreadTask downloadXMLTask = this.downloadList.get(row);
        switch (col)
        {
            case 0:
                return downloadXMLTask.getXmlURL();
            case 1:
                return downloadXMLTask.getStatusStr();
            case 2:
                return downloadXMLTask.getFileSize();
            case 3:
                return new Float(downloadXMLTask.getProgress());
        }
        return null;
    }
}
