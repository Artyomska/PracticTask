package fisapost.repo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.istack.internal.NotNull;

import fisapost.entities.User;
import javafx.scene.control.Alert;


public class UserManager
{

	public UserManager()
	{
		loadFromF();
	}
	
	HashMap<String,User> users=new HashMap<String,User>();
	
	private void loadFromF()
	{
		try {
			try (BufferedReader br=new BufferedReader(new FileReader("User.txt")))
			{
				String line = null;
				line=br.readLine();
				while (line!=null)
				{
						String[] elems=line.split("[|]");
						String usr=elems[0];
						users.put(usr,new User(elems[0],elems[1],elems[2]));
						line=br.readLine();
				}
			}
		}
		catch(IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	
	public int addUser(String usr,String psd)
	{
		if (!users.containsKey(usr))
		{
			String perms="READ";
			users.put(usr,new User(usr,psd,perms));
			saveToFile();
			return 1;
		}
		else
			return 0;
	}
	
	public String getPermisieUser(String usr)
	{
		User value = users.get(usr);
		return value.getPermissions();
	}
	
	public int addPermission(String usr,String valid,String parola,String perm)
	{
		if (valid.compareTo("admin")==0 && parola.compareTo(users.get("admin").getParola())==0)
		{
			if (users.containsKey(usr))
			{
				User u = users.get(usr);
				u.setPermissions(perm);
				saveToFile();
				return 1;
			}
			else
				return 2;
		}
		else
			return 3;
	}
	
	
	private void saveToFile() 
	{
	    File log = new File("User.txt");
	    try
	    {
	    	if(log.exists()==false)
	    	{
		    	System.out.println("S-a creat fisierul, deoarece nu exista");
		    	log.createNewFile();
	    	}
	    	Iterator<Entry<String, User>> it = users.entrySet().iterator();

	    	PrintWriter out = new PrintWriter(new FileWriter(log));
	    	while (it.hasNext()) 
	        {
	            Map.Entry<String,User> pair = (Map.Entry<String,User>)it.next();
		    	out.append(pair.getValue().getUser()+ "|"+ pair.getValue().getParola() + "|" + pair.getValue().getPermissions());
		    	out.append("\n");
	            it.remove();
	        }
	    	out.close();

	    }
	    catch(IOException ex)
	    {
	    	ex.printStackTrace();
	    }
	    
		loadFromF();

	}
	
	@NotNull 
	public int autentificare(String username, String passwords) 
	{
		
		User value = users.get(username);
		if (value != null)
			if (passwords.compareTo(value.getParola()) == 0)
				return 1;
			else
				return 0;
		else
		{
			return 0;
		}
	}
	
	
	static void showMessage(Alert.AlertType type, String header, String text)
    {
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    static void showErrorMessage(String text)
    {
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }

}