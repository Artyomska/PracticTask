package fisapost.validator;


import fisapost.validator.Validator;
import fisapost.validator.ValidatorException;
import fisapost.entities.Post;


public class ValidatorPost implements Validator<Post> 
{
	public void validate(Post p) throws ValidatorException
	{ 
		StringBuilder msg=new StringBuilder();
		if (null==p)
			msg.append("Postul e nul");
		else if (p.getId()<=0)
			msg.append("ID invalid");
		else if (p.getNume().equals(""))
			msg.append("Numele este gol");
		else if (p.getTip().equals(""))
			msg.append("Tipul este gol");
		if (msg.length() > 0)
			throw new ValidatorException(msg.toString());
	}
}

