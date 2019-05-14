package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

public class GoldFlippers extends CustomRelic {


    public static final String ID = VexMod.makeID("GoldFlippers");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GoldFlippers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GoldFlippers.png"));

    private boolean TRIGGERRED = false;

    public GoldFlippers() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        AbstractDungeon.player.gainGold(15);
        if (room instanceof MonsterRoom || room instanceof TreasureRoom || room instanceof ShopRoom && !AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
            if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
                for (int i = 0; i < 15; i++) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, this.currentX, this.currentY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, true));
                }
            }

            if (room instanceof ShopRoom) {
                this.flash();
                this.beginLongPulse();
                this.TRIGGERRED = true;
            }
            if (!(room instanceof ShopRoom)) {
                if (TRIGGERRED) {
                    for (int i = 0; i < (Math.floorDiv(AbstractDungeon.player.gold, 5)); i++) {
                        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.HP_LOSS));
                    }
                    this.TRIGGERRED = false;
                    this.stopPulse();
                }
            }
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
