package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;

/**
 * Created by Univetech Sarl on 01/11/2016.
 */
public class MainDoeuveController {
    @FXML
    private TextField label;
    @FXML
    private TextField nombrejours;
    @FXML
    private TextField montant;
    @FXML
    private TextField coefficient;
    @FXML
    private Button validate;

    private MainApp mainApp;
    private Stage stage;
    private ObservableList<Products> observableList = FXCollections.observableArrayList();
    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}
    public void setStage(Stage stage) {this.stage = stage;}
    public void setObservableList(ObservableList<Products> observableList) {
        this.observableList = observableList;
    }


    @FXML
    private void initialize(){
        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (IsInputValid()){
                    observableList.add(
                            new Products(
                                    label.getText(),
                                    label.getText().substring(0,3),
                                    "1",
                                    montant.getText(),
                                    coefficient.getText(),
                                    "0",
                                    String.valueOf(Math.round(Double.parseDouble(montant.getText()) * Double.parseDouble(coefficient.getText())))
                                    ,"122"
                            )
                    );
                    label.setText("");
                    coefficient.setText("");
                    nombrejours.setText("");
                    montant.setText("");
                    mainApp.closeCurrentWindow(stage);
                }
            }
        });
    }

    private boolean IsInputValid(){
        String errorMessage = "";
        if (label.getText().isEmpty() || label.getText() == null)
            errorMessage += "Vous devez renseigner un label";
        if (coefficient.getText().isEmpty() || coefficient.getText() == null)
            errorMessage += "Renseigner la ville";
        if (Double.isNaN(Double.parseDouble(coefficient.getText())))
            errorMessage += "Le coefficient doit etre un nombre";
        if (montant.getText().isEmpty() || montant.getText() == null)
            errorMessage += "Le Montant doit etre renseigner";
        if (Double.isNaN(Double.parseDouble(montant.getText())))
            errorMessage += "Le Montant doit etre un nombre";
        if (nombrejours.getText().isEmpty() || nombrejours.getText() == null)
            errorMessage += "Le nombre de jours doit etre renseigner";
        if (Double.isNaN(Double.parseDouble(nombrejours.getText())))
            errorMessage += "Le nombre de jours doit etre un nombre";
        return errorMessage.isEmpty();

    }



}
