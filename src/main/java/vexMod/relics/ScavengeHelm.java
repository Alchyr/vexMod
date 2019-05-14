package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.MalleablePower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ScavengeHelm extends CustomRelic {


    public static final String ID = VexMod.makeID("ScavengeHelm");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WrithingMask.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WrithingMask.png"));


    public ScavengeHelm() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MalleablePower(AbstractDungeon.player, 4), 4));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
