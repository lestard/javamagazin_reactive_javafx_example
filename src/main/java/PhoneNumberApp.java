import eu.lestard.advanced_bindings.api.StringBindings;
import eu.lestard.advanced_bindings.api.SwitchBindings;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PhoneNumberApp extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();


        ChoiceBox<String> languagesChoiceBox = new ChoiceBox<>();
        languagesChoiceBox.setItems(FXCollections.observableArrayList("DE", "US", "other"));
        languagesChoiceBox.getSelectionModel().select("DE");



        ObjectProperty<String> language = languagesChoiceBox.valueProperty();

        final ObservableValue<String> phonePattern =
                SwitchBindings.switchBinding(language, String.class)
                    .bindCase("DE", l -> "\\+?[0-9\\s]{3,20}")
                    .bindCase("US", l -> "^[2-9]\\d{2}-\\d{3}-\\d{4}$")
                    .bindDefault(() -> "[0-9 ]{3,20}")
                    .build();



        TextField phoneNumberInput = new TextField();
        Label errorMessage = new Label("Falsches Telefonnummer-Format");


        final BooleanBinding valid = StringBindings.matches(phoneNumberInput.textProperty(), phonePattern);

        errorMessage.visibleProperty().bind(valid.not());




        root.setSpacing(10);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(languagesChoiceBox, phoneNumberInput, errorMessage);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
