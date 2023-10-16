//package com.github.ArthurSchiavom.old.commands.user.regular.music;
//
//import com.github.ArthurSchiavom.old.commands.base.Category;
//import com.github.ArthurSchiavom.old.commands.base.CommandWithSubCommands;
//
//public class Music extends CommandWithSubCommands {
//	public Music() {
//		super(Category.MUSIC
//				, "\uD83C\uDFB6 Play some com.github.ArthurSchiavom.old.music!"
//		);
//		this.addName("Music");
//		this.addName("M");
//		this.addSubCommand(new MusicJoin(this));
//		this.addSubCommand(new MusicPlay(this));
//		this.addSubCommand(new MusicPlayNext(this));
//		this.addSubCommand(new MusicVolume(this));
//		this.addSubCommand(new MusicPause(this));
//		this.addSubCommand(new MusicResume(this));
//		this.addSubCommand(new MusicNowPlaying(this));
//		this.addSubCommand(new MusicQueue(this));
//		this.addSubCommand(new MusicSkip(this));
//		this.addSubCommand(new MusicClearQueue(this));
//		this.addSubCommand(new MusicReset(this));
//		this.addSubCommand(new MusicLeave(this));
//		this.buildHelpMessage();
//	}
//}
