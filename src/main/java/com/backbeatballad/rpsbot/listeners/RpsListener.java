package com.backbeatballad.rpsbot.listeners;

import com.backbeatballad.rpsbot.configs.DiscordConfig;
import com.backbeatballad.rpsbot.services.ThrowService;

import com.mewna.catnip.shard.DiscordEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RpsListener {

    private DiscordConfig discordConfig;

    private ThrowService throwService;

    public RpsListener(DiscordConfig discordConfig, ThrowService throwService){
        this.discordConfig = discordConfig;
        this.throwService = throwService;
    }

    @PostConstruct
    private void AddListeners(){
        addRPSListener();
        addBombListener();
        discordConfig.getCatnip().connect();
    }

    private void addRPSListener(){
        discordConfig.getCatnip().observable(DiscordEvent.MESSAGE_CREATE)
                .filter(msg -> msg.content().equalsIgnoreCase("!rps2"))
                .subscribe(msg -> {
                    msg.reply(msg.author().username() + " has challenged the bot! \n" + throwService.throwForMe(), true);
                }, error -> error.printStackTrace());
    }

    private void addBombListener(){
          discordConfig.getCatnip().observable(DiscordEvent.MESSAGE_CREATE)
                .filter(msg -> msg.content().equalsIgnoreCase("!rpsbomb2"))
                .subscribe(msg -> {
                    msg.reply(msg.author().username() + " has challenged the bot! \n" + throwService.throwBombForMe(), true);
                }, error -> error.printStackTrace());
    }
}
