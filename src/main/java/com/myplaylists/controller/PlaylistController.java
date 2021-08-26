package com.myplaylists.controller;

import com.myplaylists.config.auth.Login;
import com.myplaylists.dto.PlaylistRequestDto;
import com.myplaylists.dto.PlaylistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.domain.Playlist;
import com.myplaylists.service.BookmarkService;
import com.myplaylists.service.PlaylistService;

@Controller
@RequiredArgsConstructor
public class PlaylistController {
	
	private final PlaylistService playlistService;
	private final BookmarkService bookmarkService;	

	@GetMapping("/mylist")
	public String viewMyPlaylist(@Login LoginUser user, Model model, @PageableDefault(size = 6, sort = "updatedAt",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Playlist> playlists = playlistService.findMyPlaylists(pageable, user.getId());

		model.addAttribute("playlists", playlists);
		model.addAttribute("isFirst", playlists.isFirst());
		model.addAttribute("isLast", playlists.isLast());
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		
		return "playlist/mylist";
	}
	
	@GetMapping("/mylist/search")
	public String viewMyPlaylistSearchResult(@Login LoginUser user, Model model, String keyword, @PageableDefault(size = 6, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Playlist> playlists = playlistService.searchMylist(pageable, keyword, user.getId());
		
		model.addAttribute("playlists", playlists);
		model.addAttribute("isFirst", playlists.isFirst());
		model.addAttribute("isLast", playlists.isLast());
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		return "playlist/searchMylist";
	}
	
	@GetMapping("/all/search")
	public String viewAllSearchResult(Model model, String keyword, @PageableDefault(size = 6, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Playlist> playlists = playlistService.searchAll(pageable, keyword);
		model.addAttribute("playlists", playlists);
		model.addAttribute("isFirst", playlists.isFirst());
		model.addAttribute("isLast", playlists.isLast());
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		return "playlist/searchAll";
	}
	
	@GetMapping("/playlist")
	public String viewPlaylistForm() {
		return "playlist/addPlaylist";
	}

	@ResponseBody
	@PostMapping("/playlist")
	public ResponseEntity<PlaylistResponseDto> addPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto, @Login LoginUser loginUser) {
		PlaylistResponseDto playlistResponseDto = playlistService.addPlaylist(loginUser, playlistRequestDto);
		return ResponseEntity.ok(playlistResponseDto);
	}


	@GetMapping("/playlist/{playlistId}")
	public String viewPlaylistDetail(@PathVariable("playlistId") Long playlistId, Model model, @Login LoginUser user) {
		Playlist playlist = playlistService.getPlaylist(playlistId);
		
		model.addAttribute("playlist", playlist);
		model.addAttribute("author", playlist.getUser().getNickname());
		model.addAttribute("songs", playlist.getSongs());
		model.addAttribute("isBookmark", bookmarkService.validateBookmark(user.getId(), playlistId).isPresent());
		return "playlist/detail";
	}
	
	@DeleteMapping("/playlist/{playlistId}")
	public String deletePlaylist(@PathVariable("playlistId") Long playlistId) {
		playlistService.deletePlaylist(playlistId);
		return "redirect:/mylist";
	}

	@GetMapping("/all")
	public String playlists(@Login LoginUser user, @PageableDefault(size=6, sort="updatedAt",direction=Sort.Direction.DESC)Pageable pageable, Model model) {
		Page<Playlist> playlists = playlistService.findAllPlaylists(pageable);
		
		model.addAttribute("playlists", playlists);
		
		model.addAttribute("isFirst", playlists.isFirst());
		model.addAttribute("isLast", playlists.isLast());
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		return "playlist/playlists";
	}
}
