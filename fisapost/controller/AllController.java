package fisapost.controller;

import fisapost.entities.*;
import fisapost.pdf.RaportPDF;
import fisapost.service.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import fisapost.util.Observable;
import fisapost.util.Observer;
import fisapost.validator.*;

public class AllController 
{
	
	public void updateSarcina(Observable<Sarcina> observable) 
	{
		SarcinaService service = (SarcinaService)observable;
		smodel.setAll(service.getAllSarcinas());
    }
    public void updatePost(Observable<Post> observable) 
    {
    	PostService service = (PostService)observable;
    	pmodel.setAll(service.getAllPosts());
    }
    public void updateFisa(Observable<Elementfisa> observable) 
    {
    	FisaService service = (FisaService)observable;
    	fmodel.setAll(service.getAllFisa());
    }
    
	public Observer<Sarcina> getSarcinaObserver() 
	{
        return new Observer<Sarcina>() 
        {
			@Override
			public void update(Observable<Sarcina> observable) 
			{
				updateSarcina(observable);
			}
        };
    }
	public Observer<Post> getPostObserver() 
	{
        return new Observer<Post>() 
        {
			@Override
			public void update(Observable<Post> observable) 
			{
				updatePost(observable);
			}
        };
    }
	public Observer<Elementfisa> getFisaObserver() 
	{
        return new Observer<Elementfisa>() 
        {
			@Override
			public void update(Observable<Elementfisa> observable) 
			{
				updateFisa(observable);
			}
        };
    }

	@FXML Pagination paginationS;
	@FXML Pagination paginationP;
	@FXML Pagination paginationEF;
	
	
    ObservableList<Sarcina> smodel;
    ObservableList<Post> pmodel;
    ObservableList<Elementfisa> fmodel;

    @FXML private TableView<Post> tableP;
    @FXML private TableView<Sarcina> tableS;
    @FXML private TableView<Elementfisa> tableEF;
    
    @FXML private TableColumn<Sarcina, String> sFirstColumn;
    @FXML private TableColumn<Sarcina, String> sSecondColumn;
    @FXML private TableColumn<Post, String> pFirstColumn;
    @FXML private TableColumn<Post, String> pSecondColumn;
    @FXML private TableColumn<Elementfisa, String> fFirstColumn;
    @FXML  private TableColumn<Elementfisa, String> fSecondColumn;
	
    @FXML private ComboBox<String> ComboObject;
    
    @FXML private Label firstLabel;
    @FXML private Label secondLabel;
    @FXML private Label thirdLabel;
    
    @FXML private TextField firstTextField;
    @FXML private TextField secondTextField;
    @FXML private TextField thirdTextField;
    @FXML private TextField filterTextField;
    
    @FXML private RadioButton radioButtonFirst;
    @FXML private RadioButton radioButtonSecond;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearFieldsButton;
    @FXML private Button raportButton;
    @FXML private Pagination pagination ;
    SarcinaService sservice;
    PostService pservice;
    FisaService fservice;
    
    @FXML ComboBox<Post> PBox;
    @FXML ComboBox<Sarcina> SBox;
    
    private String currentComboBoxString;
    
    String perm;
    private Boolean isSelectedFC;
    private Boolean isSelectedSC;
    
    ToggleGroup toggleRadioGroup = new ToggleGroup();
    
    public AllController() 
    {
    	
    }
    
    private final static int rowsPerPage = 7;
	
