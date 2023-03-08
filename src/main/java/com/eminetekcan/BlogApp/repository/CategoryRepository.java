package com.eminetekcan.BlogApp.repository;

import com.eminetekcan.BlogApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
