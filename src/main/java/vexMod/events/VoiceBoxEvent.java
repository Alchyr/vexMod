package vexMod.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class VoiceBoxEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("VoiceBoxEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("VoiceBoxEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean pickCard = false;

    public VoiceBoxEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // TAILORING: Gain 1 of 3 colorless Rare cards.
        imageEventText.setDialogOption(OPTIONS[1]); // CONVERSION: Transform a card in your deck into a Rare card.
        imageEventText.setDialogOption(OPTIONS[2]); // OM NOM: Pay 10 Max HP to gain a Rare relic.
        imageEventText.setDialogOption(OPTIONS[3]); // Leave
    }

    @Override
    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE).makeCopy(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));// 73
            AbstractDungeon.gridSelectScreen.selectedCards.clear();// 50
            this.pickCard = false;
        }
    }


    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractCard card1 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
                        AbstractCard card2 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
                        AbstractCard card3 = AbstractDungeon.getColorlessCardFromPool(AbstractCard.CardRarity.RARE).makeCopy();
                        RewardItem reward = new RewardItem();
                        reward.cards.clear();
                        reward.cards.add(card1);
                        reward.cards.add(card2);
                        reward.cards.add(card3);
                        AbstractDungeon.getCurrRoom().addCardReward(reward);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Ease
                        this.pickCard = true;
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, OPTIONS[5], false, false, false, true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break; // Onto screen 1 we go.
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        AbstractDungeon.player.maxHealth -= 10;
                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        if (AbstractDungeon.player.maxHealth < 1) {
                            AbstractDungeon.player.maxHealth = 1;
                        }
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);

                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        break; // Onto screen 1 we go.

                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
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
