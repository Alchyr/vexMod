package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import vexMod.VexMod;
import vexMod.actions.StabbyXAction;
import vexMod.powers.PropogationPower;

import java.util.ArrayList;

import static vexMod.VexMod.makeCardPath;

public class EvolveCard extends AbstractDefaultCard {

    // TEXT DECLARATION

    public static final String ID = VexMod.makeID("EvolveCard"); // VexMod.makeID("${NAME}");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("EvolveCardBase.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 1;  // COST = ${COST}

    boolean monstersNearDeath;
    boolean emptyHand;
    boolean noBlock;
    boolean playerNearDeath;
    boolean lowGold;
    boolean turnOne;
    boolean twoDebuffs;
    boolean bossBattle;
    boolean multiFight;
    boolean nobBattle;
    boolean enemyDebuffing;
    boolean fiveOrMoreEnergy;
    boolean fightingStabby;
    boolean statusOrCurse;
    boolean isConfused;
    // boolean enemyTwentyPlus;
    boolean enemyBlocking;
    boolean isBarricaded;
    boolean noEnergyLeft;
    boolean unknownIntent;

    private static final int EXECUTION_DAMAGE = 15;
    private static final int NO_CARDS_DRAW = 5;
    private static final int NO_BLOCK_BLOCK = 20;
    private static final int PERIL_HEAL_DIVIDER = 5;
    private static final int BROKE_MOOLAH = 25;
    private static final int INTRO_POWER_AMT = 1;
    private static final int ENEMY_DEBUFF_DMG = 10;
    private static final int ENEMY_DEBUFF_WEAK = 2;
    private static final int FIVE_ENERGY_X_POWER = 5;
    private static final int STABBY_FIGHT_X_POWER = 6;
    private static final int CONFUSED_CARD_DRAW = 3;
    private static final int ENEMY_BIG_ATTACK_BLOCK = 10;
    private static final int ENEMY_BIG_ATTACK_WEAK = 2;
    private static final int BARRICADE_BLOCK = 20;
    private static final int NO_ENERGY_ENERGY_GAIN = 2;
    private static final int NO_ENERGY_CARD_DRAW = 1;


    // /STAT DECLARATION/

    public EvolveCard() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (playerNearDeath) {
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth / PERIL_HEAL_DIVIDER, true);
        } else if (noBlock) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        } else if (emptyHand) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));// 45
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        } else if (monstersNearDeath) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        } else if (lowGold) {
            AbstractDungeon.player.gainGold(magicNumber);
            for (int i = 0; i < magicNumber; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(p, this.hb.cX, this.hb.cY, p.hb.cX, p.hb.cY, true));
            }
        } else if (turnOne) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PropogationPower(p, p, 1)));
        } else if (twoDebuffs) {
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
        } else if (bossBattle) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));// 36
        } else if (multiFight) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));// 41 42
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(7, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));// 43
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));// 45 46
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(7, true), this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));// 47
        } else if (nobBattle) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        } else if (enemyDebuffing) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));// 49
        } else if (fightingStabby) {
            ArrayList<AbstractCard> handCopy = new ArrayList();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if ((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE) || (c.color == AbstractCard.CardColor.CURSE)) {
                    handCopy.add(c);
                }
            }
            if (!handCopy.isEmpty()) {
                for (AbstractCard c : handCopy) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
                handCopy.clear();
            }
            if (this.energyOnUse < EnergyPanel.totalCount) {
                this.energyOnUse = EnergyPanel.totalCount;
            }
            AbstractDungeon.actionManager.addToBottom(new StabbyXAction(p, m, 6, this.freeToPlayOnce, this.energyOnUse));
        } else if (statusOrCurse) {
            ArrayList<AbstractCard> handCopy = new ArrayList();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if ((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE) || (c.color == AbstractCard.CardColor.CURSE)) {
                    handCopy.add(c);
                }
            }
            if (!handCopy.isEmpty()) {
                for (AbstractCard c : handCopy) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
                handCopy.clear();
            }
        } else if (isConfused) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        } else if (enemyBlocking) {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(m, p));// 39
        } else if (isBarricaded) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));// 38
        } else if (noEnergyLeft) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AdrenalineEffect(), 0.15F));// 41
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));// 4
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));// 4
        } else if (fiveOrMoreEnergy) {
            if (this.energyOnUse < EnergyPanel.totalCount) {
                this.energyOnUse = EnergyPanel.totalCount;
            }
            AbstractDungeon.actionManager.addToBottom(new StabbyXAction(p, m, 5, this.freeToPlayOnce, this.energyOnUse));
        } else if (unknownIntent) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();// 35
            c.setCostForTurn(-99);// 36
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 2, true));// 37
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth / PERIL_HEAL_DIVIDER) {
            playerNearDeath = true;
        }
        else if (AbstractDungeon.player.hand.size() == 1) {
            emptyHand = true;
        }
        else if (AbstractDungeon.player.gold <= 50) {
            lowGold = true;
        }
        int monstersInCombat = 0;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                monstersInCombat++;
                if (m.currentHealth <= 15 && !lowGold) {
                    monstersNearDeath = true;
                }
            }
            if (m instanceof GremlinNob && !monstersNearDeath) {
                nobBattle = true;
            }
            if (m instanceof BookOfStabbing && !nobBattle) {
                fightingStabby = true;
            }
            if (m.intent == AbstractMonster.Intent.STRONG_DEBUFF && !fightingStabby) {
                enemyDebuffing = true;
            }
            if (m.intent == AbstractMonster.Intent.UNKNOWN && !enemyDebuffing) {
                unknownIntent = true;
            }
        }
        if (monstersInCombat >= 2 && !unknownIntent) {
            multiFight = true;
        }
        else if (AbstractDungeon.player.energy.energy == 0) {
            noEnergyLeft = true;
        }
        else if (AbstractDungeon.player.energy.energy >= 5) {
            fiveOrMoreEnergy = true;
        }
        int debuffsOwned = 0;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                debuffsOwned += 1;
            }
        }
        if (debuffsOwned >= 2 && !fiveOrMoreEnergy) {
            twoDebuffs = true;
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if ((c.type == CardType.CURSE || c.type == CardType.STATUS || c.color == CardColor.CURSE) && !twoDebuffs) {
                statusOrCurse = true;
            }
        }
        if (AbstractDungeon.player.currentBlock == 0 && !statusOrCurse) {
            noBlock = true;
        }
        else if (GameActionManager.turn == 1) {
            turnOne = true;
        }
        else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            bossBattle = true;
        }
        else if (AbstractDungeon.player.getPower("Confusion")!= null)
        {
            isConfused = true;
        }
        else if (AbstractDungeon.player.getPower("Barricade")!= null)
        {
            isBarricaded = true;
        }
        this.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null) {
            applyPowers();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}