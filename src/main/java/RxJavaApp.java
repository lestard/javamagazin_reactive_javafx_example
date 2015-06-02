import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class RxJavaApp extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox root = new VBox();




        final Observable<Long> ascendingNumbers = Observable.interval(1, TimeUnit.SECONDS);
        final Observable<Integer> temperature = temperatureService();

        final Observable<XYChart.Data<Number, Number>> dataObservable =
                Observable.zip(ascendingNumbers, temperature, XYChart.Data::new);


        final LineChart<Number, Number> lineChart =
                new LineChart<>(new NumberAxis(), new NumberAxis());
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        lineChart.getData().add(series);

        dataObservable.subscribe(data -> Platform.runLater(()-> series.getData().add(data)));




        root.getChildren().add(lineChart);
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }


    /**
     * A dummy implementation for a temperature service that
     * returns random values between 20 and 29 degree celsius every second.
     */
    public Observable<Integer> temperatureService() {
        final Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);

        return interval.map(i -> new Random().nextInt(10)+20);
    }

}
