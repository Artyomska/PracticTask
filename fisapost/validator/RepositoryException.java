package fisapost.validator;

import java.io.IOException;

@SuppressWarnings("serial")
public class RepositoryException extends RuntimeException 
{
	public RepositoryException(String str) 
	{
		super(str);
	}
	
	public RepositoryException(IOException str) 
	{
		super(str);
	}
}
