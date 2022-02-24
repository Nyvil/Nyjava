package uk.co.nyvil.bot.commands.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import uk.co.nyvil.bot.commands.manager.SlashCommand;
import uk.co.nyvil.bot.commands.manager.SlashCommandAnnotation;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class FoxCommand implements SlashCommand {

    private final String[] sentences = {"Aww :heart:", "So cute :pleading_face:", "Tell me you wouldn't adopt that fox without lying", ":fox:"};
    private final Gson gson = new Gson();

    @Override
    @SlashCommandAnnotation(name = "fox")
    public void execute(SlashCommandExecutionInfo info) {
        try {
            final InputStream inputStream = new URL("https://randomfox.ca/floof").openStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            final String json = readAll(bufferedReader);
            final JsonObject obj = gson.fromJson(json, JsonObject.class);
            final String imageUrl = obj.get("image").getAsString().replaceAll("\\\\", "");
            info.getEvent().replyEmbeds(MessageUtils.createSuccessEmbed(sentences[new Random().nextInt(sentences.length)]).setImage(imageUrl).build()).queue();
        } catch (IOException e) {
            info.getEvent().replyEmbeds(MessageUtils.createErrorEmbed("Couldn't reach API to retrieve the picture. Please try again later!").build()).queue();
        }
    }

    private String readAll(Reader rd) throws IOException {
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
