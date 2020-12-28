package me.toadless.Krisp.Events;

import me.toadless.Krisp.Utils.CurrentDate;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {
    // The ready event
    public void onReady(ReadyEvent event) {
        System.out.println(CurrentDate.date + " " + "Logged in and ready!");
    }
}
