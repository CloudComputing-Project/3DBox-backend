package CloudComputingD.DBox.dto;

import lombok.Builder;

@Builder
public record FolderCreateRequestDTO(String folderName, String userId, Long parentId) {
}
