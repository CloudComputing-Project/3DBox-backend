package CloudComputingD.DBox.dto;

import lombok.Builder;

@Builder
public record FolderCreateResponseDTO(Long FolderId, String FolderName) {
}
