package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PainConverter
        extends CustomRelic
        implements BetterOnLoseHpRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("PainConverter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PainConverter.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PainConverter.png"));

    public PainConverter() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.HEAVY);
    }

    private int triggered = 0;

    // Gain 1 boon for 15 hp lost
    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount >= 10) {
            triggered += 1;
            this.beginPulse();
        }
        return damageAmount;
    }

    @Override
    public void atTurnStartPostDraw() {
        for (int i = 0; i < triggered; i++) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
        triggered = 0;
        this.stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
