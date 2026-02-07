package us.malfeasant.c64stuff.common;

import javafx.scene.paint.Color;

public enum Palette {
    BLACK("000000"), WHITE("ffffff"), RED("813338"), CYAN("75cec8"),
    PURPLE("8e3c97"), GREEN("56ac4d"), BLUE("2e2c9b"), YELLOW("edf171"),
    ORANGE("8e5029"), BROWN("553800"), LRED("c46c71"), DGRAY("4a4a4a"),
    MGRAY("7b7b7b"), LGREEN("a9ff9f"), LBLUE("706deb"), LGRAY("b2b2b2");

    public final Color color;

    Palette(String rgb) {
        color = Color.web(rgb);
    }
}
