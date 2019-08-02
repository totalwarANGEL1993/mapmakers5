-- Include globals
Script.Load("data/script/maptools/main.lua");
Script.Load("data/script/maptools/mapeditortools.lua");

-- Load library
gvBasePath = "data/maps/externalMap/";
Script.Load(gvBasePath.. "qsb/oop.lua");
Script.Load(gvBasePath.. "qsb/questsystem.lua");
Script.Load(gvBasePath.. "qsb/questdebug.lua");
Script.Load(gvBasePath.. "qsb/interaction.lua");
Script.Load(gvBasePath.. "qsb/questbehavior.lua");
Script.Load(gvBasePath.. "qsb/extraloader.lua");

-- Include main mapscript
Script.Load(gvBasePath.. "behaviors.lua");
Script.Load(gvBasePath.. "mainmapscript.lua");

--
-- Füge in dieser Funktion deine eigenen Inhalte hinzu. Diese Funktion wird
-- aufgerufen, sobald alle Voreinstellungen gemacht wurden.
--
function PreperationDone()

end

-- Platz für deine Funktionen...

