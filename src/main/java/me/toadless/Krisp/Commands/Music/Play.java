package me.toadless.Krisp.Commands.Music;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import me.toadless.Krisp.LavaPlayer.PlayerManager;
import me.toadless.Krisp.Utils.Config;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        DBObject query = new BasicDBObject("id", event.getGuild().getId());
        DBCursor cursor = Database.guild.find(query);
        String songName = event.getMessage().getContentRaw().replaceFirst("^" + cursor.one().get("prefix") + "play" + " ", "");
        if (args[0].equalsIgnoreCase(cursor.one().get("prefix") + "play")) {
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
                final AudioManager audioManager = event.getGuild().getAudioManager();
                final VoiceChannel memberChannel = memberVoiceState.getChannel();
                audioManager.openAudioConnection(memberChannel);
            }
            if (args[1] == null) {
                channel.sendMessage("Please provide a url or search query.").queue();
                return;
            }
            String song = args[1];
            if (!isUrl(song)) {
                song = "ytsearch:" + songName;
            }
            PlayerManager.getInstance()
                    .loadAndPlay(channel, song);
            return;
        }
    }
    private boolean isUrl(String url) {
        if (url.startsWith("https://www.youtube.com/watch?v=")) {
            return true;
        } else {
            return false;
        }
    }
}
