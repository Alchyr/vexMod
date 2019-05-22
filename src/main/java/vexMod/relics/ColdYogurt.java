package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ColdYogurt extends CustomRelic {

    public static final String ID = VexMod.makeID("ColdYogurt");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ColdYogurt.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ColdYogurt.png"));

    public ColdYogurt() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.channelOrb(new Frost());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
