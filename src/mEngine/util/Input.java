package mEngine.util;

import org.lwjgl.input.Keyboard;

public class Input {

    private static boolean[] keyStates = new boolean[Keyboard.getKeyCount()];

    public static boolean isKeyPressed(int key) { return Keyboard.isKeyDown(key); }

    public static boolean isKeyDown(int key) {

        keyStates[key] = isKeyPressed(key);
        boolean isAlreadyActivated = keyStates[key];
        return keyStates[key] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isKeyUp(int key) {

        boolean isAlreadyActivated = keyStates[key];
        keyStates[key] = isKeyPressed(key);
        return keyStates[key] != isAlreadyActivated && isAlreadyActivated;

    }

}
