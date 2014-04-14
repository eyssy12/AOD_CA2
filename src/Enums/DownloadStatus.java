package Enums;

public enum DownloadStatus 
{
	NONE("None"),
	COMPLETED("Completed"),
	DOWNLOADING("Downloading"),
	FAILED("Failed"),
	CANCELED("Canceled");
	
	private String value;
	private DownloadStatus(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
}
