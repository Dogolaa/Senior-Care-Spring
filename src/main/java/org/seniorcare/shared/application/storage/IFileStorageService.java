package org.seniorcare.shared.application.storage;

public interface IFileStorageService {
    String upload(byte[] content, String filename, String contentType);
}
