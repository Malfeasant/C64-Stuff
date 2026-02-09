package us.malfeasant.c64stuff.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;

public class ColorButton {
    public final Button button;
    public final ObjectProperty<Palette> colorProperty;

    public ColorButton(String text, Palette color) {
        colorProperty = new SimpleObjectProperty<Palette>();
        button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.textFillProperty().bind(colorProperty.map(c -> c.color.invert()));
        button.backgroundProperty().bind(colorProperty.map(c -> Background.fill(c.color)));
        colorProperty.set(color);
    }

    public void setColor(Palette color) {
        colorProperty.set(color);
    }
}
