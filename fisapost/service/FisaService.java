package fisapost.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fisapost.entities.Elementfisa;
import fisapost.entities.Post;
import fisapost.entities.Sarcina;
import fisapost.repo.Repository;
import fisapost.util.Observable;
import fisapost.util.Observer;
import fisapost.validator.ValidatorException;

public class FisaService implements Observable<Elementfisa> 
{
	private Repository<Elementfisa, Integer> frep;
	protected List <Observer<Elementfisa>> observers = new ArrayList<Observer<Elementfisa>>();
	
	public FisaService(Repository<Elementfisa, Integer> frep) 
	{
		this.frep = frep;
	}
	
	public FisaService()
	{
		
	}
	
	public Elementfisa save(Elementfisa c) throws ValidatorException{
		Elementfisa lc = null;
		try 
		{
			lc = frep.save(c);
			if (lc != null)	
			{
				notifyObservers();
			}
		} catch (ValidatorException e) 
		{
			throw e;
		}
		return lc;
	}


	public List<Elementfisa> getAllFisa()
	{
		List<Elementfisa> s = new ArrayList<>();
		Iterable<Elementfisa> list=frep.findAll();
		for (Elementfisa f:list)
			s.add(f);
		return s;
	}

	@Override
	public void addObserver(Observer<Elementfisa> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Elementfisa> o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Elementfisa> o : observers){
			o.update(this);
		}
	}
	public Elementfisa findOne(int id) throws ValidatorException {
		Elementfisa ret = frep.findOne(id);
		if (ret != null)
			notifyObservers();
		return ret;
	}
	public Elementfisa update(Elementfisa c) throws ValidatorException {

		Elementfisa lc = frep.update(c);
		if (lc != null)
		{
			notifyObservers();
		}
		return lc;
	}
	public void delete(Elementfisa c) throws ValidatorException {
		frep.delete(c.getId());
		if (frep.findOne(c.getId()) == null)
			notifyObservers();
	}
	public List<Elementfisa> findEFAll() 
	{
		List<Elementfisa> posturi=new ArrayList<>(); 
		for(Elementfisa post: frep.findAll())
			posturi.add(post);
		return posturi;
	}
	public <E> List<E> filterGeneric(List<E> lista, Predicate<E> p) 
	{
		List<E> ret = lista.stream().filter(p).collect(Collectors.toList());
		return ret;
	}
	public List<Post> filterElementPost(Post p) 
	{
		List<Elementfisa> efuri=findEFAll();
		List<Elementfisa> all = filterGeneric(efuri, (c)->(c.getPost().getId() == p.getId()));
		ArrayList<Post> ls = new ArrayList<>();
		for (Elementfisa i : all)
			ls.add(i.getPost());
		return ls;
	}
	public List<Sarcina> filterElementSarcina(Sarcina s) 
	{
		List<Elementfisa> efuri=findEFAll();
		List<Elementfisa> all = filterGeneric(efuri, (c)->(c.getSarcina().getId() == s.getId()));
		ArrayList<Sarcina> ls = new ArrayList<>();
		for (Elementfisa i : all)
			ls.add(i.getSarcina());
		return ls;
	}
	
	public List<Sarcina> filterRapoarte(int nra)
	{
		List<Elementfisa> efuri=findEFAll();
		List<Sarcina> sarcini = new ArrayList<>();
		
		for (Elementfisa i : efuri)
		{
			sarcini.add(i.getSarcina());
		}

		final Map<Sarcina, Integer> counter = new HashMap<Sarcina, Integer>();
		for (Sarcina str : sarcini)
		    counter.put(str, 1 + (counter.containsKey(str) ? counter.get(str) : 0));

		List<Sarcina> list = new ArrayList<Sarcina>(counter.keySet());
		Collections.sort(list, new Comparator<Sarcina>() {
		    @Override
		    public int compare(Sarcina x, Sarcina y) 
		    {
		        return counter.get(y) - counter.get(x);
		    }
		  
		});
	    List<Sarcina> result = new ArrayList<Sarcina>();
	    
	    if (list.size()<nra)
	    	return Collections.emptyList();

		for (int i=0;i<nra;i++)
			result.add(list.get(i));
			
		
		return result;
	}
	
}
