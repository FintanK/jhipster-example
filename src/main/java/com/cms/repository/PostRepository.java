package com.cms.repository;

import com.cms.domain.Post;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Post entity.
 */
public interface PostRepository extends JpaRepository<Post,Long> {

}
