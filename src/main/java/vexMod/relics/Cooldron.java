package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Cooldron extends CustomRelic {


    public static final String ID = VexMod.makeID("Cooldron");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Cooldron.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Cooldron.png"));

    public Cooldron() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
