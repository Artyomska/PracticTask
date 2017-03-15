package fisapost.repo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.HashMap;

import fisapost.validator.Validator;
import fisapost.validator.ValidatorException;
import fisapost.validator.RepositoryException;
@Component
public abstract class Repository<E extends HasID<ID>,ID> implements CrudRepo<E,ID>
{
	protected HashMap<ID,E> map;
	protected Validator<E> val;
	
	Repository(Validator<E> val)
	{
		map = new HashMap<>();
		this.val=val;
	}
	
	@Cacheable(value="ecache")
	public E save(E e)
	{
		try 
		{
			val.validate(e);
		}
		catch(ValidatorException ex)
		{
			throw new RepositoryException(ex.toString());
		}
		map.put(e.getId(),e);
		for (E e1:findAll())
			if (e1.getId().equals(e.getId()))
				return e;
		return null;
	}
	
	@CacheEvict(value = "ecache", beforeInvocation = true)
	@Cacheable(value = "ecache")
	public void delete(ID id)
	{
		map.remove(id);
	}
	
	public int size() 
	{
		return map.size();
	}
	
	public E findOne(ID id)
	{
		return map.get(id);
	}
	
	@CacheEvict(value = "ecache", beforeInvocation = true)
	@Cacheable(value = "ecache")
	public Iterable<E> findAll()
	{
		return map.values();
	}
	
	@CacheEvict(value = "ecache", beforeInvocation = true)
	@Cacheable(value = "ecache")
	public E update (E e) 
	{
		this.delete(e.getId());
		return this.save(e);
	}

}


	