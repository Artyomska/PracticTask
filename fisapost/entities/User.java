package fisapost.entities;



public class User {

	private String user;
	private String parola;
	private String permissions;
	
	public User(String usr,String psd, String perm)
	{
		this.user=usr;
		this.parola=psd;
		this.permissions = perm;
	}
	
	public String getUser()
	{
		return user;
	}
	
	public void setUser(String val)
	{
		user=val;
	}
	
	public String getParola()
	{
		return parola;
	}
	
	public void setParola(String val)
	{
		parola=val;
	}
	

	public String getPermissions()
	{
		return permissions;
	}
	
	public void setPermissions(String val)
	{
		permissions=val;
	}
    
	public String toString()
	{
		return user+" "+parola;
	}

	
	
}

	
	
