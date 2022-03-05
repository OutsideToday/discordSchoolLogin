package bullshitPackage;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Calendar;
import java.util.TimeZone;

public class main {
    static JDA api;
    public static String prefix = ">";

    static ArrayList<String> discordIDs;
    static ArrayList<String> emails;
    static ArrayList<String> names;
    static String classChannel = "945403061550075915";
    static String class2Channel = "877931131473903676";
    static String testChannel = "945225775148322829";
    static TextChannel tableChannel;

    /*-- CHANGE ME --*/
    static int hour = 8;
    static int minute = 20;
    static boolean submitBoolean = true;


    public static void fillForm() throws InterruptedException {
        names = new ArrayList<>();
        emails = new ArrayList<>();
        discordIDs = new ArrayList<>();

        //sql connection shit
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        String SELECT_SQL = "SELECT * FROM classmates";

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://sql448.main-hosting.eu/u816645365_discordBot",
                            "u816645365_OutsideToday",
                            "Evopansy2*");
            System.out.println("connected to db");
            pStatement = connection.prepareStatement(SELECT_SQL);
            resultSet = pStatement.executeQuery();

            while (resultSet.next()) {


                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String discordID = resultSet.getString("discordID");
                names.add(name);
                discordIDs.add(discordID);
                emails.add(email);
                System.out.println(name + ": added to List");

            }
            System.out.println(names);
            System.out.println(emails);
            System.out.println(discordIDs);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //chroime driver shit
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Desktop\\driver\\chromedriver.exe");
        WebDriverManager.chromedriver().forceDownload();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
        WebDriver driver = new ChromeDriver(options);

        //embed builder
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Todays Submissions");
        embedBuilder.setAuthor("OutsideToday");
        embedBuilder.setImage("https://c.tenor.com/E0xOQmkgKE4AAAAC/betty-white-dab.gif");
        embedBuilder.setColor(Color.CYAN);

        //filling form shit per person in <names>
        for (int i = 0; i < names.size(); i++) {

            driver.get("https://forms.office.com/Pages/ResponsePage.aspx?id=ZwjbazAFD0-_iomyGPB5scW2hyWcsetPtOz3u8m5kY9UN1g4QTFPOFYwSTJMTzNSSEtYQjU5QkdXWi4u");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            WebElement noButton = driver.findElement(By.xpath("/html/body/div/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div[1]/div/div[2]/div/div[2]/div/label/input"));
            WebElement emailBox = driver.findElement(By.xpath("//*[@id=\"form-container\"]/div/div/div[1]/div/div[1]/div[2]/div[2]/div[2]/div/div[2]/div/div/input"));
            WebElement submitButton = driver.findElement(By.xpath("/html/body/div/div/div/div/div[1]/div/div[1]/div[2]/div[3]/div[1]/button"));

            noButton.click();
            Thread.sleep((long) (Math.random() * 1000));

            emailBox.click();
            emailBox.clear();
            emailBox.sendKeys(emails.get(i));
            Thread.sleep((long) (Math.random() * 1000));


            if (submitBoolean == true) {
                //submit the form
                submitButton.click();
            }

            System.out.println("SUBMITTED FOR " + names.get(i));

            embedBuilder.addField("Name", names.get(i), false);

            driver.navigate().refresh();
        }
        //output to defined text chanel
        if (submitBoolean == true) {

            tableChannel = api.getTextChannelById(classChannel);
            tableChannel.sendMessageEmbeds(embedBuilder.build())
                    .setActionRow(Button.secondary("addUser", "Sign Up!"), Button.danger("removeUser", "Remove Me"))
                    .queue();

            tableChannel = api.getTextChannelById(class2Channel);
            tableChannel.sendMessageEmbeds(embedBuilder.build())
                    .setActionRow(Button.secondary("addUser", "Sign Up!"), Button.danger("removeUser", "Remove Me"))
                    .queue();
        } else if (submitBoolean == false) {
            tableChannel = api.getTextChannelById(testChannel);
            tableChannel.sendMessageEmbeds(embedBuilder.build())
                    .setActionRow(Button.secondary("addUser", "Sign Up!"), Button.danger("removeUser", "Remove Me"))
                    .queue();
        }

        driver.quit();
    }

    static class getSleepTime implements Runnable {

        @Override
        public void run() {
            while (true) {
                // get current time
                Calendar targetCal = Calendar.getInstance(TimeZone.getTimeZone("CST"));

                // check if current time is before 8:20am
                Calendar today820 = Calendar.getInstance();
                today820.set(Calendar.HOUR_OF_DAY, hour);
                today820.set(Calendar.MINUTE, minute);

                // unless it's before 8:20a today, add 1 day for target time
                if (targetCal.after(today820)) {
                    targetCal.add(Calendar.DATE, 1);
                }

                // set time to tomorrow at 8:20am
                targetCal.set(Calendar.HOUR_OF_DAY, hour);
                targetCal.set(Calendar.MINUTE, minute);

                // add another day until we get to next day that is M-F
                while (targetCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                        targetCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {

                    targetCal.add(Calendar.DATE, 1);

                }

                // get time between now and target time
                Calendar nowCal = Calendar.getInstance(TimeZone.getTimeZone("CST"));

                long sleepTime = targetCal.getTimeInMillis() - nowCal.getTimeInMillis();

                int hours = (int) ((sleepTime / (1000 * 60 * 60)) % 24);

                System.out.println("hours till " + hour + ":" + minute + " " + hours);

                // return dat
                try {
                    Thread.sleep(sleepTime);

                    fillForm();

                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        final String TOKEN = "ODY4MzQ5NTU3NDg0OTc4MTk2.YPuXiw.MZ0JWO5gY7oodIhI8ZjoXWAD0gU";

        api = JDABuilder.createDefault(TOKEN).build();

        api.awaitReady();

        api.getPresence().setActivity(Activity.playing(">info"));

        api.addEventListener(new Commands());
        api.upsertCommand("bleach", "clean yo screen of the filth FOOL, Matt you also a bitch").queue();
        api.upsertCommand("signup", "Sign up for the submissions :)").queue();

        Thread sleepFormThread = new Thread(new getSleepTime());

        sleepFormThread.start();

    }
}
