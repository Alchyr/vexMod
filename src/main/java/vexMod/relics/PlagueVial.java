package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Iterator;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PlagueVial extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("PlagueVial");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PlagueVial.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PlagueVial.png"));

    private float MODIFIER_AMT = 0.15F;

    public PlagueVial() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }


    // Gain 1 Strength on on equip.
    @Override
    public void atBattleStart() {
        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
        {
            this.flash();
            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var1.hasNext()) {
                AbstractMonster m = (AbstractMonster) var1.next();
                if (m.currentHealth > (int) ((float) m.maxHealth * (1.0F - this.MODIFIER_AMT))) {
                    m.currentHealth = (int) ((float) m.maxHealth * (1.0F - this.MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            }
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
