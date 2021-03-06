package tannv.app.controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tannv.app.ToolsApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nguyen.van.tan on 9/3/16.
 */
@Controller
public class GoogleSheetController {


    private static final String APPLICATION_NAME =
            "Google Sheets API Java Quickstart";


    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");
    private static final String LATE_COLMN = "Late" ;

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                ToolsApplication.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     *
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    @RequestMapping("/read-from-google")
    public String getFromGoogle(Model model) {
        // Build a new authorized API client service.
        try {

            Sheets service = getSheetsService();

            // Prints the names and majors of students in a sample spreadsheet:
            // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
            String spreadsheetId = "1PXCc0Sgk8ba1Vr5yH8n6EgMt2qn4c11WPIQh0Fk3l20";
            String range = "Sep2016!A1:O";
            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();

            List<List<Object>> values = response.getValues();
            Map<String,List<List<String>>> mapbyStaff=new HashMap<>() ;
            List<String> lefFromTo;
            for (List<Object> objects: values ) {
                 lefFromTo=new ArrayList<>();
                Boolean isAprove;
                if(objects.size()>=15) {
                    isAprove=(objects.get(12).equals(objects.get(13)) && objects.get(13).equals(objects.get(14) ) && objects.get(14).equals("Approve"));
                }else
                {
                    isAprove=false;
                }
                lefFromTo.add((String)objects.get(1));
                lefFromTo.add((String)objects.get(2)) ;
                lefFromTo.add((String)objects.get(3)) ;
                lefFromTo.add((String)objects.get(11)) ;
                lefFromTo.add(isAprove.toString()) ;

                String key = (String) objects.get(1);
                List<List<String>> currentValues=  mapbyStaff.get(key);
                if(currentValues ==null)
                {
                    currentValues=new ArrayList<>();
                    currentValues.add(lefFromTo);
                }else
                {
                    currentValues.add(lefFromTo);
                }
                mapbyStaff.put(key,currentValues);

            }



            if (values == null || values.size() == 0) {
                System.out.println("No data found.");
            } else {
                System.out.println("Name, Major");

            }

            model.addAttribute("map",mapbyStaff);


        } catch (IOException io) {
        }

        return "google/index";
    }


}
