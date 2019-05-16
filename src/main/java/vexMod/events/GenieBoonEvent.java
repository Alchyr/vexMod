package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class GenieBoonEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GenieBoonEvent");
    public static final String IMG = makeEventPath("GenieBoon.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;


    public GenieBoonEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.effectList.add(new RainingGoldEffect(100));
                        AbstractDungeon.player.gainGold(100);
                        break;
                    case 1:
                        ArrayList<AbstractCard> woohoo = CardLibrary.getAllCards();
                        AbstractCard c = woohoo.get(AbstractDungeon.eventRng.random(woohoo.size() - 1)).makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2:
                        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), BlightHelper.getRandomBlight());

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
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
