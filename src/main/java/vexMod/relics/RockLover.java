package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockLover extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("RockLover");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockLover.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockLover.png"));


    public RockLover() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.HEAVY);
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
