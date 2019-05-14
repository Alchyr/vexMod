package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReactivePower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BrainBlaster extends CustomRelic {


    public static final String ID = VexMod.makeID("BrainBlaster");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BrainBlaster.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BrainBlaster.png"));


    public BrainBlaster() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
    }

    @Override
    public void atBattleStart() {

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(m, this));
            m.addPower(new ReactivePower(m));
        }

        AbstractDungeon.onModifyPower();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
