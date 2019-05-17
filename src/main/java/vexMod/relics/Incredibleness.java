package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Incredibleness extends CustomRelic {


    public static final String ID = VexMod.makeID("Incredibleness");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Incredibleness.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NotEnergy.png"));


    public Incredibleness() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
