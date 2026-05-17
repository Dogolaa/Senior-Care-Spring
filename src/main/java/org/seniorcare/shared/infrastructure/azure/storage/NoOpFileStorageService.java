package org.seniorcare.shared.infrastructure.azure.storage;

import org.seniorcare.shared.application.storage.IFileStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnMissingBean(IFileStorageService.class)
public class NoOpFileStorageService implements IFileStorageService {

    @Override
    public String upload(byte[] content, String filename, String contentType) {
        throw new UnsupportedOperationException(
                "Azure Blob Storage não está configurado. " +
                "Defina a variável de ambiente AZURE_STORAGE_CONNECTION_STRING para habilitar upload de fotos.");
    }
}
