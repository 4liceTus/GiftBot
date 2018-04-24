package Discord.Main;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.Vector;

public class BotListenger {
    private static final String prefix = "#";
    private static BotUtils botUtils = new BotUtils("gifts.txt");

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        if(event.getMessage().getContent().toLowerCase().startsWith(prefix)) {
            botUtils.proccessCommand(event.getMessage(), prefix);
        }
    }
}
