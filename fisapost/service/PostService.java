package fisapost.service;

import fisapost.validator.RepositoryException;
import fisapost.validator.ValidatorException;
import fisapost.repo.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fisapost.entities.Post;
import fisapost.util.Observable;
import fisapost.util.Observer;


public class PostService implements Observable<Post> 
{
	private Repository<Post, Integer> frep;
	protected List <Observer<Post>> observers = new ArrayList<Observer<Post>>();
	
	public PostService(Repository<Post, Integer> frep) 
	{
		this.frep = frep;
	}
	
	public PostService()
	{
		
	}
	
	public Post save(Post c) throws ValidatorException	{
		
		Post lc = null;
		try 
		{
			lc = frep.save(c);
			if (lc != null)	
			{
				notifyObservers();
			}
		}
		catch (ValidatorException e) 
		{
			throw new RepositoryException(e.toString());
		}
		
		return lc;
		
	}


	public List<Post> getAllPosts()
	{
		List<Post> s = new ArrayList<>();
		Iterable<Post> list=frep.findAll();
		for (Post f:list)
			s.add(f);
		return s;
	}

	@Override
	public void addObserver(Observer<Post> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Post> o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Post> o : observers){
			o.update(this);
		}
	}

	public Post update(Post c) throws ValidatorException {

		Post lc = frep.update(c);
		if (lc != null)
		{
			notifyObservers();
		}
		return lc;
	}
	public void delete(Post c) throws ValidatorException {
		frep.delete(c.getId());
		if (frep.findOne(c.getId()) == null)
			notifyObservers();
	}
	public Post findOne(int id) throws ValidatorException {
		Post ret = frep.findOne(id);
		if (ret != null)
			notifyObservers();
		return ret;
	}
	public <E> List<E> filterGeneric(List<E> lista, Predicate<E> p) 
	{
		List<E> ret = lista.stream().filter(p).collect(Collectors.toList());
		return ret;
	}
	
	public List<Post> filterPostNume(String dr) 
	{
		Iterable<Post> it = frep.findAll();
		List<Post> posturi = new ArrayList<Post>();
		for (Post p : it)
			posturi.add(p);
		return filterGeneric(posturi, (c)->c.getNume().contains(dr));
	}
	public List<Post> filterPostTip(String dr) 
	{
		Iterable<Post> it = frep.findAll();
		List<Post> posturi = new ArrayList<Post>();
		for (Post p : it)
			posturi.add(p);
		return filterGeneric(posturi, (c)->c.getTip().contains(dr));
	}
}
