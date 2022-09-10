package controllers;

import java.io.IOException;

import application.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	public static Stage stage;
	public static Scene homeScene, scheduleScene, ticketScene, seatsScene, billingScene, overviewScene, successScene;
	public static HomeController homeController;
	public static ScheduleController scheduleController;
	public static TicketController ticketController;
	public static SeatsController seatsController;
	public static BillingController billingController;
	public static OverviewController overviewController;
	public static SuccessController successController;
	
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
		
		//Setup scene for overview
		FXMLLoader overview = new FXMLLoader(getClass().getResource("./../scenes/Overview.fxml"));
		Parent overviewRoot = overview.load();
		overviewController = overview.getController();
		overviewScene = new Scene(overviewRoot, Constants.width, Constants.height);
		
		//Setup scene for success
		FXMLLoader success = new FXMLLoader(getClass().getResource("./../scenes/Success.fxml"));
		Parent successRoot = success.load();
		successController = success.getController();
		successScene = new Scene(successRoot, Constants.width, Constants.height);
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
	
	public static void switchToOverview() {
		stage.setScene(overviewScene);
	}
	
	public static void switchToSuccess() {
		stage.setScene(successScene);
	}
}
