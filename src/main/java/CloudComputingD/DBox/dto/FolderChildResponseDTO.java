package CloudComputingD.DBox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FolderChildResponseDTO {

    @Builder
    public record FolderDTO(Long folder_id,
                         String folder_name) {

    }

    private List<FolderDTO> folders;
}
