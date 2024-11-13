package org.example.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

public class InputData {
    public String driverPath, redditCommunitiesUrl, outputFilePath, outputFileName;
    public int    maxWaitTime, numberOfCommunities;

    public JSONObject configDict;

    public InputData(String configFileName) throws IOException {
        this.configDict = new JSONObject(new String(Files.readAllBytes(Path.of(configFileName))));
        getConstructorVariables();
        getSettingsVariables();
    }

    public void getConstructorVariables() {
        JSONObject subJson = configDict.getJSONObject("RedditCommunities").getJSONObject("constructor");
        this.driverPath    = subJson.getString("driverPath");
        this.maxWaitTime   = subJson.getInt("maxWaitTime");
    }

    public void getSettingsVariables() {
        JSONObject subJson        = configDict.getJSONObject("RedditCommunities").getJSONObject("redditCommunitiesSettingsMethod");
        this.numberOfCommunities  = subJson.getInt("numberOfCommunities");
        this.redditCommunitiesUrl = subJson.getString("redditCommunitiesUrl");
        this.outputFilePath       = subJson.getString("outputFilePath");
        this.outputFileName       = subJson.getString("outputFileName");
    }
}