    private Node createPage1(int pageIndex) 
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, smodel.size());
        tableS.setItems(FXCollections.observableArrayList(smodel.subList(fromIndex, toIndex)));
	    tableS.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> 
	    {
	    	if (newSelection != null) 
	    	{
		    	    firstTextField.setText(((Sarcina) newSelection).getId().toString());
		    	    secondTextField.setText(((Sarcina) newSelection).getDesc());
	    	}
	    });

        sFirstColumn.setCellValueFactory(
                new PropertyValueFactory<Sarcina, String>("Id"));


        sSecondColumn.setCellValueFactory(
                new PropertyValueFactory<Sarcina, String>("desc"));
        
        int numOfPages = 1;
        if (smodel.size() % rowsPerPage == 0) {
          numOfPages = smodel.size() / rowsPerPage;
        } else if (smodel.size() > rowsPerPage) {
          numOfPages = smodel.size() / rowsPerPage + 1;
        }
        
        if (smodel.size() == 0)
        	numOfPages =1;
        
	    paginationS.setPageCount(numOfPages);
        
		PBox.setItems(pmodel);
		SBox.setItems(smodel);
  
        return new BorderPane(tableS);
    }
    
    private Node createPage2(int pageIndex) 
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, pmodel.size());
        tableP.setItems(FXCollections.observableArrayList(pmodel.subList(fromIndex, toIndex)));
        
	    tableP.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> 
	    {
	    	if (newSelection != null) 
	    	{
		    	    firstTextField.setText(((Post) newSelection).getId().toString());
		    	    secondTextField.setText(((Post) newSelection).getNume());
		    	    thirdTextField.setText(((Post) newSelection).getTip());
	    	}
	    });

        
        pFirstColumn.setCellValueFactory(
                new PropertyValueFactory<Post, String>("nume"));


        pSecondColumn.setCellValueFactory(
                new PropertyValueFactory<Post, String>("tip"));


        int numOfPages = 1;
        if (pmodel.size() % rowsPerPage == 0) {
          numOfPages = pmodel.size() / rowsPerPage;
        } else if (pmodel.size() > rowsPerPage) {
          numOfPages = pmodel.size() / rowsPerPage + 1;
        }
        
        if (pmodel.size() == 0)
        	numOfPages =1;
        
	    paginationP.setPageCount(numOfPages);
	    
		PBox.setItems(pmodel);
		SBox.setItems(smodel);
        return new BorderPane(tableP);
        
    }
    
    private Node createPage3(int pageIndex) 
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, fmodel.size());
        tableEF.setItems(FXCollections.observableArrayList(fmodel.subList(fromIndex, toIndex)));
        

	    tableEF.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> 
	    {
	    	if (newSelection != null) 
	    	{
		         firstTextField.setText(((Elementfisa) newSelection).getId().toString());
		         SBox.setValue(((Elementfisa) newSelection).getSarcina());
		         PBox.setValue(((Elementfisa) newSelection).getPost());
	    	}
	    });
        
       	fFirstColumn.setCellValueFactory(new Callback<CellDataFeatures<Elementfisa, String>, ObservableValue<String>>()
    	{
		     public ObservableValue<String> call(CellDataFeatures<Elementfisa, String> p) 
		     {
		         if (p.getValue() != null && p.getValue().getPost() != null) 
		         {
		              return new SimpleStringProperty(p.getValue().getPost().getNume());
		         } else 
		         {
		             return new SimpleStringProperty("Empty");
		         }
		     }
		  });
       	
		fSecondColumn.setCellValueFactory(new Callback<CellDataFeatures<Elementfisa, String>, ObservableValue<String>>() 
		{
		     public ObservableValue<String> call(CellDataFeatures<Elementfisa, String> s) 
		     {
		         if (s.getValue() != null && s.getValue().getSarcina() != null) 
		         {
		              return new SimpleStringProperty(s.getValue().getSarcina().getDesc());
		         } else 
		         {
		             return new SimpleStringProperty("Empty");
		         }
		     }
		  });
	    
		

        int numOfPages = 1;
        if (fmodel.size() % rowsPerPage == 0) {
          numOfPages = fmodel.size() / rowsPerPage;
        } else if (fmodel.size() > rowsPerPage) {
          numOfPages = fmodel.size() / rowsPerPage + 1;
        }
        
        if (fmodel.size() == 0)
        	numOfPages =1;
        
	    paginationEF.setPageCount(numOfPages);
		
		PBox.setItems(pmodel);
		SBox.setItems(smodel);
		
        return new BorderPane(tableEF);
        
    }
	
	
    private int intValidate(String e) 
    {
	    for(int i = 0; i < e.length(); i++) 
	    {
	        if(i == 0 && e.charAt(i) == '-') 
	        {
	            if(e.length() == 1) 
	            {
	            	showErrorMessage("Numar invalid !");
	            	return -1;
	            }
	            else continue;
	        }
	        if(Character.digit(e.charAt(i), 10) < 0) 
	        {
	        	showErrorMessage("Numar invalid !");
	        	return -1;
	        }
	    }
	    
	    if(e == "" || e.isEmpty())
        {
        
        	return -1;
        }
	    return Integer.parseInt(e);
    }
    
    private int stringValidate1(String e)
    {
    	if(e == null || e.isEmpty())
    	{
    		return -1;
    	}
    	return 0;
    	
    }
    
    private int stringValidate2(String e)
    {
    	if(e == null || e.isEmpty())
    	{	
    		return -1;
    	}
    	return 0;
    	
    }
    
	private void fillItemsOnTable() 
    {
		
    	if (currentComboBoxString.equals("Sarcina") && perm.compareTo("ALL")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");

        	PBox.setVisible(false);
        	SBox.setVisible(false);
        	secondTextField.setVisible(true);
        	thirdTextField.setVisible(false);
    		tableS.getColumns().clear();
    		tableS.getColumns().add(sFirstColumn);
    		tableS.getColumns().add(sSecondColumn);
    		
    		paginationS.setPageFactory(this::createPage1);
    	}
    	
    	else if (currentComboBoxString.equals("Sarcina") && perm.compareTo("READ")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");
        	
        	firstTextField.setVisible(false);
        	secondTextField.setVisible(false);
        	thirdTextField.setVisible(false);
        	
        	firstLabel.setVisible(false);
        	secondLabel.setVisible(false);
        	thirdLabel.setVisible(false);
        	
        	addButton.setVisible(false);
        	updateButton.setVisible(false);
        	clearFieldsButton.setVisible(false);
        	deleteButton.setVisible(false);
        	raportButton.setVisible(false);
        	
        	
        	PBox.setVisible(false);
        	SBox.setVisible(false);
    		tableS.getColumns().clear();
    		tableS.getColumns().add(sFirstColumn);
    		tableS.getColumns().add(sSecondColumn);
    		
    		paginationS.setPageFactory(this::createPage1);
    	}
    	else if (currentComboBoxString.equals("Sarcina") && perm.compareTo("WRITE")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");

    		deleteButton.setVisible(false);
    		updateButton.setVisible(false);
    		raportButton.setVisible(false);
        	
        	PBox.setVisible(false);
        	SBox.setVisible(false);
        	secondTextField.setVisible(true);
        	thirdTextField.setVisible(false);
    		tableS.getColumns().clear();
    		tableS.getColumns().add(sFirstColumn);
    		tableS.getColumns().add(sSecondColumn);
    		
    		paginationS.setPageFactory(this::createPage1);
    	}
    	else if (currentComboBoxString.equals("Post") && perm.compareTo("ALL")==0)  
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");
        	secondTextField.setVisible(true);
        	thirdTextField.setVisible(true);
        	PBox.setVisible(false);
        	SBox.setVisible(false);
    		tableP.getColumns().clear();
    		tableP.getColumns().add(pFirstColumn);
    		tableP.getColumns().add(pSecondColumn);

        	paginationP.setPageFactory(this::createPage2);

    	}
    	else if (currentComboBoxString.equals("Post") && perm.compareTo("READ")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");

        	firstLabel.setVisible(false);
        	secondLabel.setVisible(false);
        	thirdLabel.setVisible(false);
        	
        	firstTextField.setVisible(false);
        	secondTextField.setVisible(false);
        	thirdTextField.setVisible(false);
        	
        	addButton.setVisible(false);
        	updateButton.setVisible(false);
        	clearFieldsButton.setVisible(false);
        	deleteButton.setVisible(false);
        	raportButton.setVisible(false);
        	
        	PBox.setVisible(false);
        	SBox.setVisible(false);
    		tableP.getColumns().clear();
    		tableP.getColumns().add(pFirstColumn);
    		tableP.getColumns().add(pSecondColumn);

        	paginationP.setPageFactory(this::createPage2);
    	}
    	else if (currentComboBoxString.equals("Post") && perm.compareTo("WRITE")==0)  
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");
        	secondTextField.setVisible(true);
        	thirdTextField.setVisible(true);
        	PBox.setVisible(false);
        	SBox.setVisible(false);
    		tableP.getColumns().clear();
    		tableP.getColumns().add(pFirstColumn);
    		tableP.getColumns().add(pSecondColumn);
    		
    		deleteButton.setVisible(false);
    		updateButton.setVisible(false);
    		raportButton.setVisible(false);
    		
        	paginationP.setPageFactory(this::createPage2);

    	}
    	else if (currentComboBoxString.equals("Fisa") && perm.compareTo("ALL")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");
        	secondTextField.setVisible(false);
        	thirdTextField.setVisible(false);
        	PBox.setVisible(true);
        	SBox.setVisible(true);
    		tableEF.getColumns().clear();
    		tableEF.getColumns().add(fFirstColumn);
    		tableEF.getColumns().add(fSecondColumn);	
    		
        	paginationEF.setPageFactory(this::createPage3);
    	}
    	else if (currentComboBoxString.equals("Fisa") && perm.compareTo("READ")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");

        	
        	firstTextField.setVisible(false);
        	secondTextField.setVisible(false);
        	thirdTextField.setVisible(false);
        	
        	PBox.setVisible(false);
        	SBox.setVisible(false);
        	
        	firstLabel.setVisible(false);
        	secondLabel.setVisible(false);
        	thirdLabel.setVisible(false);
        	addButton.setVisible(false);
        	updateButton.setVisible(false);
        	clearFieldsButton.setVisible(false);
        	deleteButton.setVisible(false);
        	raportButton.setVisible(false);

    		tableEF.getColumns().clear();
    		tableEF.getColumns().add(fFirstColumn);
    		tableEF.getColumns().add(fSecondColumn);	
    		
        	paginationEF.setPageFactory(this::createPage3);
    	}
    	else if (currentComboBoxString.equals("Fisa") && perm.compareTo("WRITE")==0) 
    	{
        	firstTextField.setText("");
        	secondTextField.setText("");
        	thirdTextField.setText("");
        	secondTextField.setVisible(false);
        	thirdTextField.setVisible(false);
        	PBox.setVisible(true);
        	SBox.setVisible(true);
    		tableEF.getColumns().clear();
    		tableEF.getColumns().add(fFirstColumn);
    		tableEF.getColumns().add(fSecondColumn);	
    		
    		deleteButton.setVisible(false);
    		updateButton.setVisible(false);
    		raportButton.setVisible(false);
    		
        	paginationEF.setPageFactory(this::createPage3);
    	}

    }

    @FXML public void handleRaport()
    {
		if (isSelectedFC) 
		{
	    		String nra = firstTextField.getText();
	    		int vid = intValidate(nra);
	    		if (vid == -1)
	    			return;
				ObservableList<Sarcina> model = FXCollections.observableArrayList(fservice.filterRapoarte(Integer.parseInt(nra)));
				if (model.isEmpty())
				{
					showErrorMessage("Lista de rapoarte este goala, numarul dat este mai mare decat numarul de rapoarte valabile");
					return;
				}
				System.out.println(model);
				ComboObject.setValue("Sarcina");
				this.fillItemsOnTable();
				tableS.setItems(model);
				RaportPDF.addPdf(model);}
    	}
    
    public void setService(SarcinaService sservice, PostService pservice, FisaService fservice,String perm) 
    {
        this.sservice = sservice;
        this.pservice = pservice;
        this.fservice = fservice;
        
        this.smodel = FXCollections.observableArrayList(sservice.getAllSarcinas());
        this.pmodel = FXCollections.observableArrayList(pservice.getAllPosts());
        this.fmodel = FXCollections.observableArrayList(fservice.getAllFisa());
        
        this.perm=perm;
        
        this.fillItemsOnTable();
    }
    @FXML private void onActionComboBox(ActionEvent event) 
    {
    	String current = ComboObject.getSelectionModel().getSelectedItem();
    	if (current.compareTo(currentComboBoxString) != 0) 
    	{
    		currentComboBoxString = current;
    		if (current.equals("Sarcina")) 
    		{
    			secondLabel.setText("Desc: ");
    	    	radioButtonSecond.setText("By Desc");
    			thirdLabel.setVisible(false);
    	    	radioButtonFirst.setVisible(false);
    	    	thirdTextField.setVisible(false);
    	    	paginationS.setStyle("visibility: visible;");
    	    	paginationP.setStyle("visibility: hidden;");
    	    	paginationEF.setStyle("visibility: hidden;");
    	    	
    		}
    		else if (current.equals("Post")) 
    		{
    			secondLabel.setText("Name: ");
    	    	thirdLabel.setText("Type: ");
    	    	radioButtonFirst.setText("By Name");
    	    	radioButtonSecond.setText("By Type");
    			thirdLabel.setVisible(true);
    	    	radioButtonFirst.setVisible(true);
    	    	thirdTextField.setVisible(true);
    	    	paginationP.setStyle("visibility: visible;");
    	    	paginationS.setStyle("visibility: hidden;");
    	    	paginationEF.setStyle("visibility: hidden;");
    		}
    		else if (current.equals("Fisa")) 
    		{
    	    	secondLabel.setText("Sarcina: ");
    	    	thirdLabel.setText("Post: ");
    	    	radioButtonFirst.setText("By Sarcina");
    	    	radioButtonSecond.setText("By Post");
    			thirdLabel.setVisible(true);
    	    	radioButtonFirst.setVisible(true);
    	    	thirdTextField.setVisible(true);
    	    	paginationEF.setStyle("visibility: visible;");
    	    	paginationS.setStyle("visibility: hidden;");
    	    	paginationP.setStyle("visibility: hidden;");
    		}
    		this.fillItemsOnTable();
    	}
    }
       
	@FXML private void initialize() 
    {
    
    	ComboObject.getItems().addAll
    	(
    		    "Sarcina",
    		    "Post",
    		    "Fisa"
    	);
    	currentComboBoxString = "Sarcina";
    	ComboObject.setValue("Sarcina");
    	
		thirdLabel.setVisible(false);
    	radioButtonFirst.setVisible(false);
    	thirdTextField.setVisible(false);
    	
    	paginationS.setStyle("visibility: visible;");
    	paginationP.setStyle("visibility: hidden;");
    	paginationEF.setStyle("visibility: hidden;");
    	
		isSelectedFC = true;
		isSelectedSC = false;
		radioButtonFirst.setToggleGroup(toggleRadioGroup);
    	radioButtonSecond.setToggleGroup(toggleRadioGroup);
    	radioButtonFirst.setSelected(true);
	
		paginationS.setPageFactory(this::createPage1);
    	paginationP.setPageFactory(this::createPage2);
    	paginationEF.setPageFactory(this::createPage3);
		

    	
    }
    
    private void clearFields() 
    {
    	firstTextField.setText("");
    	secondTextField.setText("");
    	thirdTextField.setText("");
    	PBox.setValue(null);
    	SBox.setValue(null);
    }
    

    @FXML public void handleAdd() 
    {
    	String id = firstTextField.getText();
    	String first = secondTextField.getText();
    	String sec = thirdTextField.getText();
    	int vid = intValidate(id);
    	int vid2 = stringValidate1(first);
    	if (vid == -1 || vid2 == -1 && !currentComboBoxString.equals("Fisa"))
    	{
    		showErrorMessage("Camp de text este gol!");
    		return;
    	}
    	else
    	{
	    	if (currentComboBoxString.equals("Sarcina")) 
	    	{
	    		Sarcina s = new Sarcina(Integer.parseInt(id), first);
	    		try {
	    			if (sservice.findOne(s.getId()) == null) 
	    			{
	    				sservice.save(s);
	    				showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Sarcina a fost adaugat!");
	    				clearFields();
	    			}
	    			else
	    				showErrorMessage("Exista deja o sarcina cu acest id !");
	    			
	    			
	    		}catch(ValidatorException e) 
	    		{
	    			showErrorMessage(e.getMessage());
	    		}
	    		
	        	paginationS.setPageFactory(this::createPage1);
	    	}
	    	else if (currentComboBoxString.equals("Post")) 
	    	{
	    		Post p = new Post(Integer.parseInt(id), first, sec);
	    		try {
	    			if (pservice.findOne(p.getId()) == null) 
	    			{
	    				pservice.save(p);
	    				showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Post-ul a fost adaugat!");
	    				clearFields();
	    			}
	    			else
	    				showErrorMessage("Exista deja un post cu acest id !");
	    	    	int vid3 = stringValidate2(sec);
	    	    	if (vid3 == -1)
	    	    		return;
	    		}catch(ValidatorException e) 
	    		{
	    			showErrorMessage(e.getMessage());
	    		}
	    		
	        	paginationP.setPageFactory(this::createPage2);
	    	}
    	}
    	if (currentComboBoxString.equals("Fisa")) 
    	{
    		try {
    			int n1=SBox.getSelectionModel().getSelectedItem().getId();
    			int n2=PBox.getSelectionModel().getSelectedItem().getId();
	    		Sarcina s = sservice.findOne(n1);
	    		Post p = pservice.findOne(n2);
	    		if (s == null || p == null) 
	    		{
	    			showErrorMessage("Id-ul sarcinii sau postului nu exista !");
	    			return;
	    		}
	    		Elementfisa f = new Elementfisa(Integer.parseInt(id), p, s);
	    		if (fservice.findOne(f.getId()) == null) 
	    		{
	    			fservice.save(f);
	    			showMessage(Alert.AlertType.INFORMATION, "Salvare cu succes", "Fisa a fost adaugat!");
		            clearFields();
	        	}
	            else
	                showErrorMessage("Exista deja o fisa cu acest id !");
	        	int vid3 = stringValidate2(sec);
    	    	if (vid3 == -1)
    	    		return;
	        	PBox.setValue(null);
	        	SBox.setValue(null);
	        } catch (ValidatorException e) 
    		{
	            showErrorMessage(e.getMessage());
	        }
        	paginationEF.setPageFactory(this::createPage3);
    	}
    }
    
    @FXML public void handleUpdate() 
    {
    	String id = firstTextField.getText();
    	String first = secondTextField.getText();
    	String sec = thirdTextField.getText();
    	int vid = intValidate(id);
    	int vid2 = stringValidate1(first);
    	if (vid == -1 || vid2 == -1 && !currentComboBoxString.equals("Fisa"))
    	{
    		showErrorMessage("camp de text gol!");
    		return;
    	}
    	else
    	{
	    	if (currentComboBoxString.equals("Sarcina")) 
	    	{
	    		Sarcina s = new Sarcina(Integer.parseInt(id), first);
	    		try {
	    			Sarcina updated = sservice.update(s);
	    			if (updated != null) 
	    			{
	    				showMessage(Alert.AlertType.INFORMATION, "Actualizare cu succes", "Sarcina a fost actualizata!");
	                    clearFields();
	                }
	                else
	                    showErrorMessage("Eroare la actualizare !");
	            } catch (ValidatorException e) 
	    		{
	                showErrorMessage(e.getMessage());
	            }
	    		
	        	paginationS.setPageFactory(this::createPage1);
	    	}
	    	else if (currentComboBoxString.equals("Post")) 
	    	{
	    		Post p = new Post(Integer.parseInt(id), first, sec);
	    		try {
	    			Post updated = pservice.update(p);
	    			if (updated != null) 
	    			{
	    				showMessage(Alert.AlertType.INFORMATION, "Actualizare cu succes", "Postul a fost actualizat!");
	                    clearFields();
	                }
	                else
	                    showErrorMessage("Eroare la actualizare !");
	            } catch (ValidatorException e) 
	    		{
	                showErrorMessage(e.getMessage());
	            }
	        	int vid3 = stringValidate2(sec);
		    	if (vid3 == -1)
		    		return;
	    		
	        	paginationP.setPageFactory(this::createPage2);
	    	}
    	}
    	if (currentComboBoxString.equals("Fisa")) 
    	{
    		try {
    			int n1=SBox.getSelectionModel().getSelectedItem().getId();
    			int n2=PBox.getSelectionModel().getSelectedItem().getId();
	    		Sarcina s = sservice.findOne(n1);
	    		Post p = pservice.findOne(n2);
	    		if (s == null || p == null) 
	    		{
	    			showErrorMessage("Id-ul sarcinii sau postului nu exista !");
	    			return;
	    		}
	    		Elementfisa f = new Elementfisa(Integer.parseInt(id), p, s);
	    		Elementfisa updated = fservice.update(f);
	    		if (updated != null) 
	    		{
	    			showMessage(Alert.AlertType.INFORMATION, "Actualizare cu succes", "Fisa a fost actualizata!");
                    clearFields();
                }
                else
                    showErrorMessage("Eroare la actualizare !");
	        	int vid3 = stringValidate2(sec);
    	    	if (vid3 == -1)
    	    		return;
	       	}catch (ValidatorException e) 
    		{
                showErrorMessage(e.getMessage());
            }
    		
        	paginationEF.setPageFactory(this::createPage3);
    	}
    }
    @FXML public void handleDelete() 
    {
    	if (currentComboBoxString.equals("Sarcina")) 
    	{
    		Sarcina s = (Sarcina) tableS.getSelectionModel().getSelectedItem();
    		try 
    		{
    			sservice.delete(s);
    			showMessage(Alert.AlertType.INFORMATION, "Stergere cu succes", "Sarcina a fost stersa !");
            	clearFields();
            } catch (ValidatorException e) {
                showErrorMessage(e.getMessage());
            }
    		
        	paginationS.setPageFactory(this::createPage1);
    	}
    	else if (currentComboBoxString.equals("Post")) 
    	{
    		Post p = (Post) tableP.getSelectionModel().getSelectedItem();
    		try 
    		{
    			pservice.delete(p);
    			showMessage(Alert.AlertType.INFORMATION, "Stergere cu succes", "Postul a fost sters !");
            	clearFields();
            } catch (ValidatorException e) 
    		{
                showErrorMessage(e.getMessage());
            }
    		
        	paginationP.setPageFactory(this::createPage2);
    	}
    	else if (currentComboBoxString.equals("Fisa")) 
    	{
    		Elementfisa f = (Elementfisa) tableEF.getSelectionModel().getSelectedItem();
    		try 
    		{
    			fservice.delete(f);
    			showMessage(Alert.AlertType.INFORMATION, "Stergere cu succes", "Fisa a fost stersa !");
            	clearFields();
            } catch (ValidatorException e) 
    		{
                showErrorMessage(e.getMessage());
            }
    		
        	paginationEF.setPageFactory(this::createPage3);
    	}
    }
    @FXML public void handleClearFields() 
    {
    	clearFields();
    }
    @FXML public void handleToggleButton() 
    {
    	isSelectedFC = radioButtonFirst.isSelected();
    	isSelectedSC = radioButtonSecond.isSelected();   	
    }
	@FXML public void handleFilterColumn() 
    {
    	String what = filterTextField.getText();
    	try 
    	{
	    	if (currentComboBoxString.equals("Sarcina")) 
	    	{
	    		if (what.equals("")) 
	    		{
	    			tableS.setItems(smodel);
	    			return;
	    		}
	    		if (isSelectedFC) 
	    		{
	    			showErrorMessage("N/A for Sarcina !");
	    			return;
	    		}
	    		else if (isSelectedSC) 
	    		{
	    			ObservableList<Sarcina> model = FXCollections.observableArrayList(sservice.filterSarcinaDesc(what));
	    			tableS.setItems(model);
	    		}
	    	}
	    	else if (currentComboBoxString.equals("Post")) 
	    	{
	    		if (what.equals("")) 
	    		{
	    			tableP.setItems(pmodel);
	    			return;
	    		}
	    		if (isSelectedFC) 
	    		{
	    			ObservableList<Post> model = FXCollections.observableArrayList(pservice.filterPostNume(what));
	    			tableP.setItems(model);
	    		}
	    		else if (isSelectedSC) 
	    		{
	    			ObservableList<Post> model = FXCollections.observableArrayList(pservice.filterPostTip(what));
	    			tableP.setItems(model);
	    		}
	    	}
	    	else if (currentComboBoxString.equals("Fisa")) 
	    	{
	    		if (what.equals("")) 
	    		{
	    			ComboObject.setValue("Fisa");
	    			tableS.setItems(smodel);
	    			return;
	    		}
	    		int vid = intValidate(what);
	    		if (vid == -1)
	    			return;
	    		if (isSelectedFC) 
	    		{
	    			Sarcina s = sservice.findOne(Integer.parseInt(what));
	    			ObservableList<Sarcina> model = FXCollections.observableArrayList(fservice.filterElementSarcina(s));
	    			ComboObject.setValue("Sarcina");
	    			this.fillItemsOnTable();
	    			tableS.setItems(model);
	    		}
	    		if (isSelectedSC) 
	    		{
	    			Post p = pservice.findOne(Integer.parseInt(what));
	    			ObservableList<Post> model = FXCollections.observableArrayList(fservice.filterElementPost(p));
	    			ComboObject.setValue("Fisa");
	    			this.fillItemsOnTable();
	    			tableP.setItems(model);
	    		}
	    	}
    	}
    	catch(ValidatorException e) 
    	{
    		showErrorMessage(e.getMessage());
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
