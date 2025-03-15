package CloudComputingD.DBox.repository;

import CloudComputingD.DBox.entity.File;
import CloudComputingD.DBox.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Long> {
    @Query("SELECT b FROM Folder a JOIN a.files b where a.id = :folderId and a.is_deleted = false and b.is_deleted = false")
    List<File> findFilesByFolderId(@Param("folderId") Long folderId);

    @Query("SELECT f FROM Folder f WHERE f.parent = :parentFolder and f.is_deleted = false")
    List<Folder> findAllByParent_id(@Param("parentFolder") Folder parentFolder);

    @Query("SELECT f FROM Folder f WHERE f.id = :folderId")
    Folder findByFolderId(@Param("folderId") Long folderId);

    @Query("SELECT f FROM Folder f WHERE f.user.oauthId.oauthServerId = :userId and f.is_deleted = true")
    List<Folder> findDeletedFolders(@Param("userId") String userId);

    @Query("SELECT f FROM Folder f WHERE f.user.oauthId.oauthServerId = :userId and f.is_root = true")
    Folder findRootFolder(@Param("userId") String userId);
}
