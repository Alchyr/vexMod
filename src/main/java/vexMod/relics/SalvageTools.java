package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;
import java.util.Iterator;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class SalvageTools extends CustomRelic {


    public static final String ID = VexMod.makeID("SalvageTools");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SalvageTools.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SalvageTools.png"));


    public SalvageTools() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void onManualDiscard() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        ArrayList<AbstractCard> groupCopy = new ArrayList<>();
        Iterator var4 = AbstractDungeon.player.hand.group.iterator();

        while (true) {
            while (var4.hasNext()) {
                AbstractCard c = (AbstractCard) var4.next();
                if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                    groupCopy.add(c);
                }
            }

            var4 = AbstractDungeon.actionManager.cardQueue.iterator();

            while (var4.hasNext()) {
                CardQueueItem i = (CardQueueItem) var4.next();
                if (i.card != null) {
                    groupCopy.remove(i.card);
                }
            }

            AbstractCard c = null;
            if (!groupCopy.isEmpty()) {
                c = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }

            if (c != null) {
                c.modifyCostForTurn(-1);
            }
            break;
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
