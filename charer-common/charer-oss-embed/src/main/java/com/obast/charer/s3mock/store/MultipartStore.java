/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.s3mock.store;

import static com.obast.charer.s3mock.util.DigestUtil.hexDigest;
import static java.nio.file.Files.newDirectoryStream;
import static java.nio.file.Files.newOutputStream;
import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.obast.charer.s3mock.util.AwsHttpHeaders;
import com.obast.charer.s3mock.util.DigestUtil;
import com.obast.charer.s3mock.dto.ChecksumAlgorithm;
import com.obast.charer.s3mock.dto.CompletedPart;
import com.obast.charer.s3mock.dto.MultipartUpload;
import com.obast.charer.s3mock.dto.Owner;
import com.obast.charer.s3mock.dto.Part;
import com.obast.charer.s3mock.dto.StorageClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BoundedInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRange;

/**
 * Stores parts and their metadata created in S3Mock.
 */
public class MultipartStore {
    private static final Logger LOG = LoggerFactory.getLogger(MultipartStore.class);
    private static final String PART_SUFFIX = ".part";
    private final Map<String, MultipartUploadInfo> uploadIdToInfo = new ConcurrentHashMap<>();

    private final boolean retainFilesOnExit;
    private final ObjectStore objectStore;

    public MultipartStore(boolean retainFilesOnExit, ObjectStore objectStore) {
        this.retainFilesOnExit = retainFilesOnExit;
        this.objectStore = objectStore;
    }

    /**
     * Prepares everything to store an object uploaded as multipart upload.
     *
     * @param bucket       Bucket to upload object in
     * @param key          object to upload
     * @param id           ID of the object
     * @param contentType  the content type
     * @param storeHeaders various headers to store
     * @param uploadId     id of the upload
     * @param owner        owner of the upload
     * @param initiator    initiator of the upload
     * @param userMetadata custom metadata
     * @return upload result
     */
    public MultipartUpload prepareMultipartUpload(BucketMetadata bucket,
                                                  String key,
                                                  UUID id,
                                                  String contentType,
                                                  Map<String, String> storeHeaders,
                                                  String uploadId,
                                                  Owner owner,
                                                  Owner initiator,
                                                  Map<String, String> userMetadata,
                                                  Map<String, String> encryptionHeaders,
                                                  StorageClass storageClass,
                                                  String checksum,
                                                  ChecksumAlgorithm checksumAlgorithm) {
        if (!createPartsFolder(bucket, id, uploadId)) {
            LOG.error("Directories for storing multipart uploads couldn't be created. bucket={}, key={}, "
                    + "id={}, uploadId={}", bucket, key, id, uploadId);
            throw new IllegalStateException(
                    "Directories for storing multipart uploads couldn't be created.");
        }
        var upload = new MultipartUpload(key, uploadId, owner, initiator, storageClass, new Date());
        uploadIdToInfo.put(uploadId, new MultipartUploadInfo(upload,
                contentType,
                userMetadata,
                storeHeaders,
                encryptionHeaders,
                bucket.getName(),
                storageClass,
                checksum,
                checksumAlgorithm)
        );

        return upload;
    }

    /**
     * Lists all not-yet completed parts of multipart uploads in a bucket.
     *
     * @param bucketName the bucket to use as a filter
     * @param prefix     the prefix use as a filter
     * @return the list of not-yet completed multipart uploads.
     */
    public List<MultipartUpload> listMultipartUploads(String bucketName, String prefix) {
        return uploadIdToInfo.values()
                .stream()
                .filter(info -> bucketName == null || bucketName.equals(info.getBucket()))
                .map(MultipartUploadInfo::getUpload)
                .filter(upload -> isBlank(prefix) || upload.getKey().startsWith(prefix))
                .collect(Collectors.toList());
    }

    /**
     * Get MultipartUpload, if it was not completed.
     *
     * @param uploadId id of the upload
     * @return the multipart upload, if it exists, throws IllegalArgumentException otherwise.
     */
    public MultipartUpload getMultipartUpload(String uploadId) {
        return uploadIdToInfo.values()
                .stream()
                .map(MultipartUploadInfo::getUpload)
                .filter(upload -> uploadId.equals(upload.getUploadId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MultipartUpload found with uploadId: "
                        + uploadId));
    }

    /**
     * Aborts the upload.
     *
     * @param bucket   to which was uploaded
     * @param id       of the object
     * @param uploadId of the upload
     */
    public void abortMultipartUpload(BucketMetadata bucket, UUID id, String uploadId) {
        synchronizedUpload(uploadId, uploadInfo -> {
            try {
                var partFolder = getPartsFolderPath(bucket, id, uploadId).toFile();
                FileUtils.deleteDirectory(partFolder);

                //TODO: should be in ObjectStore, we must synchronize on Object ID as well.
                var dataFile = objectStore.getDataFilePath(bucket, id).toFile();
                FileUtils.deleteQuietly(dataFile);

                uploadIdToInfo.remove(uploadId);
                return null;
            } catch (IOException e) {
                throw new IllegalStateException(String.format(
                        "Could not delete multipart upload tmp data. bucket=%s, id=%s, uploadId=%s",
                        bucket, id, uploadId), e);
            }
        });
    }

