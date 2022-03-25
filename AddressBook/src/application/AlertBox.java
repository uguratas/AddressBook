package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

	public static void display(String title,String message1,String message2) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		GridPane bottomMenu = new GridPane();
		bottomMenu.setPadding(new Insets(10,10,10,35));
		
		Label label1 = new Label();
		label1.setText(message1);
		GridPane.setConstraints(label1, 0,0);
		
		Label label2 = new Label();
		label2.setText(message2);
		GridPane.setConstraints(label2, 0,0);
		Button closeButton = new Button("OK");
		GridPane.setConstraints(closeButton,1000,1000);
		closeButton.setOnAction(e->window.close());
		closeButton.setMinWidth(80);
		
		
		bottomMenu.getChildren().addAll(label1 , closeButton);
		BorderPane borderPane= new BorderPane();
		borderPane.setCenter(closeButton);
		borderPane.setLeft(label2);
		borderPane.setTop(label1);
		
		Scene scene = new Scene(borderPane,350,100);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
