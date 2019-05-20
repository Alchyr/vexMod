package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FloorLord extends CustomRelic {


    public static final String ID = VexMod.makeID("FloorLord");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SsserpentHead.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SsserpentHead.png"));


    public FloorLord() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
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


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
