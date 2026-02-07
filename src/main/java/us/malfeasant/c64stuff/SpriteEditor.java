package us.malfeasant.c64stuff;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpriteEditor {
    private ArrayLike bytes;
    private boolean modified = false;
    private final BorderPane pane;

    /**
     * Builds a new editor with the given array view
     * @param bytes Something containing an array of (at least) 63 bytes
     */
    public SpriteEditor(ArrayLike bytes) {
        this.bytes = bytes;

        var itemNew = new MenuItem("New...");
        var itemOpen = new MenuItem("Open...");
        var itemSave = new MenuItem("Save");
        var itemSaveAs = new MenuItem("Save as...");
        var itemExit = new MenuItem("Exit");
        var menuFile = new Menu("File", null, 
            itemNew, itemOpen, itemSave, itemSaveAs, itemExit);
        var menuBar = new MenuBar(menuFile);

        pane = new BorderPane();
        pane.setTop(menuBar);
    }

    /**
     * rather than commit to making a new window, we return a pane, so caller can decide to make
     * a new window, or display within an existing one.  Maybe we'll include a static method to
     * build a standalone window?
     * @return a complete pane
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * before closing window, ask user if they want to save, or similar preparation
     * @return true if ok to close, false should do nothing.
     */
    private boolean prepareClose() {
        // TODO check if modified, save dialog maybe
        return true;
    }

    /**
     * Builds a window and a new Editor with a basic implementation of the ArrayLike interface
     */
    public static void standalone() {
        var bytes = new ArrayLike() {
            private byte[] bytes = new byte[63];

            @Override
            public void set(int index, int value) {
                bytes[index] = (byte) value;
            }

            @Override
            public int get(int index) {
                return bytes[index] & 0xff; // needed otherwise we might get a negative
            }
        };
        var editor = new SpriteEditor(bytes);
        Stage stage = new Stage();
        stage.setOnCloseRequest(e -> {
            if (!editor.prepareClose()) e.consume();;
        });
        stage.setTitle("C=64 Sprite Editor");
        stage.setScene(new Scene(editor.getPane()));
        stage.show();
    }
}
