package Discord.Main;

import sx.blah.discord.api.*;
import sx.blah.discord.api.events.*;

public class BotMain {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Необходимо ввести токен в качестве первого аргумента.");
            return;
        }

        IDiscordClient clientBot = BotUtils.createClient(args[0], true);
        EventDispatcher dispatcher = clientBot.getDispatcher();
        dispatcher.registerListener(new BotListenger());
    }
}
