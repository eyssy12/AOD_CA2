package Core;
import Interfaces.ITask;


/**
 * Creates a task object that can be added to a timer.
 * The execute() simply prints out the data.
 * @author mcguinnn
 *
 */
public class Task implements ITask 
{
	private String description;

	public Task(String description)
	{
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void execute() 
	{
		System.out.println(this.toString());
	}

	@Override
	public String toString()
	{
		return "\nTask description: " + this.description;
	}
	
}












