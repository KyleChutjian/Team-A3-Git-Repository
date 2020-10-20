package Screens;

import Engine.*;
import SpriteFont.SpriteFont;

import java.awt.*;

// This is the class for the level lose screen
public class Level3LoseScreen extends Screen {
    protected SpriteFont loseMessage;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected Level3ForestScreen level3ForestScreen;

    public Level3LoseScreen(Level3ForestScreen level3ForestScreen) {
        this.level3ForestScreen = level3ForestScreen;
    }

    @Override
    public void initialize() {
        loseMessage = new SpriteFont("You lose!", 350, 270, "Comic Sans", 30, Color.white);
        instructions = new SpriteFont("Press Interact key to try again or Escape to go back to the main menu", 120, 300,"Comic Sans", 20, Color.white);
        keyLocker.lockKey(Key.currentINTERACT);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.currentINTERACT)) {
            keyLocker.unlockKey(Key.currentINTERACT);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        // if space is pressed, reset level. if escape is pressed, go back to main menu
        if (Keyboard.isKeyDown(Key.currentINTERACT)) {
            level3ForestScreen.resetLevel();
        } else if (Keyboard.isKeyDown(Key.ESC)) {
            level3ForestScreen.goBackToMenu();
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        loseMessage.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
    }
}