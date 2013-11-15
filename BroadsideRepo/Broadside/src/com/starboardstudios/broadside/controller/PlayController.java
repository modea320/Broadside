package com.starboardstudios.broadside.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.LayoutInflater;

import com.starboardstudios.broadside.interfaces.Draggable;
import com.starboardstudios.broadside.util.LevelManager;
import com.starboardstudios.broadside.R;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.app.BroadsideApplication;
import com.starboardstudios.broadside.gameunits.Crew;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.aircrafts.EasyAircraft;
import com.starboardstudios.broadside.gameunits.ships.EasyShip;
import com.starboardstudios.broadside.gameunits.ships.HardShip;
import com.starboardstudios.broadside.gameunits.ships.MainShip;
import com.starboardstudios.broadside.gameunits.ships.MediumShip;
import com.starboardstudios.broadside.gameunits.submarine.EasySubmarine;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Dialog;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PlayController extends BaseController {

	@SuppressLint("NewApi")
	public Model model;

	final Context context = this;
	private ImageView pauseButton;

	private View activityScreen;
	PopupWindow popupWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View screen = ((LayoutInflater) getBaseContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.play_view, null);
		this.activityScreen = screen;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(screen);

		pauseButton = (ImageView) findViewById(R.id.pause);

		// Listener for pauseButton
		pauseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// pause dialog
				model.setPaused(true);
				final Dialog pauseDialog = new Dialog(context);
				pauseDialog.setContentView(R.layout.pause_dialog);
				pauseDialog.setTitle("Paused...");

				ImageView resumeButton = (ImageView) pauseDialog
						.findViewById(R.id.imageView2);
				resumeButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						pauseDialog.dismiss();
						model.setPaused(false);
					}
				});

				ImageView restartButton = (ImageView) pauseDialog
						.findViewById(R.id.imageView3);
				restartButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						pauseDialog.dismiss();
						LevelManager.restartLevel(model);
						model.setPaused(false);
					}
				});

				ImageView mainmenuButton = (ImageView) pauseDialog
						.findViewById(R.id.imageView4);
				mainmenuButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						PlayController.this.finish();
					}
				});

				ImageView optionsButton = (ImageView) pauseDialog
						.findViewById(R.id.imageView5);
				optionsButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						gotoOptions();
					}
				});

				pauseDialog.show();
			}

		});

		/**For test repair*/
		/*test = (ImageView) findViewById(R.id.test);
		test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show PopupWindow
				showPopup();
				// Everything after this will be handled in `doneInput(String)`
				// method
			}
		});*/

		name = "PlayController";
		model = ((BroadsideApplication) this.getApplication()).getModel();
		model.setCurrentActivity(this);
		LevelManager.startLevel(model);

		System.out.print("Model Rendering");

		/**
		 * Below is an example of how to add to the model without keylistener
		 * logic! Don't delete!
		 */
		if (model.getLevel() == 1) {
			model.addUnit(new MainShip(model));
			model.addUnit(model.getMainShip().getMainCannon());
			// TODO finish testing crew
			model.addUnit(new Crew(context, model));
			model.addUnit(new Crew(context, model));

		}
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		// TODO probably delete this old draggable code
		/*
		 * screen.setVisibility(View.VISIBLE); screen.setOnDragListener(new
		 * View.OnDragListener() {
		 * 
		 * @Override public boolean onDrag(View v, DragEvent event) {
		 * ((Draggable) event.getLocalState()).midDrag(event.getX(),
		 * event.getY()); if (event.getAction() ==
		 * DragEvent.ACTION_DRAG_STARTED) { ((Draggable)
		 * event.getLocalState()).dragStarted(); } else if (event.getAction() ==
		 * DragEvent.ACTION_DRAG_ENTERED) { } else if (event.getAction() ==
		 * DragEvent.ACTION_DROP) { ((Draggable)
		 * event.getLocalState()).endDrag(event.getX(), event.getY());
		 * System.out.println("drop registered"); } return true;
		 * 
		 * } });
		 */
		/** FIRING LISTENER */
		screen.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				model.getMainShip().getMainCannon()
						.fire(event.getX(), event.getY());
				return true;
			}
		});

	}

	/** for getting the upgrades button to work... */
	public void gotoUpgrades(View view) {
		Intent gotoUpgrades = new Intent(
				this,
				com.starboardstudios.broadside.controller.UpgradeController.class);
		startActivity(gotoUpgrades);
	}

	public void gotoUpgrades() {
		Intent gotoUpgrades = new Intent(
				this,
				com.starboardstudios.broadside.controller.UpgradeController.class);
		startActivity(gotoUpgrades);
	}

	public void gotoOptions() {
		Intent optionsIntent = new Intent(this, OptionsController.class);
		startActivity(optionsIntent);
	}

	public void testRepair(View view) {
		System.out.println("test repair");
		// designate target
		float xTarget = 300;
		float yTarget = 300;
		// call repairAt
		Crew c = model.getMainShip().getLastCrew();
		c.repairAt(xTarget, yTarget);
		System.out.println("stopped");
	}

	public void init() {

	}

	/** For adding turret keylistener. */
	/** Test method for spawning enemies */
	static int spawnnum = 0;

	public void spawnEnemies(View view) {

		switch (spawnnum) {
		case 0:
			EasyShip es = new EasyShip(model);
			model.addUnit(es);
			break;
		case 1:
			MediumShip ms = new MediumShip(model);
			model.addUnit(ms);
			break;
		case 2:
			HardShip hs = new HardShip(model);
			model.addUnit(hs);
			break;
		case 3:
			EasySubmarine esub = new EasySubmarine(model);
			model.addUnit(esub);
			break;
		case 4:
			EasyAircraft ea = new EasyAircraft(model);
			model.addUnit(ea);
			break;

		}
		spawnnum++;
		if (spawnnum > 4)
			spawnnum = 0;

	}

	public void showPopup(View view) {

		// Container layout to hold other components
		LinearLayout llContainer = new LinearLayout(this);

		// Set its orientation to vertical to stack item
		llContainer.setOrientation(LinearLayout.VERTICAL);

		// Container layout to hold EditText and Button
		LinearLayout llContainerInline = new LinearLayout(this);

		// Set its orientation to horizontal to place components next to each
		// other
		llContainerInline.setOrientation(LinearLayout.HORIZONTAL);

		// EditText to get input
		final EditText etInput = new EditText(this);

		// TextView to show an error message when the user does not provide
		// input
		final TextView tvError = new TextView(this);

		// For when the user is done
		Button bDone = new Button(this);

		// If tvError is showing, make it disappear
		etInput.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tvError.setVisibility(View.GONE);
			}
		});

		// This is what will show in etInput when the Popup is first created
		etInput.setHint("Enter: xTarget yTarget (Example: \"300 300\")");

		// Input type allowed: Numbers
		etInput.setRawInputType(Configuration.KEYBOARD_12KEY);

		// Center text inside EditText
		etInput.setGravity(Gravity.CENTER);

		// tvError should be invisible at first
		tvError.setVisibility(View.GONE);

		bDone.setText("Done");

		bDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// If user didn't input anything, show tvError
				if (etInput.getText().toString().equals("")) {
					tvError.setText("Please enter a valid value");
					tvError.setVisibility(View.VISIBLE);
					etInput.setText("");

					// else, call method `doneInput()` which we will define
					// later
				} else {
					doneInput(etInput.getText().toString());
					popupWindow.dismiss();
				}
			}
		});

		// Define LayoutParams for tvError
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParams.topMargin = 20;

		// Define LayoutParams for InlineContainer
		LinearLayout.LayoutParams layoutParamsForInlineContainer = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		layoutParamsForInlineContainer.topMargin = 30;

		// Define LayoutParams for EditText
		LinearLayout.LayoutParams layoutParamsForInlineET = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// Set ET's weight to 1 // Take as much space horizontally as possible
		layoutParamsForInlineET.weight = 1;

		// Define LayoutParams for Button
		LinearLayout.LayoutParams layoutParamsForInlineButton = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// Set Button's weight to 0
		layoutParamsForInlineButton.weight = 0;

		// Add etInput to inline container
		llContainerInline.addView(etInput, layoutParamsForInlineET);

		// Add button with layoutParams // Order is important
		llContainerInline.addView(bDone, layoutParamsForInlineButton);

		// Add tvError with layoutParams
		llContainer.addView(tvError, layoutParams);

		// Finally add the inline container to llContainer
		llContainer.addView(llContainerInline, layoutParamsForInlineContainer);

		// Set gravity
		llContainer.setGravity(Gravity.CENTER);

		// Set any color to Container's background
		llContainer.setBackgroundColor(0x95000000);

		// Create PopupWindow
		popupWindow = new PopupWindow(llContainer,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		// Should be focusable
		popupWindow.setFocusable(true);

		// Show the popup window
		popupWindow.showAtLocation(llContainer, Gravity.CENTER, 0, 0);

	}

	public void doneInput(String input) {
		String[] targets = input.split(" ");
		int xTarget = Integer.parseInt(targets[0]);
		int yTarget = Integer.parseInt(targets[1]);

		// Work with it // For example, show a Toast
		Toast.makeText(this, "Most recent crew member heading to (" + xTarget + ", " + yTarget + ") and back.",
				Toast.LENGTH_LONG).show();
		System.out.println("test repair");
		// call repairAt
		Crew c = model.getMainShip().getLastCrew();
		c.repairAt(xTarget, yTarget);
		System.out.println("stopped");
	}

}
