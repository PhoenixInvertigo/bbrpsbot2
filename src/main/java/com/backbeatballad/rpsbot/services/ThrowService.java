package com.backbeatballad.rpsbot.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ThrowService {

    public String rps(String symbol){
        String botSymbol = randomRPSSymbol();

        String result = "You have thrown: " + symbol;
        result += "\nI have thrown: " + botSymbol;
        result += resolveWins(symbol, botSymbol);

        return result;
    }

    public String throwForMe(){
        return rps(randomRPSSymbol());
    }

    public String throwBombForMe(){
        return rps(randomBombSymbol());
    }

    private String randomRPSSymbol(){
        Random rand = new Random();
        int result = rand.nextInt(3);
        switch(result) {
            case 0:
                return "Rock";
            case 1:
                return "Paper";
            case 2:
                return "Scissors";
        }
        return null;
    }

    private String randomBombSymbol(){
        Random rand = new Random();
        int result = rand.nextInt(3);
        switch(result) {
            case 0:
                return "Rock";
            case 1:
                return "Bomb";
            case 2:
                return "Scissors";
        }
        return null;
    }

    private String resolveWins(String player, String bot){
        if(player.equalsIgnoreCase(bot)) return "\nWe tie.";

        if(player.equalsIgnoreCase("Paper") && bot.equalsIgnoreCase("Rock")) return "\nYou win!";
        if(player.equalsIgnoreCase("Scissors") && bot.equalsIgnoreCase("Paper")) return "\nYou win!";
        if(player.equalsIgnoreCase("Rock") && bot.equalsIgnoreCase("Scissors")) return "\nYou win!";
        if(player.equalsIgnoreCase("Bomb") && bot.equalsIgnoreCase("Paper")) return "\nYou win!";
        if(player.equalsIgnoreCase("Bomb") && bot.equalsIgnoreCase("Rock")) return "\nYou win!";
        if(player.equalsIgnoreCase("Bomb") && bot.equalsIgnoreCase("Scissors")) return "\nYou lose.";
        if(player.equalsIgnoreCase("Paper") && bot.equalsIgnoreCase("Scissors")) return "\nYou lose.";
        if(player.equalsIgnoreCase("Scissors") && bot.equalsIgnoreCase("Rock")) return "\nYou lose.";
        if(player.equalsIgnoreCase("Rock") && bot.equalsIgnoreCase("Paper")) return "\nYou lose.";


        return null;
    }
}
