package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.RenderQueue;
import mEngine.graphics.Renderer;
import mEngine.util.PreferenceHelper;
import mEngine.util.TimeHelper;
import mEngine.util.serialization.Serializer;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class RenderLoop implements Runnable {

    @Override
    public void run() {

        GraphicsController.createDisplay(PreferenceHelper.getValue("title"));


        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));
            Renderer.currentRenderQueue = new RenderQueue();

            //Renders all the gameObjects
            for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

                if (!Serializer.isSerializing) ObjectController.getGameObject(i).addToRenderQueue();

            }

            Renderer.currentRenderQueue.render();
            TimeHelper.updateFPS();
            GraphicsController.update();

        }

    }

}