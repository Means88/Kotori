package cn.oureda.means88.anime;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;

public class AnimeUtils {
	
	private static Resources mResources = null;
	
	public static void addFrams(View view, 
			AnimationDrawable anime, AnimeFrame[] frams) {
		mResources = view.getResources();
		addFrams(anime, frams);
	}
	
	public static void addFrams(Context context, 
			AnimationDrawable anime, AnimeFrame[] frams) {
		mResources = context.getResources();
		addFrams(anime, frams);
	}
	
	private static void addFrams(
			AnimationDrawable anime, AnimeFrame[] frams) {
		for(int i=0; i<frams.length; i++) {
			anime.addFrame(mResources.getDrawable(frams[i].getFrame()), frams[i].getDuration());
		}
	}
	
	public static void addReverseFrams(View view, 
			AnimationDrawable anime, AnimeFrame[] frams) {
		mResources = view.getResources();
		addReverseFrams(anime, frams);
	}
	
	public static void addReverseFrams(Context context, 
			AnimationDrawable anime, AnimeFrame[] frams) {
		mResources = context.getResources();
		addReverseFrams(anime, frams);
	}
	
	private static void addReverseFrams(
			AnimationDrawable anime, AnimeFrame[] frams) {
		for(int i=frams.length-1; i>=0; i--) {
			anime.addFrame(mResources.getDrawable(frams[i].getFrame()), frams[i].getDuration());
		}
	}
}
