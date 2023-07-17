package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    @Query(value = "SELECT *\n" +
            "FROM vti_project.tbl_category c\n" +
            "where c.name like %:keyword% \n" +
            "or c.description like %:keyword%",nativeQuery = true)
    List<Category> findCategoriesByKeyword(@Param("keyword") String keyword);
}
