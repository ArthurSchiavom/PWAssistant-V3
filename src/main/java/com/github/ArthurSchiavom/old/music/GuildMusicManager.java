//package com.github.ArthurSchiavom.old.music;
//
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
//import net.dv8tion.jda.api.entities.Guild;
//
//public class GuildMusicManager {
//	/**
//	 * Audio player for the guild.
//	 */
//	public final AudioPlayer player;
//	/**
//	 * Track scheduler for the player.
//	 */
//	public final TrackScheduler scheduler;
//	public final Guild guild;
//
//	/**
//	 * Creates a player and a track scheduler.
//	 * @param manager Audio player manager to use for creating the player.
//	 * @param guild The guild to play in.
//	 */
//	public GuildMusicManager(AudioPlayerManager manager, Guild guild) {
//		player = manager.createPlayer();
//		scheduler = new TrackScheduler(player, guild.getIdLong());
//		player.addListener(scheduler);
//		this.guild = guild;
//	}
//
//	/**
//	 * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
//	 */
//	public AudioPlayerSendHandler getSendHandler() {
//		return new AudioPlayerSendHandler(player);
//	}
//
//	/**
//	 * Clears the queue and stops playing.
//	 */
//	public void reset() {
//		scheduler.clearQueue();
//		player.stopTrack();
//	}
//}
