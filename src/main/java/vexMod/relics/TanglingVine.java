package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class TanglingVine extends CustomRelic {


    public static final String ID = VexMod.makeID("TanglingVine");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TanglingVine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("TanglingVine.png"));

    public TanglingVine() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }


    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onPlayerEndTurn() {
        this.flash();
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, (AbstractDungeon.player.hand.size()*2), DamageInfo.DamageType.THORNS));
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}