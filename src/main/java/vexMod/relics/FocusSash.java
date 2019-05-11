package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FocusSash extends CustomRelic implements BetterOnLoseHpRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("FocusSash");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FocusSash.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FocusSash.png"));


    public FocusSash() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        if (damageAmount >= AbstractDungeon.player.currentHealth && AbstractDungeon.player.currentHealth >= 10) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return (AbstractDungeon.player.currentHealth - 1);
        }
        return damageAmount;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
