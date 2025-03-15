package CloudComputingD.DBox.dto;

import lombok.Builder;

@Builder
public record UserLoginResponseDTO(String userId, Long rootFolderId, String nickname) {
}
