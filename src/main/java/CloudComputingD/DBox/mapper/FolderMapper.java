package CloudComputingD.DBox.mapper;

import CloudComputingD.DBox.dto.FolderChildResponseDTO;
import CloudComputingD.DBox.dto.FolderFileResponseDTO;
import CloudComputingD.DBox.entity.File;
import CloudComputingD.DBox.entity.Folder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FolderMapper {

    public FolderFileResponseDTO.FileDTO fileToFolderFileResponseDTO(File file) {

        Long id = file.getId();
        String name = file.getName();
        String type = file.getType();
        Long size = file.getSize();
        LocalDateTime date = file.getCreated_date();

        return FolderFileResponseDTO.FileDTO.builder()
                .file_id(id)
                .file_name(name)
                .file_type(type)
                .file_size(size)
                .created_date(date)
                .build();
    }

    public FolderChildResponseDTO.FolderDTO folderToFolderChildResponseDTO(Folder folder) {

        Long id = folder.getId();
        String name = folder.getName();

        return FolderChildResponseDTO.FolderDTO.builder()
                .folder_id(id)
                .folder_name(name)
                .build();
    }
}
