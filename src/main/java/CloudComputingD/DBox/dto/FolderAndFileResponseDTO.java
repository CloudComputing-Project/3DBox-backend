package CloudComputingD.DBox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FolderAndFileResponseDTO {

    private FolderFileResponseDTO filesInfo;
    private FolderChildResponseDTO foldersInfo;
}
