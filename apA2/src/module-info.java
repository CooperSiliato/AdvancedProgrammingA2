module apA2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;

	opens application to javafx.graphics, javafx.fxml;

}
