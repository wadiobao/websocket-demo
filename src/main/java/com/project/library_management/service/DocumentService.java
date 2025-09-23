package com.project.library_management.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.library_management.entity.DocLending;
import com.project.library_management.entity.document.Document;
import com.project.library_management.entity.document.Rack;
import com.project.library_management.entity.user.Member;
import com.project.library_management.enums.ErrorCode;
import com.project.library_management.exception.MyException;
import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.DocumentRequest;
import com.project.library_management.model.DocumentResponse;
import com.project.library_management.model.LendRequest;
import com.project.library_management.repository.DocLendingRepository;
import com.project.library_management.repository.DocumentRepository;
import com.project.library_management.repository.RackRepository;
import com.project.library_management.repository.UserRepository;
import com.project.library_management.service.iservice.IDocumentMapper;
import com.project.library_management.service.iservice.IDocumentService;
import com.project.library_management.service.iservice.ISearchService;
import com.project.library_management.specification.DocumentSpecification;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentService implements IDocumentService, ISearchService {

    DocumentRepository documentRepository;
    RackRepository rackRepository;
    UserRepository userRepository;
    DocLendingRepository docLendingRepository;
    MessageSource messageSource;
    IDocumentMapper documentMapper;

    @Override
    @Transactional
    public ResponseEntity<BaseResponse<DocumentResponse>> createDocument(@Valid DocumentRequest request) {
        Rack rack = rackRepository.findById(request.getRackId())
                .orElseThrow(() -> new MyException(ErrorCode.RACK_NOT_FOUND));
        Document document = documentMapper.toDocumentEntity(request);
        document.setRack(rack);
        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.ok(BaseResponse.<DocumentResponse>builder()
                .data(documentMapper.toDocumentResponse(savedDocument))
                .message(messageSource.getMessage("message.document.created.success", null, LocaleContextHolder.getLocale()))
                .build());
    }

    @Override
    @Transactional
    public ResponseEntity<BaseResponse<DocumentResponse>> updateDocument(Long documentId, @Valid DocumentRequest request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new MyException(ErrorCode.DOCUMENT_NOT_FOUND));
        // Update logic here
        // For simplicity, we are not fully implementing the update logic
        document.setTitle(request.getTitle());
        document.setAuthor(request.getAuthor());
        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.ok(BaseResponse.<DocumentResponse>builder()
                .data(documentMapper.toDocumentResponse(savedDocument))
                .message(messageSource.getMessage("message.document.updated.success", null, LocaleContextHolder.getLocale()))
                .build());
    }

    @Override
    @Transactional
    public ResponseEntity<BaseResponse<Object>> deleteDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new MyException(ErrorCode.DOCUMENT_NOT_FOUND));
        if (document.isBorrowed()) {
            throw new MyException(ErrorCode.DOCUMENT_BORROWED_CANNOT_DELETE); 
        }
        documentRepository.delete(document);
        return ResponseEntity.ok(BaseResponse.builder().message(messageSource.getMessage("message.document.deleted.success", null, LocaleContextHolder.getLocale())).build());
    }

    @Override
    @Transactional
    public ResponseEntity<BaseResponse<Object>> lendDocument(@Valid LendRequest request) {
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new MyException(ErrorCode.DOCUMENT_NOT_FOUND)); 
        Member member = (Member) userRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MyException(ErrorCode.MEMBER_NOT_FOUND)); 

        if (document.isBorrowed()) {
            throw new MyException(ErrorCode.DOCUMENT_ALREADY_BORROWED); 
        }

        if (document.isReferenceOnly()) {
            throw new MyException(ErrorCode.DOCUMENT_REFERENCE_ONLY); 
        }

        if (member.getTotalCheckedout() >= member.getLoanLimit()) {
            throw new MyException(ErrorCode.LOAN_LIMIT_REACHED); 
        }

        document.setBorrowed(true);
        document.setDueDate(LocalDateTime.now().plusDays(member.getLoanDurationDays()));
        member.incrementTotalCheckedout();

        DocLending docLending = DocLending.builder()
                .docId(document)
                .userId(member)
                .creationDate(LocalDateTime.now())
                .dueDate(document.getDueDate())
                .build();

        docLendingRepository.save(docLending);
        documentRepository.save(document);
        userRepository.save(member);

        return ResponseEntity.ok(BaseResponse.builder().message(messageSource.getMessage("message.document.lent.success", null, LocaleContextHolder.getLocale())).build());
    }

    @Override
    @Transactional
    public ResponseEntity<BaseResponse<Object>> returnDocument(Long documentId) {
        DocLending docLending = docLendingRepository.findByDocId_IdAndReturnDateIsNull(documentId)
                .orElseThrow(() -> new MyException(ErrorCode.LENDING_RECORD_NOT_FOUND)); 

        Document document = docLending.getDocId();
        Member member = (Member) docLending.getUserId();

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(docLending.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(docLending.getDueDate(), now);
            double fine = daysLate * member.getFinePerDay();
            member.addFine(fine);
        }

        document.setBorrowed(false);
        document.setDueDate(null);
        member.returnDocItem();
        docLending.setReturnDate(LocalDateTime.now());

        docLendingRepository.save(docLending);
        documentRepository.save(document);
        userRepository.save(member);

        return ResponseEntity.ok(BaseResponse.builder().message(messageSource.getMessage("message.document.returned.success", null, LocaleContextHolder.getLocale())).build());
    }

    @Override
    public ResponseEntity<BaseResponse<List<DocumentResponse>>> searchDocuments(String title, String author, String type) {
        Specification<Document> spec = DocumentSpecification.findByCriteria(title, author, type);
        List<Document> documents = documentRepository.findAll(spec);

        List<DocumentResponse> documentResponses = documents.stream()
                .map(documentMapper::toDocumentResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(BaseResponse.<List<DocumentResponse>>builder().data(documentResponses).build());
    }
}