package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GildedClover extends CustomRelic {


    public static final String ID = VexMod.makeID("GildedClover");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GildedClover.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GildedClover.png"));

    public GildedClover() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
