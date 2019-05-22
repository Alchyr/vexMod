package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.powers.InspirationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Demotivationator extends CustomRelic {

    public static final String ID = VexMod.makeID("Demotivationator");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Demotivationator.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Demotivationator.png"));

    public Demotivationator() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new InspirationPower(AbstractDungeon.player, 1, -50), 1));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
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