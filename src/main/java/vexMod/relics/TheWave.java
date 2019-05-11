package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;
import vexMod.vfx.RelicWaveEffect;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TheWave extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     */

    // ID, images, text.
    public static final String ID = VexMod.makeID("TheWave");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TheWave.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TheWave.png"));

    public TheWave() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onRightClick() {// On right click
        if (!isObtained) {// If it has been used this turn, or the player doesn't actually have the relic (i.e. it's on display in the shop room)
            return; // Don't do anything.
        }
        CardCrawlGame.sound.play("UI_CLICK_1");
        AbstractDungeon.effectList.add(new RelicWaveEffect(0, AbstractDungeon.miscRng.random(0.25F)));
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