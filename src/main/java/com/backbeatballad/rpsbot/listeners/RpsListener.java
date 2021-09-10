package com.backbeatballad.rpsbot.listeners;

import com.backbeatballad.rpsbot.configs.DiscordConfig;
import com.backbeatballad.rpsbot.services.ThrowService;
import com.mewna.catnip.shard.DiscordEvent;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
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
//        discordConfig.getApi().addMessageCreateListener(event -> {
//            if (event.getMessageContent().equalsIgnoreCase("!rps")) {
//                new MessageBuilder()
//                        .setContent("Choose your fighter!")
//                        .addComponents(
//                                ActionRow.of(Button.secondary("rock", "Rock", "\u270A"),
//                                        Button.secondary("paper", "Paper", "\u270B"),
//                                        Button.secondary("scissors", "Scissors", "\u270C"),
//                                        Button.secondary("throwForMe", "Throw for me", "\u2753")))
//                        .send(event.getChannel());
//            }
//        });

//        discordConfig.getApi().addMessageComponentCreateListener(event -> {
//            MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
//            String customId = messageComponentInteraction.getCustomId();
//            String response = "";
//            if(event.getInteraction().getServer().isPresent()) {
//                response = event.getInteraction().getUser().getDisplayName(event.getInteraction().getServer().get()) + " has challenged the bot! \n";
//            } else{
//                response = event.getInteraction().getUser().getName() + " has challenged the bot! \n";
//            }
//
//            switch (customId) {
//                case "rock":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.rps("Rock"))
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//                case "paper":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.rps("Paper"))
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//                case "scissors":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.rps("Scissors"))
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//                case "throwForMe":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.throwForMe())
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//            }
//        });
    }

    private void addBombListener(){
//        discordConfig.getApi().addMessageCreateListener(event -> {
//            if (event.getMessageContent().equalsIgnoreCase("!rpsbomb")) {
//                new MessageBuilder()
//                        .setContent(event.getMessageAuthor().getDisplayName() + " has challenged the bot! \n" + throwService.throwBombForMe())
//                        .send(event.getChannel());
//            }
//        });

          discordConfig.getCatnip().observable(DiscordEvent.MESSAGE_CREATE)
                .filter(msg -> msg.content().equalsIgnoreCase("!rpsbomb2"))
                .subscribe(msg -> {
                    msg.reply(msg.author().username() + " has challenged the bot! \n" + throwService.throwBombForMe(), true);
                }, error -> error.printStackTrace());
    }
}
