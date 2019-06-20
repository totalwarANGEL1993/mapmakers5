
package twa.siedelwood.s5.mapmaker.service.script;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.map.MapData;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class replaces the tokens inside the internal and external map script
 * with the data from the assistant.
 * 
 * @author totalwarANGEL
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapScriptBuilder
{
	private QuestCollection quests;

	private BriefingCollection briefings;

	private MapData mapData;

	/**
	 * Loads a template and replaces all tokens with the appropriate contents.
	 * 
	 * @param filepath Template to load
	 * @return Lua string
	 */
	public String replaceTokensInMapscript(final String filepath)
	{
		List<String> lines;
		try
		{
			lines = Files.readAllLines(Paths.get(filepath), Charset.forName("UTF-8"));
			final StringBuilder mapScript = new StringBuilder();
			for (int i = 0; i < lines.size(); i++)
			{
				String lineToAppend = lines.get(i);
				lineToAppend = replaceQuestToken(lineToAppend);
				lineToAppend = replaceBriefingToken(lineToAppend);
				lineToAppend = replaceMapSettingsToken(lineToAppend);
				mapScript.append(lineToAppend).append("\n");
			}
			return mapScript.toString();
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Replaces the quest token with the generated briefings.
	 * 
	 * @param lineToAppend Line token is in
	 * @return Lua string
	 */
	private String replaceQuestToken(String lineToAppend)
	{
		if (lineToAppend.contains("QUEST_ASSISTENT_GENERATED_QUESTS"))
		{
			lineToAppend = lineToAppend
				.replaceFirst("QUEST_ASSISTENT_GENERATED_QUESTS", quests.toLua());
		}
		return lineToAppend;
	}

	/**
	 * Replaces the briefing token with the generated quests.
	 * 
	 * @param lineToAppend Line token is in
	 * @return Lua string
	 */
	private String replaceBriefingToken(String lineToAppend)
	{
		if (lineToAppend.contains("QUEST_ASSISTENT_GENERATED_BRIEFINGS"))
		{
			lineToAppend = lineToAppend.replaceFirst("QUEST_ASSISTENT_GENERATED_BRIEFINGS", briefings.toLua());
		}
		return lineToAppend;
	}

	/**
	 *
	 * @param lineToAppend
	 * @return
	 */
	private String replaceMapSettingsToken(String lineToAppend) {
		if (lineToAppend.contains("QUEST_ASSISTENT_MAP_SETTINGS_DATA"))
		{
			lineToAppend = lineToAppend.replaceFirst("QUEST_ASSISTENT_MAP_SETTINGS_DATA", mapData.toLua());
		}
		return lineToAppend;
	}
}
