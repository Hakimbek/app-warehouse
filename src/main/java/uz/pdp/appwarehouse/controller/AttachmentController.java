package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    // ADD
    @GetMapping("/upload")
    public Result upload(MultipartHttpServletRequest request) {
        return attachmentService.upload(request);
    }

    // GET
    @GetMapping
    public List<Attachment> get() {
        return attachmentService.get();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return attachmentService.getById(id);
    }

    // DOWNLOAD
    @GetMapping("/download/{id}")
    public Result download(@PathVariable Integer id, HttpServletResponse response) {
        return attachmentService.download(id, response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return attachmentService.delete(id);
    }

    // EDIT
    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Attachment attachment) {
        return attachmentService.edit(id, attachment);
    }
}
