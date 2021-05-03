package application;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "1234")) {
			
			TableManager tableManager = new TableManager(conn);
    		
    		primaryStage.setTitle("Hello World!");
            
            Group root = new Group();
            
            Canvas canvas = new Canvas( 1200, 1000 );
            root.getChildren().add( canvas );
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.fillText( "Passengers of Titanic", 700, 50 );
            
            System.out.println("Hey! Hey!");
            //System.out.println(tableManager.GetRawList());
            
            //-------------------------------------------------------------
            
            TableView<Passenger> table = new TableView<Passenger>();
            ObservableList<Passenger> data = FXCollections.observableArrayList(tableManager.GetRawList(1, 50));
            
     
            Label label = new Label("Passengers of Titanic");
            label.setFont(new Font("Arial", 20));
     
            table.setEditable(true);
     
            TableColumn nameCol = new TableColumn("Name");
            nameCol.setMinWidth(400);
            nameCol.setCellValueFactory(
                    new PropertyValueFactory<Passenger, String>("name"));
     
            TableColumn sexCol = new TableColumn("Sex");
            sexCol.setMinWidth(100);
            sexCol.setCellValueFactory(
                    new PropertyValueFactory<Passenger, String>("sex"));
     
            TableColumn ageCol = new TableColumn("Age");
            ageCol.setMinWidth(50);
            ageCol.setCellValueFactory(
                    new PropertyValueFactory<Passenger, String>("age"));
            
            TableColumn survivedCol = new TableColumn("Survived");
            survivedCol.setMinWidth(50);
            survivedCol.setCellValueFactory(
                    new PropertyValueFactory<Passenger, String>("survived"));
     
            table.setItems(data);
            table.getColumns().addAll(nameCol, sexCol, ageCol, survivedCol);
     
            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table);
            
            root.getChildren().addAll( vbox );
            
            //-----------------------------------------------------------------
            
            Map <String, Integer> mfRatio = tableManager.MaleFemaleRatio();
            
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                    	new PieChart.Data("Male (" + mfRatio.get("male") + ")", mfRatio.get("male")),
                    	new PieChart.Data("Female (" + mfRatio.get("female") + ")", mfRatio.get("female"))
                    );
            final PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Male-Female Ratio");
            chart.setLayoutX(700);
            
            root.getChildren().add(chart);
            
            //------------------------------------------------------------------
            
            Map <String, NumOfSurvivors> numOfSurvivors = tableManager.GetSurvived();
            
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
            bc.setTitle("Survival");
            xAxis.setLabel("Sex");       
            yAxis.setLabel("Number of people");
            
            
            XYChart.Series survived = new XYChart.Series();
            survived.setName("Survived");
            survived.getData().add(new XYChart.Data("Male", numOfSurvivors.get("Male").getSurvived()));
            survived.getData().add(new XYChart.Data("Female", numOfSurvivors.get("Female").getSurvived()));
            
            XYChart.Series died = new XYChart.Series();
            died.setName("Died");
            died.getData().add(new XYChart.Data("Male", numOfSurvivors.get("Male").getDied()));
            died.getData().add(new XYChart.Data("Female", numOfSurvivors.get("Female").getDied()));
            
            
            bc.getData().addAll(survived, died);
            bc.setLayoutX(700);
            bc.setLayoutY(500);
            root.getChildren().add(bc);
            
            //------------------------------------------------------------------
            
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
			
		} catch (SQLException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
