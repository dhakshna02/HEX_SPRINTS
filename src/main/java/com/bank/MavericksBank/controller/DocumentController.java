package com.bank.MavericksBank.controller;


import com.bank.MavericksBank.dto.*;
import com.bank.MavericksBank.model.Document;
import com.bank.MavericksBank.service.AccountService;
import com.bank.MavericksBank.service.CustomerService;
import com.bank.MavericksBank.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/document")
@CrossOrigin("http://localhost:5173/")
public class DocumentController {

    private final CustomerService customerService;
    private final DocumentService documentService;
    private final AccountService accountService;

    @PostMapping("/upload")
    public Document uploadFile(Principal principal,
                               @RequestParam("file") MultipartFile file) throws IOException {

        return documentService.upload(principal.getName(),file);

    }


    // upload the IdentityProof

    @PostMapping("/upload-identity-proof")
    public ResponseEntity<HttpStatus> UploadIdenityProof(@RequestBody IdentityProofUploadDto identityProofUploadDto,
                                                         Principal principal){
        customerService.UploadIdenityProof(identityProofUploadDto,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // addres proof upload
    @PostMapping("/upload-address-proof")
    public ResponseEntity<HttpStatus> UploadAddressProof(@RequestBody AddressProofUploadDtp addressProofUploadDtp,
                                                         Principal principal){
        customerService.UploadAddressProof(addressProofUploadDtp,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // upload tha pan


    @PostMapping("/upload-pan")
    public ResponseEntity<HttpStatus> UploadPan(@RequestBody PanUploadDto panUploadDto,
                                                         Principal principal){
        customerService.UploadPan(panUploadDto,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    ///  upload the photo

    @PostMapping("/upload-photo")
    public ResponseEntity<HttpStatus> UploadPhoto(@RequestBody PhotoUploadDto photoUploadDto,
                                                Principal principal){
        customerService.UploadPhoto(photoUploadDto,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // signature and account type
    @PostMapping("/initate-account-signature-upload")
    public ResponseEntity<HttpStatus> UploadSignatureAndAccountType(@RequestBody SignatureAndAccountTypeDto signatureAndAccountTypeDto,
                                                  Principal principal){
        accountService.createAccount(signatureAndAccountTypeDto,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/signature-upload")
    public ResponseEntity<HttpStatus> uploadSign(@RequestBody SignatureUploadDto signatureUploadDto,
                                                                    Principal principal){
        customerService.uploadSign(signatureUploadDto,principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }




}
