package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.Iterator;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GorgonsHead extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("GorgonsHead");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GorgonsHead.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GorgonsHead.png"));


    public GorgonsHead() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -3), -3));
        }

        AbstractDungeon.onModifyPower();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
