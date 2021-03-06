package com.starboardstudios.broadside.gameunits.turrets;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;

//Turret 1
public class Cannon extends Turret {
	Cannon me;

	public Cannon(Model model) {
		super(model);
		turretNum = 1;
		me = this;
		/* ARBITRARY VALUES */
		this.fireSpeed = 3;
		this.cooldown = 180;
		spendSetCost(50);	
		this.projectile = new CannonBall(model, 20);
		projectile.setTurret(this);
		size = (float) .125;
		
		/* Image */
		imageView.setImageResource(drawable.main_cannon); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
			
		/* Dragging */
		imageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				imageView.getParent().requestDisallowInterceptTouchEvent(true);
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println(view.toString());
					ClipData data = ClipData.newPlainText("", "");
					View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
							imageView);
					view.startDrag(data, shadowBuilder, me, 0);
					view.setVisibility(View.INVISIBLE);
					return true;
				}
				return false;
			}
		});
		System.out.println("Cannon is Created");
	}
	
	public Cannon(Model model, int x, int y) {
		super(model);
		turretNum = 1;
		me = this;
		/* ARBITRARY VALUES */
		this.fireSpeed = 3;
		this.cooldown = 180;
		setCost(50);
		this.projectile = new CannonBall(model, 20);
		size = (float) .125;
		
		this.x = x;
		this.y = y;
		
		/* Image */
		imageView.setImageResource(drawable.main_cannon); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
	}

	/*Fired from playcontroller in same spot as maincannon via fireBroadside method in mainship*/
	public void update() {
		// System.out.println("Updating Turret1");
		model.runOnMain(new Runnable() {
			@Override
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				if (currentCooldown > 0) {
					currentCooldown--;
				} else {
					imageView.setColorFilter(null);
				}
			}
		});	
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	@Override
	public void collide(BaseUnit collidedWith) {
		// TODO: Turrent available drop.
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}


	@Override
	public void setImageView(ImageView image) {
		imageView = image;
	}

	public void setPosition(float x, float y) {
		this.x = (int) x;
		this.y = (int) y;
	}
}
