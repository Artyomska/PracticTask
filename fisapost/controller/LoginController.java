package fisapost.controller;


import fisapost.main.PrimaryLayoutMain;
import fisapost.repo.UserManager;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController 
{
	  UserManager manager;
	  @FXML private TextField user;
	  @FXML private PasswordField password;
	  @FXML private Button loginButton;
	  @FXML private Button registerButton;
	  @FXML private Button modificaButton;
	  @FXML private TextField modUser;
	  @FXML private TextField permis;
	  @FXML private ToggleButton menuButton;
	  @FXML private Label l1;
	  @FXML private Label l2;
	  
	  
	  public void initialize() 
	  {
		  password.setPromptText("Your password");
		  user.setPromptText("Your username");
		  manager=new UserManager();
		  modUser.setVisible(false);
		  permis.setVisible(false);
		  modificaButton.setVisible(false);
		  l1.setVisible(false);
		  l2.setVisible(false);
		  
		  
	  }
	  
	  public void initManager(final PrimaryLayoutMain loginManager) 
	  {
		  loginButton.setOnAction(new EventHandler<ActionEvent>() 
		  {
			  @Override 
			  public void handle(ActionEvent event) 
			  {
				  String sessionID = login();
				  if (sessionID.compareTo("error")!=0) 
				  {
					  loginManager.authenticated(sessionID);
				  }
				  else
				  {
					 showErrorMessage("Nume invalid sau parola invalida");
					 user.setText("");
					 password.setText("");
				  }
			  }
		  });
	  }
  
	  @FXML private void registerUser()
	    {
	        String username = user.getText();
	        String passwords = password.getText();
	            if (username.isEmpty())
	            {
	            	showErrorMessage("Dati un username");
	            }
	            else if (passwords.isEmpty())
	            {
	            	showErrorMessage("Dati o parola");
	            }         
	            else
	            {
	                int i=manager.addUser(username,passwords);
	                if (i==1)
	                	showMessage(Alert.AlertType.CONFIRMATION,"Succes","Succes");
	                else
	                	showErrorMessage("User-ul deja exista");
	            }
	     }

	    @FXML private void handleModifica()
	    {
	    	 String valid = user.getText();
		     String passwords = password.getText();
		     String userc = modUser.getText();
		     String perm = permis.getText();
		     
	            if (valid.isEmpty())
	            {
	            	showErrorMessage("Dati numele administratorului");
	            }
	            else if (passwords.isEmpty())
	            {
	            	showErrorMessage("Dati o parola");
	            }  
	            else if (userc.isEmpty())
	            {
	            	showErrorMessage("Dati userul ce va fi modificat");
	            }  
	            else if (perm.isEmpty())
	            {
	            	showErrorMessage("Dati o permisiune");
	            }
	            else if (perm.compareTo("ALL")!=0 && perm.compareTo("READ")!=0 && perm.compareTo("WRITE")!=0)
	            {
	            	showErrorMessage("Permisiune invalida. Dati ALL/READ/WRITE");
	            }
	            else
	            {
	                int i=manager.addPermission(userc,valid,passwords,perm);
	                if (i==3)
	                	showErrorMessage("Eroare, userul dat nu e administrator sau parola lui e gresita!");
	                else if(i==2)	                	
	                	showErrorMessage("User-ul nu exista");
	                else if(i==1)
	                	showMessage(Alert.AlertType.CONFIRMATION,"Succes","Succes"); 	
	            }
				 user.setText("");
				 password.setText("");
				 permis.setText("");
				 modUser.setText("");
	    }
	   
	    @FXML void handleMenu()
	    {
	    	boolean isSelected = menuButton.isSelected();
	    	if (isSelected)
	    	{
				  modUser.setVisible(true);
				  permis.setVisible(true);
				  modificaButton.setVisible(true);
				  l1.setVisible(true);
				  l2.setVisible(true);
	    	}
	    	else
	    	{
				  modUser.setVisible(false);
				  permis.setVisible(false);
				  modificaButton.setVisible(false);
				  l1.setVisible(false);
				  l2.setVisible(false);
	    	}
	    }
	    
	    private String login()
	    {
	        String username = user.getText();
	        String passwords = password.getText();
	        if (manager.autentificare(username,passwords)==1)
	        {
	            return manager.getPermisieUser(username);
	        }
	        else
	        	return "error";
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
