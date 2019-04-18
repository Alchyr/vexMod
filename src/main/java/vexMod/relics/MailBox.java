package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import vexMod.VexMod;
import vexMod.actions.RelicTalkAction;
import vexMod.util.TextureLoader;

import java.util.List;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MailBox extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     */

    // ID, images, text.
    public static final String ID = VexMod.makeID("MailBox");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MailBox.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MailBox.png"));

    private boolean usedThisTurn = false; // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    public MailBox() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void onRightClick() {// On right click
        if (!isObtained || usedThisTurn) {// If it has been used this turn, or the player doesn't actually have the relic (i.e. it's on display in the shop room)
            return; // Don't do anything.
        }
        usedThisTurn = true;
        CardCrawlGame.sound.play("UI_CLICK_1");
        Thread t = new Thread() {
            public void run() {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey("dSpsG146tUir9joAnM49c92aM")
                        .setOAuthConsumerSecret("ua4n3m4hcq3hwbpsEGwogLto8fFWIzzVxG9vXtARWNY0FmV9k1")
                        .setOAuthAccessToken("1114339198247493632-a0gde0NOIWImc7EHPXC2xJsqQbJCCM")
                        .setOAuthAccessTokenSecret("7VfNOz9fiDbKaz2JlW8QpRtenvcXIhLWAFpBDhZzuZGCm");
                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();
                try {
                    List<Status> statuses = twitter.getUserTimeline();
                    for (Status status : statuses) {
                        if ((status.getText().contains("Vex") && status.getText().contains(CardCrawlGame.playerName)) || status.getText().contains("Vex") && status.getText().contains("all"))
                        {
                            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(AbstractDungeon.player.getRelic(MailBox.ID), status.getText(), 0.0F, 3.5F));
                        }
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        };
        t.run();
    }

    @Override
    public void atPreBattle() {
        usedThisTurn = false; // Make sure usedThisTurn is set to false at the start of each combat.
    }

    @Override
    public int getPrice() {
        return 1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}