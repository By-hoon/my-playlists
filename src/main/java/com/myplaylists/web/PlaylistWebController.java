package com.myplaylists.web;

import com.myplaylists.config.auth.Login;
import com.myplaylists.domain.Playlist;
import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.PlaylistDto;
import com.myplaylists.service.BookmarkService;
import com.myplaylists.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PlaylistWebController {

    private final PlaylistService playlistService;

    @GetMapping("/")
    public String main(@Login LoginUser user) {
        if (user == null) {
            return "redirect:/login";
        }
        return "playlist/mylist";
    }

    @GetMapping("/all")
    public String viewAllPlaylists(@Login LoginUser user, @PageableDefault(size=6, sort="updatedDate", direction=Sort.Direction.DESC)Pageable pageable, Model model) {
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "playlist/all";
    }

    @GetMapping("/playlist")
    public String viewPlaylistAddForm() {
        return "playlist/addPlaylist";
    }

    @GetMapping("/search")
    public String viewMyPlaylistSearchResult(@Login LoginUser user, Model model, String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Playlist> playlists = playlistService.searchMylist(pageable, keyword, user.getId());

        model.addAttribute("playlists", playlists);
        model.addAttribute("isFirst", playlists.isFirst());
        model.addAttribute("isLast", playlists.isLast());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "playlist/searchMylist";
    }

    @GetMapping("/all/search")
    public String viewAllSearchResult(Model model, String keyword, @PageableDefault(size = 6, sort = "updatedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Playlist> playlists = playlistService.searchAll(pageable, keyword);
        model.addAttribute("playlists", playlists);
        model.addAttribute("isFirst", playlists.isFirst());
        model.addAttribute("isLast", playlists.isLast());
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "playlist/searchAll";
    }

    @GetMapping("/bookmark")
    public String findBookmarkedPlaylists(@Login LoginUser user, @PageableDefault(size=6, sort="createdDate",direction= Sort.Direction.DESC) Pageable pageable, Model model) {
        List<PlaylistDto> bookmarkedPlaylists = playlistService.findBookmarkedPlaylists(user.getId(), pageable);
        model.addAttribute("playlists", bookmarkedPlaylists);

        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        return "playlist/bookmark";
    }
}
