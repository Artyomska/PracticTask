package fisapost.validator;

public interface Validator <E>
{
	public void validate(E e) throws ValidatorException;
}
