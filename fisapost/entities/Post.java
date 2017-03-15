package fisapost.entities;


import fisapost.repo.HasID;

public class Post extends HasID<Integer> 
{

	private int id;
	private String nume, tip;
	
	public Post(int id,String nume,String tip)
	{
		this.id=id;
		this.nume=nume;
		this.tip=tip;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer val)
	{
		id=val;
	}
	
	public String getNume()
	{
		return nume;
	}
	
	public void setNume(String val)
	{
		nume=val;
	}	
	
	public String getTip()
	{
		return tip;
	}
	
	public void setTip(String val)
	{
		tip=val;
	}
	
	public String toString()
	{
		return id+" "+nume+" "+tip;
	}
		
}
