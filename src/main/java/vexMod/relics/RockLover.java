package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockLover extends CustomRelic {


    public static final String ID = VexMod.makeID("RockLover");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockLover.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockLover.png"));


    public RockLover() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.HEAVY);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
