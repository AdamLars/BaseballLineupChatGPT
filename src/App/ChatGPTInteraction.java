package App;
import java.net.URI;
import java.net.http.*;
import java.io.BufferedReader;
import java.io.*;

public class ChatGPTInteraction {
    //String of positions with possible abbreviations in case ChatGPT gives back different responses.
    private String[] positions = {"C ", "C:", "C-", "CATCHER", "1ST BASEMAN", "1B", "FIRST BASEMAN", "1ST BASE", "2ND BASEMAN", "2B",
    "SECOND BASEMAN", "2ND BASE", "3RD BASEMAN", "3B", "THIRD BASEMAN", "3RD BASE", "SHORTSTOP", "SS", "SHORT STOP", "SHORT",
    "LEFT FIELD", "LF", "LEFTFIELD", "LEFT", "CENTER FIELD", "CF", "CENTERFIELD", "CENTER", "RIGHT FIELD", "RF", "RIGHTFIELD",
    "RIGHT", "DESIGNATED HITTER", "DH", "DESIGNATEDHITTER", "DESIGNATED"};

    //Best representation of positions. 4 positions for every 1 cleaned position.
    private String[] cleanedPositionArray = {"Catcher" , "1st Base", "2nd Base", "3rd Base", "Shortstop", "Left Field", 
    "Center Field", "Right Field", "Designated Hitter"};

    //Start of query to pass to ChatGPT
    private String slashLinesPrompt = "What would be the best batting order for players based on these slashlines? ";

    //Lineup that ChatGPT creates
    private static String[] aiLineUp = new String[9];

    //Tries to connect to ChatGPT and pass in slashlines
    public ChatGPTInteraction(String[] stats)
    {
        //Each position on the cleanedPositionArray array will be associated to 3 numbers from stats so divide by 3
        for(int i = 0; i < stats.length; i+=3)
            slashLinesPrompt += cleanedPositionArray[i/3] + ": " + stats[i] + "/" + stats[i+1] + "/" + stats[i+2] + ", ";
        try{
            chatGPT();
        }
        catch(Exception s){
            DataEntryPanel.setErrorLabel("Error: Problem connecting to ChatGPT");
        }
    }

    //connecting to the internet and to ChatGPT and giving it the prompt
    private void chatGPT() throws Exception
    {
        //Using functions found here. I am using the "Completions" part of the guide. https://platform.openai.com/docs/api-reference/completions
        String input = "\n{\n\"model\": \"text-davinci-003\",\n\"prompt\": \"" + slashLinesPrompt + "\",\n\"temperature\": 0,\n\"max_tokens\": 2048\n}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + getAPIKey())
            .POST(HttpRequest.BodyPublishers.ofString(input))
            .build();

        //Sends request to OpenAI and receives a response. 
        HttpClient client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        if(response.body().length() < 1)
            DataEntryPanel.setErrorLabel("Problem connecting to ChatGPT");
        else
            parseResponse(response.body());
    }

    //Breaks down the string to the response and adds it to the AILineup array
    private void parseResponse(String response)
    {
        //truncates the responses so only the important part is still around
        response = response.substring(response.indexOf("\\n\\n") + 4, response.indexOf("\"index\"")).toUpperCase();
        System.out.println(response);
        //Loops through all 9 positions and then sees if anything from positions array is found there.
        //Adds it to the AILineup array.
        for(int i = 0; i < cleanedPositionArray.length; i++)
        {
            int smallestIndex = 3000;
            int lengthOfPosition = 0;
            boolean positionFound = false;
            //Loops through all of the potential listed positions I created, if none are found then there will be an error message
            //that appears.
            for(int j = 0; j < positions.length; j++)
                if(smallestIndex > response.indexOf(positions[j]) && response.contains(positions[j]))
                {
                    //cleanedPositionArray is j/4 because there are 4 possible abbreviations for every cleaned version
                    positionFound = true;
                    aiLineUp[i] = cleanedPositionArray[j/4];
                    lengthOfPosition = positions[j].length();
                    smallestIndex = response.indexOf(positions[j]);
                }
            if(!positionFound)
            {
                DataEntryPanel.setErrorLabel("Error: ChatGPT output was not understandable.");
                break;
            }
            response = response.substring(smallestIndex + lengthOfPosition);
        }
    }

    //Pulls private API key from APIkey.txt. This key is not meant to be shared so the .txt file is empty in Github
    //Get your own API key from here https://platform.openai.com/account/api-keys
    private String getAPIKey()
    {
        String key = "";
        try{
            InputStream inputUrl = getClass().getResourceAsStream("APIkey.txt");
            InputStreamReader file = new InputStreamReader(inputUrl);
            BufferedReader line = new BufferedReader(file);
            key = line.readLine();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(key);
        return key;
    }

    //AiLineup array getter
    public static String[] getAiLineup()
    {
        return aiLineUp;
    }
}
