package CloudComputingD.DBox.service;

import CloudComputingD.DBox.dto.*;
import CloudComputingD.DBox.entity.File;
import CloudComputingD.DBox.entity.Folder;
import CloudComputingD.DBox.entity.User;
import CloudComputingD.DBox.mapper.FolderMapper;
import CloudComputingD.DBox.repository.FileRepository;
import CloudComputingD.DBox.repository.FolderRepository;
import CloudComputingD.DBox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, FolderMapper folderMapper, UserRepository userRepository, FileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public Folder getFolderInfo(Long folderId) {
        return folderRepository.findById(folderId).orElse(null);
    }

    public FolderCreateResponseDTO createFolder(FolderCreateRequestDTO folderCreateRequestDTO) {
        String folderName = folderCreateRequestDTO.folderName();
        String userId = folderCreateRequestDTO.userId();
        Long parentId = folderCreateRequestDTO.parentId();

        User user = userRepository.findByOauthServerId(userId);
        Folder folder = folderRepository.findByFolderId(parentId);
        System.out.println(user.id());
        Folder savedFolder = folderRepository.save(Folder.builder()
                .name(folderName)
                .created_date(LocalDateTime.now())
                .parent(folder)
                .user(user)
                .build());


        return FolderCreateResponseDTO.builder()
                .FolderId(savedFolder.getId())
                .FolderName(folderName)
                .build();
    }

    //folderId를 받아 해당 폴더에 속하는 File들의 list를 받아오고 mapper를 통해 ResponseDto로 변환하여 리턴
    public FolderFileResponseDTO getFilesByFolderId(Long FolderId) {
        List<File> files = folderRepository.findFilesByFolderId(FolderId);

        List<FolderFileResponseDTO.FileDTO> responseFiles = files.stream()
                .map(folderMapper::fileToFolderFileResponseDTO)
                .toList();

        return FolderFileResponseDTO.builder()
                .files(responseFiles)
                .build();
        /*
        List<FolderFileResponseDTO> list = new ArrayList<>();
        FolderFileResponseDTO.FileDTO
        for (File f : files) {
            folderMapper.fileToFolderFileResponseDTO(f);
            list.add
                    (fileDTO);
        }
        return list;
        */
    }

    public FolderChildResponseDTO getFoldersByParentId(Long folderId) {
        Folder folder = folderRepository.findByFolderId(folderId);
        List<Folder> folders = folderRepository.findAllByParent_id(folder);

        List<FolderChildResponseDTO.FolderDTO> responseFolders = folders.stream()
                .map(folderMapper::folderToFolderChildResponseDTO)
                .toList();

        return FolderChildResponseDTO.builder()
                .folders(responseFolders)
                .build();
    }

    public void changeFolderName(Long folderId, String folderName) {
        Folder folder = folderRepository.findByFolderId(folderId);
        folder.setName(folderName);
        folderRepository.save(folder);
    }

    public void moveFolderToTrash(Long folderId) {
        Folder folder = folderRepository.findByFolderId(folderId);
        folder.setIs_deleted(true);
        folder.setDeleted_date(LocalDateTime.now());
        folderRepository.save(folder);
    }

    public void moveFolder(Long folderId, Long parentId) {
        Folder folder = folderRepository.findByFolderId(folderId);
        Folder parentFolder= folderRepository.findByFolderId(parentId);
        folder.setParent(parentFolder);
        folderRepository.save(folder);
    }

    public void restoreFolderFromTrash(Long folderId) {
        Folder folder = folderRepository.findByFolderId(folderId);
        folder.setIs_deleted(false);
        folderRepository.save(folder);
    }

    public void deleteFolderFromTrash(Long folderId) {
        Folder folder = folderRepository.findByFolderId(folderId);
        folderRepository.delete(folder);
    }

    public TrashFolderAndFileResponseDTO getTrashFilesandFolders(TrashRequestDTO trashRequestDTO) {
        String userId = trashRequestDTO.userId();
        List<Folder> deletedFolders = folderRepository.findDeletedFolders(userId);
        List<File> deletedFiles = fileRepository.findDeletedFiles(userId);
        List<TrashFolderFileResponseDTO.TrashFileDTO> fileDTOS = deletedFiles.stream()
                .map(folderMapper::trashFileToFolderFileResponseDTO)
                .toList();

        TrashFolderFileResponseDTO trashFolderFileResponseDTO = TrashFolderFileResponseDTO.builder()
                .trashFiles(fileDTOS)
                .build();

        List<FolderChildResponseDTO.FolderDTO> folderDTOS = deletedFolders.stream()
                .map(folderMapper::folderToFolderChildResponseDTO)
                .toList();

        FolderChildResponseDTO folderChildResponseDTO = FolderChildResponseDTO.builder()
                .folders(folderDTOS)
                .build();

        return TrashFolderAndFileResponseDTO.builder()
                .filesInfo(trashFolderFileResponseDTO)
                .foldersInfo(folderChildResponseDTO)
                .build();
    }

    public FolderAndFileResponseDTO getFilesAndFolders(Long folderId) {
        FolderChildResponseDTO folders = getFoldersByParentId(folderId);
        FolderFileResponseDTO files = getFilesByFolderId(folderId);

        return FolderAndFileResponseDTO.builder()
                .foldersInfo(folders)
                .filesInfo(files)
                .build();
    }

}

