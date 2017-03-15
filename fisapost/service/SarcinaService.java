package fisapost.service;

import fisapost.validator.ValidatorException;
import fisapost.repo.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fisapost.entities.Sarcina;
import fisapost.util.Observable;
import fisapost.util.Observer;


public class SarcinaService implements Observable<Sarcina> 
{
	private Repository<Sarcina, Integer> frep;
	protected List <Observer<Sarcina>> observers = new ArrayList<Observer<Sarcina>>();
	
	public SarcinaService(Repository<Sarcina, Integer> frep) 
	{
		this.frep = frep;
	}
	
	public SarcinaService()
	{
		
	}
	public Sarcina save(Sarcina c) throws ValidatorException{
		Sarcina lc = null;
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
			throw e;
		}
		return lc;
	}


	public List<Sarcina> getAllSarcinas()
	{
		List<Sarcina> s = new ArrayList<>();
		Iterable<Sarcina> list=frep.findAll();
		for (Sarcina f:list)
			s.add(f);
		return s;
	}

	@Override
	public void addObserver(Observer<Sarcina> o) 
	{
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer<Sarcina> o) 
	{
		observers.remove(o);
	}

	@Override
	public void notifyObservers(){
		for(Observer<Sarcina> o : observers)
		{
			o.update(this);
		}
	}

	public Sarcina update(Sarcina c) throws ValidatorException {

		Sarcina lc = frep.update(c);
		if (lc != null)
		{
			notifyObservers();
		}
		return lc;
	}
	public void delete(Sarcina c) throws ValidatorException {
		frep.delete(c.getId());
		if (frep.findOne(c.getId()) == null)
			notifyObservers();
	}
	public Sarcina findOne(int id) throws ValidatorException {
		Sarcina ret = frep.findOne(id);
		if (ret != null)
			notifyObservers();
		return ret;
	}
	public <E> List<E> filterGeneric(List<E> lista, Predicate<E> p) 
	{
		List<E> ret = lista.stream().filter(p).collect(Collectors.toList());
		return ret;
	}
	public List<Sarcina> filterSarcinaDesc(String what) 
	{
		Iterable<Sarcina> it = frep.findAll();
		List<Sarcina> sarcini = new ArrayList<Sarcina>();
		for (Sarcina s : it)
			sarcini.add(s);
		return filterGeneric(sarcini, (c)->c.getDesc().contains(what));
	}
	public List<Sarcina> filterSarcinaId(String what) 
	{
		int id = Integer.parseInt(what);
		Iterable<Sarcina> it = frep.findAll();
		List<Sarcina> sarcini = new ArrayList<Sarcina>();
		for (Sarcina s : it)
			sarcini.add(s);
		return filterGeneric(sarcini, (c)->c.getId() == id);
	}
}
