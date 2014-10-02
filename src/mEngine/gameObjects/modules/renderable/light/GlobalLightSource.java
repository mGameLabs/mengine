package mEngine.gameObjects.modules.renderable.light;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GlobalLightSource extends LightSource {

    public GlobalLightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(255, 255, 255, 1), direction);

    }

    public GlobalLightSource(float strength, Vector4f color, Vector3f direction) {

        super(strength, color, direction);

    }

}