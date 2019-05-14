package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class StrangeSmithEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("StrangeSmithEvent");
    public static final String IMG = makeEventPath("StrangeSmithEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    private boolean pickCard = false;

    public StrangeSmithEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if (AbstractDungeon.miscRng.randomBoolean()) {
                c.upgrade();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
            } else {
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(c);
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.pickCard = true;
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, OPTIONS[5], true, false, false, false);
                        screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();


                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;


                        ArrayList<AbstractRelic> possibleRelics = new ArrayList<>();
                        possibleRelics.add(RelicLibrary.getRelic(CursedBlade.ID));
                        possibleRelics.add(RelicLibrary.getRelic(ToySword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(DoubleEdgedSword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(BroadSword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(RustySword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(DrainingSword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(ImaginarySword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(InfernalSword.ID));
                        possibleRelics.add(RelicLibrary.getRelic(BerrySword.ID));

                        AbstractRelic relicToAdd = possibleRelics.get(AbstractDungeon.miscRng.random(possibleRelics.size() - 1));


                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(relicToAdd);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();

                        break;
                    case 2:
                        AbstractDungeon.player.maxHealth -= 10;
                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        if (AbstractDungeon.player.maxHealth < 1) {
                            AbstractDungeon.player.maxHealth = 1;
                        }
                        AbstractDungeon.player.heal(30, true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;


                        break;
                    case 3:
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
