package concurrent;

public class RegisterStack <T> {

	public long threadId;
	public long timeStamp;
	public String method;
	public boolean begin;
	public T argument;
	public T returnValue;

	RegisterStack(long threadId, long timeStamp, String method, boolean begin, T argument, T returnValue) {
		this.threadId = threadId;
		this.timeStamp = timeStamp;
		this.method = method;
		this.begin = begin;
		this.argument = argument;
		this.returnValue = returnValue;
	}

}