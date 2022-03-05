package bullshitPackage;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.text.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;


public class Commands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("bleach")){
            String gifs[] = new String[15];
            gifs[0] = "https://tenor.com/view/cant-unsee-how-to-unsee-blind-homer-simpson-simpsons-gif-17613101";
            gifs[1] = "https://tenor.com/view/must-unsee-spongebob-washing-eyes-cant-look-gif-14237119";
            gifs[2] = "https://tenor.com/view/therapy-alcohol-archer-gif-10593300";
            gifs[3] = "https://tenor.com/view/unseen-my-eyes-delete-gif-12212414";
            gifs[4] = "https://tenor.com/view/unsee-gif-7234566";
            gifs[5] = "https://tenor.com/view/please-no-michael-scott-the-office-no-god-please-no-steve-carell-gif-3532257";
            gifs[6] = "https://tenor.com/view/throw-up-dry-heave-vomit-gross-eww-gif-23254765";
            gifs[7] = "https://tenor.com/view/bleach-clorox-gif-11117255";
            gifs[8] = "https://tenor.com/view/mr-krabs-eyes-clean-shine-spongebob-gif-16260550";
            gifs[9] = "https://tenor.com/view/faltas-de-ortograf%C3%ADa-eyes-eye-wash-gif-13468132";
            gifs[10] = "https://tenor.com/view/robot-pixel-pixel-art-8bit-eye-gif-23574208";
            gifs[11] = "https://tenor.com/view/eye-otter-rubbing-eyes-tired-irritated-gif-16638234";
            gifs[12] = "https://tenor.com/view/unreal-no-unbelievable-steve-harvey-no-way-gif-5365038";
            gifs[14] = "https://tenor.com/view/cleaning-windshield-wiping-squeegee-clear-view-gif-13959606";

            event.reply("cleaning coming :)").queue();

