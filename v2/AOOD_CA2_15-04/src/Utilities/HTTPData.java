package Utilities;

import java.util.Calendar;

public class HTTPData
{

    private String strURL; 				//where?
    private StringBuilder strHTMLData; 	//what?
    //store calendar as a long just in case we serialise to disk
    private long downloadDate; 			//when?

    public HTTPData(String strURL, StringBuilder strData)
    {
        this.strURL = strURL;

        this.strHTMLData = strData;

        this.downloadDate = Calendar.getInstance().getTimeInMillis();
    }

    public String getURL()
    {
        return strURL;
    }

    public StringBuilder getHTMLData()
    {
        return strHTMLData;
    }

    /**
     * Converts deadline data - stored as a long - back into a Calendar object
     *
     * @return Calendar object denoting the date and time upon which the data
     * was downloaded.
     */
    public Calendar getDownloadDate()
    {
        Calendar date = Calendar.getInstance();

        date.setTimeInMillis(downloadDate);

        return date;
    }

    public String getDownloadDateAsString()
    {
        Calendar date = getDownloadDate();
        return DateUtility.getCalendarAsString(date, DateUtility.Format.DATE_TIME);
    }

    public int getLength()
    {
        return strHTMLData.length();
    }

    @Override
    public String toString()
    {
        return "URL:\n" + strURL
                + "\n\nSize:\n" + this.getLength() + " bytes"
                + "\n\nDownloaded:\n" + this.getDownloadDateAsString()
                + "\n\nHTML Data:\n" + strHTMLData;
    }
}
