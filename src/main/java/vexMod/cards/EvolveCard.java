package vexMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import vexMod.VexMod;
import vexMod.actions.BlazeAction;
import vexMod.actions.StabbyXAction;
import vexMod.powers.PropogationPower;

import java.util.ArrayList;

import static com.evacipated.cardcrawl.mod.stslib.StSLib.getMasterDeckEquivalent;
import static com.megacrit.cardcrawl.core.Settings.language;
import static vexMod.VexMod.makeCardPath;

public class EvolveCard extends AbstractDefaultCard {

    public static final String ID = VexMod.makeID("EvolveCard");
    public static final String IMG = makeCardPath("EvolveCardSkill.png");
    public static final CardColor COLOR = CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int PERIL_HEAL = 25;
    private static final int EXECUTION_DAMAGE = 15;
    private static final int NO_CARDS_AND_ENERGY_DRAW = 3;
    private static final int NO_CARDS_AND_ENERGY_ENERGY = 2;
    private static final int NO_CARDS_DRAW = 4;
    private static final int NO_ENERGY_ENERGY_GAIN = 2;
    private static final int STABBY_FIGHT_X_POWER = 6;
    private static final int NOB_FIGHT_DAMAGE = 12;
    private static final int NOB_FIGHT_BLOCK = 12;
    private static final int ENEMY_DEBUFF_DMG = 15;
    private static final int ENEMY_DEBUFF_WEAK = 3;
    private static final int FIVE_ENERGY_X_POWER = 6;
    private static final int BARRICADE_BLOCK = 20;
    private static final int BOSSFIGHT_DAMAGE = 25;
    private static final int NO_BLOCK_BLOCK = 13;
    private static final int CONFUSED_CARD_DRAW = 3;
    private static final int MULTI_FIGHT_ENEMY_DAMAGE = 7;
    private static final int ENEMY_BLOCKING_DRAW = 1;
    private static final int BROKE_MOOLAH = 50;
    private boolean playerNearDeath;
    private boolean noCardsAndEnergy;
    private boolean noEnergyLeft;
    private boolean monstersNearDeath;
    private boolean turnOne;
    private boolean emptyHand;
    private boolean twoDebuffs;
    private boolean fightingStabby;
    private boolean nobBattle;
    private boolean statusOrCurse;
    private boolean enemyDebuffing;
    private boolean unknownIntent;
    private boolean fiveOrMoreEnergy;
    private boolean isBarricaded;
    private boolean bossBattle;
    private boolean noBlock;
    private boolean isConfused;
    private boolean multiFight;
    private boolean enemyBlocking;
    private boolean lowGold;


