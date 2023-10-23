package application;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class VIPFeaturesController {
	@FXML
	private Button viewPieChartButton;
	
	@FXML
	public void showPieChart() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("PieChartView.fxml"));
	        Parent root = loader.load();

	        Scene pieChartScene = new Scene(root);
	        Stage primaryStage = (Stage) viewPieChartButton.getScene().getWindow();

	        primaryStage.setScene(pieChartScene);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
    public void backToDashboard() {
        // Load the dashboard FXML and show it
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) viewPieChartButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
