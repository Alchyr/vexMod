package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class DrawConverter extends CustomRelic {


    public static final String ID = VexMod.makeID("DrawConverter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GameCart.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GameCart.png"));

    private boolean working = false;

    public DrawConverter() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    public void onCardDraw(AbstractCard drawnCard) {
        if (working) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }

    public void onPlayerEndTurn() {
        working = false;
    }

    public void atBattleStart() {
        working = false;
    }

    public void atTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                working = true;
                isDone = true;
            }
        });
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
