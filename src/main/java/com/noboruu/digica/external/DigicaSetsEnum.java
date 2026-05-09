package com.noboruu.digica.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DigicaSetsEnum {

    // Enum with all DCG wiki card set parent URLs
    // Add more as they get added

    // -- Booster sets --
    BT1("BT1", "BT-01:_Booster_New_Evolution"),
    BT2("BT2", "BT-02:_Booster_Ultimate_Power"),
    BT3("BT3", "BT-03:_Booster_Union_Impact"),
    BT4("BT4", "BT-04:_Booster_Great_Legend"),
    BT5("BT5", "BT-05:_Booster_Battle_Of_Omni"),
    BT6("BT6", "BT-06:_Booster_Double_Diamond"),
    BT7("BT7", "BT-07:_Booster_Next_Adventure"),
    BT8("BT8", "BT-08:_Booster_New_Awakening"),
    BT9("BT9", "BT-09:_Booster_X_Record"),
    BT10("BT10", "BT-10:_Booster_Xros_Encounter"),
    BT11("BT11", "BT-11:_Booster_Dimensional_Phase"),
    BT12("BT12", "BT-12:_Booster_Across_Time"),
    BT13("BT13", "BT-13:_Booster_Versus_Royal_Knights"),
    BT14("BT14", "BT-14:_Booster_Blast_Ace"),
    BT15("BT15", "BT-15:_Booster_Exceed_Apocalypse"),
    BT16("BT16", "BT-16:_Booster_Beginning_Observer"),
    BT17("BT17", "BT-17:_Booster_Secret_Crisis"),
    BT18("BT18", "BT-18:_Booster_Elemental_Successor"),
    BT19("BT19", "BT-19:_Booster_Xros_Evolution"),
    BT20("BT20", "BT-20:_Booster_Over_the_X"),
    BT21("BT21", "BT-21:_BOOSTER_WORLD_CONVERGENCE"),
    BT22("BT22", "BT-22:_BOOSTER_CYBER_EDEN"),
    BT23("BT23", "BT-23:_BOOSTER_HACKERS%27_SLUMBER"),
    BT24("BT24", "BT-24:_BOOSTER_TIME_STRANGER"),
    BT25("BT25", "BT-25:_BOOSTER_DUAL_REVOLUTION"),
    BT26("BT26", "BT-26:_BOOSTER_TIMELESS_BONDS"),
    // -- Theme Boosters --
    EX1("EX1", "EX-01:_Theme_Booster_Classic_Collection"),
    EX2("EX2", "EX-02:_Theme_Booster_Digital_Hazard"),
    EX3("EX3", "EX-03:_Theme_Booster_Draconic_Roar"),
    EX4("EX4", "EX-04:_Theme_Booster_Alternative_Being"),
    EX5("EX5", "EX-05:_Theme_Booster_Animal_Colosseum"),
    EX6("EX6", "EX-06:_Theme_Booster_Infernal_Ascension"),
    EX7("EX7", "EX-07:_Extra_Booster_Digimon_Liberator"),
    EX8("EX8", "EX-08:_Extra_Booster_Chain_of_Liberation"),
    EX9("EX9", "EX-09:_EXTRA_BOOSTER_VERSUS_MONSTERS"),
    EX10("EX10", "EX-10:_EXTRA_BOOSTER_SINISTER_ORDER"),
    EX11("EX11", "EX-11:_EXTRA_BOOSTER_DAWN_OF_LIBERATOR"),
    EX12("EX12", "EX-12:_EXTRA_BOOSTER_DIGITAL_WORLD_SHAMBALA"),
    EX13("EX13", "EX-13:_EXTRA_BOOSTER_CHIVALROUS_XIII"),
    // -- Resurgence Boost --
    RB1("RB1", "RB-01:_Resurgence_Booster"),
    // -- Limited Packs --
    LM1("LM", "LM-01:_Limited_Card_Pack_Digimon_Ghost_Game"),
    // LM2 omitted because it only has reprints
    LM3("LM", "LM-03:_Limited_Card_Set_2024"),
    LM4("LM", "LM-04:_Limited_Card_Pack_Torrid_Weiss"),
    LM5("LM", "LM-05:_Limited_Card_Pack_Final_Elysion"),
    LM6("LM", "LM-06:_LIMITED_CARD_PACK_BILLION_BULLET"),
    LM7("LM", "LM-07:_LIMITED_CARD_PACK_ANOTHER_KNIGHT"),
    LM8("LM", "LM-08:_LIMITED_CARD_PACK_FINAL_CREST"),
    // -- Starters Deck --
    ST1("ST1", "ST-1:_Starter_Deck_Gaia_Red"),
    ST2("ST2", "ST-2:_Starter_Deck_Cocytus_Blue"),
    ST3("ST3", "ST-3:_Starter_Deck_Heaven%27s_Yellow"),
    ST4("ST4", "ST-4:_Starter_Deck_Giga_Green"),
    ST5("ST5", "ST-5:_Starter_Deck_Machine_Black"),
    ST6("ST6", "ST-6:_Starter_Deck_Venomous_Violet"),
    ST7("ST7", "ST-7:_Starter_Deck_Gallantmon"),
    ST8("ST8", "ST-8:_Starter_Deck_UlforceVeedramon"),
    ST9("ST9", "ST-9:_Starter_Deck_Ultimate_Ancient_Dragon"),
    ST10("ST10", "ST-10:_Starter_Deck_Parallel_World_Tactician"),
    ST12("ST12", "ST-12:_Starter_Deck_Jesmon"),
    ST13("ST13", "ST-13:_Starter_Deck_RagnaLoardmon"),
    ST14("ST14", "ST-14:_Advanced_Deck_Set_Beelzemon"),
    ST15("ST15", "ST-15:_Starter_Deck_Dragon_of_Courage"),
    ST16("ST16", "ST-16:_Starter_Deck_Wolf_of_Friendship"),
    ST17("ST17", "ST-17:_Advanced_Deck_Set_Double_Typhoon"),
    ST18("ST18", "ST-18:_Starter_Deck_Guardian_Vortex"),
    ST19("ST19", "ST-19:_Starter_Deck_Fable_Waltz"),
    ST20("ST20", "ST-20:_STARTER_DECK_PROTECTOR_OF_LIGHT"),
    ST21("ST21", "ST-21:_STARTER_DECK_HERO_OF_HOPE"),
    ST22("ST22", "ST-22:_ADVANCED_DECK_SET_AMETHYST_MANDALA"),
    ST23("ST23", "ST-23:_STARTER_DECK_DIGIMON_BEATBREAK"),
    ST24("ST24", "ST-24:_STARTER_DECK_DIGIMON_DATA_SQUAD"),
    ST25("ST25", "ST-25:_STARTER_DECK_DIGIMON_ALYSION_RUBEUSDRAMON"),
    ST26("ST26", "ST-26:_STARTER_DECK_DIGIMON_ALYSION_YUKINAMON"),
    // -- Advanced Booster --
    AD1("AD1", "AD-01:_ADVANCED_BOOSTER_DIGIMON_GENERATION");

    private final String code;
    private final String path;
}