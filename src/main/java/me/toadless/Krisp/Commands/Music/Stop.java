package me.toadless.Krisp.Commands.Music;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import me.toadless.Krisp.LavaPlayer.GuildMusicManager;
import me.toadless.Krisp.LavaPlayer.PlayerManager;
import me.toadless.Krisp.Utils.Config;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Stop extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        DBObject query = new BasicDBObject("id", event.getGuild().getId());
        DBCursor cursor = Database.guild.find(query);
        if (args[0].equalsIgnoreCase(cursor.one().get("prefix") + "stop")) {
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
            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();
            channel.sendMessage("The player has stopped.").queue();
        }
    }
}