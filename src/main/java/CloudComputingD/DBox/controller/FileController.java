package CloudComputingD.DBox.controller;

import CloudComputingD.DBox.entity.File;
import CloudComputingD.DBox.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Tag(name = "1.파일")
@RestController
public class FileController {
    private FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 파일 업로드
    @PostMapping(value = "/file/upload/{folderId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "파일 업로드", description = "폴더 id, binary 파일 데이터 받아서 s3상에 업로드(복수 파일 가능)")
    public ResponseEntity<?> uploadFile(
            @PathVariable("folderId") Long folderId,
            @RequestParam("files") List<MultipartFile> multipartFiles
    ) throws IOException {
        return new ResponseEntity<>(fileService.uploadFile(folderId, multipartFiles), HttpStatus.CREATED);
    }

    // 파일 정보 조회
    @GetMapping(value = "/file/{fileId}")
    @Operation(summary = "파일 정보 조회", description = "파일 id, 상위 폴더 id 받아 파일 정보 조회")
    public ResponseEntity<?> getFile(
            @PathVariable("fileId") Long fileId
    ) {
        return new ResponseEntity<>(fileService.getFileById(fileId), HttpStatus.OK);
    }

    // 파일 이름 수정
    @PatchMapping(value="/file/{fileId}/name/{fileName}")
    @Operation(summary = "파일 이름 수정", description = "파일 id, 파일 이름 받아 파일 이름 수정")
    public ResponseEntity<HttpStatus> renameFile(
            @PathVariable("fileId") Long fileId,
            @PathVariable("fileName") String newName
    ){
        fileService.renameFile(fileId, newName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 파일 휴지통 이동
    @PatchMapping(value="/file/trash/{fileId}")
    @Operation(summary = "파일 휴지통 이동", description = "파일 id 받아 해당 파일 휴지통 이동")
    public ResponseEntity<HttpStatus> trashFile(
            @PathVariable("fileId") Long fileId
    ) {
        fileService.trashFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 파일 복원
    @PatchMapping(value="/file/restore/{fileId}")
    @Operation(summary = "파일 휴지통 복원", description = "파일 id 받아 해당 파일 휴지통 복원")
    public ResponseEntity<HttpStatus> restoreFile(
            @PathVariable("fileId") Long fileId
    ) {
        fileService.restoreFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 파일 영구 삭제
    @DeleteMapping(value="/file/delete/{fileId}")
    @Operation(summary = "파일 영구 삭제", description = "파일 id 받아 db상 파일 정보 삭제")
    public ResponseEntity<HttpStatus> deleteFile(
            @PathVariable("fileId") Long fileId
    ) {
        fileService.deleteFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 파일 다운로드
    @GetMapping(value="/file/download/{fileId}")
    @Operation(summary = "파일 다운로드", description = "파일 id 받아 파일 다운로드")
    public ResponseEntity<?> downloadFile(
            @PathVariable("fileId") Long fileId
    ) throws IOException {
        ByteArrayOutputStream body = fileService.downloadFile(fileId);

        String fileName = fileService.getFileName(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body.toByteArray());
    }

    // 파일 이동
    @PatchMapping(value="/file/move/{fileId}/{folderId}")
    @Operation(summary = "파일 이동", description = "파일 id, 폴더 id 받아 해당 폴더로 파일 이동")
    public ResponseEntity<HttpStatus> moveFile(
            @PathVariable("fileId") Long fileId,
            @PathVariable("folderId") Long folderId
    ) {
        fileService.moveFile(fileId, folderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 파일 복사
    @PostMapping(value="/file/copy/{fileId}/{folderId}")
    @Operation(summary = "파일 복사", description = "파일 id, 폴더 id 받아 동일 파일 해당 폴더 내 복사 저장")
    public ResponseEntity<HttpStatus> copyFile(
            @PathVariable("fileId") Long fileId,
            @PathVariable("folderId") Long folderId
    ) {
        fileService.copyFile(fileId, folderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/file/uploadGan", consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> uploadGan(
            @RequestParam("files") List<MultipartFile> multipartFiles
    ) {
        fileService.uploadGan(multipartFiles);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
