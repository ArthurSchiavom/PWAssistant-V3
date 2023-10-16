//package com.github.ArthurSchiavom.old.music;
//
//import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
//import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
//import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
//import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
//import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
//import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
//import net.dv8tion.jda.api.entities.Guild;
//import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
//import net.dv8tion.jda.api.entities.VoiceChannel;
//import net.dv8tion.jda.api.managers.AudioManager;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class PlayerManager {
//	private static PlayerManager INSTANCE;
//	private final AudioPlayerManager playerManager;
//	private final Map<Long, GuildMusicManager> musicManagers;
//
//	private PlayerManager() {
//		this.musicManagers = Collections.synchronizedMap(new HashMap<>());
//
//		this.playerManager = new DefaultAudioPlayerManager();
//		AudioSourceManagers.registerRemoteSources(playerManager);
//		AudioSourceManagers.registerLocalSource(playerManager);
//	}
//
//	/**
//	 *
//	 *
//	 * @param guildId
//	 * @return (1) The registered com.github.ArthurSchiavom.old.music manager for the guild or (2) null if no com.github.ArthurSchiavom.old.music manager is registered for the given guild ID.
//	 */
//	public GuildMusicManager getGuildMusicManager(long guildId) {
//		return musicManagers.get(guildId);
//	}
//
//	/**
//	 * Gets a registered guild com.github.ArthurSchiavom.old.music manager or registers a new one if none is registered.
//	 *
//	 * @param guild The target guild.
//	 * @return The com.github.ArthurSchiavom.old.music manager for a guild.
//	 */
//	public GuildMusicManager retrieveGuildMusicManager(Guild guild) {
//		long guildId = guild.getIdLong();
//		GuildMusicManager guildMusicManager = musicManagers.get(guildId);
//
//		if (guildMusicManager == null) {
//			guildMusicManager = startGuildMusicManager(guild);
//		}
//
//		guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());
//
//		return guildMusicManager;
//	}
//
//	/**
//	 * Joins the com.github.ArthurSchiavom.old.music channel and registers a new guild com.github.ArthurSchiavom.old.music manager.
//	 *
//	 * @param guild The guild.
//	 * @return The registered guild com.github.ArthurSchiavom.old.music manager.
//	 */
//	public GuildMusicManager startGuildMusicManager(Guild guild) {
//		GuildMusicManager guildMusicManager = new GuildMusicManager(playerManager, guild);
//		musicManagers.put(guild.getIdLong(), guildMusicManager);
//		return guildMusicManager;
//	}
//
//	/**
//	 * Joins the designated voice channel <b>if not already in one</b>,
//	 *
//	 * @param vc The voice channel to play in.
//	 * @param tc The text channel to play in.
//	 * @param trackUrl The URL of the video to play.
//	 */
//	public void joinChannelAndPlay(VoiceChannel vc, TextChannel tc, String trackUrl) {
//		AudioManager audioManager = vc.getGuild().getAudioManager();
//		if (!audioManager.isConnected()) {
//			audioManager.openAudioConnection(vc);
//		}
//	}
//
//	/**
//	 * Loads and plays a Music. A message is sent informing the state of the operation.
//	 *
//	 * @param channel The channel where it was requested.
//	 * @param trackUrl The Music URL.
//	 * @param queueOnFront If the track should be put in the front of the queue instead of on the end.
//	 */
//	public void loadAndPlay(TextChannel channel, String trackUrl, boolean queueOnFront) {
//		GuildMusicManager musicManager = retrieveGuildMusicManager(channel.getGuild());
//
//		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
//			@Override
//			public void trackLoaded(AudioTrack track) {
//				boolean success;
//				if (queueOnFront)
//					success = musicManager.scheduler.queueFront(track);
//				else
//					success = musicManager.scheduler.queue(track);
//
//				if (success)
//					channel.sendMessage("\uD83C\uDFB6 **`" + track.getInfo().title + "` queued!**").queue();
//				else
//					channel.sendMessage("\uD83D\uDED1 **The queue is full.**").queue();
//			}
//
//			@Override
//			public void playlistLoaded(AudioPlaylist playlist) {
//				List<AudioTrack> tracks = playlist.getTracks();
//
//				int nQueued = 0;
//				if (queueOnFront) {
//					for (AudioTrack track : tracks) {
//						if (musicManager.scheduler.queueFront(track))
//							nQueued++;
//						else
//							break;
//					}
//				}
//				else {
//					for (AudioTrack track : tracks) {
//						if (musicManager.scheduler.queue(track))
//							nQueued++;
//						else
//							break;
//					}
//				}
//
//				channel.sendMessage("✅ **Enqueued " + nQueued + " songs from `" + playlist.getName() + "`!**").queue();
//			}
//
//			@Override
//			public void noMatches() {
//				channel.sendMessage("**`" + trackUrl + "` was not found.**").queue();
//			}
//
//			@Override
//			public void loadFailed(FriendlyException exception) {
//				channel.sendMessage("The link you requested is not currently supported :(").queue();
//			}
//		});
//	}
//
//	/**
//	 * @return This singleton's instance.
//	 */
//	public static synchronized PlayerManager getInstance() {
//		if (INSTANCE == null) {
//			INSTANCE = new PlayerManager();
//		}
//
//		return INSTANCE;
//	}
//
//	public void unregisterMusicManager(long idLong) {
//		synchronized (musicManagers) {
//			GuildMusicManager musicManager = musicManagers.get(idLong);
//			if (musicManager != null) {
//				musicManagers.remove(idLong);
//				musicManager.reset();
//				musicManager.player.destroy();
//			}
//		}
//	}
//}
