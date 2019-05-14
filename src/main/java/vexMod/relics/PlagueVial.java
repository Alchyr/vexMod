package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PlagueVial extends CustomRelic {


    public static final String ID = VexMod.makeID("PlagueVial");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PlagueVial.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PlagueVial.png"));

    public PlagueVial() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }


    @Override
    public void atBattleStart() {
        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)) {
            this.flash();
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                float MODIFIER_AMT = 0.15F;
                if (m.currentHealth > (int) ((float) m.maxHealth * (1.0F - MODIFIER_AMT))) {
                    m.currentHealth = (int) ((float) m.maxHealth * (1.0F - MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            }
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
