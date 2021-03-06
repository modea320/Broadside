package com.starboardstudios.broadside.gameunits.ships;

import java.util.TimerTask;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class EasyShip extends BaseShip {

	public EasyShip(Model model) {
		super(model);
		plunder = 10;

		/** Unique variables for an EasyShip */
		health = 10;
		projectile = new CannonBall(model, -1); // default damage
		projectile.creator = this;

		/** Projectile speed */
		fireSpeed = -(float) (model.getScreenX() * .004);

		/** Art asset assigned to EasyShip */
		imageView.setImageResource(drawable.enemyship1);

		/** Scale of the EasyShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .20), (int) (model.getScreenY() * .20)));

		/**
		 * Current onClick listener for testing firing. TODO: Delete and
		 * implement periodic firing
		 */

		imageView.setVisibility(View.VISIBLE);

		x = (int) (model.getScreenX() + 75);
		y = (int) (model.getScreenY() * .4);

	}

	/**
	 * Features current basic pathing TODO: Implement advanced pathing
	 */
	public void update() {
		lifetime++;
		checkShipCollisions();
		x = x + xSpeed;
		y = y + ySpeed;

		if (random == 0)
			pathOne();

		if (random == 1)
			pathTwo();

		if (random == 2) {
			pathSix();
		}

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);

				imageView.setRotation((float) angle);

				if (lifetime > 150) {
					fire();
					lifetime = 0;
				}
			}

		});

		double num1 = Math.pow(xSpeed, 2);
		double num2 = Math.pow(ySpeed, 2);
		double toSqrt = num2 + num1;

	}

}
