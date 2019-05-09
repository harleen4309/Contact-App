import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ContactApp extends javafx.application.Application {
	TextField txtFirst;
	TextField txtLast;
	TextField txtPhone;
	TextField txtEmail;
	TextField txtAddress;
	ArrayList<Contact> contactList = new ArrayList<Contact>();
	String path = "c:\\temp\\Contact.txt";
	final PasswordField pb = new PasswordField();
	final Label messageForPassword = new Label("");
	Label enterPassLabel = new Label("Enter password to access your phone");
	int index;
	
	
	public void showRecord() {
		if( contactList.size() != 0) {
			Contact p = contactList.get(index);
			txtFirst.setText(p.getFirstName());
			txtLast.setText(p.getLastName());
			txtPhone.setText(p.getPhoneNumber());
			txtEmail.setText(p.getEmail());
			txtAddress.setText(p.getAddress());
		}
	}
	
	public void saveDataToFile() throws FileNotFoundException {
		File f = new File(path);
		PrintWriter pw = new PrintWriter(f);
		for(Contact p : contactList) {
			pw.printf("%s:%s:%s:%s:%s", p.getFirstName(), p.getLastName(), p.getPhoneNumber(), p.getEmail(), p.getAddress());
			pw.println();
		}
		pw.close();
	}
	
	public void loadDataFromFile() throws FileNotFoundException {
		File f = new File(path);
		Scanner s = new Scanner(f);
		while(s.hasNextLine()) {
			String record = s.nextLine();
			Scanner s1 = new Scanner(record);
			s1.useDelimiter(":\\s*");
			String firstName = s1.next();
			String lastName= s1.next();
			String phoneNumber = s1.next();
			String eMail = s1.next();
			String Address = s1.next();
			Contact p = new Contact(firstName, lastName, phoneNumber, eMail, Address);
			contactList.add(p);
			s1.close();
		}
		s.close();
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vBox = new VBox(10);
		vBox.setPrefWidth(300);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.setStyle("-fx-background-color: #95D9EB;");
		
		VBox vBoxAdd = new VBox(10);
		vBoxAdd.setPrefWidth(300);
		vBoxAdd.setPadding(new Insets(10, 10, 10, 10));
		vBoxAdd.setStyle("-fx-background-color: #90D6AE;");
		
		String path="/image/canada.jpg";
	    Image image = new Image(path); 
	    
		txtFirst = new TextField();
		txtLast= new TextField();
		txtPhone=new TextField();
		txtEmail=new TextField();
		txtAddress=new TextField();
		Label lblFirst = new Label("First Name: ");
		Label lblLast = new Label("Last Name: ");
		Label lblPhone = new Label("Phone: ");
		Label lblEmail = new Label("Email");
		Label lblAddress = new Label("Address: ");
		Button btnFirst = new Button("<<");
		Button btnPrev = new Button("<");
		Button btnNext = new Button(">");
		Button btnLast = new Button(">>");
		Button btnAddRecord = new Button("Add Record");
		Button btnDoneAdd = new Button("Done");
		Button btnRemoveRecord = new Button("Remove Record");
		Button btnEdit = new Button("Edit Record");
		Button btnSearch = new Button("Search Record");
		Button btnShow = new Button("Show Record");
		
		FlowPane navPane = new FlowPane();		
		navPane.setAlignment(Pos.CENTER);
		
		FlowPane actionPane = new FlowPane();
		actionPane.setAlignment(Pos.CENTER);
		
		
		
		navPane.getChildren().addAll(btnFirst, btnPrev, btnNext, btnLast);
		actionPane.getChildren().addAll(btnAddRecord, btnRemoveRecord,btnEdit,btnSearch,btnShow);
		
		
		
		vBox.getChildren().addAll(new ImageView(image), lblFirst, txtFirst, lblLast, txtLast, lblPhone, txtPhone, lblEmail,
				txtEmail, lblAddress, txtAddress,navPane, actionPane);
		Scene passScene = new Scene(vBox);
		
		
		
		Scene sceneAdd = new Scene(vBoxAdd);
		btnAddRecord.setOnAction( e-> {
			FlowPane actionPaneAdd = new FlowPane();
			actionPaneAdd.setAlignment(Pos.CENTER);
			actionPaneAdd.getChildren().addAll(btnDoneAdd);
			
			vBoxAdd.getChildren().addAll(new ImageView(image), lblFirst, txtFirst, lblLast, txtLast, lblPhone, txtPhone, lblEmail,
					txtEmail, lblAddress, txtAddress,actionPaneAdd);
			
			primaryStage.setScene(sceneAdd);
			txtFirst.setText("");
			txtLast.setText("");
			txtPhone.setText("");
			txtEmail.setText("");
			txtAddress.setText("");
		});
			
		
		
		
		//password page
		BorderPane mainBorderPane = new BorderPane();
		mainBorderPane.setStyle("-fx-background-color: #95D9EB;");
		BorderPane passBorderPane = new BorderPane();		
		StackPane topPane = new StackPane();
		topPane.setPadding(new Insets(45, 5, 5, 5));		
		topPane.getChildren().add(enterPassLabel);
		passBorderPane.setTop(topPane);		
		StackPane passStackPane = new StackPane();
		passStackPane.setPadding(new Insets(25, 25, 25, 25));
		passStackPane.getChildren().add(pb);		
		passBorderPane.setCenter(passStackPane);		
		StackPane submitStackPane = new StackPane();
		submitStackPane.setPadding(new Insets(35, 35, 35, 35));
		submitStackPane.getChildren().add(messageForPassword);
		passBorderPane.setBottom(submitStackPane);
		mainBorderPane.setCenter(passBorderPane);
		
		
		pb.setOnAction(e ->{
			if (!pb.getText().equals("a")) {
				messageForPassword.setText("Your password is incorrect!");
				messageForPassword.setTextFill(Color.rgb(210, 39, 30));
	        } else {
	        	messageForPassword.setText("Your password has been confirmed");
	        	messageForPassword.setTextFill(Color.rgb(21, 117, 84));
	        	primaryStage.setScene(passScene);
	        }
	        pb.clear();
		});
		
		
		Scene scene = new Scene(mainBorderPane);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Contact App");
		primaryStage.sizeToScene();
		primaryStage.show();
		
		try {
			loadDataFromFile();
		}catch(Exception e) {
			e.getMessage();
		}
		index = 0;
		showRecord();
		
		btnFirst.setOnAction( e-> {
			index = 0;
			showRecord();
		});		
		btnLast.setOnAction( e-> {
			index = contactList.size() - 1;
			showRecord();
		});
		btnPrev.setOnAction( e-> {
			index--;
			if(index >= 0) {
				showRecord();
			}
			else {
				index++;
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("navigation");
				a.setContentText("cannot go to previous record");
				a.showAndWait();
			}
		});
		btnNext.setOnAction( e-> {
			index++;
			if(index < contactList.size()) {
				showRecord();
			}
			else {
				index--;
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("navigation");
				a.setContentText("cannot go to next record");
				a.showAndWait();
			}			
		});
btnDoneAdd.setOnAction(e-> { 
			
			String firstName = txtFirst.getText();
			String lastName = txtLast.getText();
			String phoneNumber= txtPhone.getText();
			String eMail = txtEmail.getText();
			String address = txtAddress.getText();
			
			Contact p = new Contact(firstName, lastName, phoneNumber, eMail, address);
			contactList.add(p);
			try {
				saveDataToFile();
			} catch (FileNotFoundException e1) {
				e1.getMessage();
			}
			index = contactList.size() - 1;
			primaryStage.setScene(passScene);
			showRecord();
		});
		btnRemoveRecord.setOnAction( e-> {
			if(contactList.size() > 0) {
				contactList.remove(index);
				try {
					saveDataToFile();
				} catch (FileNotFoundException e1) {
					e1.getMessage();
				}
				if(--index >= 0) {
					showRecord();
				}
				else if(++index < contactList.size()) {
					showRecord();
				}
				else {
					txtFirst.setText("");
					txtLast.setText("");
					txtPhone.setText("");
					txtEmail.setText("");
					txtAddress.setText("");
					Alert a = new Alert(AlertType.INFORMATION);
					a.setTitle("removing");
					a.setContentText("This was last record");
					a.showAndWait();
				}
			}else {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("error");
				a.setContentText("There is no record to remove");
				a.showAndWait();
			}
		});
		btnEdit.setOnAction(e->{
			contactList.get(index);
			String firstName = txtFirst.getText();
			String lastName = txtLast.getText();
			String phoneNumber= txtPhone.getText();
			String eMail = txtEmail.getText();
			String address = txtAddress.getText();
			
			Contact p = new Contact(firstName, lastName, phoneNumber, eMail, address);
			contactList.add(p);
			try {
				saveDataToFile();
			} catch (FileNotFoundException e1) {
				e1.getMessage();
			}
			index = contactList.size() - 1;
			
		});
		btnSearch.setOnAction(e->{
			
		});
		btnShow.setOnAction(e->{
			showContactList stage = new showContactList(contactList);
			stage.show();
			
		});
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}

class Contact {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String Email;
	private String Address;
	public Contact() {
		super();
	}
	public Contact(String firstName, String lastName, String phoneNumber, String email, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		Email = email;
		Address = address;
	}
	public String getFirstName () {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber != null) {
			long phn;
			try {
			 phn= Long.parseLong(phoneNumber);
		this.phoneNumber = phoneNumber;}
			catch(NumberFormatException e) {
				System.out.println("Must input a number in this field");
			}
		}
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	
}

class showContactList extends Stage{
	
	public showContactList() {
		super();
	}
	public showContactList(ArrayList<Contact> contactList) {
	ScrollPane sPane = new ScrollPane();
	VBox VboxN = new VBox(15);
	VboxN.setPadding(new Insets(15,15,15,15));
	VBox VboxL = new VBox(15);
	VboxL.setPadding(new Insets(15,15,15,15));
	VBox VboxP = new VBox(15);
	VboxP.setPadding(new Insets(15,15,15,15));
	VBox VboxE = new VBox(15);
	VboxE.setPadding(new Insets(15,15,15,15));
	VBox VboxA = new VBox(15);
	VboxA.setPadding(new Insets(15,15,15,15));
	HBox hBox = new HBox(20);
	if( ((List<Contact>) contactList).size() != 0) {
		
		for (int i=0;i<((List<Contact>) contactList).size();i++) {
			
			Contact per = ((List<Contact>) contactList).get(i);
			VboxN.getChildren().add(new Label(per.getFirstName()+"  "));
			VboxL.getChildren().add(new Label(per.getLastName()+"  "));
			VboxP.getChildren().add(new Label(per.getPhoneNumber()+"  "));
			VboxE.getChildren().add(new Label(per.getEmail()+"  "));
			VboxA.getChildren().add(new Label(per.getAddress()+"  "));
			
			
		}
		hBox.getChildren().addAll(VboxN, VboxL, VboxP, VboxE, VboxA);
		sPane.setContent(hBox);
	}
	Scene secondScene = new Scene(sPane);
	setAlwaysOnTop(true);
	setScene(secondScene);
	sizeToScene();
	}
}

















