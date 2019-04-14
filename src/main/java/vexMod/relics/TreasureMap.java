package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TreasureMap extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("TreasureMap");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("AdventureMap.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("AdventureMap.png"));


    public TreasureMap() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40 && !AbstractDungeon.player.hasRelic(MallPass.ID) && !AbstractDungeon.player.hasRelic(HatredEngine.ID);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
