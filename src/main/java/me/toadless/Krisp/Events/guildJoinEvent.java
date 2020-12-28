package me.toadless.Krisp.Events;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import me.toadless.Krisp.Utils.Config;
import me.toadless.Krisp.Utils.CurrentDate;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class guildJoinEvent extends ListenerAdapter {
    public void onGuildJoin(GuildJoinEvent event) {
        try {
            DBObject guild = new BasicDBObject("id", event.getGuild().getId())
                    .append("prefix", Config.prefix);
            Database.guild.insert(guild);
        } catch (Error e) {
            System.out.println(CurrentDate.date + " " + "Error creating a guilds database! Error: " + e);
        }
    }
}