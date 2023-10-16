//package com.github.ArthurSchiavom.old.music;
//
//import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
//import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
//import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
//import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
//import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
//import com.github.ArthurSchiavom.old.utils.Utils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.BlockingDeque;
//import java.util.concurrent.LinkedBlockingDeque;
//
//public class TrackScheduler extends AudioEventAdapter {
//	public final int MAX_CAPACITY = 200;
//	private final AudioPlayer player;
//	private final BlockingDeque<AudioTrack> deque;
//	private final long guildId;
//
//	/**
//	 * @param player The audio player this scheduler uses
//	 */
//	public TrackScheduler(AudioPlayer player, long guildId) {
//		this.player = player;
//		this.guildId = guildId;
//		this.deque = new LinkedBlockingDeque<>(MAX_CAPACITY);
//	}
//
//	/**
//	 * Add the next track to the queue or play right away if none is playing and the queue is empty.
//	 *
//	 * @param track The track to queue.
//	 * @return (1) True if the track was added or (2) false if the queue is full.
//	 */
//	public boolean queue(AudioTrack track) {
//		// Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
//		// something is playing, it returns false and does nothing. In that case the player was already playing so this
//		// track goes to the deque instead.
//
//		if (player.startTrack(track, true))
//			return true;
//		else
//			return deque.offer(track);
//	}
//
//	/**
//	 * Add the next track to the front of the queue or play right away if none is playing and the queue is empty.
//	 *
//	 * @param track The track to queue.
//	 * @return (1) True if the track was added or (2) false if the queue is full.
//	 */
//	public boolean queueFront(AudioTrack track) {
//		// Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
//		// something is playing, it returns false and does nothing. In that case the player was already playing so this
//		// track goes to the deque instead.
//
//		if (player.startTrack(track, true))
//			return true;
//		else
//			return deque.offerFirst(track);
//	}
//
//	/**
//	 * @return The Music deque.
//	 */
//	public List<AudioTrack> getQueue() {
//		return new ArrayList<>(deque);
//	}
//
//	/**
//	 * Start the next track.
//	 *
//	 * @param skipCurrent If the current track should be skipped if any. If no tracks are left, stops playing.
//	 *
//	 * @return If a new track is now playing. (stopping counts as a new track)
//	 */
//	public boolean nextTrack(boolean skipCurrent) {
//		// Start the next track, regardless of if something is already playing or not. In case deque was empty, we are
//		// giving null to startTrack, which is a valid argument and will simply stop the player.
//		AudioTrack track = deque.poll();
//		if (track != null)
//			return player.startTrack(track, !skipCurrent);
//		else {
//			if (skipCurrent && player.getPlayingTrack() != null) {
//				player.destroy();
//				return true;
//			}
//			return false;
//		}
//	}
//
//	@Override
//	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
//		// Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
//		if (endReason.mayStartNext) {
//			nextTrack(true);
//		}
//		else {
//			PlayerManager.getInstance().unregisterMusicManager(guildId);
//		}
//	}
//
//	public void clearQueue() {
//		deque.clear();
//	}
//
//	public List<String> getQueueTracksDisplayable() {
//		List<String> tracksDisplay = new ArrayList<>();
//		for (AudioTrack track : getQueue()) {
//			AudioTrackInfo trackInfo = track.getInfo();
//			tracksDisplay.add(trackInfo.title + " (" + track.getDuration()/60000 + "min)");
//		}
//		return tracksDisplay;
//	}
//
//	public List<String> retrieveQueueTracksDisplayableWithLinkMarkdown() {
//		List<String> tracksDisplay = new ArrayList<>();
//		for (AudioTrack track : getQueue()) {
//			AudioTrackInfo trackInfo = track.getInfo();
//			tracksDisplay.add("[" + trackInfo.title + "](" + trackInfo.uri + ") (" + Utils.millisecondsToMinuteSecondDisplay(track.getDuration()) + ")");
//		}
//		return tracksDisplay;
//	}
//}
