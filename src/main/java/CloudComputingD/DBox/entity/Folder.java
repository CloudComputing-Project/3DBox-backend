package CloudComputingD.DBox.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<File> files = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Folder> subFolders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ToString.Exclude
    private Folder parent;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime created_date;

    @Column
    private LocalDateTime modified_date;

    @Column
    private LocalDateTime deleted_date;

    @Column
    @ColumnDefault("false")
    private Boolean is_deleted;

    @Column
    @ColumnDefault("false")
    private Boolean is_root;

}
