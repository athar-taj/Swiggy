package cln.swiggy.restaurant.repository;

import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuImagesRepository extends JpaRepository<MenuImage, Long> {

    List<MenuImage> findByMenuId(Long menuItemId);
}
