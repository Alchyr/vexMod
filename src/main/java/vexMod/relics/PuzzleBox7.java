package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.actions.ShitRelicFlashAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PuzzleBox7 extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     */

    // ID, images, text.
    public static final String ID = VexMod.makeID("PuzzleBox7");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static boolean loseRelic = false;

    public PuzzleBox7() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 1;
    }

    @Override
    public void onRightClick() {// On right click
        if (!isObtained) {// If it has been used this turn, or the player doesn't actually have the relic (i.e. it's on display in the shop room)
            return; // Don't do anything.
        }

        this.counter -= 1;
        if (this.counter == 0)
        {
            this.flash();
            loseRelic = true;
        }
    }

    public static void relicBullshit() {
        if (loseRelic) {
            CardCrawlGame.sound.play("GOLD_GAIN");
            AbstractDungeon.player.loseRelic(PuzzleBox7.ID);
            AbstractDungeon.player.gainGold(1000);
            loseRelic = false;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS [1];
    }

}