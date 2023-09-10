package uk.co.nyvil.bot.commands.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataObject;
import uk.co.nyvil.bot.commands.manager.SlashCommand;
import uk.co.nyvil.bot.commands.status.SlashCommandRecord;
import uk.co.nyvil.utils.MessageUtils;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

public class FoxCommand extends SlashCommand {

    private final String[] foxFacts = {
            "Foxes belong to the family Canidae, which also includes dogs and wolves.",
            "There are about 37 different species of foxes found around the world.",
            "The red fox (Vulpes vulpes) is the most common and widely distributed species of fox.",
            "Foxes are known for their cleverness and are often associated with intelligence in folklore and mythology.",
            "They have excellent night vision, which helps them hunt in low-light conditions.",
            "Foxes are omnivores and have a varied diet that includes small mammals, birds, insects, fruits, and even some vegetation.",
            "Some species of foxes are known for their vocalizations, including the eerie and high-pitched screams of the red fox during mating season.",
            "Arctic foxes have thick fur that changes color with the seasons, allowing them to blend in with their surroundings.",
            "Fennec foxes, the smallest species of fox, have oversized ears that help them dissipate heat in their desert habitats.",
            "Foxes are solitary animals and typically prefer to live alone or in small family groups.",
            "They are known for their agility and can leap fences and climb trees when necessary.",
            "Foxes are opportunistic hunters and scavengers, which makes them adaptable to various environments.",
            "In some cultures, foxes are seen as tricksters or symbols of cunning and adaptability.",
            "The gray fox is the only species of fox that can climb trees, thanks to its semi-retractable claws.",
            "Foxes are known for their distinctive bushy tails, which they use for balance and to help keep them warm in cold weather.",
            "In Japan, the fox is often considered a messenger of the rice deity Inari and is associated with prosperity and good fortune.",
            "Foxes communicate with each other through a variety of vocalizations, body language, and scent marking.",
            "The Swift Fox, native to North America, is one of the rarest and smallest species of fox, and it was once considered endangered.",
            "Foxes are known for their digging skills and use underground dens for shelter and raising their young.",
            "Domesticated foxes, known as 'silver foxes,' have been bred in Russia for their fur and as part of experiments in selective breeding for tameness."
    };

    private final Gson gson = new Gson();

    @Override
    public String name() {
        return "fox";
    }

    @Override
    public String description() {
        return "Sends a cute picture of a fox!";
    }

    @Override
    public Permission neededPermission() {
        return Permission.USE_APPLICATION_COMMANDS;
    }


    @Override
    public boolean guildOnly() {
        return true;
    }

    @Override
    public OptionData[] options() {
        return new OptionData[]{
                new OptionData(OptionType.BOOLEAN, "fact", "Sends a random fox fact", false)
        };
    }

    @Override
    public void execute(SlashCommandRecord info) {

        if(info.event().getOption("fact") == null) {
            info.event().replyEmbeds(MessageUtils.createSuccessEmbed(foxFacts[new Random().nextInt(foxFacts.length)]).setImage(getImageUrl().orElse("https://i.imgur.com/removed.png")).build()).queue();
            return;
        }

        if(info.event().getOption("fact").getAsBoolean()) {
            info.event().replyEmbeds(MessageUtils.createSuccessEmbed(foxFacts[new Random().nextInt(foxFacts.length)]).setImage(getImageUrl().orElse("https://i.imgur.com/removed.png")).build()).queue();
        } else {
            info.event().replyEmbeds(MessageUtils.createSuccessEmbed().setImage(getImageUrl().orElse("https://i.imgur.com/removed.png")).build()).queue();
        }

    }


    private Optional<String> getImageUrl() {
        String url = "https://randomfox.ca/floof/";
        try (InputStream inputStream = URI.create(url).toURL().openStream()) {
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
            return Optional.of(jsonObject.get("image").getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
