package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DAO.BoardDAO;
import DTO.BoardDTO;

public class writeFreeFormService {
private BoardDAO boardDao;
	
	public writeFreeFormService() {
		boardDao = new BoardDAO();
	}
	
	//자유게시판 글쓰기
	public int insert(BoardDTO boardDto) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");  
		LocalDateTime now = LocalDateTime.now();
		boardDto.setWriteTime(dtf.format(now));
		
		return boardDao.writeFree(boardDto);
		
	}
}
