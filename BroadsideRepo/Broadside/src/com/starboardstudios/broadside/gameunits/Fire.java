package com.starboardstudios.broadside.gameunits;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.ships.Section;
import com.starboardstudios.broadside.interfaces.GifPlayer;

public class Fire extends BaseUnit{
	// properties
	public GifPlayer fireImage;
	private Context context;
	private Model model;
	private Section sect;

	// constructors
	public Fire(Model model) {
		this.context = model.context;
		this.model = model;
		// create image
		fireImage = new GifPlayer(context,drawable.fire_cartoon, true);
		float size = (float) .1;
		fireImage.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size)));
		fireImage.setVisibility(View.VISIBLE);
		// initialize other stuff
		x = y = 0;
	}




	// methods
	public void update() {
		// System.out.println("Fire updating...");

		model.runOnMain(new Runnable() {
			public void run() {
				fireImage.setX(x);
				fireImage.setY(y);
			}
		});
	}


	public ImageView getImage() {
		return null;
	}

	//required...
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	//USED
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		this.sect = model.getMainShip().determineSection(y);
		sect.addFire(this);
		//TODO possibly remove
		Crew crew = model.getMainShip().getNextAvailableCrew();
		if (crew!=null) {
			crew.repairAt(x, y);
		}
	}

	public void setImageView(ImageView image) {

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Section getSect() {
		return sect;
	}

	public void setSect(Section sect) {
		this.sect = sect;
	}

	@Override
	public void collide(BaseUnit collidedWith) {
		// TODO Auto-generated method stub
		
	}
}