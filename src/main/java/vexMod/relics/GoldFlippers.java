package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GoldFlippers extends CustomRelic {

    // ID, images, text.
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
        if (room instanceof MonsterRoom || room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss || room instanceof TreasureRoom || room instanceof ShopRoom && !AbstractDungeon.player.hasRelic(Ectoplasm.ID)) {
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
                        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS));
                    }
                    this.TRIGGERRED = false;
                    this.stopPulse();
                }
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
