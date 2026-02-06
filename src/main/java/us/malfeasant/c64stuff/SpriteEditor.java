package us.malfeasant.c64stuff;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SpriteEditor {
    private ArrayLike bytes;
    private final BorderPane pane;

    /**
     * Builds a new editor with the given array view
     * @param bytes Something containing an array of (at least) 63 bytes
     */
    public SpriteEditor(ArrayLike bytes) {
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
