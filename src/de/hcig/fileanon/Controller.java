package de.hcig.fileanon;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 *
 *
 * @author Dipl. Ing. Clemens Schartmüller, Technische Hochschule Ingolstadt
 **/
public class Controller {



    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField dateRegex;

    private FileChooser fileChooser;
    private Stage stage;


    @FXML
    public void initialize(){
        setDefaultDatePickerDate();

        dateRegex.setText(FileAnonymizerUtil.DATE_REGEX_DEFAULT);
    }

    private void setDefaultDatePickerDate() {
        datePicker.setValue(LocalDate.now());
    }

    public void dirChooserClicked(MouseEvent mouseEvent) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Dir with to-be-anonymized Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        //fileChooser.showOpenDialog(stage);
        Scene scene = datePicker.getScene();
        Window window = scene.getWindow();
        stage = (Stage) window;
        List<File> list =
                fileChooser.showOpenMultipleDialog(stage);

        if(list != null && list.size() > 0)
            askForConfirmation(list);
    }

    private void askForConfirmation(List<File> fileList) {

        Optional<ButtonType> response = showConfirmationDialog(fileList);

        if(response != null && response.get() == ButtonType.OK){
            processConfirmedFiles(fileList);
        }

    }

    private void processConfirmedFiles(List<File> list) {
        String dateRegexString = dateRegex.getText();
        if(dateRegexString == null || dateRegexString.isEmpty()){
            dateRegexString = FileAnonymizerUtil.DATE_REGEX_DEFAULT;
        }
        List<File> anonFiles = FileAnonymizerUtil.anonymizeFiles(list, datePicker.getValue(), dateRegexString);
        if(anonFiles.size() == list.size()){
            Alert resultDialog = new Alert(Alert.AlertType.INFORMATION);
            resultDialog.setTitle("Processing done!");
            resultDialog.setContentText("All files were successfully anonymized");
            resultDialog.showAndWait();

        }else{
            list.removeAll(anonFiles);
            Alert resultDialog = new Alert(Alert.AlertType.WARNING);
            resultDialog.setHeaderText(list.size() + " files could not be renamed or have their lastAccess-date set:");
            setAsExpandableDialogText(resultDialog, buildFileList(list).toString());
            resultDialog.showAndWait();

        }
    }

    private Optional<ButtonType> showConfirmationDialog(List<File> list) {
        // show dilaog
        Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
        alertDialog.setTitle("Process "+list.size()+" files...?");
        alertDialog.setHeaderText("Do you really want to anonymize these "+list.size()+" files (NO COPIES ARE MADE!) ?");

        final String contentText = buildFileList(list).toString();

        setAsExpandableDialogText(alertDialog, contentText);

        return alertDialog.showAndWait();
    }

    private void setAsExpandableDialogText(Alert alertDialog, String contentText) {

        alertDialog.getDialogPane().setPrefSize(480, 320);

        TextArea textArea = new TextArea(contentText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setStyle("overflow-y: visible;");

// Set expandable Exception into the dialog pane.
        alertDialog.getDialogPane().setContent(textArea);
        alertDialog.setContentText(contentText);
    }

    private StringBuilder buildFileList(List<File> list) {
        StringBuilder builder = new StringBuilder();
        for(File f : list){
            builder.append(f.getName()).append("\n");
        }
        return builder;


    }
}