    /**
     * Uploads a part of a multipart upload.
     *
     * @param bucket                        in which to upload
     * @param id                            of the object to upload
     * @param uploadId                      id of the upload
     * @param partNumber                    number of the part to store
     * @param inputStream                   file data to be stored
     * @param useV4ChunkedWithSigningFormat If {@code true}, V4-style signing is enabled.
     * @return the md5 digest of this part
     */
    public String putPart(BucketMetadata bucket,
                          UUID id,
                          String uploadId,
                          String partNumber,
                          InputStream inputStream,
                          boolean useV4ChunkedWithSigningFormat,
                          Map<String, String> encryptionHeaders) {
        var file = objectStore.inputStreamToFile(
                objectStore.wrapStream(inputStream, useV4ChunkedWithSigningFormat, false),
                getPartPath(bucket, id, uploadId, partNumber)
        );

        return hexDigest(encryptionHeaders.get(AwsHttpHeaders.X_AMZ_SERVER_SIDE_ENCRYPTION_AWS_KMS_KEY_ID), file);
    }

    /**
     * Completes a Multipart Upload for the given ID.
     *
     * @param bucket   in which to upload.
     * @param key      of the object to upload.
     * @param id       id of the object
     * @param uploadId id of the upload.
     * @param parts    to concatenate.
     * @return etag of the uploaded file.
     */
    public String completeMultipartUpload(BucketMetadata bucket, String key, UUID id,
                                          String uploadId, List<CompletedPart> parts, Map<String, String> encryptionHeaders) {
        return synchronizedUpload(uploadId, uploadInfo -> {
            var partFolder = getPartsFolderPath(bucket, id, uploadId);
            var partsPaths =
                    parts
                            .stream()
                            .map(part ->
                                    Paths.get(partFolder.toString(), part.getPartNumber() + PART_SUFFIX)
                            )
                            .collect(Collectors.toList());

            try (var inputStream = toInputStream(partsPaths)) {
                var etag = DigestUtil.hexDigestMultipart(partsPaths);
                objectStore.storeS3ObjectMetadata(bucket,
                        id,
                        key,
                        uploadInfo.getContentType(),
                        uploadInfo.getStoreHeaders(),
                        inputStream,
                        false, //TODO: no signing?
                        uploadInfo.getUserMetadata(),
                        encryptionHeaders,
                        etag,
                        Collections.emptyList(), //TODO: no tags for multi part uploads?
                        uploadInfo.getChecksumAlgorithm(),
                        uploadInfo.getChecksum(),
                        uploadInfo.getUpload().getOwner(),
                        uploadInfo.getStorageClass()
                );
                uploadIdToInfo.remove(uploadId);
                FileUtils.deleteDirectory(partFolder.toFile());
                return etag;
            } catch (IOException e) {
                throw new IllegalStateException(String.format(
                        "Error finishing multipart upload bucket=%s, key=%s, id=%s, uploadId=%s",
                        bucket, key, id, uploadId), e);
            }
        });
    }

