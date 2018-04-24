package Discord.Main;

import sx.blah.discord.api.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

import java.io.*;
import java.util.*;

public class BotUtils {
    private Vector<String> gifts;

    public BotUtils(String filePath) {
        File file = new File(filePath);
        BufferedReader reader = null;
        String word = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((word = reader.readLine()) != null) {
                gifts.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static IDiscordClient createClient(String token, boolean login) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        try {
            if (login) {
                return clientBuilder.login();
            } else {
                return clientBuilder.build();
            }
        } catch (DiscordException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void proccessCommand(IMessage message, String prefix) {
        IChannel channel = message.getChannel();
        IUser user = message.getAuthor();
        String[] command = message.getContent().toLowerCase().replace(prefix, "").split(" ");
        String gift;
        message.delete();

        switch (command[0]) {
            case "get":
                gift = getRandomGift();
                sendMessageGift(channel, user, "", gift, true);
                break;
            case "send":
                if(command.length > 1) {
                    gift = getRandomGift();
                    sendMessageGift(channel, user, command[1], gift, false);
                }
                else {
                    channel.sendMessage("Укажите получателя");
                }
                break;
            case "help":
                channel.sendMessage("Список доступных команд: https://github.com/4liceTus/GiftBot/wiki/Описание-команд-для-GiftBot");
                break;
            default:
                channel.sendMessage("Неверная команда");
                channel.sendMessage("Список доступных команд: https://github.com/4liceTus/GiftBot/wiki/Описание-команд-для-GiftBot");
        }
    }

    private static void sendMessageGift(IChannel channel, IUser sender, String getter, String gift, boolean forSender) {
        Vector<String> messages = new Vector<String>();
        if(forSender) {
            messages.add(sender.mention() + " хочет получить подарок");
            messages.add("Ковыряюсь в своем барахле...");
            messages.add("И " + sender.mention() + " получает...");
            messages.add(gift);
        }
        else {
            IGuild guild = channel.getGuild();
            IUser getterUser = guild.getUserByID(Long.parseLong(getter));
            if(getterUser.equals(null)) {
                channel.sendMessage("Неправильное имя получателя");
                return;
            }
            messages.add(sender.mention() + " хочет отправить подарок " + getterUser.mention());
            messages.add("Ковыряется в своем барахле...");
            messages.add("И дарит " + getterUser.mention() + "...");
            messages.add(gift);
        }

        int messageSize = messages.size();
        long timeWait = 1000;
        for(int index = 0; index < messageSize; index++) {
            channel.sendMessage(messages.get(index));
            try {
                channel.wait(timeWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRandomGift() {
        int countLines = gifts.size();
        if(countLines > 0) {
            Random random = new Random();
            int indexRandom = random.nextInt(countLines);
            return gifts.get(indexRandom);
        }
        else {
            return "Ничего";
        }
    }
}
