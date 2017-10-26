package HW2;

@SuppressWarnings("serial")
public class QueueUnderflowException extends RuntimeException
{
	public QueueUnderflowException()
	{
		super();
	}

	public QueueUnderflowException(String message)
	{
		super(message);
	}
}