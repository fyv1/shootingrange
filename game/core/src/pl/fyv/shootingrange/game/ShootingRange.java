package pl.fyv.shootingrange.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.codeandweb.physicseditor.PhysicsShapeCache;
import pl.fyv.shootingrange.game.strings.MainStrings;

import java.util.Arrays;
import java.util.Random;

public class ShootingRange extends ApplicationAdapter {
	SpriteBatch batch;
	Texture coinTxt;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	BitmapFont font;
	float timeHelper;
	boolean	isMouseClicked;
	int[] pointerPos;
	Circle bounds;
	float txtRadius;
	int score;
	Music music;

	float x;
	float y;

	@Override
	public void create () {
		batch = new SpriteBatch();
		coinTxt = new Texture("img/coin.png");

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Lato-Regular.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		parameter.borderColor = Color.BLACK;
		parameter.borderWidth = 5f;

		font = generator.generateFont(parameter);

		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if (button == Input.Buttons.LEFT) {
					isMouseClicked = true;
					pointerPos = new int[]{screenX, screenY};
					return true;
				}
				return false;
			}
		});

		x = randomizer(true);
		y = randomizer(false);
		txtRadius = coinTxt.getHeight()/2f;

		bounds = new Circle(x+txtRadius, Gdx.graphics.getHeight()-y-txtRadius, txtRadius);

		music = Gdx.audio.newMusic(Gdx.files.internal("music/playing.mp3"));
		music.setLooping(true);
		music.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(coinTxt, x, y);

		font.draw(batch, Integer.toString(score), Gdx.graphics.getWidth() - 69, Gdx.graphics.getHeight() - 100); //score
		timeHelper += Gdx.graphics.getDeltaTime();
		if (timeHelper < 30) {

			String displayTime = Float.toString(timeHelper).substring(0, 4);
			font.draw(batch, displayTime, Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 40); //timer


			if (isMouseClicked) {
				if (bounds.contains(pointerPos[0], pointerPos[1])) {
					drawRandomPos();
					score++;
				}
				isMouseClicked = false;
			}

		} else {
			font.draw(batch, "30.0", Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 40);
			font.draw(batch, MainStrings.GAME_OVER, Gdx.graphics.getWidth()/2f - 110, Gdx.graphics.getHeight()/2f - 18);
		}

		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		coinTxt.dispose();
		generator.dispose();
	}

	private void drawRandomPos() {
		x = randomizer(true);
		y = randomizer(false);
		batch.draw(coinTxt, x, y);
		bounds.set(x+txtRadius, Gdx.graphics.getHeight()-y-txtRadius, txtRadius);
	}

	private float randomizer(boolean resolutionFlag) {
		Random random = new Random();
		return random.nextFloat() * ((resolutionFlag ? Gdx.graphics.getWidth() : Gdx.graphics.getHeight())-coinTxt.getHeight()/2f+1);
	}
}