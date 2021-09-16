package com.backbeatballad.rpsbot.listeners;

import com.backbeatballad.rpsbot.configs.DiscordConfig;
import com.backbeatballad.rpsbot.services.ThrowService;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.springframework.stereotype.Component;
import org.javacord.*;

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
    }

    private void addRPSListener(){
        discordConfig.getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!rps")) {
                new MessageBuilder()
                        .setContent("Choose your fighter!")
                        .addComponents(
                                ActionRow.of(Button.secondary("Rock", "Rock", "\u270A"),
                                        Button.secondary("Paper", "Paper", "\u270B"),
                                        Button.secondary("Scissors", "Scissors", "\u270C"),
                                        Button.secondary("ThrowForMe", "Throw for me", "\u2753")))
                        .send(event.getChannel());
            }
        });

        discordConfig.getApi().addMessageComponentCreateListener(event -> {
            MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
            String customId = messageComponentInteraction.getCustomId();
            String response = "";
            if(event.getInteraction().getServer().isPresent()) {
                response = event.getInteraction().getUser().getDisplayName(event.getInteraction().getServer().get()) + " has challenged the bot! \n";
            } else{
                response = event.getInteraction().getUser().getName() + " has challenged the bot! \n";
            }

            switch (customId) {
                case "Rock":
                case "Paper":
                case "Scissors":
                    messageComponentInteraction.createImmediateResponder()
                            .setContent(response + throwService.rps(customId))
                            .respond();
                    messageComponentInteraction.getMessage().delete();
                    break;
                case "ThrowForMe":
                    messageComponentInteraction.createImmediateResponder()
                            .setContent(response + throwService.throwForMe())
                            .respond();
                    messageComponentInteraction.getMessage().delete();
                    break;
            }
        });
    }

    private void addBombListener(){
        discordConfig.getApi().addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!rpsbomb")) {
                new MessageBuilder()
                        .setContent(event.getMessageAuthor().getDisplayName() + " has challenged the bot! \n" + throwService.throwBombForMe())
                        .send(event.getChannel());
            }
        });
    }
}