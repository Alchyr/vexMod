package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RobotsGift extends CustomRelic {


    public static final String ID = VexMod.makeID("RobotsGift");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RobotsGift.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RobotsGift.png"));


    public RobotsGift() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.increaseMaxOrbSlots(2, false);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
