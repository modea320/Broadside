package com.starboardstudios.broadside.gameunits.ships;

import android.content.Context;

import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;
import com.starboardstudios.broadside.gameunits.turrets.Turret;

import java.util.ArrayList;

public abstract class Ship extends BaseUnit {

	
	protected int x;
	protected int y;
	protected int xSpeed;
	protected int ySpeed;
	protected Context context;
	
	public Ship(Context c)
	{
		this.context=c;
	}
	
	
	
	
	void destroy(){
		//Destroy means different things depending on if it's a main ship
		//Or enemy. Will be implemented in extended classes
	}
	
	void GoTo(int x, int y){
		this.x = x;
		this.y = y;
		xSpeed = 0;
		ySpeed = 0;
	}
	
	void Damage(Projectile p){
		
	}
	
	void Update(){
		
	}
	
	
}
