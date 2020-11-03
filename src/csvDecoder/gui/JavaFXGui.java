package csvDecoder.gui;

import java.util.Set;

import csvDecoder.TaskAmountMapping;
import csvDecoder.util.StudentData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXGui extends Application {
	
	private int exercise;
	private Set<StudentData> data;
	
	public JavaFXGui(int exercise, Set<StudentData> data) throws Exception {
		this.exercise = exercise;
		this.data = data;
		start(new Stage());
	}
	
	//TODO
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//build table view and columns
		TableView<StudentData> tableView = new TableView<StudentData>();
		TableColumn<StudentData, Integer> exerciseNr = new TableColumn<StudentData, Integer>();
		tableView.getColumns().add(exerciseNr);
		TableColumn<StudentData, String> name = new TableColumn<StudentData, String>();
		tableView.getColumns().add(name);
		for (int i = 0; i < TaskAmountMapping.getAmountOfTasks(exercise); i++) {
			TableColumn<StudentData, Boolean> vote = new TableColumn<StudentData, Boolean>();
			tableView.getColumns().add(vote);
		}
		
		//fill the table view
		for (StudentData curData : data) {
			  tableView.getItems().add(curData);
		}
		
	    VBox vbox = new VBox(tableView);
	    Scene scene = new Scene(vbox);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	
}
