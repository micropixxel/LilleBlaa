package hiof.magnus.mpgame;

import com.badlogic.gdx.Game;

import java.util.Random;

import hiof.magnus.mpghelpers.AssetLoader;
import hiof.magnus.screens.MenuScreen;

public class MPGame extends Game {

    @Override
    public void create() {
        AssetLoader.load();
        Random r = new Random();
        AssetLoader.setGamesLeft(r.nextInt(5));
        setScreen(new MenuScreen(this, 0));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
