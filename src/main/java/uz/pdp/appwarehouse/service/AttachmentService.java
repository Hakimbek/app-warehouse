package uz.pdp.appwarehouse.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    // ADD
    @SneakyThrows
    public Result upload(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file == null) {
            return new Result("Request is null", false);
        }

        Attachment attachment = new Attachment();
        attachment.setName(file.getName());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setBytes(file.getBytes());
        attachmentContent.setAttachment(attachment);
        attachmentContentRepository.save(attachmentContent);
        return new Result("Successfully uploaded", true, attachment.getId());
    }

    // GET
    public List<Attachment> get() {
        return attachmentRepository.findAll();
    }

    // GET BY ID
    public Result getById(Integer id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (!optionalAttachment.isPresent()) {
            return new Result("Attachment not found", false);
        }

        return new Result("Success", true, optionalAttachment.get());
    }

    // DOWNLOAD
    @SneakyThrows
    public Result download(Integer id, HttpServletResponse response) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (!optionalAttachment.isPresent()) {
            return new Result("Attachment not found", false);
        }
        Attachment attachment = optionalAttachment.get();

        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
        if (!optionalAttachmentContent.isPresent()) {
            return new Result("AttachmentContent not found", false);
        }
        AttachmentContent attachmentContent = optionalAttachmentContent.get();

        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + attachment.getName() + "\"");
        response.setContentType(attachment.getContentType());
        FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
        return new Result("Successfully downloaded", true);
    }

    // DELETE
    public Result delete(Integer id) {
        try {
            attachmentRepository.deleteById(id);
            return new Result("Successfully deleted", true);
        } catch (Exception e) {
            return new Result("Error", false);
        }
    }

    // EDIT
    public Result edit(Integer id, Attachment attachment) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (!optionalAttachment.isPresent()) {
            return new Result("Attachment not found", false);
        }

        Attachment editedAttachment = optionalAttachment.get();
        editedAttachment.setName(attachment.getName());
        attachmentRepository.save(editedAttachment);
        return new Result("Successfully edited", true);
    }
}
