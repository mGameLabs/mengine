package mEngine.graphics;

import mEngine.gameObjects.components.Camera;
import mEngine.gameObjects.components.Skybox;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.graphics.renderable.Model;

import java.util.ArrayList;
import java.util.List;

public class RenderQueue {

    private Camera camera;
    private Skybox skybox;
    private List<Model> modelQueue = new ArrayList<Model>();
    private List<GUIElement> guiQueue = new ArrayList<GUIElement>();

    public void addCamera(Camera camera) {

        this.camera = camera;

    }

    public void addSkybox(Skybox skybox) {

        this.skybox = skybox;

    }

    public void addModel(Model model) {

        modelQueue.add(model);

    }

    public void addGUIElement(GUIElement element) {

        guiQueue.add(element);

    }

    public void render() {

        GraphicsController.switchTo3D();
        for (Model model : modelQueue) {

            model.render();

        }

        skybox.render();

        GraphicsController.switchTo2D();
        for (GUIElement element : guiQueue) {

            element.render();

        }

        GraphicsController.switchTo3D();
        if (camera != null) camera.render();

    }

}
