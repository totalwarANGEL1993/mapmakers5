-- ########################################################################## --
-- #  Internal main mapscript                                               # --
-- #  --------------------------------------------------------------------  # --
-- #    Author:   totalwarANGEL                                             # --
-- ########################################################################## --

QUEST_ASSISTENT_MAP_SETTINGS_DATA

--
-- Setzt die im Assistenten eingestellten diplomatischen Beziehungen für
-- den menschlichen Spieler. Außerdem werden die Namen der Parteien gesetzt.
--
function InitDiplomacy()
    for k, v in pairs(Global_MapConfigurationData.Players) do
        for i= 1, table.getn(v.Diplomacy), 1 do
            if v.Diplomacy[i][1] ~= k then
                Logic.SetDiplomacyState(k, v.Diplomacy[i][1], v.Diplomacy[i][2]);
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
        if v.Color ~= "DEFAULT_COLOR" then
            Display.SetPlayerColorMapping(k, _G[v.Color]);
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
    
    QuestSystemBehavior:PrepareQuestSystem();
    if Global_MapConfigurationData.Debug[1] or Global_MapConfigurationData.Debug[2] or Global_MapConfigurationData.Debug[3] or Global_MapConfigurationData.Debug[4] then
        QuestSystemDebug:Activate(unpack(Global_MapConfigurationData.Debug));
    end
    CreateQuests();
    PreperationDone();
end

--
-- Hier werden die im Assistenten angelegten Quests definiert und gestartet.
--
function CreateQuests()
    QUEST_ASSISTENT_GENERATED_QUESTS
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
