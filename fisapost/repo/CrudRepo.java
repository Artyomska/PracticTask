package fisapost.repo;

public interface CrudRepo<E,ID>
{
	E save(E e);
	void delete (ID id);
	E findOne(ID id);
	Iterable<E> findAll();
	int size();
}
