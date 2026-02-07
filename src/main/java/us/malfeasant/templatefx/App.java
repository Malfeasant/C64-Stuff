package us.malfeasant.templatefx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import us.malfeasant.c64stuff.sprite.SpriteEditor;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var grid = new GridPane(5.0, 5.0);
        var charButton = newButton("Character", e -> {});
        var spriteButton = newButton("Sprite", e -> SpriteEditor.standalone());
        var bitmapButton = newButton("Bitmap", e -> {});
        var hexButton = newButton("Raw Hex", e -> {});
        var asmButton = newButton("Assemble", e -> {});
        var dasmButton = newButton("Disassemble", e -> {});

        grid.addRow(0, charButton, spriteButton);
        grid.addRow(1, bitmapButton, hexButton);
        grid.addRow(2, asmButton, dasmButton);

        stage.setTitle("C64 Stuff");
        stage.setScene(new Scene(grid));
        stage.show();
    }

    private Button newButton(String text, EventHandler<ActionEvent> e) {
        var button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e);
        return button;
    }

    public static void main(String[] args) {
        launch();
    }

}