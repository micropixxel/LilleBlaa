package hiof.magnus.mpghelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Gamer on 13.03.2015.
 */
public class AssetLoader {

    public static Texture texture, logoTexture, iconTexture;
    public static TextureRegion logo, zbLogo, bg, grass, bird, birdDown, birdUp,
            skullUp, skullDown, bar, playButtonUp, playButtonDown, ready, gameOver,
            highScore, scoreboard, star, noStar, retry, homeButtonUp, homeButtonDown,
            gameButtonUp, gameButtonDown, usageBarLvl1, usageBarLvl2, usageBarLvl3,
            usageBarLvl4, usageBarLvl5, icon;
    public static Animation birdAnimation;
    public static Sound dead, flap, coin, fall;
    public static Music blaafugl, menu;
    public static BitmapFont font, shadow, whiteFont;
    public static Preferences prefs;
    public static UsageData uDat;
    public static int lvl;

    public static void load() {
        logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 512, 86);

        iconTexture = new Texture(Gdx.files.internal("data/icon.png"));
        iconTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        icon = new TextureRegion(iconTexture, 0, 0, 72, 72);

        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);
        homeButtonUp = new TextureRegion(texture, 0, 101, 29, 16);
        homeButtonDown = new TextureRegion(texture, 29, 101, 29, 16);
        gameButtonUp = new TextureRegion(texture, 0, 119, 29, 16);
        gameButtonDown = new TextureRegion(texture, 29, 119, 29, 16);
        homeButtonUp.flip(false, true);
        homeButtonDown.flip(false, true);
        gameButtonUp.flip(false, true);
        gameButtonDown.flip(false, true);

        ready = new TextureRegion(texture, 59, 83, 34, 7);
        ready.flip(false, true);

        retry = new TextureRegion(texture, 59, 110, 33, 7);
        retry.flip(false, true);

        gameOver = new TextureRegion(texture, 59, 92, 46, 7);
        gameOver.flip(false, true);

        scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
        scoreboard.flip(false, true);

        star = new TextureRegion(texture, 152, 70, 10, 10);
        noStar = new TextureRegion(texture, 165, 70, 10, 10);

        star.flip(false, true);
        noStar.flip(false, true);

        highScore = new TextureRegion(texture, 59, 101, 48, 7);
        highScore.flip(false, true);

        zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
        zbLogo.flip(false, true);

        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);

        TextureRegion[] birds = { birdDown, bird, birdUp };
        birdAnimation = new Animation(0.06f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

        usageBarLvl1 = new TextureRegion(texture, 220, 0, 75, 15);
        usageBarLvl2 = new TextureRegion(texture, 220, 20, 75, 15);
        usageBarLvl3 = new TextureRegion(texture, 220, 40, 75, 15);
        usageBarLvl4 = new TextureRegion(texture, 220, 60, 75, 15);
        usageBarLvl5 = new TextureRegion(texture, 220, 80, 75, 15);

        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

        blaafugl = Gdx.audio.newMusic(Gdx.files.internal("data/blaafugl.wav"));
        // Music "borrowed" from FamilyJules7X (https://www.youtube.com/user/FamilyJules7X)
        menu = Gdx.audio.newMusic(Gdx.files.internal("data/GymLeaderBattle.mp3"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"), true);
        font.setScale(.25f, .25f);
        whiteFont = new BitmapFont(Gdx.files.internal("data/whitetext.fnt"), true);
        whiteFont.setScale(.1f, .1f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"), true);
        shadow.setScale(.25f, .25f);

        // Create (or retrieve existing) preference file
        prefs = Gdx.app.getPreferences("MasterProjectGame");
        //prefs.putInteger("highScore", 0); //reset highScore temporarily for testing purposes

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
        if (!prefs.contains("gamesLeft")) {
            prefs.putInteger("gamesLeft", 5);
        }
        if (!prefs.contains("gameChosen")) {
            prefs.putInteger("gameChosen", 0);
        }
        if (!prefs.contains("randomUsage")) {
            prefs.putInteger("randomUsage", 0);
        }
        /*if (!prefs.contains("lastTime")) {
            long time = System.nanoTime();
            double seconds = time / 1E9;
            long minutes = (long) (seconds / 60);
            prefs.putLong("lastTime", minutes);
        }*/
        FileLoader fl = new FileLoader();
        int rand = fl.getRandomID();
        if (!prefs.contains("installID")) {
            prefs.putInteger("installID", rand);
            prefs.flush();
        }
        String[] useData = fl.storeUsageData(prefs.getInteger("installID"));
        float[] vals = fl.getVal(useData[1], useData[4]);
        float sum = fl.getSum(vals);
        uDat = new UsageData(useData[0], vals, sum, useData[3]);
        lvl = uDat.getUsageLevel();
        setGamesLeft(lvl);
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void loseOneLife() {
        int val = getGamesLeft() - 1;
       /*if (val < 1) {
            val = 1;
        }*/
        prefs.putInteger("gamesLeft", val);
        prefs.flush();
    }

    public static void setGamesLeft(int val) {
        int games = getGamesLeft();
        /*long time = System.nanoTime();
        long seconds = (long) (time / 1E9);
        long minutes = seconds / 60;
        int hours = (int) ((minutes - getLastTime()) / 60 );
        if (val < 1) {
            val = 1;
        }*/
        if (games >= val) {
            // Do nothing
        /*} else if (hours >= val) {*/
            prefs.putInteger("gamesLeft", val);
            prefs.flush();
        /*} else if (hours < val) {
            if (hours < 1) {
                hours = 1;
            }
            prefs.putInteger("gamesLeft", hours);
            prefs.flush();*/
        } else {
            prefs.putInteger("gamesLeft", val);
            prefs.flush();
        }
    }

    public static int getGamesLeft() {
        return prefs.getInteger("gamesLeft");
    }

    public static void setGameChosen(int val) {
        prefs.putInteger("gameChosen", val);
        prefs.flush();
    }

    public static int getGameChosen() {
        return prefs.getInteger("gameChosen");
    }

    public static void setRandomUsage(int val) {
        prefs.putInteger("randomUsage", val);
        prefs.flush();
    }

    public static int getRandomUsage() {
        return prefs.getInteger("randomUsage");
    }

    public static void setInstallID(int val) {
        prefs.putInteger("installID", val);
        prefs.flush();
    }

    public static int getInstallID() {
        return prefs.getInteger("installID");
    }

    /*public static void setLastTime() {
        long time = System.nanoTime();
        long seconds = (long) (time / 1E9);
        long minutes = seconds / 60;
        prefs.putLong("lastTime", minutes);
        prefs.flush();
    }

    public static long getLastTime() {
        return prefs.getLong("lastTime");
    }*/

    public static int getUsageLevel() {
        return lvl;
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
        logoTexture.dispose();
        iconTexture.dispose();

        // Dispose sounds
        dead.dispose();
        flap.dispose();
        coin.dispose();
        fall.dispose();

        blaafugl.dispose();
        menu.dispose();

        font.dispose();
        whiteFont.dispose();
        shadow.dispose();
    }
}
