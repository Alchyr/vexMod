package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.colorless.Finesse;
import com.megacrit.cardcrawl.cards.colorless.FlashOfSteel;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.EmptyCage;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PuzzleBox extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     */

    // ID, images, text.
    public static final String ID = VexMod.makeID("PuzzleBox");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PuzzleBox.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static boolean loseRelic = false;

    public PuzzleBox() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 1000;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public void onRightClick() {// On right click
        if (!isObtained) {// If it has been used this turn, or the player doesn't actually have the relic (i.e. it's on display in the shop room)
            return; // Don't do anything.
        }

        CardCrawlGame.sound.play("UI_CLICK_1");
        this.counter -= 1;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
        if (this.counter == 0) {
            this.flash();
            loseRelic = true;
        }
    }

    public static void relicBullshit() {
        if (loseRelic) {
            int v = AbstractDungeon.cardRandomRng.random(2);
            if (v == 0) {
                CardCrawlGame.sound.play("GOLD_GAIN");
                AbstractDungeon.player.gainGold(777);
            } else if (v == 1) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FlashOfSteel(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Finesse(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FlashOfSteel(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Finesse(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            } else if (v == 2) {
                ArrayList<AbstractRelic> themRelics = new ArrayList();
                themRelics.add(RelicLibrary.getRelic(EmptyCage.ID));
                themRelics.add(RelicLibrary.getRelic(ImprovementManual.ID));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, themRelics.get(AbstractDungeon.miscRng.random(themRelics.size() - 1)));

            }
            AbstractDungeon.player.loseRelic(PuzzleBox.ID);
            loseRelic = false;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
    }

}