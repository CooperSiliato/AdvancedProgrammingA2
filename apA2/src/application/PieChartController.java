package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PieChartController {

    @FXML
    private PieChart pieChart;

    public void initialize() {
        // Read "shares" data from the "posts.csv" file and generate the pie chart
        try (BufferedReader reader = new BufferedReader(new FileReader("csvfiles/posts.csv"))) {
            // Skip the header line
            reader.readLine();

            int shares0To99 = 0;
            int shares100To999 = 0;
            int shares1000Plus = 0;
            
            int i = 0;
            int j = 0;
            int k = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int shares = Integer.parseInt(parts[4]);
                    if (shares >= 0 && shares <= 99) {
                        shares0To99++;
                        System.out.println("i" + i);
                        i++;
                    } else if (shares >= 100 && shares <= 999) {
                        shares100To999++;
                        System.out.println("j" + j);
                        j++;
                    } else {
                        shares1000Plus++;
                        System.out.println("k" + k);
                        k++;
                    }
                }
            }

            // Create the pie chart data
            PieChart.Data data0To99 = new PieChart.Data("0-99 Shares", shares0To99);
            PieChart.Data data100To999 = new PieChart.Data("100-999 Shares", shares100To999);
            PieChart.Data data1000Plus = new PieChart.Data("1000+ Shares", shares1000Plus);

            // Add the data to the pie chart
            pieChart.getData().addAll(data0To99, data100To999, data1000Plus);

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
            Stage stage = (Stage) pieChart.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
