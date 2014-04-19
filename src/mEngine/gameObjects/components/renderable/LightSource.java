package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class LightSource extends ComponentRenderable {

    public float strength;
    public float radiusRadians;
    public Vector3f position;
    public Vector4f color;
    public Vector3f direction;

    public LightSource(float strength) {

        this(strength, new Vector4f(1, 1, 1, 1), new Vector3f(), 0);

    }

    public LightSource(float strength, Vector4f color) {

        this(strength, color, new Vector3f(), 0);

    }

    public LightSource(float strength, Vector4f color, Vector3f direction) {

        this(strength, color, direction, -1);

    }

    public LightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(1, 1, 1, 1), direction, -1);

    }

    public LightSource(float strength, Vector3f direction, float radius) {

        this(strength, new Vector4f(1, 1, 1, 1), direction, radius);

    }

    public LightSource(float strength, Vector4f color, Vector3f direction, float radius) {

        this.strength = strength;
        this.color = color;
        this.direction = VectorHelper.normalizeVector(direction);

        if (radius == 0 || radius == -1) {

            radiusRadians = radius;

        } else {



        }

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        position = parent.position;

    }

    public void onUpdate() {

        position = parent.position;

        if (!VectorHelper.areEqual(direction, new Vector3f()) && !(radiusRadians == 0)) {

            //direction = parent.percentRotation;

            /*if (!(radiusRadians != -1)) {



            }*/

        }

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addLightSource(this);

    }

}
