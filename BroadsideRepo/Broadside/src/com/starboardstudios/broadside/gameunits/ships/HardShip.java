package com.starboardstudios.broadside.gameunits.ships;

import android.view.View;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

public class HardShip extends BaseShip {

	public HardShip(Model model) {
		super(model);
		plunder = 30;

		/** Unique variables for a HardShip */
		health = 100;
		projectile = new CannonBall(model, 40);
		projectile.creator = this;

		/** Projectile speed */
		fireSpeed = -(float) (model.getScreenX() * .004);

		/** Art asset assigned to HardShip */
		imageView.setImageResource(drawable.hardship);

		/** Scale of the HardShip type */
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * .15), (int) (model.getScreenY() * .26)));

		imageView.setVisibility(View.VISIBLE);


		x = (int) (model.getScreenX() + 75);
		y = (int) (model.getScreenY() * .4);
	}

	/**
	 * Features current basic pathing TODO: Implement advanced pathing
	 */
	public void update() {
		checkShipCollisions();
		x = x + xSpeed;
		y = y + ySpeed;
		lifetime++;
		if (random == 1)
			pathOne();

		if (random == 0)
			pathTwo();

		if (random == 2)
			pathThree();

		model.runOnMain(new Runnable() {
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				
				imageView.setRotation((float)angle);

				if (lifetime > 250) {
					fire();
					lifetime = 0;
				}

			}

		});

	}

}
