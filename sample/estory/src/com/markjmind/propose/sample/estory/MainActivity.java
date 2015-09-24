package com.markjmind.propose.sample.estory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.markjmind.propose.sample.estory.book.Book;
import com.markjmind.propose.sample.estory.common.LeftMenu;
import com.markjmind.propose.sample.estory.page.Page1;
import com.markjmind.propose.sample.estory.page.Page2;
import com.markjmind.propose.sample.estory.page.Page3;
import com.markjmind.propose.sample.estory.page.Page4;
import com.markjmind.propose.sample.estory.page.Page5;
import com.markjmind.propose.sample.estory.page.Page6;
import com.markjmind.propose.sample.estory.sound.Music;
import com.markjmind.propose.sample.estory.sound.Sound;
import com.markjmind.propose.sample.estory.sound.Sound.AllLoadComplete;
import com.markjmind.sample.propose.estory.R;


public class MainActivity extends Activity {
	Music music;
	Sound sound;
	Book book;
	LeftMenu leftMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        View book_layout = findViewById(R.id.book_layout);
        
//        initPage();
//        book.setFolio(0);
//        book.loadBook();
//        book_layout.post(new Runnable() {
//			@Override
//			public void run() {
//				leftMenu.initLeftMenu();
//			}
//		});
        book = new Book(findViewById(R.id.book_layout));
        leftMenu = new LeftMenu(book, (ViewGroup)findViewById(R.id.banner_lyt));
        music = new Music();
        music.setMusic(this,R.raw.back_music);
        music.playMusic(true);
        music.setVolume(0.3f, 0.3f);
        sound = new Sound(new AllLoadComplete() {
			@Override
			public void onAllComplete() {
				initPage();
		        book.setFolio(0);
		        book.loadBook();
				leftMenu.initLeftMenu();
			}
		});
        sound.addSound(R.raw.door);
        sound.addSound(R.raw.frog);
        sound.addSound(R.raw.mouse);
        sound.addSound(R.raw.squeaky);
        sound.addSound(R.raw.car);
        sound.addSound(R.raw.car_start);
        sound.addSound(R.raw.car_beep);
        sound.addSound(R.raw.male_hello);
        sound.addSound(R.raw.whistle1);
        sound.addSound(R.raw.whistle2);
        sound.addSound(R.raw.girl_hello);
        sound.addSound(R.raw.gril_oopsi);
        sound.addSound(R.raw.dog);
        sound.addSound(R.raw.pig);
        sound.addSound(R.raw.lion);
        sound.addSound(R.raw.mouse);
        sound.addSound(R.raw.duck);
        sound.addSound(R.raw.chicken);
        
        sound.load(this);
        book.setSound(sound);
    }
    
    
    private void initPage(){
    	/** page1 */
    	book.addPage(new Page1(this,R.layout.page1));
    	
    	/** page2 */
    	book.addPage(new Page2(this,R.layout.page2));
    	
    	/** page3 */
    	book.addPage(new Page3(this,R.layout.page3));
    	
    	/** page4 */
    	book.addPage(new Page4(this,R.layout.page4));
    	
    	/** page5 */
    	book.addPage(new Page5(this,R.layout.page5));
    	
    	/** page6 */
    	book.addPage(new Page6(this,R.layout.page6));
   	 
   }
   
    @Override
    protected void onResume() {
    	sound.resume();
    	music.playMusic(true);
    	super.onResume();
    }
    @Override
    protected void onStop() {
    	super.onStop();
    	sound.pause();
    	music.stop();
    	book.disposeAll();
    }
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
    
    @Override
    protected void onDestroy() {
    	sound.dispose();
    	music.dispose();
    	book.disposeAll();
    	super.onDestroy();
    }
    
    
}
