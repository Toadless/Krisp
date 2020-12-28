/*
 * Copyright (c) 2020 Toadless
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package me.toadless.Krisp;

import me.toadless.Krisp.Commands.Help;
import me.toadless.Krisp.Commands.Info;
import me.toadless.Krisp.Commands.Music.*;
import me.toadless.Krisp.Commands.Prefix;
import me.toadless.Krisp.Database.Database;
import me.toadless.Krisp.Events.Mention;
import me.toadless.Krisp.Events.Ready;
import me.toadless.Krisp.Events.guildJoinEvent;
import me.toadless.Krisp.Events.guildLeaveEvent;
import me.toadless.Krisp.Utils.Ascii;
import me.toadless.Krisp.Utils.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws LoginException, IOException, SQLException {
        Ascii.printVainity();
        Config.loadSettings(); //Loading the config settings from our json file
        JDABuilder builder = JDABuilder.createDefault(
                Config.token,
                GatewayIntent.GUILD_VOICE_STATES, // Enabeling us to use voice
                GatewayIntent.GUILD_MESSAGES // Enableing us to listen to messages
        );
        builder.disableCache(
                CacheFlag.MEMBER_OVERRIDES,
                CacheFlag.EMOTE
        );
        builder.enableCache(
                CacheFlag.VOICE_STATE
        );
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setCompression(Compression.NONE);
        builder.setActivity(Activity.listening("Krisp audio."));
        //Adding all our events
        builder.addEventListeners( // All event listeners
                new Ready(), // The ready event
                new guildJoinEvent(), // The guild join event
                new guildLeaveEvent(), // The guild leave event
                new Mention() // Detects when the bot gets mentioned
        );
        builder.addEventListeners( // All music command event listeners
                new Join(), // The join command
                new Play(), // The play command
                new Stop(), // The stop command
                new Skip(), // The skip command
                new Leave(), // The leave command
                new Queue(), // The queue command
                new NowPlaying() // The now playing command
        );
        builder.addEventListeners( // All misc commands
                new Info(), // The info command
                new Help(), // The help command
                new Prefix() // Sets the guilds prefix
        );
        builder.build(); // Building the bot
        Database.main();
    }
}
