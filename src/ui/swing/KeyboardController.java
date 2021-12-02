package ui.swing;

import domain.needForSpear.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class KeyboardController implements KeyListener {
    Controller controller = Controller.getInstance();
    Scanner scanner = new Scanner(System.in);

    public KeyboardController() { this.controller = controller; }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!controller.isPaused()) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) controller.updateMovementNP("HeldRight");
            if(e.getKeyCode() == KeyEvent.VK_LEFT) controller.updateMovementNP("HeldLeft");

            if(e.getKeyCode() == KeyEvent.VK_A) controller.rotateNoblePhantasm("rotateLeft");
            if(e.getKeyCode() == KeyEvent.VK_D) controller.rotateNoblePhantasm("rotateRight");

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
