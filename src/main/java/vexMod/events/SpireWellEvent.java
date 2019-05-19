package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import vexMod.VexMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static vexMod.VexMod.makeEventPath;

public class SpireWellEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("SpireWellEvent");
    public static final String IMG = makeEventPath("SpireWellEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public SpireWellEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold / 5);
                        int randomRelicChance = AbstractDungeon.cardRandomRng.random(99);
                        if (randomRelicChance == 0) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else if (randomRelicChance == 1) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.SPECIAL));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else if (randomRelicChance == 2) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.STARTER));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else if (randomRelicChance > 2 && randomRelicChance <= 4) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else if (randomRelicChance > 4 && randomRelicChance <= 7) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.SHOP));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else if (randomRelicChance > 7 && randomRelicChance <= 12) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else if (randomRelicChance > 12 && randomRelicChance <= 22) {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON));
                            this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        }

                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1:
                        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth / 10, true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion());
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break;
                    case 3:
                        ArrayList<AbstractCard> removablecards = new ArrayList<>(AbstractDungeon.player.masterDeck.getPurgeableCards().group);
                        Collections.shuffle(removablecards, new Random(AbstractDungeon.miscRng.randomLong()));
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(removablecards.get(0), (float) Settings.WIDTH / 2.0F + 30.0F * Settings.scale + AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractCard card = removablecards.get(0);
                        AbstractDungeon.player.masterDeck.removeCard(card);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1:
                if (i == 0) {
                    openMap();
                }
                break;
        }
    }

}