            for(int i = 0; i != 4; i++){
                event.getChannel().sendMessage(gifs[(int) (Math.random() * 15)]).queue();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            event.getChannel().sendMessage("https://tenor.com/view/asian-all-clear-gaped-tooth-thumbs-up-gif-10075950").queue();
        }
        if(event.getName().equals("signup")){
            // bring up the select menu
            SelectMenu menu = SelectMenu.create("menu:class")
                    .setPlaceholder("Choose one option.")
                    .setRequiredRange(1, 1)
                    .addOption("Filling out for Self", "self")
                    .addOption("Filling out for Other", "other")
                    .build();

            event.reply("Please pick your answer!")
                    .setEphemeral(true)
                    .addActionRow(menu)
                    .queue();
        }
    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        if(event.getComponentId().equals("menu:class")){
            //adding yourself
            if(event.getSelectedOptions().get(0).getValue().equals("self")){
                TextInput name = TextInput.create("name", "Name", TextInputStyle.SHORT)
                        .setPlaceholder("Enter your Name")
                        .setRequired(true)
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();

                TextInput email = TextInput.create("email", "Email", TextInputStyle.SHORT)
                        .setPlaceholder("Enter your Email")
                        .setRequired(true)
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();



                Modal modal = Modal.create("addSelf", "Sign up for the Form!")
                        .addActionRows(ActionRow.of(name), ActionRow.of(email))
                        .build();

                event.replyModal(modal).queue();
            }
            //adding another user
            if(event.getSelectedOptions().get(0).getValue().equals("other")){
                TextInput name = TextInput.create("name", "Name", TextInputStyle.SHORT)
                        .setPlaceholder("Enter your Name")
                        .setRequired(true)
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();

                TextInput email = TextInput.create("email", "Email", TextInputStyle.SHORT)
                        .setPlaceholder("Enter your Email")
                        .setRequired(true)
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();

                TextInput discordID = TextInput.create("discordID", "DiscordID of user", TextInputStyle.SHORT)
                        .setPlaceholder("Enter Users discordID")
                        .setRequired(true)
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();


                Modal modal = Modal.create("addOther", "Add Another User To The Database!")
                        .addActionRows(ActionRow.of(name), ActionRow.of(email), ActionRow.of(discordID))
                        .build();

                event.replyModal(modal).queue();
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        // INFO
        if (args[0].equalsIgnoreCase(main.prefix + "info")) {
            EmbedBuilder infoEmbed = new EmbedBuilder();
            infoEmbed.setTitle("GigaChad Info!");
            infoEmbed.setAuthor("OutsideToday");
            infoEmbed.setColor(Color.green);
            infoEmbed.addField("Creator", "The Creator of this bot " +
                    "is <@124654744571478016> !", false);
            infoEmbed.addField("Help?", "A lot of this bots success " +
                    "can be credited to <@171086994099798016> !", false);
            infoEmbed.addField("Commands",
                    "Currently\n" +
                    "/bleach\n" +
                    "/signup", false);

            event.getChannel().sendMessageEmbeds(infoEmbed.build()).queue();
        }
        //TIME
        if (args[0].equalsIgnoreCase(main.prefix + "time")) {
            Calendar time = Calendar.getInstance();
            time.setTimeZone(TimeZone.getTimeZone("CST"));
            String timeString = time.getTime().toString();

            event.getChannel().sendMessage(timeString).queue();
            System.out.println(timeString);
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        if (event.getComponentId().equals("addUser")) {
            SelectMenu menu = SelectMenu.create("menu:class")
                    .setPlaceholder("Choose one option.")
                    .setRequiredRange(1, 1)
                    .addOption("Filling out for Self", "self")
                    .addOption("Filling out for Other", "other")
                    .build();

            event.reply("Please pick your answer!")
                    .setEphemeral(true)
                    .addActionRow(menu)
                    .queue();
        }
        if (event.getComponentId().equals("removeUser")) {
            String discordID = event.getUser().getId();

            //sql shit
            Connection conn = null;
            PreparedStatement ptmt = null;
            try {
                conn = DriverManager
                        .getConnection("jdbc:mysql://sql448.main-hosting.eu/u816645365_discordBot",
                                "u816645365_OutsideToday",
                                "Evopansy2*");
                System.out.println("connected to db");

                String query = "DELETE FROM classmates WHERE (discordID) = ?";

                ptmt = conn.prepareStatement(query);

                ptmt.setString(1, discordID);

                ptmt.executeUpdate();

                ptmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //reply wehn done
            event.reply("\nSuccessfully REMOVED!\n" + "<@" + discordID + ">").setEphemeral(false).queue();

        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (event.getModalId().equals("addSelf")) {
            //grabbing tha data bitch
            String name = event.getValue("name").getAsString();
            String email = event.getValue("email").getAsString();
            String discordID = event.getUser().getId();

            //sql shit
            Connection conn = null;
            PreparedStatement ptmt = null;
            try {
                conn = DriverManager
                        .getConnection("jdbc:mysql://sql448.main-hosting.eu/u816645365_discordBot",
                                "u816645365_OutsideToday",
                                "Evopansy2*");
                System.out.println("connected to db");

                String query = "INSERT INTO classmates(discordID,name,email) VALUES(?,?,?)";

                ptmt = conn.prepareStatement(query);

                ptmt.setString(1, discordID);
                ptmt.setString(2, name);
                ptmt.setString(3, email);

                ptmt.executeUpdate();

                ptmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //reply wehn done
            event.reply("\nSuccessfully added!\n" + name).setEphemeral(false).queue();

        }
        if (event.getModalId().equals("addOther")) {
            //grabbing tha data bitch
            String name = event.getValue("name").getAsString();
            String email = event.getValue("email").getAsString();
            String discordID = event.getValue("discordID").getAsString();

            //sql shit
            Connection conn = null;
            PreparedStatement ptmt = null;
            try {
                conn = DriverManager
                        .getConnection("jdbc:mysql://sql448.main-hosting.eu/u816645365_discordBot",
                                "u816645365_OutsideToday",
                                "Evopansy2*");
                System.out.println("connected to db");

                String query = "INSERT INTO classmates(discordID,name,email) VALUES(?,?,?)";

                ptmt = conn.prepareStatement(query);

                ptmt.setString(1, discordID);
                ptmt.setString(2, name);
                ptmt.setString(3, email);

                ptmt.executeUpdate();

                ptmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            //reply wehn done
            event.reply("\nSuccessfully added!\n" + name).setEphemeral(false).queue();

        }
    }


}
