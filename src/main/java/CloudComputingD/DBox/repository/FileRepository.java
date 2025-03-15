package CloudComputingD.DBox.repository;

import CloudComputingD.DBox.entity.File;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(File file) {
        em.persist(file);
    }
    public File findById(Long id) {
        return em.find(File.class, id);
    }

    public List<File> findDeletedFiles(String userId) {
        String jpql = "SELECT f FROM File f WHERE f.user.oauthId.oauthServerId = :userId AND f.is_deleted = true";
        TypedQuery<File> query = em.createQuery(jpql, File.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
  
    public void deleteById(Long id) {
        File file = em.find(File.class, id);
        em.remove(file);
    }

//    public List<File> findByUser(User user);
}
