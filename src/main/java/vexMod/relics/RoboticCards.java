package vexMod.relics;

import ThePokerPlayer.cards.PokerCard;
import ThePokerPlayer.interfaces.IShowdownEffect;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RoboticCards extends CustomRelic implements IShowdownEffect {

    public static final String ID = VexMod.makeID("RoboticCards");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RoboticCards.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GenericCardOutline.png"));

    public RoboticCards() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.increaseMaxOrbSlots(3, false);
    }

    @Override
    public void onShowdownStart() {
        int numODiamonds = 0;
        int numOSpades = 0;
        int numOClubs = 0;
        int numOHearts = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof PokerCard) {
                if (((PokerCard) c).suit == PokerCard.Suit.Diamond) {
                    numODiamonds++;
                } else if (((PokerCard) c).suit == PokerCard.Suit.Spade) {
                    numOSpades++;
                } else if (((PokerCard) c).suit == PokerCard.Suit.Club) {
                    numOClubs++;
                } else if (((PokerCard) c).suit == PokerCard.Suit.Heart) {
                    numOHearts++;
                }
            }
        }
        int max = 0;
        ArrayList<AbstractOrb> possibleOrbs = new ArrayList<>();

        if (numODiamonds > 0) {
            max = numODiamonds;
            possibleOrbs.add(new Lightning());
        }
        if (numOSpades > 0) {
            if (numOSpades == max) {
                possibleOrbs.add(new Frost());
            } else if (numOSpades > max) {
                max = numOSpades;
                possibleOrbs.clear();
                possibleOrbs.add(new Frost());
            }
        }
        if (numOClubs > 0) {
            if (numOClubs == max) {
                possibleOrbs.add(new Dark());
            } else if (numOClubs > max) {
                max = numOClubs;
                possibleOrbs.clear();
                possibleOrbs.add(new Dark());
            }
        }
        if (numOHearts > 0) {
            if (numOHearts == max) {
                possibleOrbs.add(new Plasma());
            } else if (numOHearts > max) {
                max = numOHearts;
                possibleOrbs.clear();
                possibleOrbs.add(new Plasma());
            }
        }
        if (possibleOrbs.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(possibleOrbs.get(AbstractDungeon.cardRandomRng.random(possibleOrbs.size() - 1))));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
