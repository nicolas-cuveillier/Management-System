import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


/**<h1>DBConnector</h1>
 * implement the concept of connector and handler for database actions
 * @author Nicolas Cuveillier
 */
public final class DBConnector {

    private final MongoClient mongoClient;
    private final String dbName;
    private final String ivParam = "Bar12345Bar12345"; // 128 bit key
    private final String keySpec = "ThisIsASecretKet";

    /**
     * construct a DBConnector from the database password
     * @param uri uri of the database
     * @param password password of the database
     * @param dbName name of the database
     */
    public DBConnector(String uri, String password, String dbName) {
        this.dbName = dbName;
        mongoClient = MongoClients.create(String.format(uri, password));
    }

    /**
     * retrieve student information and create a new student from it
     * @param uniqueId unique id of the student
     * @param password password of the student
     * @return the corresponding student
     */
    public Student fetchStudentInfo(int uniqueId, String password){
        MongoDatabase db = mongoClient.getDatabase(dbName);

        MongoCollection<Document> collection = db.getCollection("Students");
        //System.out.println(new String(encrypt(password))); testing
        Bson comparison = and(eq("uniqueId", uniqueId), eq("password.$binary.base64", new String(encrypt(password))));
        //TODO password encrypted comparison not working
        Document doc = collection.find(comparison).first();

        if (doc != null) {
            JSONObject o = new JSONObject(doc.toJson());
            List<Course> courses = new ArrayList<>();
            JSONArray array = o.getJSONArray("courses");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                courses.add(new Course(CourseType.fromString(obj.getString("type")),
                        obj.getString("name"), obj.getInt("price")));
            }


            return Student.fromLogin(o.getInt("uniqueId"),
                    Integer.parseInt(
                            decrypt(o.getJSONObject("age").getJSONObject("$binary").getString("base64").getBytes(StandardCharsets.UTF_8))),
                    decrypt(o.getJSONObject("name").getJSONObject("$binary").getString("base64").getBytes(StandardCharsets.UTF_8)),
                    decrypt(o.getJSONObject("mail").getJSONObject("$binary").getString("base64").getBytes(StandardCharsets.UTF_8)),courses, password);
        }
        return null;
    }

    /**
     * store student information in the database with an upsert method
     * @param student instance of the student to upsert
     * @param password password of the student to upsert
     */
    public void storeStudentInfo(Student student, String password){
        MongoDatabase db = mongoClient.getDatabase(dbName);

        MongoCollection<Document> collection = db.getCollection("Students");
        System.out.println(student.courses());

        Bson filter = eq("uniqueId", student.uniqueId());
        Bson update = Updates.combine(Updates.set("name", encrypt(student.name())),
                Updates.set("age", encrypt(String.valueOf(student.age()))),
                Updates.set("mail", encrypt(student.mail())),
                Updates.set("password", encrypt(password)));
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(filter, update, options);

        // update the list of courses //TODO issue to add courses
        Document doc = collection.find(filter).first();
        Document docUpdated = collection.find(filter).first();
        if (doc != null && docUpdated != null) {
            docUpdated.remove("courses");
            docUpdated.append("courses", student.courses());
            collection.findOneAndReplace(doc, docUpdated);
        }
    }

    /**
     * fetch all the courses that are in the database
     * @return the list of courses
     */
    public List<Course> fetchCourses(){
        MongoDatabase db = mongoClient.getDatabase(dbName);

        MongoCollection<Document> collection = db.getCollection("Courses");

        List<Course> courses = new ArrayList<>();
        for (Document doc:collection.find()) {
            JSONObject obj = new JSONObject(doc.toJson());
            courses.add(
                    new Course(CourseType.fromString(obj.getString("type")),
                            obj.getString("name"),
                            obj.getInt("price")));

        }
        return courses;
    }

    public byte[] encrypt(String s){

        try {
            IvParameterSpec iv = new IvParameterSpec(ivParam.getBytes(StandardCharsets.UTF_8));

            SecretKeySpec secretKeySpec = new SecretKeySpec(keySpec.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(s.getBytes());
            return Base64.getEncoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] encrypted){
        try {
            IvParameterSpec iv = new IvParameterSpec(ivParam.getBytes(StandardCharsets.UTF_8));

            SecretKeySpec skeySpec = new SecretKeySpec(keySpec.getBytes(StandardCharsets.UTF_8),
                    "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
