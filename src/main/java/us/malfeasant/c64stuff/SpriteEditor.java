package us.malfeasant.c64stuff;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SpriteEditor {
    private final byte[] bytes;
    private final BorderPane pane;

    public SpriteEditor() {
        this(new byte[63]);
    }
    /**
     * Will make a helper function to extract the array from specific ArrayLike implementations
     * then call this?
     * @param bytes an array of (at least) 63 bytes
     */
    SpriteEditor(byte[] bytes) {
        if (bytes.length < 63) {
            throw new ArrayIndexOutOfBoundsException("Array must be at least 63 bytes long.");
        }
        this.bytes = bytes;

        pane = new BorderPane();
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
}
