package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FloorLord extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("FloorLord");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SsserpentHead.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SsserpentHead.png"));


    public FloorLord() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    public void onEnterRoom(AbstractRoom room) {
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(AbstractDungeon.floorNum);
        if (room instanceof MonsterRoom || room instanceof TreasureRoom || room instanceof ShopRoom && !AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
            for (int i = 0; i < AbstractDungeon.floorNum; i++) {
                AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.currentX, this.currentY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
            }
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
