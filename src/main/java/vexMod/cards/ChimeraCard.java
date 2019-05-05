package vexMod.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import vexMod.VexMod;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static vexMod.VexMod.makeCardPath;

public class ChimeraCard extends AbstractDefaultCard {

    // TEXT DECLARATION

    public static final String ID = VexMod.makeID("ChimeraCard"); // VexMod.makeID("${NAME}");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("UltimateCard.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;  // COST = ${COST}

    public float TRANSFORMATION = 119;

    public int TRANSFORMS_LEFT = 10;

    private AbstractCard transformedCard;

    // /STAT DECLARATION/

    public ChimeraCard() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.dontTriggerOnUseCard = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        TRANSFORMS_LEFT = 10;
        AbstractCard newTransformedCard = transformedCard.makeCopy();
        if (this.upgraded) {
            newTransformedCard.upgrade();
        }
        newTransformedCard.applyPowers();
        newTransformedCard.setCostForTurn(0);
        newTransformedCard.purgeOnUse = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(newTransformedCard, m));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (TRANSFORMATION >= 120 && TRANSFORMS_LEFT > 0 && !AbstractDungeon.player.masterDeck.contains(this)) {
                TRANSFORMS_LEFT -= 1;
                TRANSFORMATION = 0;
                transformedCard = getRandomChimeraCard().makeCopy();
                if (this.upgraded) {
                    transformedCard.upgrade();
                }
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    transformedCard.applyPowers();
                }
                this.type = transformedCard.type;
                this.target = transformedCard.target;
                this.color = transformedCard.color;
                this.rarity = transformedCard.rarity;
                this.name = transformedCard.name;
                if (TRANSFORMS_LEFT > 1) {
                    this.rawDescription = transformedCard.rawDescription + " NL " + this.TRANSFORMS_LEFT + " transformations left.";
                } else if (TRANSFORMS_LEFT == 1) {
                    this.rawDescription = transformedCard.rawDescription + " NL " + this.TRANSFORMS_LEFT + " transformation left.";
                } else if (TRANSFORMS_LEFT < 1) {
                    this.rawDescription = transformedCard.rawDescription + " NL " + "No longer transforms until played.";
                }

                this.costForTurn = transformedCard.cost;
                this.cost = transformedCard.cost;
                this.baseDamage = transformedCard.baseDamage;
                this.multiDamage = transformedCard.multiDamage;
                this.baseBlock = transformedCard.baseBlock;
                this.baseMagicNumber = transformedCard.baseMagicNumber;
                this.magicNumber = transformedCard.magicNumber;
                this.exhaust = transformedCard.exhaust;
                Field yuckyPrivateAtlas;
                try {
                    yuckyPrivateAtlas = AbstractCard.class.getDeclaredField("cardAtlas");
                    yuckyPrivateAtlas.setAccessible(true);
                    TextureAtlas cardAtlas = (TextureAtlas) yuckyPrivateAtlas.get(null);
                    this.portrait = cardAtlas.findRegion(transformedCard.assetUrl);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (TRANSFORMATION >= 120) {
                TRANSFORMATION = 0;
                transformedCard = getRandomChimeraCard().makeCopy();
                if (this.upgraded) {
                    transformedCard.upgrade();
                }
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    transformedCard.applyPowers();
                }
                this.type = transformedCard.type;
                this.target = transformedCard.target;
                this.color = transformedCard.color;
                this.rarity = transformedCard.rarity;
                this.name = transformedCard.name;
                this.rawDescription = transformedCard.rawDescription;
                this.costForTurn = transformedCard.cost;
                this.cost = transformedCard.cost;
                this.baseDamage = transformedCard.baseDamage;
                this.multiDamage = transformedCard.multiDamage;
                this.baseBlock = transformedCard.baseBlock;
                this.baseMagicNumber = transformedCard.baseMagicNumber;
                this.magicNumber = transformedCard.magicNumber;
                this.exhaust = transformedCard.exhaust;
                Field yuckyPrivateAtlas;
                try {
                    yuckyPrivateAtlas = AbstractCard.class.getDeclaredField("cardAtlas");
                    yuckyPrivateAtlas.setAccessible(true);
                    TextureAtlas cardAtlas = (TextureAtlas) yuckyPrivateAtlas.get(null);
                    this.portrait = cardAtlas.findRegion(transformedCard.assetUrl);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        this.initializeDescription();

    }

    private AbstractCard getRandomChimeraCard() {
        if (AbstractDungeon.player != null) {
            ArrayList<AbstractCard> tmp = new ArrayList<>();
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.player.masterDeck.contains(this))
            {
                for (AbstractCard card : CardLibrary.getAllCards()) {
                    if (!card.cardID.contains("Poker") && !card.cardID.contains("theDuelist") && !card.cardID.contains("theCatGirl") && !card.cardID.contains("anomaly") && !card.cardID.equals("vexMod:ChimeraCard") && !card.cardID.equals("vexMod:WellTimedStrike") && !card.cardID.equals("vexMod:MidnightStrike") && !card.cardID.equals("Tactician") && !card.cardID.equals("Reflex") && card.cost <= EnergyPanel.getCurrentEnergy() && !card.hasTag(CardTags.HEALING) && card.type != CardType.CURSE && card.type != CardType.STATUS) {
                        tmp.add(card);
                    }
                }
            }
            else
            {
                for (AbstractCard card : CardLibrary.getAllCards()) {
                    if (!card.cardID.contains("Poker") && !card.cardID.contains("theDuelist") && !card.cardID.contains("theCatGirl") && !card.cardID.contains("anomaly") && !card.cardID.equals("vexMod:ChimeraCard") && !card.cardID.equals("vexMod:WellTimedStrike") && !card.cardID.equals("vexMod:MidnightStrike") && !card.cardID.equals("Tactician") && !card.cardID.equals("Reflex") && !card.hasTag(CardTags.HEALING) && card.type != CardType.CURSE && card.type != CardType.STATUS) {
                        tmp.add(card);
                    }
                }
            }
            return tmp.get(AbstractDungeon.cardRandomRng.random(tmp.size() - 1)).makeCopy();
        } else {
            return null;
        }
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null && this.TRANSFORMS_LEFT > 0) {
            TRANSFORMATION += 60 * Gdx.graphics.getDeltaTime();
            this.applyPowers();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (transformedCard != null) {
            if (!canUse) {
                return false;
            } else if (!transformedCard.canUse(p, m)) {
                this.cantUseMessage = transformedCard.cantUseMessage;
                return false;
            } else {
                return canUse;
            }
        } else {
            if (!canUse) {
                return false;
            } else {
                return canUse;
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}