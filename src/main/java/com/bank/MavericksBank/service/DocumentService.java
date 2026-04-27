package com.bank.MavericksBank.service;

import com.bank.MavericksBank.model.Customers;
import com.bank.MavericksBank.model.Document;
import com.bank.MavericksBank.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final CustomerService customerService;


    private final static String UPLOAD_PATH ="/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/assets/uploads";
    public Document upload(String name, MultipartFile file) throws IOException {

        Customers customer = customerService.getByUsername(name);

        File file1 = new File(UPLOAD_PATH);

        String fileName = file.getOriginalFilename();

        Path path = Paths.get(UPLOAD_PATH+"/"+fileName);

        Files.write(path,file.getBytes());

        Document document = new Document();
        document.setDocumentNamee(fileName);
        document.setCustomers(customer);

        return documentRepository.save(document);
    }
}
