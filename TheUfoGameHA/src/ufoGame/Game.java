package ufoGame;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ufoGame.Projectile;
import view.GameFrameWork;
import view.IGameObject;
import view.IKeyboardListener;
import view.ITickableListener;
import view.Message;

/**
 * @author 
 * @version 1.1
 */
public class Game implements ITickableListener, IKeyboardListener {

	private ArrayList<Ufo> ufos = new ArrayList<>();
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private Ship ship;
	private int screenWidth = 1700;
	private int screenHeight = 900;
	private GameFrameWork view = new GameFrameWork();
	private Background background;
	private int counter = 0;
	private int targetMissed = 0;
	private boolean gameOver = false;
	private Message score;
	private Message ammo;
	private int collisionTimer = 0;
	private int MAX_MISSED = 20;
	private static final int COLLISION_DELAY = 5;
	
	/**
	 * Starts the game.
	 * The game field is set to the given screen width and screen height. The highest screen size could just be your screen size.
	 * @param screenWidth of the game window
	 * @param screenHeight of the game window
	 */
	public void init(int screenWidth, int screenHeight) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;

		//create all game objects and the background image
		background = new Background("src\\assets\\space.png");
		ship = new Ship(screenWidth / 2, 3 * screenHeight / 4, screenHeight / 8, screenHeight /7,
				"src\\assets\\ship.png");

		Ufo ufo = new Ufo(-20, screenHeight / 6, screenHeight / (2 * 5), screenHeight / (2 * 9), 6,
				"src\\assets\\ufo.png" + "");
		ufos.add(ufo);

		for (int i = 1; i < 10; i++) {
			addUfo();
		}
		
		score = new Message("SCORE: " + counter, 50, 50, 30,
				new Color(0, 160, 225));
		ammo = new Message("AMMO: " + MAX_MISSED, screenWidth - 200, 50, 30,
				new Color(255, 60, 0));
		
		
	
		view.setSize(screenWidth, screenHeight);
		view.addGameObject(ship);
		view.setBackground(background);
		view.addTick(this);
		view.addIKeyInput(this);
		view.addMessage(score);
		view.addMessage(ammo);
	}

	@Override
	public void tick(long ellapseTime) {
		
		if (!gameOver) {

			moveUfos();
			moveProjectiles();			
			handleProjectileOutsideWindow();
			checkCollision();
			handleCollision();
			checkGameOver();
		
		}
	}

	
	/**
	 * Checks if two rectangle objects from type {@link IGameObject} collides
	 * @param objA to check if it collides 
	 * @param objB to check if it collides 
	 * @return true if the rectangles collides if not returns false.
	 */
	protected boolean isCollided(IGameObject objA, IGameObject objB) {

		if ((objA.getX() < (objB.getX() + objB.getWidth())) && ((objA.getX() + objA.getWidth()) > objB.getX())
				&& ((objA.getY()) < (objB.getY() + objB.getHeight()))
				&& ((objA.getY() + objA.getHeight()) > objB.getY())) {
			return true;
		}
		return false;
	}

	protected void addUfo() {
		Ufo ufo = new Ufo(ufos.get(ufos.size() - 1).getX() - 500, ufos.get(0).getY(), ufos.get(0).getWidth(),
				ufos.get(0).getHeight(), ufos.get(0).getSpeed(), ufos.get(0).getSprite());
		ufos.add(ufo);
		view.addGameObject(ufo);
	}


	private void shoot() {
		Projectile projectile = new Projectile(ship.getX() + ship.getWidth()/4, 
				ship.getY() - ship.getWidth() / 2, ship.getWidth() / 2, ship.getWidth() / 2, 3,
				"src\\assets\\projectile.png");
		projectiles.add(projectile);

		view.addGameObject(projectile);

	}


	protected void checkGameOver() {
		if (MAX_MISSED == 0) {
			gameOver = true;
			view.addMessage(new Message("Game Over! \n" + counter + " Points", screenWidth / 2 - 150,
					screenHeight / 2, 40, new Color(255, 255, 255)));
		}
	}

	
	public void checkCollision() {
		if (collisionTimer == 0) {
			for (Projectile projectile : projectiles) {
				for (Ufo ufo : ufos) {

					if (isCollided(ufo, projectile)) {
						ufo.setCollided(true);
						projectile.setCollided(true);
						counter++;
						score.setMessage("SCORE: " + counter);
						collisionTimer++;
						return;
					}
				}
			}
		}
	}


	protected void handleCollision() {
		
		//Increase delay counter
		if (collisionTimer > 0) {
			collisionTimer++;
		}

		//after a certain delay remove collided object
		if (collisionTimer >= COLLISION_DELAY) {
			for (Ufo ufo : ufos) {
				if (ufo.isCollided()) {
					view.removeGameObject(ufo);
					ufos.remove(ufo);
					addUfo();
					break;
				}
			}
			for (Projectile projectile : projectiles) {
				if (projectile.isCollided()) {
					view.removeGameObject(projectile);
					projectiles.remove(projectile);
					break;
				}
			}			
			collisionTimer = 0;

			levelChanger();
		}
	}


	private void moveProjectiles() {
		
		for (Projectile projectile : projectiles) {
			projectile.move();
		}
	}


	private void handleProjectileOutsideWindow() {
		if (!projectiles.isEmpty() && (projectiles.get(0).getY() + projectiles.get(0).getHeight() < 0)) {
			MAX_MISSED--;
			ammo.setMessage("AMMO: " + MAX_MISSED);
			view.removeGameObject(projectiles.get(0));
			projectiles.remove(0);
		}
	}

	private void moveUfos() {
		if (ufos.get(0).getX() > screenWidth) {
			view.removeGameObject(ufos.get(0));
			ufos.remove(0);
			addUfo();
		}
		for (Ufo ufo : ufos) {
			ufo.move();
		}
	}

	@Override
	public int[] getKeys() {
		int[] events = { KeyEvent.VK_SPACE  , KeyEvent.VK_RIGHT,KeyEvent.VK_LEFT};
		return events;
	}
	
	
	@Override
	public void keyDown(int key) {
		if (key ==  KeyEvent.VK_SPACE) {
			shoot();
		}
		else if (key == KeyEvent.VK_RIGHT) {
			ship.moveRight();
		}
		else if (key == KeyEvent.VK_LEFT) {
			ship.moveLeft();
		}
		
	}

	
	private void levelChanger() {
		if (counter % 3 == 0) {
			for (Ufo ufo : ufos) {
				ufo.increaseSpeed();
			}
		}
	}
}
