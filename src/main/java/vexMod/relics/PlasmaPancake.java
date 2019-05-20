package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class PlasmaPancake extends CustomRelic {


    public static final String ID = VexMod.makeID("PlasmaPancake");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PlasmaPancake.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PlasmaPancake.png"));

    public PlasmaPancake() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new SweepingBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.flipHorizontal), 0.4F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(11, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(6, true);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
