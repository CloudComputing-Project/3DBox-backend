package CloudComputingD.DBox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Builder // 클래스 빌더 패턴으로 JPA 엔티티 객체를 생성한다.
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity // 해당 어노테이션이 적용된 클래스는 JPA가 엔티티로 인식하며 테이블과 링크될 클래스임을 나타낸다.
public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long size;

//    @Column
//    private Long parent_id;

    @Column(nullable = false)
    private LocalDateTime created_date;

    @Column
    private LocalDateTime modified_date;

    @Column
    private LocalDateTime deleted_date;

    @Column
    @ColumnDefault("false")
    private Boolean is_deleted;

    @Column(nullable = false)
    private String s3_key;

//    @Column
//    private BigDecimal location_x;
//
//    @Column
//    private BigDecimal location_y;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private Folder folder;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    // Getter for folder_id
    public Long getFolderId() {
        return folder != null ? folder.getId() : null;
    }
//    public Long getSize() {
//        return size;
//    }
//
//    public LocalDateTime getCreated_date() {
//        return created_date;
//    }
//
//    public void setDeleted_date(LocalDateTime deleted_date) {
//        this.deleted_date = deleted_date;
//    }
//
//    public void setIs_deleted(Boolean is_deleted) {
//        this.is_deleted = is_deleted;
//    }
}
