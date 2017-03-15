package fisapost.entities;

import fisapost.repo.HasID;

public class Elementfisa extends HasID<Integer> {

	private int id;
	Post post;
	Sarcina sarcina;
	
	public Elementfisa(int id,Post post,Sarcina sarcina)
	{
		this.id=id;
		this.post=post;
		this.sarcina=sarcina;
	}

	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer val)
	{
		id=val;
	}
	
	public Post getPost()
	{
		return post;
	}
	
	public void setPost(Post val)
	{
		post=val;
	}	
	
	public Sarcina getSarcina()
	{
		return sarcina;
	}
	
	public void setSarcina(Sarcina val)
	{
		sarcina=val;
	}
	
	public String toString()
	{
		return id+"|idFilm:"+post.getId()+"|idClient:"+sarcina.getId();
	}

}
