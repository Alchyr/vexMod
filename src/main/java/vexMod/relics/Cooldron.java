package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Cooldron extends CustomRelic {


    public static final String ID = VexMod.makeID("Cooldron");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Cooldron.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Cooldron.png"));

    public Cooldron() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
