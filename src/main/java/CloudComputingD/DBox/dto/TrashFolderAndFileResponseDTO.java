package CloudComputingD.DBox.dto;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TrashFolderAndFileResponseDTO {

    private TrashFolderFileResponseDTO filesInfo;
    private FolderChildResponseDTO foldersInfo;
}
