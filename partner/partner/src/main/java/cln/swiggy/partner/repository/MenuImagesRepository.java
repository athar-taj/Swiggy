package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.Menu;
import cln.swiggy.partner.model.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuImagesRepository extends JpaRepository<MenuImage, Long> {

    List<MenuImage> findByMenu(Menu menu);
}
