package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MagicMissile extends CustomRelic {


    public static final String ID = VexMod.makeID("MagicMissile");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MagicMissile.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MagicMissile.png"));


    public MagicMissile() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onShuffle() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        for (int i = 0; i < 3; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, AbstractDungeon.cardRandomRng.random(2, 5), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
