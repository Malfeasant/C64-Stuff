package us.malfeasant.c64stuff;

/**
 * This is a hack to run without modules-
 * rather than starting the JavaFX app directly,
 * we launch this class which then calls main() on the App class.
 * Otherwise, JavaFX subsystem doesn't get invoked properly.
 * Only needed when we're packaging into a fat jar.
 */
public class Launch {
    public static void main(String[] args) {
        System.out.println("Running from Launch class");
        App.main(args);
    }
}
