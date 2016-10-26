package hiof.magnus.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import hiof.magnus.gameworld.GameRenderer;
import hiof.magnus.gameworld.GameWorld;
import hiof.magnus.mpgame.MPGame;
import hiof.magnus.mpghelpers.InputHandler;

/**
 * Created by Gamer on 13.03.2015.
 */
public class GameScreen implements Screen {

    private MPGame game;
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen(MPGame game) {
        this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(game, midPointY); // Init world
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new GameRenderer(world, (int) gameHeight, midPointY); // Init renderer
        world.setRenderer(renderer);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta); // GameWorld updates
        renderer.render(delta, runTime); // GameRenderer renders
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
