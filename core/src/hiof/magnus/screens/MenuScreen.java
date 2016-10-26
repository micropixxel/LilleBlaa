package hiof.magnus.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

import hiof.magnus.mpgame.MPGame;
import hiof.magnus.mpghelpers.AssetLoader;

/**
 * Created by Gamer on 16.03.2015.
 */
public class MenuScreen implements Screen {

    private MPGame game;

    private Stage stage = new Stage();
    private Table table = new Table();
    private Skin skin;
    private TextureAtlas atlas;
    private TextButton buttonHome, buttonGame, buttonPlayLB, buttonPlayIG;
    private Label usage, total, totalVal, info, gamesLeftTxt, gamesLeftInt, gameNrTwo;
    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();
    private float gameWidth = 272;
    private float gameHeight = screenHeight / (screenWidth / gameWidth);
    private float scaleX = screenWidth / gameWidth;
    private float scaleY = screenHeight / gameHeight;
    Image usageBar = new Image();
    Image lilleBlaIcon = new Image();

    private MenuState currentState;

    public enum MenuState {
        INFO, GAME
    }

    public MenuScreen(MPGame game, int state) {
        this.game = game;
        Random r = new Random();
        if (state == 1) {
            currentState = MenuState.GAME;
        } else {
            currentState = MenuState.INFO;
            AssetLoader.setRandomUsage(r.nextInt(50));
        }
        AssetLoader.setGameChosen(0);

        atlas = new TextureAtlas(Gdx.files.internal("data/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        skin.addRegions(atlas);
        buttonHome = new TextButton("Hjem", skin);
        buttonGame = new TextButton("Spill", skin);
        buttonPlayLB = new TextButton("Lille Blå", skin);
        buttonPlayIG = new TextButton("Spill #2", skin); // Jump Box (Impossible Game clone)
        usage = new Label("Forbruk:", skin);
        total = new Label("Totalt:", skin);
        totalVal = new Label("132 kW", skin);
        info = new Label("Kalkulerer forbruket.", skin);
        gamesLeftTxt = new Label("Antall spill:", skin);
        gameNrTwo = new Label("Spill #2 ikon", skin);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        lilleBlaIcon.setDrawable(new TextureRegionDrawable(AssetLoader.icon));
        // Next part is to be usage fetched from smart meter, and measured up
        // with neighbouring homes' usage:
        if (AssetLoader.getUsageLevel() == 1) {
            usageBar.setDrawable(new TextureRegionDrawable(AssetLoader.usageBarLvl1));
            info.setText("Høyt forbruk.");
        } else if (AssetLoader.getUsageLevel() == 2) {
            usageBar.setDrawable(new TextureRegionDrawable(AssetLoader.usageBarLvl2));
            info.setText("Over normalt forbruk.");
        } else if (AssetLoader.getUsageLevel() == 3) {
            usageBar.setDrawable(new TextureRegionDrawable(AssetLoader.usageBarLvl3));
            info.setText("Normalt forbruk.");
        } else if (AssetLoader.getUsageLevel() == 4) {
            usageBar.setDrawable(new TextureRegionDrawable(AssetLoader.usageBarLvl4));
            info.setText("Under normalt forbruk.");
        } else {
            usageBar.setDrawable(new TextureRegionDrawable(AssetLoader.usageBarLvl5));
            info.setText("Lavt forbruk.");
        }
        gamesLeftInt = new Label("" + getGamesLeft(), skin);
    }

    @Override
    public void show() {
        buttonHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = MenuState.INFO;
                drawStage();
            }
        });
        buttonGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentState = MenuState.GAME;
                drawStage();
            }
        });
        buttonPlayLB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (getGamesLeft() > 0) {
                    AssetLoader.setGameChosen(1);
                    //((MPGame) Gdx.app.getApplicationListener()).setScreen(new SplashScreen(game));
                    ((MPGame) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game));
                }
            }
        });
        drawStage();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        stage.dispose();
    }

    private void drawStage() {
        table.reset();
        table.top();
        table.setBounds(0, 0, screenWidth, screenHeight);
        table.add(buttonHome).size(100 * scaleX, 30 * scaleY).padTop(5).padBottom(20);
        table.add(buttonGame).size(100 * scaleX, 30 * scaleY).padTop(5).padBottom(20).row();
        if (currentState == MenuState.INFO) {
            drawInfo();
        } else if (currentState == MenuState.GAME) {
            drawGame();
        }
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void drawInfo() {
        table.add(usage).size(usage.getPrefWidth() * scaleX, usage.getPrefHeight() * scaleY).padBottom(20);
        table.add(usageBar).size(usageBar.getPrefWidth() * scaleX, usageBar.getPrefHeight() * scaleY).padBottom(20).row();
        table.add(total).size(total.getPrefWidth() * scaleX, total.getPrefHeight() * scaleY).padBottom(20);
        table.add(totalVal).size(totalVal.getPrefWidth() * scaleX, totalVal.getPrefHeight() * scaleY).padBottom(20).row();
        table.add(info).size(info.getPrefWidth() * scaleX, info.getPrefHeight() * scaleY).colspan(2).center();
    }

    private void drawGame() {
        table.add(gamesLeftTxt).size(gamesLeftTxt.getPrefWidth() * scaleX, gamesLeftTxt.getPrefHeight() * scaleY).padBottom(20);
        table.add(gamesLeftInt).size(gamesLeftInt.getPrefWidth() * scaleX, gamesLeftInt.getPrefHeight() * scaleY).padBottom(20).row();
        table.add(lilleBlaIcon).size(lilleBlaIcon.getPrefWidth() * scaleX, lilleBlaIcon.getPrefHeight() * scaleY).padBottom(20);
        table.add(buttonPlayLB).size(100 * scaleX, 30 * scaleY).padBottom(20).row();
        table.add(gameNrTwo).size(gameNrTwo.getPrefWidth() * scaleX, gameNrTwo.getPrefHeight() * scaleY).padBottom(20);
        table.add(buttonPlayIG).size(100 * scaleX, 30 * scaleY).padBottom(20).row();
    }

    private int getGamesLeft() {
        return AssetLoader.getGamesLeft();
    }
}
