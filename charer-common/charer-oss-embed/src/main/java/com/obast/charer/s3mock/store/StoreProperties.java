package com.obast.charer.s3mock.store;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("s3mock.domain") //TODO: wrong package.
public class StoreProperties {
    // True if files should be retained when S3Mock exits gracefully.
    // False to let S3Mock delete all files when S3Mock exits gracefully.
    private boolean retainFilesOnExit = true;
    // The root directory to use. If omitted a default temp-dir will be used.
    private String root="data/iot-oss";
    private Set<String> validKmsKeys = new HashSet<>();
    // A comma separated list of buckets that are to be created at startup.
    private List<String> initialBuckets = List.of("iot");

}
