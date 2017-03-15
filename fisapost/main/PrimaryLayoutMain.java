package fisapost.main;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fisapost.validator.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import fisapost.repo.*;
import fisapost.service.*;
import fisapost.controller.AllController;
import fisapost.controller.LoginController;

import java.io.File;
import java.io.IOException;
import java.net.URL;


@EnableCaching
public class PrimaryLayoutMain extends Application {

	AnchorPane apview;
	AnchorPane apview2;
    BorderPane rootLayout;
    Stage primaryStage;
    
    SarcinaXMLRepo srep;
    SarcinaService sservice;
    PostXMLRepo prep;
    PostService pservice;
    EFXMLRepo frep;
    FisaService fservice;
    
    FXMLLoader loader;

    
    @Override
    public void start(Stage primaryStage) throws ValidatorException 
    {
    	try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml")) 
    	{
            sservice = (SarcinaService) context.getBean("sarcinaService");
            pservice = (PostService) context.getBean("postService");
            fservice = (FisaService) context.getBean("fisaService");
    	}

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Management de sarcini.");
        
        loader = new FXMLLoader();

        initRootLayout();  
        showLoginScreen();    
    }

	  public void authenticated(String perm) 
	  {
	        initView(perm); 
	  }
	
	  
	  public void showLoginScreen() 
	  {
		  try 
		  {
			  	String pathToFxml = "src/fisapost/view/LoginWindow.fxml";
	            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
	            loader.setLocation(fxmlUrl);
	            apview2 = (AnchorPane) loader.load();
	            rootLayout.setCenter(apview2);
	            
		    	LoginController controller = loader.getController();
		    	controller.initManager(this);
	    	
	    } 
		 
	    catch (IOException ex) 
	    {
	    	ex.printStackTrace();
	    }
	  }
  
    private void initView(String perm) 
    {
        try {
        	
        	loader.setRoot(null);
        	loader.setController(null);
            String pathToFxml = "src/fisapost/view/PrimaryLayout.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            apview = (AnchorPane) loader.load();
            rootLayout.setCenter(apview);
            
            AllController viewCtrl = loader.getController();
            viewCtrl.setService(sservice, pservice, fservice,perm);
            sservice.addObserver(viewCtrl.getSarcinaObserver());
            pservice.addObserver(viewCtrl.getPostObserver());
            fservice.addObserver(viewCtrl.getFisaObserver());
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout() 
    {
        try {
           
            FXMLLoader loader = new FXMLLoader();
            String pathToFxml = "src/fisapost/view/RootLayout.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);
            rootLayout = (BorderPane) loader.load();

            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
