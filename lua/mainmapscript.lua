-- ########################################################################## --
-- #  Internal main mapscript                                               # --
-- #  --------------------------------------------------------------------  # --
-- #    Author:   totalwarANGEL                                             # --
-- ########################################################################## --

gvWeatherGenerator = {
    ["SetupNormalWeatherGfxSet"]    = {RainProp = 55, SnowProp = 60},
    ["SetupHighlandWeatherGfxSet"]  = {RainProp = 70, SnowProp = 70},
    ["SetupMediteranWeatherGfxSet"] = {RainProp = 30, SnowProp = 10},
    ["SetupEvelanceWeatherGfxSet"]  = {RainProp = 30, SnowProp = 10},
    ["SetupSteppeWeatherGfxSet"]    = {RainProp = 15, SnowProp = 0},
    ["SetupMoorWeatherGfxSet"]      = {RainProp = 85, SnowProp = 0},

    MaxDuration  = 120,
    MinDuration  = 30,
    StatesAmount = 30,
    LastState    = 1;
};

gvPlayerColorMapping = {
    ["DEFAULT_COLOR"] = -1,
    ["PLAYER_COLOR"] = 1,
    ["NEPHILIM_COLOR"] = 2,
    ["FRIENDLY_COLOR1"] = 3,
    ["FRIENDLY_COLOR2"] = 4,
    ["ENEMY_COLOR2"] = 5,
    ["MERCENARY_COLOR"] = 6,
    ["ENEMY_COLOR3"] = 7,
    ["FARMER_COLOR"] = 8,
    ["EVIL_GOVERNOR_COLOR"] = 9,
    ["TRADER_COLOR"] = 10,
    ["NPC_COLOR"] = 11,
    ["KERBEROS_COLOR"] = 12,
    ["ENEMY_COLOR1"] = 13,
    ["ROBBERS_COLOR"] = 14,
    ["SAINT_COLOR"] = 15,
    ["FRIENDLY_COLOR3"] = 16,
};

QUEST_ASSISTENT_MAP_SETTINGS_DATA

--
-- Setzt die im Assistenten eingestellten diplomatischen Beziehungen für
-- den menschlichen Spieler. Außerdem werden die Namen der Parteien gesetzt.
--
function InitDiplomacy()
    for k, v in pairs(Global_MapConfigurationData.Players) do
        for i= 1, table.getn(v.Diplomacy), 1 do
            if v.Diplomacy[i][1] ~= k then
                local DiplomacyStateName = "Neutral";
                if (v.Diplomacy[i][2] == 2) then
                    DiplomacyStateName = "Friendly";
                elseif (v.Diplomacy[i][2] == 3) then
                    DiplomacyStateName = "Hostile";
                end
                _G["Set" ..DiplomacyStateName](k, v.Diplomacy[i][1]);
            end
        end
        if v.Name ~= "" then
            SetPlayerName(k, v.Name);
        end
    end
end

--
-- Gibt dem menschlichen Spieler die eingestellten Startrohstoffe.
--
function InitResources()
    Tools.GiveResouces(gvMission.PlayerID, unpack(Global_MapConfigurationData.Resources));
end

--
-- Verbietet alle im Assistenten eingestellten Technologien.
--
function InitTechnologies()
end

--
-- Ruft das im Assistenten eingestellte Wetterset auf.
--
function InitWeatherGfxSets()
	_G[Global_MapConfigurationData.WeatherSet]();
end

--
-- Setzt die Spielerfarben der Parteien fest.
--
function InitPlayerColorMapping()
    for k, v in pairs(Global_MapConfigurationData.Players) do
        if gvPlayerColorMapping[v.Color] and gvPlayerColorMapping[v.Color] ~= -1 then
            Display.SetPlayerColorMapping(k, gvPlayerColorMapping[v.Color]);
        end
    end
end

--
-- Erzeugt zufälliges Wetter für das ausgewählte Wetterset.
--
function GenerateWeather()
    if Global_MapConfigurationData.RandomizeWeather then
        local WeatherData = gvWeatherGenerator[Global_MapConfigurationData.WeatherSet];
        for i= 1, gvWeatherGenerator.StatesAmount, 1 do
            local Length = math.random(gvWeatherGenerator.MinDuration, gvWeatherGenerator.MaxDuration);
            if i == 1 then
                gvWeatherGenerator.LastState = 1;
                AddPeriodicSummer(Length);
            else
                if WeatherData.RainProp > 0 and math.random(1, 100) <= WeatherData.RainProp then
                    if WeatherData.SnowProp > 0 and gvWeatherGenerator.LastState > 1 and math.random(1, 100) <= WeatherData.SnowProp then
                        gvWeatherGenerator.LastState = 3;
                        AddPeriodicWinter(Length);
                    else
                        gvWeatherGenerator.LastState = 2;
                        AddPeriodicRain(Length);
                    end
                else
                    gvWeatherGenerator.LastState = 1;
                    AddPeriodicSummer(Length);
                end
            end
        end
    end
end

--
-- Startet alle Prozesse, die zu Beginn der Mission aktiviert werden müssen.
--
function FirstMapAction()
    Score.Player[0] = {};
	Score.Player[0]["buildings"] = 0;
    Score.Player[0]["all"] = 0;

    math.randomseed(XGUIEng.GetSystemTime());
    GenerateWeather();

    QuestSystemBehavior:PrepareQuestSystem();
    if Global_MapConfigurationData.Debug[1] or Global_MapConfigurationData.Debug[2] or Global_MapConfigurationData.Debug[3] or Global_MapConfigurationData.Debug[4] then
        QuestSystemDebug:Activate(unpack(Global_MapConfigurationData.Debug));
    end
    CreateQuests();
    PreperationDone();
end

QUEST_ASSISTENT_GENERATED_QUESTS

--
-- Hier werden die im Assistenten angelegten Quests definiert und gestartet.
--
function CreateQuests()
    for k, v in pairs(Global_QuestsToGenerate) do
        local Behaviors = {}
        for i= 1, table.getn(v), 1 do
            local CurrentBehaviorData = copy(v[i]);
            local CurrentBehaviorName = table.remove(CurrentBehaviorData, 1);
            table.insert(Behaviors, _G[CurrentBehaviorName](unpack(CurrentBehaviorData)));
        end

        local Description;
        if v.Visible then
            Description = {
                Title = v.Title,
                Text  = v.Text,
                Type  = _G[v.Type],
                Info  = 1
            }
        end

        CreateQuest {
            Name        = v.Name,
            Description = Description,
            Receiver    = v.Receiver,
            Time        = v.Time,

            unpack(Behaviors)
        };
    end
end

-- -------------------------------------------------------------------------- --

QUEST_ASSISTENT_GENERATED_BRIEFINGS