    /**
     * Get all multipart upload parts.
     *
     * @param bucket   name of the bucket
     * @param id       object ID
     * @param uploadId upload identifier
     * @return List of Parts
     */
    public List<Part> getMultipartUploadParts(BucketMetadata bucket, UUID id, String uploadId) {
        var partsPath = getPartsFolderPath(bucket, id, uploadId);
        try (var directoryStream = newDirectoryStream(partsPath,
                path -> path.getFileName().toString().endsWith(PART_SUFFIX))) {
            return StreamSupport.stream(directoryStream.spliterator(), false)
                    .map(path -> {
                        String name = path.getFileName().toString();
                        String prefix = name.substring(0, name.indexOf('.'));
                        int partNumber = Integer.parseInt(prefix);
                        String partMd5 = DigestUtil.hexDigest(path.toFile());
                        Date lastModified = new Date(path.toFile().lastModified());

                        return new Part(partNumber, partMd5, lastModified, path.toFile().length());
                    })
                    .sorted(Comparator.comparing(Part::getPartNumber))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not read all parts. "
                    + "bucket=%s, id=%s, uploadId=%s", bucket, id, uploadId), e);
        }
    }

    /**
     * Copies the range, define by from/to, from the S3 Object, identified by the given key to given
     * destination into the given bucket.
     *
     * @param bucket            The source Bucket.
     * @param id                Identifies the S3 Object.
     * @param copyRange         Byte range to copy. Optional.
     * @param partNumber        The part to copy.
     * @param destinationBucket The Bucket the target object (will) reside in.
     * @param destinationId     The target object ID.
     * @param uploadId          id of the upload.
     * @return etag of the uploaded file.
     */
    public String copyPart(BucketMetadata bucket,
                           UUID id,
                           HttpRange copyRange,
                           String partNumber,
                           BucketMetadata destinationBucket,
                           UUID destinationId,
                           String uploadId,
                           Map<String, String> encryptionHeaders) {

        verifyMultipartUploadPreparation(destinationBucket, destinationId, uploadId);

        return copyPartToFile(bucket, id, copyRange,
                createPartFile(destinationBucket, destinationId, uploadId, partNumber));
    }

    /**
     * Returns an InputStream containing InputStreams from each path element.
     *
     * @param paths the paths to read
     * @return an InputStream containing all data.
     */
    private static InputStream toInputStream(List<Path> paths) {
        var result = new ArrayList<InputStream>();
        for (var path : paths) {
            try {
                result.add(Files.newInputStream(path));
            } catch (IOException e) {
                throw new IllegalStateException("Can't access path " + path, e);
            }
        }
        return new SequenceInputStream(Collections.enumeration(result));
    }

    /**
     * Synchronize access on the upload, to handler concurrent abortion/completion.
     */
    private <T> T synchronizedUpload(String uploadId, Function<MultipartUploadInfo, T> callback) {
        var uploadInfo = uploadIdToInfo.get(uploadId);
        if (uploadInfo == null) {
            throw new IllegalArgumentException("Unknown upload " + uploadId);
        }

        // we assume that an uploadId -> uploadInfo is only registered once and not modified in between,
        // therefore we can synchronize on the uploadInfo instance
        synchronized (uploadInfo) {
            // check if the upload was aborted or completed in the meantime
            if (!uploadIdToInfo.containsKey(uploadId)) {
                throw new IllegalStateException(
                        "Upload was aborted or completed concurrently. uploadId=" + uploadId);
            }
            return callback.apply(uploadInfo);
        }
    }

    private String copyPartToFile(BucketMetadata bucket,
                                  UUID id,
                                  HttpRange copyRange,
                                  File partFile) {
        var from = 0L;
        var s3ObjectMetadata = objectStore.getS3ObjectMetadata(bucket, id);
        var len = s3ObjectMetadata.getDataPath().toFile().length();
        if (copyRange != null) {
            from = copyRange.getRangeStart(len);
            len = copyRange.getRangeEnd(len) - copyRange.getRangeStart(len) + 1;
        }

        try (var sourceStream = openInputStream(s3ObjectMetadata.getDataPath().toFile());
             var targetStream = newOutputStream(partFile.toPath())) {
            var skip = sourceStream.skip(from);
            if (skip == from) {
                IOUtils.copy(new BoundedInputStream(sourceStream, len), targetStream);
            } else {
                throw new IllegalStateException("Could not skip exact byte range");
            }
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not copy object. "
                    + "bucket=%s, id=%s, range=%s, partFile=%s", bucket, id, copyRange, partFile), e);
        }
        return DigestUtil.hexDigest(partFile);
    }

    private File createPartFile(BucketMetadata bucket,
                                UUID id,
                                String uploadId,
                                String partNumber) {
        if (id == null) {
            return null;
        }
        var partFile = getPartPath(
                bucket,
                id,
                uploadId,
                partNumber).toFile();

        try {
            if (!partFile.exists() && !partFile.createNewFile()) {
                throw new IllegalStateException(String.format("Could not create buffer file. "
                        + "bucket=%s, id=%s, uploadId=%s, partNumber=%s", bucket, id, uploadId, partNumber));
            }
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Could not create buffer file. "
                    + "bucket=%s, id=%s, uploadId=%s, partNumber=%s", bucket, id, uploadId, partNumber), e);
        }
        return partFile;
    }

    private void verifyMultipartUploadPreparation(BucketMetadata bucket, UUID id, String uploadId) {
        Path partsFolder = null;
        var multipartUploadInfo = uploadIdToInfo.get(uploadId);
        if (id != null) {
            partsFolder = getPartsFolderPath(bucket, id, uploadId);
        }

        if (multipartUploadInfo == null
                || partsFolder == null
                || !partsFolder.toFile().exists()
                || !partsFolder.toFile().isDirectory()) {
            throw new IllegalStateException(String.format(
                    "Multipart Request was not prepared. bucket=%s, id=%s, uploadId=%s, partsFolder=%s",
                    bucket, id, uploadId, partsFolder));
        }
    }

    private boolean createPartsFolder(BucketMetadata bucket, UUID id, String uploadId) {
        var partsFolder = getPartsFolderPath(bucket, id, uploadId).toFile();
        var created = partsFolder.mkdirs();
        if (created && !retainFilesOnExit) {
            partsFolder.deleteOnExit();
        }
        return created;
    }

    private Path getPartsFolderPath(BucketMetadata bucket, UUID id, String uploadId) {
        return Paths.get(bucket.getPath().toString(), id.toString(), uploadId);
    }

    private Path getPartPath(BucketMetadata bucket, UUID id, String uploadId, String partNumber) {
        return Paths.get(getPartsFolderPath(bucket, id, uploadId).toString(),
                partNumber + PART_SUFFIX);
    }
}
