package CloudComputingD.DBox.controller;


import CloudComputingD.DBox.dto.*;
import CloudComputingD.DBox.service.FolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "2.폴더")
@RequiredArgsConstructor
@RequestMapping("/folder")
@RestController
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/create")
    @Operation(summary = "폴더 생성", description = "폴더 이름, 유저 id, 상위 폴더 id 받아 폴더 생성")
    public ResponseEntity<FolderCreateResponseDTO> createFolder(
            @RequestBody FolderCreateRequestDTO folderCreateRequestDTO
    ) {
        FolderCreateResponseDTO folderCreateResponseDTO = folderService.createFolder(folderCreateRequestDTO);
        return new ResponseEntity<>(folderCreateResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("child/file/{folderId}")
    @Operation(summary = "폴더 내 파일들 조회", description = "폴더 id를 통해 폴더 내 파일들 조회")
    public ResponseEntity<FolderFileResponseDTO> getFiles(
            @PathVariable("folderId") Long folderId
    ) {
        FolderFileResponseDTO folderFileResponseDTO = folderService.getFilesByFolderId(folderId);
        return new ResponseEntity<>(folderFileResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/child/folder/{folderId}")
    @Operation(summary = "폴더 내 하위 폴더들 조회", description = "폴더 id를 통해 폴더 내 하위 폴더들 조회")
    public ResponseEntity<FolderChildResponseDTO> getChildFolders(
            @PathVariable("folderId") Long folderId
    ) {
        FolderChildResponseDTO folderChildResponseDTO = folderService.getFoldersByParentId(folderId);

        return new ResponseEntity<>(folderChildResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/{folderId}/name/{folderName}")
    @Operation(summary = "폴더 이름 변경", description = "폴더 id, 폴더 이름을 통해 폴더 이름 변경")
    public ResponseEntity<HttpStatus> changeFolderName(
            @PathVariable("folderId") Long folderId,
            @PathVariable("folderName") String folderName
    ) {
        folderService.changeFolderName(folderId, folderName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/move/{folderId}/{parentId}")
    @Operation(summary = "폴더 이동", description = "폴더 id를 통해 폴더 휴지통 복원")
    public ResponseEntity<HttpStatus> moveFolder(
            @PathVariable("folderId") Long folderId,
            @PathVariable("parentId") Long parentId
    ) {
        folderService.moveFolder(folderId, parentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/trash/{folderId}")
    @Operation(summary = "폴더 휴지통 이동", description = "폴더 id를 통해 폴더 휴지통 이동")
    public ResponseEntity<HttpStatus> moveFolderToTrash(
            @PathVariable("folderId") Long folderId
    ) {
        folderService.moveFolderToTrash(folderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/restore/{folderId}")
    @Operation(summary = "폴더 휴지통 복원", description = "폴더 id를 통해 폴더 휴지통 복원")
    public ResponseEntity<HttpStatus> restoreFolderFromTrash(
            @PathVariable("folderId") Long folderId
    ) {
        folderService.restoreFolderFromTrash(folderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{folderId}")
    @Operation(summary = "폴더 영구 삭제", description = "폴더 id를 통해 폴더 db상에서 제거")
    public ResponseEntity<HttpStatus> deleteFolderPermanently(
            @PathVariable("folderId") Long folderId
    ) {
        folderService.deleteFolderFromTrash(folderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/trash")
    @Operation(summary = "휴지통 내 파일, 폴더들 조회", description = "유저 id를 통해 휴지통 내 파일, 폴더들 조회")
    public ResponseEntity<TrashFolderAndFileResponseDTO> getTrashFilesAndFolders(
            @RequestBody TrashRequestDTO trashRequestDTO
    ) {
        TrashFolderAndFileResponseDTO responseDTO = folderService.getTrashFilesandFolders(trashRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/child/{folderId}")
    @Operation(summary = "폴더 내 파일, 폴더들 전체 조회", description = "폴더 id를 통해 폴더 내 파일, 폴더들 전체 조회")
    public ResponseEntity<FolderAndFileResponseDTO> getFilesAndFolders(
            @PathVariable("folderId") Long folderId
    ) {
        FolderAndFileResponseDTO folderAndFileResponseDTO = folderService.getFilesAndFolders(folderId);

        return new ResponseEntity<>(folderAndFileResponseDTO, HttpStatus.OK);
    }
}
