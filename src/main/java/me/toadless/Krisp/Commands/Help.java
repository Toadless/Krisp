package me.toadless.Krisp.Commands;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        DBObject query = new BasicDBObject("id", event.getGuild().getId());
        DBCursor cursor = Database.guild.find(query);
        if (cursor.one() == null) {
            return;
        }
        if (args[0].equalsIgnoreCase(cursor.one().get("prefix") + "help")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.addField("!join", "Joins your vc.", true);
            embed.addField("!play <song>", "Plays a song to your vc.", true);
            embed.addField("!leave", "Leaves your vc.", true);
            embed.addField("!stop", "Stops playing the current song.", true);
            embed.addField("!skip", "Skips to the next song in the queue.", true);
            embed.addField("!queue", "Displays the current queue.", true);
            embed.addField("!nowplaying", "Displays the current playing song", true);
            embed.addField("!info", "Shows useless info about me", true);
            embed.addField("!help", "Displays this help page", true);
            embed.setColor(0x0a5469);
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
