package com.noboruu.digica.extractor.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DigicaSetsEnum {

    // Enum with all DCG wiki card set parent URLs
    // Add more as they get added

    // -- Booster sets --
    RSB1("RSB1.0", "/wiki/BT01-03:_Release_Special_Booster_Ver.1.0"),
    RSB15("RSB1.5", "/wiki/BT01-03:_Release_Special_Booster_Ver.1.5"),
    BT4("BT04", "/wiki/BT-04:_Booster_Great_Legend"),
    BT5("BT05", "/wiki/BT-05:_Booster_Battle_Of_Omni"),
    BT6("BT06", "/wiki/BT-06:_Booster_Double_Diamond"),
    BT7("BT07", "/wiki/BT-07:_Booster_Next_Adventure"),
    BT8("BT08", "/wiki/BT-08:_Booster_New_Awakening"),
    BT9("BT09", "/wiki/BT-09:_Booster_X_Record"),
    BT10("BT10", "/wiki/BT-10:_Booster_Xros_Encounter"),
    BT11("BT11", "/wiki/BT-11:_Booster_Dimensional_Phase"),
    BT12("BT12", "/wiki/BT-12:_Booster_Across_Time"),
    BT13("BT13", "/wiki/BT-13:_Booster_Versus_Royal_Knights"),
    BT14("BT14", "/wiki/BT-14:_Booster_Blast_Ace"),
    BT15("BT15", "/wiki/BT-15:_Booster_Exceed_Apocalypse"),
    BT16("BT16", "/wiki/BT-16:_Booster_Beginning_Observer"),
    BT17("BT17", "/wiki/BT-17:_Booster_Secret_Crisis"),
    BT18("BT18", "/wiki/BT-18:_Booster_Elemental_Successor"),
    RSB2("RSB2.0", "/wiki/BT18-19:_Release_Special_Booster_Ver.2.0"),
    RSB25("RSB2.5", "/wiki/BT19-20:_Release_Special_Booster_Ver.2.5"),
    // -- Theme Boosters --
    EX1("EX1", "/wiki/EX-01:_Theme_Booster_Classic_Collection"),
    EX2("EX2", "/wiki/EX-02:_Theme_Booster_Digital_Hazard"),
    EX3("EX3", "/wiki/EX-03:_Theme_Booster_Draconic_Roar"),
    EX4("EX4", "/wiki/EX-04:_Theme_Booster_Alternative_Being"),
    EX5("EX5", "/wiki/EX-05:_Theme_Booster_Animal_Colosseum"),
    EX6("EX6", "/wiki/EX-06:_Theme_Booster_Infernal_Ascension"),
    EX7("EX7", "/wiki/EX-07:_Extra_Booster_Digimon_Liberator"),
    // -- Resurgence Boost --
    RB1("RB1", "/wiki/RB-01:_Resurgence_Booster"),
    // -- Limited Packs --
    LM1("LM1", "/wiki/LM-01:_Limited_Pack_Digimon_Ghost_Game"),
    LM2("LM2", "/wiki/LM-02:_Limited_Pack_DeathXmon"),
    LM3("LM3", "/wiki/LM-03:_Limited_Card_Set_2024"),
    // -- Starters Deck --
    ST1("ST1", "/wiki/ST-1:_Starter_Deck_Gaia_Red"),
    ST2("ST2", "/wiki/ST-2:_Starter_Deck_Cocytus_Blue"),
    ST3("ST3", "/wiki/ST-3:_Starter_Deck_Heaven%27s_Yellow"),
    ST4("ST4", "/wiki/ST-4:_Starter_Deck_Giga_Green"),
    ST5("ST5", "/wiki/ST-5:_Starter_Deck_Machine_Black"),
    ST6("ST6", "/wiki/ST-6:_Starter_Deck_Venomous_Violet"),
    ST7("ST7", "/wiki/ST-7:_Starter_Deck_Gallantmon"),
    ST8("ST8", "/wiki/ST-8:_Starter_Deck_UlforceVeedramon"),
    ST9("ST9", "/wiki/ST-9:_Starter_Deck_Ultimate_Ancient_Dragon"),
    ST10("ST10", "/wiki/ST-10:_Starter_Deck_Parallel_World_Tactician"),
    ST12("ST12", "/wiki/ST-12:_Starter_Deck_Jesmon"),
    ST13("ST13", "/wiki/ST-13:_Starter_Deck_RagnaLoardmon"),
    ST14("ST14", "/wiki/ST-14:_Advanced_Deck_Set_Beelzemon"),
    ST15("ST15", "/wiki/ST-15:_Starter_Deck_Dragon_of_Courage"),
    ST16("ST16", "/wiki/ST-16:_Starter_Deck_Wolf_of_Friendship"),
    ST17("ST17", "/wiki/ST-17:_Advanced_Deck_Set_Double_Typhoon"),
    ST18("ST18", "/wiki/ST-18:_Starter_Deck_Guardian_Vortex"),
    ST19("ST19", "/wiki/ST-19:_Starter_Deck_Fable_Waltz");

    private final String code;
    private final String path;
}