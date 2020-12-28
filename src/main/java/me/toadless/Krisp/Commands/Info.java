package me.toadless.Krisp.Commands;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        DBObject query = new BasicDBObject("id", event.getGuild().getId());
        DBCursor cursor = Database.guild.find(query);
        if (cursor.one() == null) {
            return;
        }
        if (args[0].equalsIgnoreCase(cursor.one().get("prefix") + "info")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.addField("Version", "1.0.0", true);
            embed.addField("Libary", "JDA", true);
            embed.addField("Creator", "Toadless:0001", true);
            embed.addField("Language", "Java", true);
            embed.setAuthor("Krisp", "https://leaf-bot.xyz", event.getJDA().getSelfUser().getAvatarUrl());
            embed.setColor(0x0a5469);
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
