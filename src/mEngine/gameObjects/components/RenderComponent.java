package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Dimension;
import mEngine.graphics.renderable.Model;

public class RenderComponent extends Component {

    public Model model;
    String modelFileName;

    public RenderComponent(String modelFileName) {

        this.modelFileName = modelFileName;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        model = new Model(modelFileName, parent.position, parent.rotation);

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null; //Delete unserializable model

    }

    @Override
    public void onLoad() {

        super.onLoad();
        model = new Model(modelFileName, parent.position, parent.rotation); //Create model again

    }
}
