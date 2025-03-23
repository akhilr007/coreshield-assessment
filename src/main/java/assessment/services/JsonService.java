package assessment.services;

import assessment.models.Location;
import assessment.models.MergedData;
import assessment.models.Metadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonService {

    private static final String JSON_PATH = "src/main/java/assessment/data/";
    private static final String LOCATION_FILE = "location.json";
    private static final String METADATA_FILE = "metadata.json";
    private static final String MERGED_DATA_FILE = "mergedData.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Location> locations;
    private final List<Metadata> metadatas;
    private final List<MergedData> mergedDataList = new ArrayList<>();

    private final Map<String, Integer> typeCount = new HashMap<>();
    private final List<Location> incompleteLocations = new ArrayList<>();

    public JsonService() throws IOException {
        var locationFile = new File(JSON_PATH+LOCATION_FILE);
        var metadataFile = new File(JSON_PATH+METADATA_FILE);

        locations = objectMapper.readValue(locationFile, new TypeReference<>() {});
        metadatas = objectMapper.readValue(metadataFile, new TypeReference<>() {});
    }

    public void processJson() {

        var locationMap = new HashMap<String, Location>();
        for(var location : locations) {
            locationMap.put(location.id(), location);
        }

        var processedIds = new HashSet<String>();
        for(var metadata: metadatas) {
            Location location = locationMap.get(metadata.id());
            mergedDataList.add(
                    new MergedData(
                            metadata.id(),
                            location != null ? location.latitude() : null,
                            location != null ? location.longitude() : null,
                            metadata.type(),
                            metadata.rating(),
                            metadata.reviews()
                    )
            );
            processedIds.add(metadata.id());
        }

        for(var location : locations) {
            if(!processedIds.contains(location.id())) {
                mergedDataList.add(
                        new MergedData(
                                location.id(),
                                location.latitude(),
                                location.longitude(),
                                null,
                                null,
                                null
                        )
                );
            }
        }

        try{
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(JSON_PATH+MERGED_DATA_FILE), mergedDataList);
        } catch (IOException e) {
            System.out.println("Error writing merged data to file");
        }

    }

    public void countValidPointsExistPerType() {
        for(var mergedData : mergedDataList) {
            if(mergedData.type() != null) {
                typeCount.put(mergedData.type(), typeCount.getOrDefault(mergedData.type(), 0) + 1);
            }
        }

        System.out.println("TYPE" + " : " + "COUNT");
        for(var type : typeCount.keySet()) {
            System.out.println(type + " : " + typeCount.get(type));
        }
    }

    public void calculateAverageRatingPerType() {
        Map<String, Double> typeRating = new HashMap<>();
        for(var mergedData : mergedDataList) {
            if(mergedData.type() != null && mergedData.rating() != null) {
                typeRating.put(
                        mergedData.type(),
                        typeRating.getOrDefault(mergedData.type(), 0.0) + mergedData.rating());
            }
        }

        System.out.println("TYPE" + " : " + "AVERAGE RATING");
        for(var type : typeRating.keySet()) {
            System.out.println(type + " : " + String.format("%.2f", typeRating.get(type)/typeCount.get(type)));
        }
    }

    public void calculateLocationWithHighestReviews() {
        Optional<MergedData> highestReviewLocation = mergedDataList.stream()
                .filter(mergedData -> mergedData.reviews() != null)
                .max(Comparator.comparing(MergedData::reviews));

        if (highestReviewLocation.isPresent()) {
            MergedData mergedData = highestReviewLocation.get();
            System.out.println("Location with highest reviews: ");
            System.out.println("ID: " + mergedData.id());
            System.out.println("Latitude: " + mergedData.latitude());
            System.out.println("Longitude: " + mergedData.longitude());
            System.out.println("Type: " + mergedData.type());
            System.out.println("Rating: " + mergedData.rating());
            System.out.println("Reviews: " + mergedData.reviews());
        } else {
            System.out.println("No locations with reviews found.");
        }
    }

    public void calculateLocationWithIncompleteData() {
        for(var mergedData : mergedDataList) {
            if(mergedData.type() == null || mergedData.rating() == null || mergedData.reviews() == null) {
                incompleteLocations.add(new Location(mergedData.id(), mergedData.latitude(), mergedData.longitude()));
            }
        }


        if(incompleteLocations.isEmpty()) {
            System.out.println("No incomplete data found for any location");
            return;
        }

        System.out.println("Locations with incomplete data: ");
        for(var location : incompleteLocations) {
            System.out.println("ID: " + location.id());
            System.out.println("Latitude: " + location.latitude());
            System.out.println("Longitude: " + location.longitude());
        }
    }

}
