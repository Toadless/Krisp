package me.toadless.Krisp.Events;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import me.toadless.Krisp.Utils.CurrentDate;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class guildLeaveEvent extends ListenerAdapter {
    public void onGuildLeave(GuildLeaveEvent event) {
        try {
            DBObject query = new BasicDBObject("id", event.getGuild().getId());
            DBCursor cursor = Database.guild.find(query);
            if (cursor.one() == null) {
                return;
            }
            Database.guild.findAndRemove(query); // Once the bot is removed from the guild we will drop the guilds document
        } catch (Error e) {
            System.out.println(CurrentDate.date + " " + "An error occured whilst removing a database! Error: " + e);
        }
    }
}
