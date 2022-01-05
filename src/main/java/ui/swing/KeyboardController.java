package ui.swing;

import domain.needForSpear.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class KeyboardController implements KeyListener {
    private Controller controller = Controller.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public KeyboardController() { this.controller = controller; }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!controller.isPaused()) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) controller.updateMovementNP("HeldRight");
            if(e.getKeyCode() == KeyEvent.VK_LEFT) controller.updateMovementNP("HeldLeft");

            if(e.getKeyCode() == KeyEvent.VK_A) controller.rotateNoblePhantasm("rotateLeft");
            if(e.getKeyCode() == KeyEvent.VK_D) controller.rotateNoblePhantasm("rotateRight");

            if(e.getKeyCode() == KeyEvent.VK_W) controller.startPlaying();

            if(e.getKeyCode() == KeyEvent.VK_C) controller.getPlayer().useAbility("ChanceGiving");
            if(e.getKeyCode() == KeyEvent.VK_E) controller.getPlayer().useAbility("DoubleNP");
            if(e.getKeyCode() == KeyEvent.VK_M) controller.getPlayer().useAbility("MagicalHex");
            if(e.getKeyCode() == KeyEvent.VK_U) controller.getPlayer().useAbility("Unstoppable");

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Reserved for future use
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!controller.isPaused()){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) controller.updateMovementNP("PressedRight");
            if(e.getKeyCode() == KeyEvent.VK_LEFT) controller.updateMovementNP("PressedLeft");

        }
    }
}
