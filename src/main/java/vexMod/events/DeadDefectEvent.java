package vexMod.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.RunData;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;
import java.util.List;

import static vexMod.VexMod.makeEventPath;

public class DeadDefectEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("DeadDefectEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("DeadDefectEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean canPurge;

    public DeadDefectEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        this.canPurge = CardHelper.hasCardWithType(AbstractCard.CardType.CURSE);
        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Loot Body: Gain some gold.
        imageEventText.setDialogOption(OPTIONS[1]); // Help Body: Get a card from the Silent class.
        if (this.canPurge) {
            this.imageEventText.setDialogOption(OPTIONS[2]); // Desecrate Body: Remove your Curses.
        } else {
            this.imageEventText.setDialogOption(OPTIONS[3], !this.canPurge); // Cannot desecrate.
        }

        imageEventText.setDialogOption(OPTIONS[4]); // Leave
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0:
                        int goldAmount = AbstractDungeon.miscRng.random(80, 120);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmount));
                        AbstractDungeon.player.gainGold(goldAmount);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        break;
                    case 1: // If you press button the second button (Button at index 1), in this case: Ease

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractCard card1 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        AbstractCard card2 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        AbstractCard card3 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        RewardItem reward = new RewardItem();
                        AbstractCard card4 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        AbstractCard card5 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        AbstractCard card6 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        RewardItem reward2 = new RewardItem();
                        AbstractCard card7 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        AbstractCard card8 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        AbstractCard card9 = CardLibrary.getRandomColorSpecificCard(AbstractCard.CardColor.BLUE, AbstractDungeon.cardRandomRng);
                        RewardItem reward3 = new RewardItem();
                        reward.cards.clear();
                        reward.cards.add(card1);
                        reward.cards.add(card2);
                        reward.cards.add(card3);
                        reward2.cards.clear();
                        reward2.cards.add(card4);
                        reward2.cards.add(card5);
                        reward2.cards.add(card6);
                        reward3.cards.clear();
                        reward3.cards.add(card7);
                        reward3.cards.add(card8);
                        reward3.cards.add(card9);
                        AbstractDungeon.getCurrRoom().addCardReward(reward);
                        AbstractDungeon.getCurrRoom().addCardReward(reward2);
                        AbstractDungeon.getCurrRoom().addCardReward(reward3);
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new RobotsGift());
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();



                        break; // Onto screen 1 we go.
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        if (this.canPurge) {
                            List<String> metacurses = new ArrayList();

                            for (int q = AbstractDungeon.player.masterDeck.group.size() - 1; q >= 0; --q) {
                                if (((AbstractCard) AbstractDungeon.player.masterDeck.group.get(q)).type == AbstractCard.CardType.CURSE && !((AbstractCard) AbstractDungeon.player.masterDeck.group.get(i)).inBottleFlame && !((AbstractCard) AbstractDungeon.player.masterDeck.group.get(i)).inBottleLightning && ((AbstractCard) AbstractDungeon.player.masterDeck.group.get(i)).cardID != "AscendersBane" && ((AbstractCard) AbstractDungeon.player.masterDeck.group.get(i)).cardID != "Necronomicurse") {
                                    AbstractDungeon.effectList.add(new PurgeCardEffect((AbstractCard) AbstractDungeon.player.masterDeck.group.get(q)));
                                    metacurses.add(((AbstractCard) AbstractDungeon.player.masterDeck.group.get(q)).cardID);
                                    AbstractDungeon.player.masterDeck.removeCard((AbstractCard) AbstractDungeon.player.masterDeck.group.get(q));
                                }
                            }

                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                            this.imageEventText.updateDialogOption(0, OPTIONS[5]); // 1. Change the first button to the [Leave] button
                            this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                            screenNum = 1;
                            break; // Onto screen 1 we go.
                        }
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

}
