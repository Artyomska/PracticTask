package fisapost.validator;

import fisapost.entities.Elementfisa;

public class ValidatorEF implements Validator<Elementfisa>
{
	public void validate(Elementfisa e) throws ValidatorException
	{ 
		String msg="";
		if (null==e)
			msg+="EF e nul";
		else if (e.getId()<=0)
			msg+="ID invalid";
		else if (e.getPost()==null)
			msg+="Postul este gol";
		else if (e.getSarcina()==null)
			msg+="Sarcina este goala";
		if (msg!="")
			throw new ValidatorException(msg);
	}
}
