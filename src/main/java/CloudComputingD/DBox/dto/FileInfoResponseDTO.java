package CloudComputingD.DBox.dto;

import CloudComputingD.DBox.entity.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class FileInfoResponseDTO {

    @Builder
    @AllArgsConstructor
    @Getter
    static public class Info {
        Long folder_id;
        String name;
        String type;
        Long size;
        LocalDateTime created_date;
        String s3_key;

        public static FileInfoResponseDTO.Info of(File file) {
            return Info.builder()
                    .folder_id(file.getFolderId())
                    .name(file.getName())
                    .type(file.getType())
                    .size(file.getSize())
                    .created_date(file.getCreated_date())
                    .s3_key(file.getS3_key())
                    .build();
        }
    }
}