    public EvolveCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.upgraded = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (playerNearDeath) {
            this.exhaust = true;
            AbstractDungeon.player.heal(PERIL_HEAL, true);
            AbstractDungeon.player.masterDeck.removeCard(getMasterDeckEquivalent(this));
        } else if (noCardsAndEnergy) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AdrenalineEffect(), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(NO_CARDS_AND_ENERGY_ENERGY));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, NO_CARDS_AND_ENERGY_DRAW));
        } else if (noEnergyLeft) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(NO_ENERGY_ENERGY_GAIN));
        } else if (monstersNearDeath) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        } else if (turnOne) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PropogationPower(p, p, 1), 1));
        } else if (emptyHand) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, NO_CARDS_DRAW));
        } else if (twoDebuffs) {
            AbstractDungeon.actionManager.addToBottom(new RemoveDebuffsAction(p));
        } else if (fightingStabby) {
            if (this.energyOnUse < EnergyPanel.totalCount) {
                this.energyOnUse = EnergyPanel.totalCount;
            }
            ArrayList<AbstractCard> handCopy = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if ((c.type == AbstractCard.CardType.STATUS)) {
                    handCopy.add(c);
                }
            }
            if (!handCopy.isEmpty()) {
                for (AbstractCard c : handCopy) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
                handCopy.clear();
            }
            AbstractDungeon.actionManager.addToBottom(new ReinforcedBodyAction(p, block, this.freeToPlayOnce, this.energyOnUse));
        } else if (nobBattle) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        } else if (statusOrCurse) {
            ArrayList<AbstractCard> handCopy = new ArrayList<>();
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
        } else if (enemyDebuffing) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, ENEMY_DEBUFF_WEAK, false), ENEMY_DEBUFF_WEAK));
        } else if (unknownIntent) {
            AbstractDungeon.actionManager.addToBottom(new BlazeAction(AbstractDungeon.player, 2, false));
        } else if (fiveOrMoreEnergy) {
            if (this.energyOnUse < EnergyPanel.totalCount) {
                this.energyOnUse = EnergyPanel.totalCount;
            }
            AbstractDungeon.actionManager.addToBottom(new StabbyXAction(p, m, damage, block, this.freeToPlayOnce, this.energyOnUse));
        } else if (isBarricaded) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        } else if (bossBattle) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        } else if (noBlock) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        } else if (isConfused) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, CONFUSED_CARD_DRAW));
        } else if (multiFight) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        } else if (enemyBlocking) {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(m, p));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, ENEMY_BLOCKING_DRAW));
        } else if (lowGold) {
            AbstractDungeon.player.gainGold(BROKE_MOOLAH);
            for (int i = 0; i < BROKE_MOOLAH; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(p, this.hb.cX, this.hb.cY, p.hb.cX, p.hb.cY, true));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.isMultiDamage = false;
        playerNearDeath = false;
        noCardsAndEnergy = false;
        noEnergyLeft = false;
        monstersNearDeath = false;
        turnOne = false;
        emptyHand = false;
        twoDebuffs = false;
        fightingStabby = false;
        nobBattle = false;
        statusOrCurse = false;
        enemyDebuffing = false;
        unknownIntent = false;
        fiveOrMoreEnergy = false;
        isBarricaded = false;
        bossBattle = false;
        noBlock = false;
        isConfused = false;
        multiFight = false;
        enemyBlocking = false;
        lowGold = false;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (AbstractDungeon.player.currentHealth <= 10) {
                playerNearDeath = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "凤凰涅槃";
                } else {
                    this.name = "Phoenix Revival";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 0;
                this.costForTurn = 0;
                this.exhaust = true;
                this.rawDescription = EXTENDED_DESCRIPTION[0];
                loadCardImage(makeCardPath("PhoenixRevival.png"));
            } else if (AbstractDungeon.player.hand.size() == 1 && EnergyPanel.totalCount == 0) {
                noCardsAndEnergy = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "一键重启";
                } else {
                    this.name = "Repower";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 0;
                this.costForTurn = 0;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[2];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (EnergyPanel.totalCount == 0) {
                noEnergyLeft = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "瞬时充能";
                } else {
                    this.name = "Energize";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 0;
                this.costForTurn = 0;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[4];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (checkNearDead()) {
                monstersNearDeath = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "手起刀落";
                } else {
                    this.name = "Execution";
                }
                this.baseDamage = EXECUTION_DAMAGE;
                this.type = CardType.ATTACK;
                this.target = CardTarget.ENEMY;
                this.cost = 0;
                this.costForTurn = 0;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[1];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            } else if (GameActionManager.turn == 1) {
                turnOne = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "繁衍生息";
                } else {
                    this.name = "Propagation";
                }
                this.type = CardType.POWER;
                this.target = CardTarget.SELF;
                this.cost = 3;
                this.costForTurn = 3;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[5];
                loadCardImage(makeCardPath("EvolveCardPower.png"));
            } else if (AbstractDungeon.player.hand.size() == 1) {
                emptyHand = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "即时补牌";
                } else {
                    this.name = "Redraw";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 1;
                this.costForTurn = 0;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[3];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (checkDebuffCount()) {
                twoDebuffs = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "神清气爽";
                } else {
                    this.name = "Shake it Off";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 1;
                this.costForTurn = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[6];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (checkStabby()) {
                fightingStabby = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "焚书秘技";
                } else {
                    this.name = "Book Blaster";
                }
                this.baseBlock = STABBY_FIGHT_X_POWER;
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = -1;
                this.costForTurn = -1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[7];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (checkNob()) {
                nobBattle = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "猛男克星";
                } else {
                    this.name = "Nobsbane";
                }
                this.baseDamage = NOB_FIGHT_DAMAGE;
                this.baseBlock = NOB_FIGHT_BLOCK;
                this.type = CardType.ATTACK;
                this.target = CardTarget.SELF_AND_ENEMY;
                this.cost = 2;
                this.costForTurn = 2;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[8];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            } else if (checkCursedStatused()) {
                statusOrCurse = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "净化灵药";
                } else {
                    this.name = "Elixir Purge";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 1;
                this.costForTurn = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[9];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (checkEnemyDebuffing()) {
                enemyDebuffing = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "兵来将挡";
                } else {
                    this.name = "Debuff Punisher";
                }
                this.baseDamage = ENEMY_DEBUFF_DMG;
                this.type = CardType.ATTACK;
                this.target = CardTarget.ENEMY;
                this.cost = 1;
                this.costForTurn = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[10];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            } else if (checkEnemyUnknown()) {
                unknownIntent = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "幻惑之镜";
                } else {
                    this.name = "Unknown Mirroring";
                }
                this.type = CardType.POWER;
                this.target = CardTarget.SELF;
                this.cost = 1;
                this.costForTurn = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[11];
                loadCardImage(makeCardPath("EvolveCardPower.png"));
            } else if (AbstractDungeon.player.energy.energy >= 5) {
                fiveOrMoreEnergy = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "充能爆发";
                } else {
                    this.name = "Charge Blast";
                }
                this.baseDamage = FIVE_ENERGY_X_POWER;
                this.baseBlock = FIVE_ENERGY_X_POWER;
                this.type = CardType.ATTACK;
                this.target = CardTarget.SELF_AND_ENEMY;
                this.cost = -1;
                this.costForTurn = -1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[12];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            } else if (AbstractDungeon.player.getPower("Barricade") != null) {
                isBarricaded = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "添砖加瓦";
                } else {
                    this.name = "Barricade Boost";
                }
                this.baseBlock = BARRICADE_BLOCK;
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 2;
                this.costForTurn = 2;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[13];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                bossBattle = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "痛殴BOSS";
                } else {
                    this.name = "Boss Bash";
                }
                this.baseDamage = BOSSFIGHT_DAMAGE;
                this.type = CardType.ATTACK;
                this.target = CardTarget.SELF_AND_ENEMY;
                this.cost = 2;
                this.costForTurn = 2;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[14];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            } else if (AbstractDungeon.player.currentBlock == 0) {
                noBlock = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "紧急护盾";
                } else {
                    this.name = "Emergency Shielding";
                }
                this.baseBlock = NO_BLOCK_BLOCK;
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.costForTurn = 1;
                this.cost = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[15];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (AbstractDungeon.player.getPower("Confusion") != null) {
                isConfused = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "紧急护盾";
                } else {
                    this.name = "Snecko Mulligan";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 0;
                this.costForTurn = 0;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[16];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (checkMultiCombat()) {
                multiFight = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "戏蛇窍门";
                } else {
                    this.name = "Splash Crash";
                }
                this.baseDamage = MULTI_FIGHT_ENEMY_DAMAGE;
                this.isMultiDamage = true;
                this.type = CardType.ATTACK;
                this.target = CardTarget.ALL_ENEMY;
                this.cost = 1;
                this.costForTurn = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[17];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            } else if (checkEnemyBlock()) {
                enemyBlocking = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "雨露均沾";
                } else {
                    this.name = "Peeler";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.ENEMY;
                this.cost = 0;
                this.costForTurn = 0;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[18];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else if (AbstractDungeon.player.gold <= 150) {
                lowGold = true;
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "就地印钞";
                } else {
                    this.name = "Moolah Maker";
                }
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.cost = 3;
                this.costForTurn = 3;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[19];
                loadCardImage(makeCardPath("EvolveCardSkill.png"));
            } else {
                if (language == Settings.GameLanguage.ZHS) {
                    this.name = "随便打打";
                } else {
                    this.name = "Easy-Going Damage";
                }
                this.baseDamage = 8;
                this.type = CardType.ATTACK;
                this.target = CardTarget.ENEMY;
                this.cost = 1;
                this.costForTurn = 1;
                this.exhaust = false;
                this.rawDescription = EXTENDED_DESCRIPTION[20];
                loadCardImage(makeCardPath("EvolveCardAttack.png"));
            }
        } else {
            if (language == Settings.GameLanguage.ZHS) {
                this.name = "演变之牌";
            } else {
                this.name = "Evolving Card";
            }
            this.cost = 1;
            this.exhaust = false;
            this.rawDescription = DESCRIPTION;
            this.type = CardType.SKILL;
        }
        this.initializeDescription();
    }

    private boolean checkMultiCombat() {
        int monstersInCombat = 0;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                monstersInCombat++;
            }
        }
        return monstersInCombat >= 2;
    }

    private boolean checkEnemyBlock() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.currentBlock > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCursedStatused() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == CardType.CURSE || c.type == CardType.STATUS || c.color == CardColor.CURSE) {
                return true;
            }
        }
        return false;
    }

    private boolean checkEnemyDebuffing() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.intent == AbstractMonster.Intent.STRONG_DEBUFF) {
                return true;
            }
        }
        return false;
    }

    private boolean checkEnemyUnknown() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.intent == AbstractMonster.Intent.UNKNOWN) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDebuffCount() {
        int debuffsOwned = 0;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF) {
                debuffsOwned += 1;
            }
        }
        return debuffsOwned >= 2;
    }

    private boolean checkNob() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m instanceof GremlinNob) {
                return true;
            }
        }
        return false;
    }

    private boolean checkStabby() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m instanceof BookOfStabbing) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNearDead() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.currentHealth <= 15) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.player != null) {
            applyPowers();
        }
    }


    @Override
    public void upgrade() {
    }
}