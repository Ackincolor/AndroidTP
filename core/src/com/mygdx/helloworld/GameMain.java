package com.mygdx.helloworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class GameMain extends ApplicationAdapter {
	ModelBatch batch;
	public PerspectiveCamera cam;
	public Model model;
	public Array<ModelInstance> instance;
	public Environment environement;
	public CameraInputController camController;
	public EnvironmentCubemap envCubemap;
	
	@Override
	public void create () {

		environement = new Environment();
		environement.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
		environement.add(new DirectionalLight().set(0.8f,0.8f,0.8f,-1f,-0.8f,-0.2f));

		cam = new PerspectiveCamera(67,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(1f,1f,1f);
		cam.lookAt(0,0,0);
		cam.near = 0.1f;
		cam.far = 300f;
		cam.update();
		//ModelBuilder modelBuilder = new ModelBuilder();
		//model = modelBuilder.createBox(5f,5f,5f, new Material(ColorAttribute.createDiffuse(Color.BLUE)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		ModelLoader loader = new ObjLoader();
		model = loader.loadModel(Gdx.files.internal("data/ship/ship.obj"));
		instance = new Array<ModelInstance>();
		ModelInstance model1 = new ModelInstance(model);
		instance.add(model1);
		batch = new ModelBatch();
		this.envCubemap = new EnvironmentCubemap(new Pixmap(Gdx.files.internal("data/Skybox/Day_Skybox.png")));
		camController = new CameraInputController(cam);
		GestureDetector inputAdapterPerso = new InputAdapterPerso(cam);
		InputMultiplexer inputMultiplexer = new InputMultiplexer(inputAdapterPerso);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render () {
		camController.update();
		Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin(cam);
		batch.render(instance, environement);
		this.envCubemap.render(cam);
		batch.end();
	}
	
	@Override
	public void dispose () {
		model.dispose();
		batch.dispose();
		envCubemap.dispose();
	}/*
	public int getObject (int screenX, int screenY) {
		Ray ray = cam.getPickRay(screenX, screenY);
		int result = -1;
		float distance = -1;
		for (int i = 0; i < instances.size; ++i) {
			final GameObject instance = instances.get(i);
			instance.transform.getTranslation(position);
			position.add(instance.center);
			float dist2 = ray.origin.dst2(position);
			if (distance >= 0f && dist2 > distance) continue;
			if (Intersector.intersectRaySphere(ray, position, instance.radius, null)) {
				result = i;
				distance = dist2;
			}
		}
		return result;
	}*/
}