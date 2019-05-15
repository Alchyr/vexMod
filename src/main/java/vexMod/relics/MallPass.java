package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MallPass extends CustomRelic {


    public static final String ID = VexMod.makeID("MallPass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MallPass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MallPass.png"));


    public MallPass() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof ShopRoom || room instanceof EventRoom) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.gainGold(50);
            for (int i = 0; i < 50; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.currentX, this.currentY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
            }
        }

    }

    public boolean canSpawn() {
        return !AbstractDungeon.player.hasRelic(TreasureMap.ID) && !AbstractDungeon.player.hasRelic(HatredEngine.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
