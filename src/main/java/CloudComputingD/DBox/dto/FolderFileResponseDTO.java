package CloudComputingD.DBox.dto;


import CloudComputingD.DBox.entity.File;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FolderFileResponseDTO {

    @Builder
    public record FileDTO(Long file_id,
                          String file_name,
                          String file_type,
                          Long file_size,
                          LocalDateTime created_date,
                          String s3_key) {
//        public static FileDTO from(File file) {
//            return FileDTO.builder()
//                    .file_id(file.getId())
//                    .file_type(file.getType())
//                    .file_size(file.getSize())
//                    .created_date(file.getCreated_date())
//                    .build();
//        }
    }

    @Builder
    public record TrashFileDTO(Long file_id,
                          String file_name,
                          String file_type,
                          Long file_size,
                          LocalDateTime deleted_date,
                          String s3_key) {
    }

    private List<FileDTO> files;
}
