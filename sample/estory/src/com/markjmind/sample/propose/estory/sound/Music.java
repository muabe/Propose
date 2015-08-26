package com.markjmind.sample.propose.estory.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class Music {
	private MediaPlayer media;

	public void setMusic(Context context, int raw_id){
		dispose();
		media = MediaPlayer.create(context, raw_id);
		media.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	public void playMusic(boolean isLoop){
		if(media==null){
			return;
		}
		media.setLooping(isLoop);
		media.start();
	}
	
	public void stop(){
		if(media!=null){
			media.pause();
		}
	}
	
	public void dispose(){
		if(media!=null){
			media.stop();
			media.release();
			media = null;
		}
	}
	
	public void setVolume(float leftVolume, float rightVolume){
		media.setVolume(leftVolume, rightVolume);
	}
}
