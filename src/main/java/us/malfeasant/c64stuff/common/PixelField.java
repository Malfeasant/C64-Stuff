package us.malfeasant.c64stuff.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;

public class PixelField {
    public final BooleanProperty multicolorEnable;
    public final ColorProperty color0Property;
    public final ColorProperty color1Property;
    public final ColorProperty color2Property;
    public final ColorProperty color3Property;
    
    public final IntegerProperty fieldWidthProperty;
    public final IntegerProperty fieldHeightProperty;
    
    public final DoubleProperty viewWidthProperty;
    public final DoubleProperty viewHeightProperty;
    
    public final ObjectProperty<ArrayLike> arrayProperty;

    private final Canvas editorCanvas;

    public PixelField() {
        multicolorEnable = new SimpleBooleanProperty();
        color0Property = new ColorProperty();
        color1Property = new ColorProperty();
        color2Property = new ColorProperty();
        color3Property = new ColorProperty();

        editorCanvas = new Canvas();

        fieldWidthProperty = new SimpleIntegerProperty();
        fieldHeightProperty = new SimpleIntegerProperty();

        viewWidthProperty = editorCanvas.widthProperty();
        viewHeightProperty = editorCanvas.heightProperty();

        arrayProperty = new SimpleObjectProperty<>();


    }
}
