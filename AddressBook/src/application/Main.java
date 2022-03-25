package application;
	
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Main extends Application {
	int index=0;
	int indexDirection=0;
	Person[] person;
	Stage window;
	
	final static int ID_SIZE = 4;
	final static int NAME_SIZE = 32;
	final static int STREET_SIZE = 32;
	final static int CITY_SIZE = 20;
	final static int GENDER_SIZE = 1;
	final static int ZIP_SIZE = 5;
	final static int RECORD_SIZE =(ID_SIZE+NAME_SIZE + STREET_SIZE + CITY_SIZE + GENDER_SIZE + ZIP_SIZE);
	public RandomAccessFile raf;
	

	public Main() {
		try {
			raf = new RandomAccessFile("addressaa.dat", "rw");
		

			
			person=new Person[100];

		}
		catch(IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) throws IOException{
	
	launch(args);
	}
	Label idLabel = new Label("ID:");
	TextField idInput = new TextField();
	
	Label searchUpdateIDLabel = new Label("Search/Update ID:");
	TextField searchUpdateIDInput = new TextField();
	
	Label nameLabel = new Label("Name:");
	TextField nameInput = new TextField();
	
	Label streetLabel = new Label("Street:");
	TextField streetInput = new TextField();
	
	Label cityLabel = new Label("City:");
	TextField cityInput = new TextField();
	
	Label genderLabel = new Label("Gender:");
	TextField genderInput = new TextField();
	
	Label zipLabel = new Label("Zip:");
	TextField zipInput = new TextField();
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;		
		window.setTitle("Address Book");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,10,0,55));
		grid.setVgap(3);
		grid.setHgap(5);
		
		
		GridPane.setConstraints(idLabel, 0, 0);
		
		
		
		GridPane.setConstraints(idInput, 1, 0);
		idInput.setMaxWidth(70);
		idInput.setEditable(false);
		idInput.setMouseTransparent(true);
		idInput.setFocusTraversable(false);
		

		GridPane.setConstraints(searchUpdateIDLabel, 2, 0,1,1);
		GridPane.setConstraints(searchUpdateIDInput, 3, 0,4,1);
		GridPane.setConstraints(nameLabel, 0, 1);
		GridPane.setConstraints(nameInput, 1, 1,6,1);
		GridPane.setConstraints(streetLabel, 0, 2);
		GridPane.setConstraints(streetInput, 1, 2,6,1);
		GridPane.setConstraints(cityLabel, 0, 3);
		GridPane.setConstraints(cityInput, 1, 3,2,1);
		GridPane.setConstraints(genderLabel, 3, 3);
		GridPane.setConstraints(genderInput, 4, 3);
		genderInput.setMaxWidth(40);
		GridPane.setConstraints(zipLabel, 5, 3);
		GridPane.setConstraints(zipInput, 6, 3);

		try {

			if (raf.length() > 0)
			{
				long currentPos= raf.getFilePointer();
				while(currentPos < raf.length())
				{
					readFileFillArray(person, currentPos);
					currentPos=raf.getFilePointer();
					idInput.setPromptText(String.valueOf(index));
				}
				readFileByPos(0);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
		grid.getChildren().addAll(	idLabel,idInput,
									searchUpdateIDLabel,searchUpdateIDInput,
									nameLabel,nameInput,
									streetLabel,streetInput,
									cityLabel,cityInput,
									genderLabel,genderInput,
									zipLabel,zipInput);
		

		GridPane bottomMenu = new GridPane();
		bottomMenu.setPadding(new Insets(0,0,3,46));
		bottomMenu.setHgap(3);
		Button buttonA = new Button("Add");
		GridPane.setConstraints(buttonA, 1, 0);
		buttonA.setOnAction(e-> {	
			try {

				writeAddressToFile(raf.length());
				readFileFillArray(person, RECORD_SIZE*2*(index));
				cleanTextFields();

				AlertBox.display("Information Dialog"," \n Look an Information Dialog" ,"\n\n Record is added succesfully");

			} catch (Exception ex) {
				
			}
		});
		
		Button buttonB = new Button("First");
		GridPane.setConstraints(buttonB, 2, 0);
		buttonB.setOnAction(e-> {
			try {
				indexDirection = 0;
				
				readFileFillArrayByArray(person,0);
				
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		
		Button buttonC = new Button("Next");
		GridPane.setConstraints(buttonC, 3, 0);
		buttonC.setOnAction(e->{
			try {
				indexDirection = indexDirection +1;

				readFileFillArrayByArray(person,indexDirection);
				
			}
			catch (Exception ex) {
				AlertBox.display("Information Dialog"," \n Look an Information Dialog" ,"\n\n No item with this id");

				ex.printStackTrace();
			
			}});
			
		Button buttonD = new Button("Previous");
		GridPane.setConstraints(buttonD, 4, 0);
		buttonD.setOnAction(e->{
			try {
				indexDirection = indexDirection - 1;

				readFileFillArrayByArray(person,indexDirection);
			}
			catch (Exception ex) {
				AlertBox.display("Information Dialog"," \n Look an Information Dialog" ,"\n\n No item with this id");

				ex.printStackTrace();
			}});
		
		Button buttonE = new Button("Last");
		GridPane.setConstraints(buttonE, 5, 0);
		buttonE.setOnAction(e->{
			try {
				indexDirection = index - 1;
				readFileFillArrayByArray(person,index - 1);

			}
			catch (IOException ex) {
				ex.printStackTrace();
			}});
		
		Button buttonF = new Button("UpdateByID");
		GridPane.setConstraints(buttonF, 6, 0);
		buttonF.setOnAction(e->{
			 searchUpdateIDInput.setText(searchUpdateIDInput.getText());
			 int id = Integer.valueOf(searchUpdateIDInput.getText());
			 idInput.setPromptText(searchUpdateIDInput.getText());
			 person[id - 1].setName(nameInput.getText());
			 person[id - 1].setStreet(streetInput.getText());
			 person[id - 1].setCity(cityInput.getText());
			 person[id - 1].setGender(genderInput.getText());
			 person[id - 1].setZip(zipInput.getText());
				try {
					raf.close();
					File file = new File("addressaa.dat");
					file.delete();
					raf = new RandomAccessFile("addressaa.dat", "rw");
					for(int i = 0;i<index;i++) {
						updateAddressToFile(raf.length(),i);
					}

					
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			 

		});
		
		Button buttonG = new Button("SearchByID");
		GridPane.setConstraints(buttonG, 7, 0);
		buttonG.setOnAction(e-> {
			int id = Integer.valueOf(searchUpdateIDInput.getText());
			idInput.setPromptText(searchUpdateIDInput.getText());
			nameInput.setText(person[id - 1].getName());
			streetInput.setText(person[id - 1].getStreet());
			cityInput.setText(person[id - 1].getCity());
			genderInput.setText(person[id - 1].getGender());
			zipInput.setText(person[id - 1].getZip());
		});
		
		Button buttonH = new Button("Clean textFields");
		GridPane.setConstraints(buttonH, 8, 0);
		buttonH.setOnAction(e-> cleanTextFields());
		
		bottomMenu.getChildren().addAll(buttonA , buttonB , buttonC,buttonD,buttonE,buttonF,buttonG,buttonH);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(grid);
		borderPane.setBottom(bottomMenu);
		

		Scene scene = new Scene(borderPane,590,180);
		window.setScene(scene);
		window.show();
		
		}
	public void cleanTextFields(){
		
		searchUpdateIDInput.clear();
		nameInput.clear();
		streetInput.clear();
		cityInput.clear();
		genderInput.clear();
		zipInput.clear();

	}
	public void writeAddressToFile(long position) {
		try {
			raf.seek(position);
			if(raf.length() == 0) {
				FileOperations.writeFixedLengthString("1",ID_SIZE, raf);
				idInput.setPromptText("2");

			}else {
			int id = person[index -1 ].getID() + 1;
			String idS = String.valueOf(id);
			idInput.setPromptText(idS);
			FileOperations.writeFixedLengthString(idS,ID_SIZE, raf);

			}
		
			FileOperations.writeFixedLengthString(nameInput.getText(), NAME_SIZE, raf);
			FileOperations.writeFixedLengthString(streetInput.getText(), STREET_SIZE, raf);
			FileOperations.writeFixedLengthString(cityInput.getText(), CITY_SIZE, raf);
			FileOperations.writeFixedLengthString(genderInput.getText(), GENDER_SIZE, raf);
			FileOperations.writeFixedLengthString(zipInput.getText(), ZIP_SIZE, raf);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateAddressToFile(long position,int index) {
		try {
			raf.seek(position);
			if(raf.length() == 0) {
				FileOperations.writeFixedLengthString("1",ID_SIZE, raf);
				idInput.setPromptText("2");

			}else {
			int id = person[index -1 ].getID() + 1;
			String idS = String.valueOf(id);
			idInput.setPromptText(idS);
			FileOperations.writeFixedLengthString(idS,ID_SIZE, raf);

			}
		
			FileOperations.writeFixedLengthString(nameInput.getText(), NAME_SIZE, raf);
			FileOperations.writeFixedLengthString(streetInput.getText(), STREET_SIZE, raf);
			FileOperations.writeFixedLengthString(cityInput.getText(), CITY_SIZE, raf);
			FileOperations.writeFixedLengthString(genderInput.getText(), GENDER_SIZE, raf);
			FileOperations.writeFixedLengthString(zipInput.getText(), ZIP_SIZE, raf);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void readFileFillArray(Person[]people,long position) throws IOException {
		raf.seek(position);
		String ID = FileOperations.readFixedLengthString(ID_SIZE, raf);
		int intID= Integer.parseInt(ID.trim().toString());
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim();
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender= FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();


		Person p= new Person(intID, name, gender, street, city, zip);
		people[index]=p;
		index++;
	}
	
	public void readFileFillArrayByArray(Person[]people,long position) throws IOException {
		String posS = Long.toString(position);
		int posI = Integer.valueOf(posS);
		String ID = String.valueOf(people[posI].getID());
		int intID= Integer.parseInt(ID.trim().toString());
		intID = intID -1;
		String name = people[posI].getName().trim();
		String street =  people[posI].getStreet().trim();
		String city =  people[posI].getCity().trim();
		String gender=  people[posI].getGender().trim();
		String zip =  people[posI].getZip().trim();

		Person p= new Person(intID, name, gender, street, city, zip);
		searchUpdateIDInput.setText(ID);
		nameInput.setText(name);
		streetInput.setText(street);
		cityInput.setText(city);
		genderInput.setText(gender);
		zipInput.setText(zip);
	}

	public void readFileByPos(long position) throws IOException {
		raf.seek(position);
		String ID = FileOperations.readFixedLengthString(ID_SIZE, raf);
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf);
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf);
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf);
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf);
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf);

		searchUpdateIDInput.setText(ID);
		nameInput.setText(name);
		streetInput.setText(street);
		cityInput.setText(city);
		genderInput.setText(gender);
		zipInput.setText(zip);
	}
	}

