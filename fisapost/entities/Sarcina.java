package fisapost.entities;

import fisapost.repo.HasID;

public class Sarcina extends HasID<Integer>{

	private int id;
	private String desc;
	
	public Sarcina(int id,String desc)
	{
		this.id=id;
		this.desc=desc;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer val)
	{
		id=val;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public void setDesc(String val)
	{
		desc=val;
	}
	
	public String toString()
	{
		return id+" "+desc;
	}

	
	
}