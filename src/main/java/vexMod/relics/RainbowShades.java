package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RainbowShades extends CustomRelic {

    public static final String ID = VexMod.makeID("RainbowShades");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RainbowShades.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    private static boolean activated = false;

    public RainbowShades() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        activated = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (!activated && drawnCard.isEthereal) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(drawnCard.makeCopy(), 1, false, false, false));
            activated = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
