package org.example;

import org.example.config.SeleniumSettings;
import org.example.config.InputData;
import org.example.pipelines.RedditCommunities;

public class Main {
    public static void main(String[] args) throws Exception {
        InputData         redditCollectorInputs     = new InputData("C:\\Users\\user\\IdeaProjects\\05.RedditCollector\\src\\main\\java\\org\\example\\config\\RedditCommunitiesConfig.json");
        RedditCommunities redditCommunityCollection = new RedditCommunities(new SeleniumSettings(redditCollectorInputs.driverPath, redditCollectorInputs.maxWaitTime));
        redditCommunityCollection.redditCommunitiesSettingsMethod(redditCollectorInputs.numberOfCommunities, redditCollectorInputs.redditCommunitiesUrl, redditCollectorInputs.outputFilePath, redditCollectorInputs.outputFileName);
        redditCommunityCollection.getData();
        redditCommunityCollection.saveData();
    }
}
