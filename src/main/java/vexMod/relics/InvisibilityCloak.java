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

public class InvisibilityCloak extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("InvisibilityCloak");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("InvisibleCloak.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("InvisibleCloak.png"));


    public InvisibilityCloak() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(m, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new StrengthPower(m, -1), -1));
        }

        AbstractDungeon.onModifyPower();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
