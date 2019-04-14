package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import com.megacrit.cardcrawl.powers.MalleablePower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Jellybean extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("Jellybean");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("JellyBean.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("JellyBean.png"));


    public Jellybean() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.tips.add(new PowerTip("Malleable", "Upon receiving Attack damage, gains #b2 #yBlock. NL #yBlock gain increases as #yMalleable is triggered. NL Resets to #b2 at the start of your turn."));
    }

    @Override
    public void onEquip()
    {
        AbstractDungeon.player.increaseMaxHp(6, true);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MalleablePower(AbstractDungeon.player, 2)));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
