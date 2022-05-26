import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.spi.TypeLiteral;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class ExampleTest {

    @Test
    void testCsvParse() throws Exception {
        //Test that we've set up the gradle project properly and can use the CSV library
        ClassLoader classLoader = getClass().getClassLoader();
        File testCSVFile = new File(classLoader.getResource("test.csv").getFile());
        Reader in = new FileReader(testCSVFile);
        int totalRecords = 0;
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader("GPA","Gender","breakfast","coffee","exercise","drink","comfort_food","sports","weight")
                .setSkipHeaderRecord(true)
                .build();
        for (CSVRecord record : format.parse(in)) {
            String gpa = record.get("GPA");
            totalRecords++;
        }
        Assertions.assertEquals(124, totalRecords);
    }

    @Test
    void testJsonParse() throws Exception {
        //Test that we've set up the gradle project properly and can use the Jackson library
        ClassLoader classLoader = getClass().getClassLoader();
        File testJSONFile = new File(classLoader.getResource("test.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> data = mapper.readValue(testJSONFile, new TypeReference<List<Map<String, Object>>>(){});
        Assertions.assertEquals(3, data.size());
        Assertions.assertEquals("3.654", data.get(1).get("GPA"));
        Assertions.assertEquals("1", data.get(1).get("Gender"));
        Assertions.assertEquals("1", data.get(1).get("breakfast"));
        Assertions.assertEquals("2", data.get(1).get("coffee"));
        Assertions.assertEquals("1", data.get(1).get("exercise"));
        Assertions.assertEquals("2", data.get(1).get("drink"));
        Assertions.assertEquals("chocolate, chips, ice cream", data.get(1).get("comfort_food"));
        Assertions.assertEquals("1", data.get(1).get("sports"));
        Assertions.assertEquals("155", data.get(1).get("weight"));
    }

    @Test
    void testJsonParse2() throws Exception {
        //Test that we've set up the gradle project properly and can use the Jackson library
        ClassLoader classLoader = getClass().getClassLoader();
        File testJSONFile = new File(classLoader.getResource("test.json").getFile());
        String fileContent = Files.readString(testJSONFile.toPath());
        Any data = JsonIterator.deserialize(fileContent);
        Assertions.assertEquals(3, data.size());
        Assertions.assertEquals("3.654", data.get(1).toString("GPA"));
        Assertions.assertEquals("1", data.get(1).toString("Gender"));
        Assertions.assertEquals("1", data.get(1).toString("breakfast"));
        Assertions.assertEquals("2", data.get(1).toString("coffee"));
        Assertions.assertEquals("1", data.get(1).toString("exercise"));
        Assertions.assertEquals("2", data.get(1).toString("drink"));
        Assertions.assertEquals("chocolate, chips, ice cream", data.get(1).toString("comfort_food"));
        Assertions.assertEquals("1", data.get(1).toString("sports"));
        Assertions.assertEquals("155", data.get(1).toString("weight"));
    }

    @Test
    void testJsonParse3() throws Exception {
        String json = """
            [
                {
                    "key1": 1,
                    "key2": 2
                },
                {
                    "key3": 3
                }
            ]
        """;
        List<Map<String, Integer>> parsed;
        try (JsonIterator jsonIterator = JsonIterator.parse(json)) {
            parsed = jsonIterator.read(new TypeLiteral<>() {});
        }
        Assertions.assertEquals(2, parsed.size());
        Assertions.assertEquals(2, parsed.get(0).size());
        Assertions.assertEquals(1, parsed.get(0).get("key1"));
        Assertions.assertEquals(2, parsed.get(0).get("key2"));
        Assertions.assertEquals(1, parsed.get(1).size());
        Assertions.assertEquals(3, parsed.get(1).get("key3"));
    }

}

