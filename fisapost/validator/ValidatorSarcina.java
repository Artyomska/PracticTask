package fisapost.validator;

import fisapost.validator.Validator;
import fisapost.validator.ValidatorException;
import fisapost.entities.Sarcina;;

public class ValidatorSarcina implements Validator<Sarcina> 
{
	public void validate(Sarcina s) throws ValidatorException
	{ 
		
		StringBuilder msg=new StringBuilder();
		if (null==s)
			msg.append("Sarcina e nula");
		else if (s.getId()<=0)
			msg.append("ID invalid");
		else if (s.getDesc().equals(""))
			msg.append("Numele este gol");
		if (msg.length() > 0)
			throw new ValidatorException(msg.toString());
	}
}
