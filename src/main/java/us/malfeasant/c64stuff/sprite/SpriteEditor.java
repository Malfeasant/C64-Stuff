package us.malfeasant.c64stuff.sprite;

import javafx.beans.property.BooleanProperty;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import us.malfeasant.c64stuff.common.ArrayLike;
import us.malfeasant.c64stuff.common.ColorButton;
import us.malfeasant.c64stuff.common.Palette;

public class SpriteEditor {
    private static final int SPRITE_WIDTH = 24;
    private static final int SPRITE_HEIGHT = 21;
    // Enlarge the pixels for modern display.  Try to get the aspect ratio right.
    private static final double WIDTH_CORRECT = 0.91 * 3;
    private static final double HEIGHT_CORRECT = 1.09 * 3;
    private static final int EDITOR_ZOOM = 8;
    private static final double EDITOR_ZOOM_WIDTH = EDITOR_ZOOM * WIDTH_CORRECT;
    private static final double EDITOR_ZOOM_HEIGHT = EDITOR_ZOOM * HEIGHT_CORRECT;
    private static final double VIEW_WIDTH = WIDTH_CORRECT * SPRITE_WIDTH;
    private static final double VIEW_HEIGHT = HEIGHT_CORRECT * SPRITE_HEIGHT;
    private static final double EDITOR_WIDTH = EDITOR_ZOOM * WIDTH_CORRECT * SPRITE_WIDTH;
    private static final double EDITOR_HEIGHT = EDITOR_ZOOM * HEIGHT_CORRECT * SPRITE_HEIGHT;

    private ArrayLike bytes;
    private boolean modified = false;
    private final BorderPane pane;
    private final WritableImage pattern;
    private final BooleanProperty multiColorProperty;

    private final ColorButton[] colors;
    private final Canvas canvasEditor;

    /**
     * Builds a new editor with the given array view
     * @param bytes Something containing an array of (at least) 63 bytes
     */
    public SpriteEditor(ArrayLike bytes) {
        this.bytes = bytes;
        pattern = new WritableImage(SPRITE_WIDTH, SPRITE_HEIGHT);

        var itemNew = new MenuItem("New...");
        var itemOpen = new MenuItem("Open...");
        var itemSave = new MenuItem("Save");
        var itemSaveAs = new MenuItem("Save as...");
        var itemExit = new MenuItem("Exit");
        itemExit.setOnAction(e -> tryClose(e));
        var menuFile = new Menu("File", null, 
            itemNew, itemOpen, itemSave, itemSaveAs, itemExit);
        var menuBar = new MenuBar(menuFile);

        var buttonMulti = new ToggleButton("Multicolor");
        buttonMulti.setMaxWidth(Double.MAX_VALUE);
        multiColorProperty = buttonMulti.selectedProperty();

        colors = new ColorButton[4];
        colors[0] = new ColorButton("Background", Palette.BLUE);
        colors[1] = new ColorButton("Forground", Palette.LBLUE);
        colors[2] = new ColorButton("Multicolor 0", Palette.PURPLE);
        colors[3] = new ColorButton("Multicolor 1", Palette.BLACK);
        colors[2].button.visibleProperty().bind(multiColorProperty);
        colors[3].button.visibleProperty().bind(multiColorProperty);
        var gridButtonColor = new GridPane(5.0, 5.0);
        gridButtonColor.addColumn(0, buttonMulti,
            colors[0].button, colors[1].button,
            colors[2].button, colors[3].button);
        
        canvasEditor = new Canvas(EDITOR_WIDTH, EDITOR_HEIGHT);
        canvasEditor.setOnMouseClicked(e -> handleEditorClick(e));
        
        var imageViewNormal = new ImageView(pattern);
        imageViewNormal.setFitWidth(1 * VIEW_WIDTH);
        imageViewNormal.setFitHeight(1 * VIEW_HEIGHT);

        var imageViewWide = new ImageView(pattern);
        imageViewWide.setFitWidth(2 * VIEW_WIDTH);
        imageViewWide.setFitHeight(1 * VIEW_HEIGHT);

        var imageViewTall = new ImageView(pattern);
        imageViewTall.setFitWidth(1 * VIEW_WIDTH);
        imageViewTall.setFitHeight(2 * VIEW_HEIGHT);

        var imageViewLarge = new ImageView(pattern);
        imageViewLarge.setFitWidth(2 * VIEW_WIDTH);
        imageViewLarge.setFitHeight(2 * VIEW_HEIGHT);

        var gridSpriteView = new GridPane(5.0, 5.0);
        gridSpriteView.addRow(0, imageViewNormal, imageViewWide);
        gridSpriteView.addRow(1, imageViewTall, imageViewLarge);

        pane = new BorderPane();
        pane.setTop(menuBar);
        pane.setLeft(gridButtonColor);
        pane.setRight(gridSpriteView);
        pane.setCenter(canvasEditor);
        redraw();
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
    private boolean canClose() {
        // TODO check if modified, save dialog maybe
        return true;
    }

    private void tryClose(Event e) {
        if (!canClose()) e.consume();
    }

    private void handleEditorClick(MouseEvent e) {
        var whichButton = e.getButton();
        var x = (int) (e.getX() / (EDITOR_ZOOM_WIDTH));
        var y = (int) (e.getY() / (EDITOR_ZOOM_HEIGHT));

        if (whichButton == MouseButton.PRIMARY) {
            setBit(x, y, true);
        } else if (whichButton == MouseButton.SECONDARY) {  // could also be middle...
            setBit(x, y, false);
        }
        redraw();
    }

    private void setBit(int x, int y, boolean c) {
        var col = x / 8;
        var bi = x % 8;
        var index = y * 3 + col;
        var power = 1 << (7 - bi);
        var by = bytes.get(index);
        if (c) {
            by |= power;
        } else {
            by &= ~power;
        }
        bytes.set(index, by);
    }

    private void redraw() {
        var writer = pattern.getPixelWriter();
        var gc = canvasEditor.getGraphicsContext2D();
        for (int row = 0; row < SPRITE_HEIGHT; ++row) {
            for (int col = 0; col < 3; ++col) {
                int b = bytes.get(row * 3 + col);
                for (int bit = 0; bit < 8; ++bit) {
                    int i = b >> (7 - bit);
                    Color c = colors[i & 1].colorProperty.get().color;
                    writer.setColor(col * 8 + bit, row, c);
                    gc.setFill(c);
                    gc.fillRect(
                        EDITOR_ZOOM_WIDTH * (col * 8 + bit),
                        EDITOR_ZOOM_HEIGHT * row,
                        EDITOR_ZOOM_WIDTH, 
                        EDITOR_ZOOM_HEIGHT
                    );
                }
            }
        }
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
        // Test pattern
        byte b = (byte) 0xaa;
        for (int row = 0; row < SPRITE_HEIGHT; ++row) {
            for (int col = 0; col < 3; ++col) {
                bytes.bytes[row * 3 + col] = b;
            }
            b ^= 0xff;
        }

        var editor = new SpriteEditor(bytes);
        Stage stage = new Stage();
        stage.setOnCloseRequest(e -> editor.tryClose(e));
        stage.setTitle("C=64 Sprite Editor");
        stage.setScene(new Scene(editor.getPane()));
        stage.show();
    }
}
