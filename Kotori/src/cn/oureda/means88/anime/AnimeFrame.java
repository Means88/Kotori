package cn.oureda.means88.anime;

public class AnimeFrame {
	private int frame;
	private int duration;
	
	public AnimeFrame(int frame, int duration) {
		this.frame = frame;
		this.duration = duration;
	}
	
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
