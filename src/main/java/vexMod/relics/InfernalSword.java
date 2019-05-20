package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class InfernalSword extends CustomRelic {


    public static final String ID = VexMod.makeID("InfernalSword");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("InfernalSword.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("InfernalSword.png"));


    public InfernalSword() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
        c.setCostForTurn(0);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
