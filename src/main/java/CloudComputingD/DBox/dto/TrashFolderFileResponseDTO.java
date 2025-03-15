package CloudComputingD.DBox.dto;


import CloudComputingD.DBox.entity.File;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TrashFolderFileResponseDTO {

    @Builder
    public record TrashFileDTO(Long file_id,
                               String file_name,
                               String file_type,
                               Long file_size,
                               LocalDateTime deleted_date,
                               String s3_key) {
    }

    private List<TrashFileDTO> trashFiles;
}
