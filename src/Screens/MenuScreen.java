package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import java.awt.*;

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Audio audio = null;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont characters;
    protected SpriteFont instructions;
    protected SpriteFont options;
    protected SpriteFont credits;
    protected Map background;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    protected TitleAnimation title;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        audio = GamePanel.getAudio();
    		//"Play Game" main menu text
        playGame = new SpriteFont("PLAY GAME", 110, 200, "Comic Sans", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        
        	//"Cosmetics" main menu text
        characters = new SpriteFont("CHARACTERS", 110, 250, "Comic Sans", 30, new Color(49, 207, 240));
        characters.setOutlineColor(Color.black);
        characters.setOutlineThickness(3);

        	//"Instruction" main menu text
        instructions = new SpriteFont("INSTRUCTIONS", 110, 300, "Comic Sans", 30, new Color(49, 207, 240));
        instructions.setOutlineColor(Color.black);
        instructions.setOutlineThickness(3);

        	//"Options" main menu text
        options = new SpriteFont("OPTIONS", 110, 350, "Comic Sans", 30, new Color(49, 207, 240));
        options.setOutlineColor(Color.black);
        options.setOutlineThickness(3);

        	//"Credits" main menu text
        credits = new SpriteFont("CREDITS", 110, 400, "Comic Sans", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);

        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        title = new TitleAnimation(125, 20);
        keyTimer.setWaitTime(200);
        menuItemSelected = -1;
        keyLocker.lockKey(Key.currentINTERACT);
    }

    public void update() {
        	// update background map (to play tile animations)
        background.update(null);
        title.update();
        audio.startPlayingLoop(5);
        	// if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.currentDOWN) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            audio.startPlayingOnce(6);
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.currentUP) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            audio.startPlayingOnce(6);
            currentMenuItemHovered--;
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 4) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 4;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
        	//Highlists "Play Game"
            playGame.setColor(new Color(255, 215, 0));
            characters.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color( 49, 207, 240));
            options.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(49, 207, 240));
            pointerLocationX = 70;
            pointerLocationY = 180;
        } else if (currentMenuItemHovered == 1) {
        	//Highlights "Cosmetics Shop"
            playGame.setColor(new Color(49, 207, 240));
            characters.setColor(new Color(255, 215, 0));
            instructions.setColor(new Color(49, 207, 240));
            options.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(49, 207, 240));
            pointerLocationX = 70;
            pointerLocationY = 230;
        } else if (currentMenuItemHovered == 2) {
        	//Highlights "Instructions"
            playGame.setColor(new Color(49, 207, 240));
            characters.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(255, 215, 0));
            options.setColor(new Color(49, 207, 240));
            credits.setColor(new Color(49, 207, 240));
            pointerLocationX = 70;
            pointerLocationY = 280;
        }else if (currentMenuItemHovered == 3) {
        	//Highlights "Options"
        	playGame.setColor(new Color(49, 207, 240));
        	characters.setColor(new Color(49, 207, 240));
            instructions.setColor(new Color(49, 207, 240));
            options.setColor(new Color(255, 215, 0));
            credits.setColor(new Color(49, 207, 240));
            pointerLocationX = 70;
            pointerLocationY = 330;
        } else if (currentMenuItemHovered == 4) {
        	//Highlights "Credits"
        	playGame.setColor(new Color(49, 207, 240));
        	characters.setColor(new Color(49, 207, 240));
        	instructions.setColor(new Color(49, 207, 240));
        	options.setColor(new Color(49, 207, 240));
        	credits.setColor(new Color(255, 215, 0));
        	pointerLocationX = 70;
        	pointerLocationY = 380;
        }

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.currentINTERACT)) {
            keyLocker.unlockKey(Key.currentINTERACT);
        }
        if (!keyLocker.isKeyLocked(Key.currentINTERACT) && Keyboard.isKeyDown(Key.currentINTERACT)) {
            menuItemSelected = currentMenuItemHovered;
            audio.startPlayingOnce(6);
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVELSELECT);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CHARACTERS);
            } else if (menuItemSelected == 2) {
            	screenCoordinator.setGameState(GameState.INSTRUCTIONS);
            } else if (menuItemSelected == 3) {
            	screenCoordinator.setGameState(GameState.OPTIONS);
            } else if (menuItemSelected == 4) {
            	screenCoordinator.setGameState(GameState.CREDITS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        characters.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
        options.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        title.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }

    public int getMenuItemSelected() {
        return menuItemSelected;
    }
}