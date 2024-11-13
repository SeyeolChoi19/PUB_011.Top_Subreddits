package org.example.pipelines;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import org.example.config.SeleniumSettings;

public class RedditCommunities {
    public int          numberOfCommunities;
    public String       currentDate, redditCommunitiesUrl, outputFilePath, outputFileName;
    public List<String> dictKeys;

    public SeleniumSettings seleniumObject;
    public JSONObject       outputDataDict;

    public RedditCommunities(SeleniumSettings seleniumObject) {
        this.currentDate    = String.valueOf(LocalDate.now());
        this.seleniumObject = seleniumObject;
        seleniumObject.driverSettings();
    }

    public void redditCommunitiesSettingsMethod(int numberOfCommunities, String redditCommunitiesUrl, String outputFilePath, String outputFileName) {
        this.numberOfCommunities  = numberOfCommunities;
        this.redditCommunitiesUrl = redditCommunitiesUrl;
        this.outputFilePath       = outputFilePath;
        this.outputFileName       = outputFileName;
        this.dictKeys             = Arrays.asList(new String[] {"extraction_date", "subreddit_name", "subreddit_theme", "subreddit_members", "subreddit_url"});
        this.outputDataDict       = new JSONObject();

        for (String dictKey : dictKeys) {
            outputDataDict.put(dictKey, new JSONArray());
        }
    }

    private void saveSubredditData(List<String> subredditData) {
        ArrayList<String> resultsList = new ArrayList<>();
        resultsList.add(currentDate);
        resultsList.addAll(subredditData);
        resultsList.add(String.format("https://www.reddit.com/%s", subredditData.getFirst()));

        for (int index = 0; index < dictKeys.size(); index++) {
            outputDataDict.getJSONArray(dictKeys.get(index)).put(resultsList.get(index));
        }
    }

    public void getData() throws Exception {
        List<WebElement> elementsList;
        List<String>     subredditData;
        int              pageNumber = 1;
        int              subNumber  = 0;

        while (subNumber < numberOfCommunities) {
            seleniumObject.driver.get(String.format(redditCommunitiesUrl, pageNumber));
            elementsList = seleniumObject.searchForElements(".flex.flex-col.flex-grow.items-start", "css");
            pageNumber  += 1;

            for (WebElement element : elementsList) {
                subredditData = Arrays.asList(element.getText().split("\n"));
                subNumber    += 1;
                saveSubredditData(subredditData);

                if (subNumber >= numberOfCommunities) {
                    break;
                }
            }
        }
    }

    public void saveData() throws IOException {
        String     saveFileName = String.join(File.separator, outputFilePath, String.format("%s %s", currentDate, outputFileName));
        FileWriter writerObject = new FileWriter(saveFileName);
        writerObject.write(outputDataDict.toString(4));
        writerObject.close();
    }
}
