package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.VexMod;
import vexMod.actions.RelicBallAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockBasket extends CustomRelic {


    public static final String ID = VexMod.makeID("RockBasket");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockBasket.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockBasket.png"));


    public RockBasket() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.commonRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.uncommonRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.rareRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.shopRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
        AbstractDungeon.bossRelicPool.removeIf(wahoo -> !wahoo.startsWith("vexMod:"));
    }

    @Override
    public void atTurnStart() {
        int numOfVexModRelics = 0;
        for (AbstractRelic ar : AbstractDungeon.player.relics) {
            if (ar.relicId.startsWith("vexMod:")) {
                numOfVexModRelics++;
            }
        }
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new RelicBallAction(m, true, 1.5F, 25, 10));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(m, numOfVexModRelics, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
