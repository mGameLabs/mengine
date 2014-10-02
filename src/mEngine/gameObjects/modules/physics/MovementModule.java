/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.physics;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.controls.Controller;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.physics.forces.Force;
import mEngine.physics.forces.ForceController;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class MovementModule extends Module {

    public Map<String, Force> forces = new HashMap<String, Force>();
    public int forceCount = 0;
    public Vector3f speed;
    public Vector3f movedSpace;
    Vector3f previousSpeed;
    boolean sprinting;
    boolean sneaking;
    float mass = 0;

    public MovementModule() {

        speed = new Vector3f();
        movedSpace = new Vector3f();
        previousSpeed = new Vector3f();

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        RenderModule renderComponent = (RenderModule) obj.getModule(RenderModule.class);

        if (renderComponent != null) mass = renderComponent.model.getMass();

        else mass = 60;

        for (String key : ForceController.generalForces.keySet()) {

            forces.put(key, ForceController.generalForces.get(key));

        }

    }

    public void onUpdate() {

        if (!GameController.isGamePaused) {

            Controller controller = (Controller) parent.getModule(Controller.class);
            PhysicsModule physics = (PhysicsModule) parent.getModule(PhysicsModule.class);

            if (controller != null) {

                if (!controller.sprintModeToggle) sprinting = false;
                if (!controller.sneakModeToggle) sneaking = false;

                controller.onRemoteUpdate();

            }

            parent.percentRotation = new Vector3f(0, 0, 1);

            Matrix3f xAxisRotationMatrix = new Matrix3f(
              new Vector3f(1, 0, 0),
              new Vector3f(0, (float) Math.cos(Math.toRadians(parent.rotation.x)), (float) -Math.sin(Math.toRadians(parent.rotation.x))),
              new Vector3f(0, (float) Math.sin(Math.toRadians(parent.rotation.x)), (float) Math.cos(Math.toRadians(parent.rotation.x))));
            parent.percentRotation = xAxisRotationMatrix.multiplyByVector(parent.percentRotation);

            Matrix3f yAxisRotationMatrix = new Matrix3f(
              new Vector3f((float) Math.cos(Math.toRadians(parent.rotation.y)), 0, (float) Math.sin(Math.toRadians(parent.rotation.y))),
              new Vector3f(0, 1, 0),
              new Vector3f((float) -Math.sin(Math.toRadians(parent.rotation.y)), 0, (float) Math.cos(Math.toRadians(parent.rotation.y))));
            parent.percentRotation = yAxisRotationMatrix.multiplyByVector(parent.percentRotation);


        }

    }

    public void moveInSpecificDirection(Vector3f direction) {

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    private Vector3f calculateForce(Vector3f direction) {
        Vector3f force = new Vector3f();

        force.x = -(direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        force.z = direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        force.y = direction.y;

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{force, new Vector3f(0.3f, 1, 0.3f)});

            force.x = newDirection.x;
            force.z = newDirection.z;

        }

        return force;
    }

    private void applyForce(Vector3f direction) {
        Vector3f force = calculateForce(direction);
        PhysicsModule physics = (PhysicsModule) parent.getModule(PhysicsModule.class);

        physics.applyCentralForce(new javax.vecmath.Vector3f(force.x, force.y, force.z));
    }

    public void moveForward() {
        applyForce(new Vector3f(0, 0, -10));
    }

    public void moveBackward() {
        applyForce(new Vector3f(0, 0, 10));
    }

    public void moveLeft() {
        applyForce(new Vector3f(10, 0, 0));
    }

    public void moveRight() {
        applyForce(new Vector3f(-10, 0, 0));
    }

    public void moveDown() {
        applyForce(new Vector3f(0, -10, 0));
    }

    public void moveUp() {
        applyForce(new Vector3f(0, 10, 0));
    }

    public void jump() {

        forces.get("jump").enabled = true;

    }

    public void sprint() {

        Controller controller = (Controller) parent.getModule(Controller.class);

        if (controller != null) {

            if (!controller.sprintModeToggle) {

                sprinting = true;
                sneaking = false;

            } else {

                sprinting = !sprinting;
                sneaking = false;

            }

        }

    }

    public void sneak() {

        Controller controller = (Controller) parent.getModule(Controller.class);


        if (controller != null)
            if (!sprinting)
                sneaking = !controller.sneakModeToggle || !sneaking;

    }

    public void rotate(float pitch, float yaw) {

        parent.rotation.x = pitch;
        parent.rotation.y = yaw;

    }

}