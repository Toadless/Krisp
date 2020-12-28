package me.toadless.Krisp.Commands.Music;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.toadless.Krisp.Database.Database;
import me.toadless.Krisp.LavaPlayer.GuildMusicManager;
import me.toadless.Krisp.LavaPlayer.PlayerManager;
import me.toadless.Krisp.Utils.Config;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Queue extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        DBObject query = new BasicDBObject("id", event.getGuild().getId());
        DBCursor cursor = Database.guild.find(query);
        if (args[0].equalsIgnoreCase(cursor.one().get("prefix") + "queue")) {
            final TextChannel channel = (TextChannel) event.getChannel();
            final Member self = event.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();

            final Member member = event.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("You need to be in a voice channel for this command to work.").queue();
                return;
            }

            if (!selfVoiceState.inVoiceChannel()) {
                channel.sendMessage("I need to be in a voice channel for this to work.").queue();
                return;
            }
            if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
                channel.sendMessage("You need to be in the same voice channel as me for this to work!").queue();
                return;
            }

            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            final AudioPlayer audioPlayer = musicManager.audioPlayer;
            if (audioPlayer.getPlayingTrack() == null) {
                channel.sendMessage("No current playing song.").queue();
                return;
            }
            final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            if (queue.isEmpty()) {
                channel.sendMessage("The queue is empty.");
                return;
            }
            final int trackCount = Math.min(queue.size(), 20);
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            final MessageAction messageAction = channel.sendMessage("**Current Queue:**\n");
            for(int i = 0; i < trackCount; i++) {
                final AudioTrack track = trackList.get(i);
                final AudioTrackInfo info = track.getInfo();
                messageAction.append('#')
                        .append(String.valueOf(i + 1))
                        .append(" `")
                        .append(" by ")
                        .append(info.author)
                        .append("` [`")
                        .append(formatTime(track.getDuration()))
                        .append("`]\n")
                ;
            }
            if (trackList.size() > trackCount) {
                messageAction.append("And `")
                        .append(String.valueOf(trackList.size() - trackCount))
                        .append("` more...")
                ;
            }

            messageAction.queue();
        }
    }
    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}