package Utilities;
/**
 * Provides a wrapper class i.e. a class to hold more than one value
 *
 * @author mcguinnn
 * @version 1.0
 * @since 7/1/14
 */
public class RegexOutput 
{
	private String data;
	private int start, end, length;
	
	public RegexOutput(String data, int start, int end)
	{
		this.data = data;
		this.start = start; //position where we found match
		this.end = end;     //position where the match finished
		this.length = end - start;
	}

	public String getData() {
		return data;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getLength() {
		return length;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}

	@Override
	public String toString() {
		return "RegexOutput [data=" + data + ", start=" + start + ", end="
				+ end + ", length=" + length + "]";
	}
	
	
	
}







