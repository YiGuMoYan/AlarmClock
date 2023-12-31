package top.yigumoyan;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static void updateTime(Label currentTimeLabel) {
        Date date = new Date();
        String currentTimeLabelText = formatTime(date);
        currentTimeLabel.setText(currentTimeLabelText);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TIME_FORMAT);
        return dateFormat.format(date);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        EventList eventList = new EventList();
        EventList currentList = new EventList();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        Label currentTimeLabel = new Label();
        currentTimeLabel.setFont(new Font(Constant.TIME_FONT_SIZE));
        gridPane.add(currentTimeLabel, 0, 0);

        GridPane inputGridPane = new GridPane();
        inputGridPane.setAlignment(Pos.CENTER);
        Label nextEventTimeLabel = new Label(Constant.NEXT_INPUT_EVENT_TIME);
        Label nextEventLabel = new Label(Constant.NEXT_INPUT_EVENT);
        TextField timeTextField = new TextField();
        TextField eventTextField = new TextField();
        Button submitButton = new Button(Constant.COMMIT);
        inputGridPane.add(nextEventTimeLabel, 0, 0);
        inputGridPane.add(timeTextField, 1, 0);
        inputGridPane.add(nextEventLabel, 0, 1);
        inputGridPane.add(eventTextField, 1, 1);
        inputGridPane.add(submitButton, 0, 2);
        gridPane.add(inputGridPane, 0, 1);

        timeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // 使用正则检查是否数字
            if (!newValue.matches("\\d*")) {
                timeTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        submitButton.setOnAction(event -> {
            Date date = new Date();
            int time = Integer.parseInt(timeTextField.getText());
            date.setTime(date.getTime() + time * 1000L);
            String eventMessage = eventTextField.getText();
            Event e = new Event(date, eventMessage);
            eventList.push(e);
        });

        Scene Scene = new Scene(gridPane, Constant.STAGE_WIDTH, Constant.STAGE_HEIGHT);

        primaryStage.setScene(Scene);

        primaryStage.setWidth(Constant.STAGE_WIDTH);
        primaryStage.setHeight(Constant.STAGE_HEIGHT);

        Date lastDate = new Date();

        GridPane eventGridPane = new GridPane();

        eventGridPane.setAlignment(Pos.CENTER);
        eventGridPane.setHgap(20);

        EventList lastCurrentEventList = new EventList(currentList.eventList);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            updateTime(currentTimeLabel);
            Date currentDate = new Date();

            currentList.remove(currentDate);
            List<Event> events = eventList.getEventList(currentDate);
            currentList.pushList(events);

            if (currentList.compareTo(lastCurrentEventList) > 0) {
                return;
            }

            eventGridPane.getChildren().clear();

            if (currentList.isEmpty()) {
                return;
            }

            gridPane.getChildren().remove(eventGridPane);

            eventGridPane.add(new Label(Constant.TIME), 0, 0);
            eventGridPane.add(new Label(Constant.EVENT), 1, 0);

            for (int i = 0; i < currentList.getLength(); i++) {
                Event e = currentList.eventList.get(i);
                eventGridPane.add(new Label(formatTime(e.getTime())), 0, i + 1);
                eventGridPane.add(new Label(e.getMessage()), 1, i + 1);
            }
            lastCurrentEventList.setEventList(currentList.eventList);
            gridPane.add(eventGridPane, 0, 2);

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.show();
    }
}
