package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RobotsGift extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("RobotsGift");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RobotsGift.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RobotsGift.png"));


    public RobotsGift() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.increaseMaxOrbSlots(2,false);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
