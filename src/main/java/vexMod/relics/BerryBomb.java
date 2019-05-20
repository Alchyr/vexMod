package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BerryBomb extends CustomRelic {


    public static final String ID = VexMod.makeID("BerryBomb");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BerryBomb.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BerryBomb.png"));
    private static boolean loseRelic = false;


    public BerryBomb() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
        this.counter = 8;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public static void relicBullshit() {
        if (loseRelic) {
            AbstractDungeon.player.loseRelic(BerryBomb.ID);
            loseRelic = false;
        }
    }

    public void atBattleStart() {
        this.counter -= 1;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atTurnStart() {
        if (this.counter == 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(100, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            loseRelic = true;
        }
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(6, true);
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 33);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
    }

}
