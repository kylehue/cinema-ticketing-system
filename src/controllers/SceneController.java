package controllers;

import java.io.IOException;

import application.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	public static Stage stage;
	public static Scene homeScene, scheduleScene, ticketScene, seatsScene, billingScene;
	public static HomeController homeController;
	public static ScheduleController scheduleController;
	public static TicketController ticketController;
	public static SeatsController seatsController;
	public static BillingController billingController;
	
	public SceneController(Stage stage) throws IOException {
		SceneController.stage = stage;
		
		//Setup scene for home
		FXMLLoader home = new FXMLLoader(getClass().getResource("./../scenes/Home.fxml"));
		Parent homeRoot = home.load();
		homeController = home.getController();
		homeScene = new Scene(homeRoot, Constants.width, Constants.height);

		//Setup scene for schedule
		FXMLLoader schedule = new FXMLLoader(getClass().getResource("./../scenes/Schedule.fxml"));
		Parent scheduleRoot = schedule.load();
		scheduleController = schedule.getController();
		scheduleScene = new Scene(scheduleRoot, Constants.width, Constants.height);

		//Setup scene for schedule
		FXMLLoader ticket = new FXMLLoader(getClass().getResource("./../scenes/Ticket.fxml"));
		Parent ticketRoot = ticket.load();
		ticketController = ticket.getController();
		ticketScene = new Scene(ticketRoot, Constants.width, Constants.height);

		//Setup scene for seats
		FXMLLoader seats = new FXMLLoader(getClass().getResource("./../scenes/Seats.fxml"));
		Parent seatsRoot = seats.load();
		seatsController = seats.getController();
		seatsScene = new Scene(seatsRoot, Constants.width, Constants.height);
		
		//Setup scene for billing
		FXMLLoader billing = new FXMLLoader(getClass().getResource("./../scenes/Billing.fxml"));
		Parent billingRoot = billing.load();
		billingController = billing.getController();
		billingScene = new Scene(billingRoot, Constants.width, Constants.height);
	}
	
	public static void switchToHome() {
		stage.setScene(homeScene);
	}
	
	public static void switchToSchedule() {
		stage.setScene(scheduleScene);
	}
	
	public static void switchToTicket() {
		stage.setScene(ticketScene);
	}
	
	public static void switchToSeats() {
		stage.setScene(seatsScene);
	}
	
	public static void switchToBilling() {
		stage.setScene(billingScene);
	}
}
