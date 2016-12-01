package pixelizer.graphics.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[66568];
	private boolean left, right, a, d, backspace, enter;
	private boolean lastLeft, lastRight, lastA, lastD;
	private boolean space, lastSpace;
	private boolean e, lastE;
	private int holdLeft = 0;
	private int holdRight = 0;

	public void update() {
		lastE = e;
		lastSpace = space;
		lastLeft = left;
		lastRight = right;
		lastA = a;
		lastD = d;
		backspace = keys[KeyEvent.VK_BACK_SPACE];
		enter = keys[KeyEvent.VK_ENTER];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];
		right = keys[KeyEvent.VK_RIGHT];;
		left = keys[KeyEvent.VK_LEFT];
		space = keys[KeyEvent.VK_SPACE];
		e = keys[KeyEvent.VK_E];
		if(left) {
			holdLeft++;
		}
		else {
			holdLeft = 0;
		}
		if(right) {
			holdRight++;
		}
		else {
			holdRight = 0;
		}
	}
	
	public boolean getE() {
		return e && !lastE;
	}
	
	public boolean getSpace() {
		return space && !lastSpace;
	}
	
	public boolean getBack() {
		return backspace;
	}
	
	public boolean getForward() {
		return enter;
	}
	
	int holdTime = 30;
	public boolean getLeft() {
		return (left && (!lastLeft || holdLeft >= holdTime)) || (a && (!lastA || holdLeft >= holdTime));
	}
	
	public boolean getRight() {
		return (right && (!lastRight || holdRight >= holdTime)) || (d && (!lastD || holdRight >= holdTime));
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}

}
