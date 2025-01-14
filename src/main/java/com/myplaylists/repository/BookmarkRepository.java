package com.myplaylists.repository;

import java.util.Optional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	Page<Bookmark> findByUser(Pageable pageable, User user);
	Optional<Bookmark> findByUserAndPlaylist(User user, Playlist playlist);
	Optional<Bookmark> findAllByUser(User user);

}
