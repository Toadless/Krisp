package me.toadless.Krisp.Commands;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import me.toadless.Krisp.Database.Database;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Prefix extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        DBObject query = new BasicDBObject("id", event.getGuild().getId());
        DBCursor cursor = Database.guild.find(query);
        if (cursor.one() == null) {
            return;
        }
        if (args[0].equalsIgnoreCase(cursor.one().get("prefix") + "prefix")) {
            if (cursor.one() == null) {
                return;
            }
            if (!event.getMember().hasPermission(Permission.getFromOffset(0x00000020))) {
                event.getChannel().sendMessage("You dont have permission to do this.").queue();
                return;
            }
            if (args.length < 2) {
                event.getChannel().sendMessage("Your current prefix is:" + " " + cursor.one().get("prefix")).queue();
                return;
            }
            try {
                DBObject update = new BasicDBObject("prefix", args[1]);
                update.put("id", event.getGuild().getId());
                //Database.guild.findAndModify(query, blank, blank, false, new BasicDBObject("$set", "prefix", args[1]), true ,true); // Updating the guilds database document
                Database.guild.update(query, update);
                event.getChannel().sendMessage("Successfully set this guilds prefix to" + " " + args[1]).queue();
            } catch (Error e) {
                event.getChannel().sendMessage("An error occured.").queue();
                return;
            }
        }
    }
}