package com.todaymusic.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todaymusic.dto.MusicDTO;
import com.todaymusic.service.MusicService;
import com.todaymusic.service.WeatherService;
import com.todaymusic.service.YoutubeService;



@Controller
public class BoardController {
//	@Autowired
//	MusicService musicService;
//	@Autowired
//	WeatherService weatherService;
	
	@Autowired
	private MusicService musicService;
	private WeatherService weatherService;
	private YoutubeService youtubeService;
	public BoardController(MusicService musicService, WeatherService weatherService, YoutubeService youtubeService) {
		this.musicService = musicService;
		this.weatherService = weatherService;
		this.youtubeService = youtubeService;
	}
	

	@GetMapping("/")
	public String main(Model model, @RequestParam(value="page", defaultValue="1") Integer pageNum) throws ParseException{
		HashMap<String, String> items = weatherService.getItemsFromApi(); //공공데이터api로부터 날씨정보 얻어온다.
	
		List<MusicDTO> musicList = musicService.getMusicList(pageNum,"1");//items.get("PTY")
		Integer[] pageList = musicService.getPageList(pageNum);
		
		model.addAttribute("musicList",musicList);
		model.addAttribute("pageList", pageList);
		
//		HashMap<String, String> items = weatherService.getItemsFromApi();
//		List<MusicDTO> musicList = musicService.getMusicList("3");//items.get("PTY")
//		model.addAttribute("musicList",musicList);
		return "board/list";
	}
	
	@GetMapping("/post")
	public String post() {
		return "board/post";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) throws IOException, ParseException {
		MusicDTO musicDTO = musicService.getMusic(id);
		model.addAttribute("music", musicDTO);
		String videoURL;
		videoURL = "http://www.youtube.com/embed/"+youtubeService.getVideoId(musicDTO.getTitle()+musicDTO.getArtist())+"?enablejsapi=1&origin=http://example.com";
		model.addAttribute("videoURL",videoURL);
		return "board/detail";
	}
	
	@PostMapping("/post")
	public String save(MusicDTO musicDTO) {
		musicService.saveMusic(musicDTO);
		return "redirect:/";
	}
}
