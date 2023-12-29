package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DAO.BoardDAO;
import DTO.BoardDTO;


public class writeSecretFormService {
private BoardDAO boardDao;
	
	public writeSecretFormService() {
		boardDao = new BoardDAO();
	}
	
	//1:1 문의 글 쓰기
	public int insert(BoardDTO boardDto) {
		int num = boardDao.selectMaxNum();
		boardDto.setNum(num+1);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");  
		LocalDateTime now = LocalDateTime.now();
		boardDto.setWriteTime(dtf.format(now));
		
		return boardDao.writeSecret(boardDto);
		
	}
}
