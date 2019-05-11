package vexMod.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
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

public class XCostLoverEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("XCostLoverEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("XCostLoverEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean pickCard = false;

    public XCostLoverEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Smith Card: 50% chance to remove the card, 50% chance to upgrade it.
        imageEventText.setDialogOption(OPTIONS[1]); // Smith Sword: Gain a random Sword relic.
        imageEventText.setDialogOption(OPTIONS[2]); // Smith Meal: Gain a bunch of HP at a cost of Max HP.
        imageEventText.setDialogOption(OPTIONS[3]); // Leave
    }

    AbstractCard getXCostCard() {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.SPECIAL && card.cost == -1) {
                allCards.add(card);
            }
        }
        return allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1)).makeCopy();
    }

    @Override
    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(getXCostCard().makeCopy(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));// 73
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
                        int hpLoss = (int) ((float) AbstractDungeon.player.maxHealth * 0.75F);// 67
                        int diff = AbstractDungeon.player.maxHealth - hpLoss;// 68
                        AbstractDungeon.player.maxHealth = hpLoss;// 69
                        if (AbstractDungeon.player.maxHealth <= 0) {// 70
                            AbstractDungeon.player.maxHealth = 1;// 71
                        }

                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {// 74
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;// 75
                        }
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new ChemicalX());
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Ease
                        this.pickCard = true;
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, OPTIONS[5], false, false, false, true);
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        break;
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        //X cost card reward here
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractCard card1 = getXCostCard().makeCopy();
                        AbstractCard card2 = getXCostCard().makeCopy();
                        AbstractCard card3 = getXCostCard().makeCopy();
                        RewardItem reward = new RewardItem();
                        reward.cards.clear();
                        reward.cards.add(card1);
                        reward.cards.add(card2);
                        reward.cards.add(card3);
                        AbstractDungeon.getCurrRoom().addCardReward(reward);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)
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
