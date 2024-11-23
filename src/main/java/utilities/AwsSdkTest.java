package utilities;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import java.nio.file.Files;
import java.nio.file.Paths;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class AwsSdkTest {
	public static void main(String[] args) {
//		S3Client s3 = S3Client.builder()
//                .region(Region.US_EAST_1) // Replace with your region
//                .build();
//        System.out.println("AWS SDK is successfully integrated!");
        
        String bucketName = "automatahon-20241";
        String keyName = "folder/subfolder/yourfile.txt"; // File path in the bucket
        String filePath = "yourfile.txt"; // Local file to upload

        // Set up the S3 client
        S3Client s3Client = S3Client.builder()
                .region(Region.EU_NORTH_1) // Replace with your bucket's region
                .build();

        try {
            // Create a local file to upload if it doesn't exist
            if (!Files.exists(Paths.get(filePath))) {
                Files.writeString(Paths.get(filePath), "This is the content of your file.");
            }

            // Upload the file
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));

            System.out.println("File uploaded successfully to S3 bucket: " + bucketName);
        } catch (Exception e) {
            System.err.println("Error occurred while uploading file to S3: " + e.getMessage());
        } finally {
            // Close the S3 client
            s3Client.close();
        }
    }
    }

