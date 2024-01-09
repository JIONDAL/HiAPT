package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import DTO.AptDTO;
import DTO.BoardDTO;
import DTO.replyDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BoardDAO {
	public Connection con;

	private int num;
	private String title;
	private String content;
	private String writer;
	private String writeTime;
	private int hits;
	private int likes;
	private String comments;

	public BoardDAO() {
		String url = "jdbc:oracle:thin:@59.15.170.143:1521:xe";
		String user = "oracle";
		String password = "oracle";

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection<BoardDTO> viewAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> members = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM anounce ORDER BY num ASC");
			rs = ps.executeQuery();
			while (rs.next()) {
				// BoardDTO member = new BoardDTO(num, title, content, writer, writeTime, hits,
				// likes);
				BoardDTO member = new BoardDTO();
				member.setNum(rs.getInt("num"));
				member.setTitle(rs.getString("title"));
				member.setContent(rs.getString("content"));
				member.setWriter(rs.getString("writer"));
				member.setWriteTime(rs.getString("write_time"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	public void insert(BoardDTO board) {
		String sql = "SELECT MAX(num) FROM anounce";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
				num += 1;
			} else {
				num = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		board.setNum(num);

		sql = "INSERT INTO anounce VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, board.getNum());
			ps.setString(2, board.getTitle());
			ps.setString(3, board.getContent());
			ps.setString(4, board.getWriter());
			ps.setString(5, board.getWriteTime());
			ps.setInt(6, board.getHits());
			ps.setInt(7, board.getLikes());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void update(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE anounce SET title=?, content=? WHERE num=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM anounce WHERE title=? and content=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 관리자계정 1:1 문의 전체보기
	public Collection<BoardDTO> secretViewAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> members = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM secret ORDER BY num ASC");
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO member = new BoardDTO();
				member.setNum(rs.getInt("num"));
				member.setTitle(rs.getString("title"));
				member.setContent(rs.getString("content"));
				member.setWriter(rs.getString("writer"));
				member.setWriteTime(rs.getString("write_time"));
				member.setComments(rs.getString("comments"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	// 관리자 계정 1:1문의 답변하기
	public void insertComment(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE secret SET comments=? WHERE num=?");
			ps.setString(1, board.getComments());
			ps.setInt(2, board.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 관리자 계정 1:1문의 답변 수정하기
	public void updateComment(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE secret SET comments=? WHERE num=?");
			ps.setString(1, board.getComments());
			ps.setInt(2, board.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 관리자 계정 1:1문의 삭제하기
	public void deleteSecret(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM secret WHERE title=? and content=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 1:1 문의 남기기
	public int writeSecret(BoardDTO boardDto) {
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement("INSERT INTO secret VALUES(?,?,?,?,?,?,?)");
			ps.setInt(1, boardDto.getNum());
			ps.setString(2, boardDto.getTitle());
			ps.setString(3, boardDto.getContent());
			ps.setString(4, boardDto.getWriter());
			ps.setString(5, boardDto.getWriteTime());
			ps.setString(6, boardDto.getId());
			ps.setString(7, null);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int selectMaxNum() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int num = 0;
		try {
			ps = con.prepareStatement("SELECT nvl(max(num), 0) FROM secret");
			rs = ps.executeQuery();
			if (rs.next())
				num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	// 내 문의 전체보기
	public Collection<BoardDTO> mySecretViewAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT num,title,content,write_time,comments FROM secret WHERE id=? ORDER BY num ASC");
			ps.setString(1, AptDTO.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriteTime(rs.getString("write_time"));
				dto.setComments(rs.getString("comments"));

				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 나의 문의 삭제하기
	public void mySecretdelete(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM secret WHERE num=?");
			ps.setInt(1, board.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 나의 문의 수정하기
	public int mySecretUpdate(BoardDTO board) {
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement("UPDATE secret SET title=?, content=? WHERE num=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getNum());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 관리자계정 자유게시판 전체보기
	public Collection<BoardDTO> freeAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> members = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM free ORDER BY num ASC");
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO member = new BoardDTO();
				member.setNum(rs.getInt("num"));
				member.setTitle(rs.getString("title"));
				member.setContent(rs.getString("content"));
				member.setWriter(rs.getString("writer"));
				member.setWriteTime(rs.getString("write_time"));
				members.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return members;
	}

	// 관리자계정 자유게시판 수정하기
	public void updateFree(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE free SET title=?, content=? WHERE num=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 관리자계정 댓글 전체보기(자유게시판)
	public Collection<BoardDTO> replyAll(String title, String content) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT * FROM reply where title=? and content=? ORDER BY num ASC");
			ps.setString(1, title);
			ps.setString(2, content);
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO member = new BoardDTO();
				member.setNum(rs.getInt("num"));
				member.setReplyComment(rs.getString("reply_comment"));
				member.setReplyId(rs.getString("reply_id"));
				member.setReplyTime(rs.getString("reply_time"));
				list.add(member);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 관리자 계정 댓글 넣기(자유게시판)
	public void insertReply(BoardDTO board) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT MAX(num) FROM reply");
			rs = ps.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1);
				num += 1;
			} else {
				num = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		board.setNum(num);

		try {
			ps = con.prepareStatement("INSERT INTO reply VALUES(?, ?, ?, ?, ?, ?)");
			ps.setInt(1, board.getNum());
			ps.setString(2, board.getTitle());
			ps.setString(3, board.getContent());
			ps.setString(4, board.getReplyComment());
			ps.setString(5, board.getWriter());
			ps.setString(6, board.getWriteTime());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 관리자 계정 자유게시판 댓글 삭제하기
	public void deleteReply(BoardDTO board) {
		PreparedStatement ps = null;
		String deleteComment = "관리자에 의해 삭제되었습니다.";
		try {
			ps = con.prepareStatement("UPDATE reply SET reply_comment=? WHERE num=?");
			ps.setString(1, deleteComment);
			ps.setInt(2, board.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 관리자 계정 자유게시판 삭제하기
	public void deleteFree(BoardDTO board) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM free WHERE title=? and content=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 유저-------------------------------------------------------------------------------------------------------------------
	// 활동내역
	// 작성게시글 전체보기
	public Collection<BoardDTO> myWriteAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT content, write_time FROM secret WHERE writer = ? "
					+ "UNION ALL SELECT content, write_time FROM free WHERE writer = ?");
			ps.setString(1, AptDTO.getId());
			ps.setString(2, AptDTO.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO myWrtie = new BoardDTO();
				myWrtie.setContent(rs.getString("content"));
				myWrtie.setWriteTime(rs.getString("write_time"));

				list.add(myWrtie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 작성댓글 전체보기
	public Collection<replyDTO> myReplyAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<replyDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT reply_comment,reply_time from reply where reply_id=?");
			ps.setString(1, AptDTO.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				replyDTO myReply = new replyDTO();
				myReply.setReply(rs.getString("reply_comment"));
				myReply.setWriteTime(rs.getString("reply_time"));

				list.add(myReply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 유저가 관리자가 등록한 공지사항 보기
	public Collection<BoardDTO> announceViewAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement("SELECT num,title,content,write_time FROM anounce ORDER BY num ASC");
			rs = ps.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriteTime(rs.getString("write_time"));

				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 공지사항 내용 검색
	public Collection<BoardDTO> searchContent(String searchFld) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			ps = con.prepareStatement("SELECT num,title,content,write_time FROM anounce WHERE content like ?");
			ps.setString(1, "%" + searchFld + "%");
			rs = ps.executeQuery();

			ArrayList<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriteTime(rs.getString("write_time"));

				list.add(dto);
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 자유게시판 목록
	// 더블 클릭시 -> 게시글 상세보기를 위해 작성일, 작성자, 조회수, 좋아요수까지 DB에서 가져옴
	public Collection<BoardDTO> freeViewAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<BoardDTO> list = new ArrayList<>();
		try {

			ps = con.prepareStatement(
					"SELECT num,title,content,write_time,writer,hits,likes FROM free ORDER BY num ASC");
			rs = ps.executeQuery();

			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriteTime(rs.getString("write_time"));
				dto.setWriter(rs.getString("writer"));
				dto.setHits(rs.getInt("hits"));
				dto.setLikes(rs.getInt("likes"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 자유게시판 글쓰기
	public int writeFree(BoardDTO boardDto) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT MAX(num) FROM free");
			rs = ps.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1);
				num += 1;
			} else {
				num = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		boardDto.setNum(num);

		int result = 0;
		try {// 조회수, 좋아요 수 0
			ps = con.prepareStatement("INSERT INTO free VALUES(?,?,?,?,?,0,0)");
			ps.setInt(1, boardDto.getNum());
			ps.setString(2, boardDto.getTitle());
			ps.setString(3, boardDto.getContent());
			ps.setString(4, boardDto.getWriter());
			ps.setString(5, boardDto.getWriteTime());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 자유게시판 검색
	public Collection<BoardDTO> searchFree(String searchFld) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(
					"SELECT num,title,content FROM free WHERE content like ?");
			ps.setString(1, "%" + searchFld + "%");
			rs = ps.executeQuery();

			ArrayList<BoardDTO> list = new ArrayList<>();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));

				list.add(dto);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 자유게시판 목록에서 게시글 더블 클릭시 조회수 +1
	public void updateHits(int num) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE free SET hits=hits+1 WHERE num=?");
			ps.setInt(1, num);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 자유게시판 글 수정
	public int myFreeUpdate(BoardDTO board) {
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement("UPDATE free SET title=?, content=? WHERE num=?");
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getNum());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 자유게시판 글 삭제
	public int myFreeDelete(BoardDTO board) {
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement("DELETE from free WHERE num=?");
			ps.setInt(1, board.getNum());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 자유게시판 댓글 등록
	public int writeReply(replyDTO replyDto) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("SELECT MAX(num) FROM reply where title=? and content=?");
			ps.setString(1, replyDto.getTitle());
			ps.setString(2, replyDto.getContent());
			rs = ps.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1);
				num += 1;
			} else {
				num = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		replyDto.setNum(num);

		int result = 0;
		try {
			ps = con.prepareStatement("INSERT INTO reply VALUES(?,?,?,?,?,?)");
			ps.setInt(1, replyDto.getNum());// num
			ps.setString(2, replyDto.getTitle());// title(댓글 단 게시글의 제목)
			ps.setString(3, replyDto.getContent());// content(댓글 단 게시글의 내용)
			ps.setString(4, replyDto.getReply());// reply_comment(댓글)
			ps.setString(5, replyDto.getWriter());// reply_id(댓글 작성자)
			ps.setString(6, replyDto.getWriteTime());// reply_time(댓글 작성시간)
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 댓글수 카운트
	public int replyNum(BoardDTO dto) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM reply where title=? and content=?");
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			rs = ps.executeQuery();
			num = 0;
			while (rs.next())
				num += 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	// 자유게시판 댓글 삭제
	public void myReplyDelete(replyDTO dto) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("DELETE FROM reply WHERE num=?");
			ps.setInt(1, dto.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 자유게시판 댓글 목록
	public Collection<replyDTO> replyViewAll(BoardDTO boardDto) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<replyDTO> list = new ArrayList<>();
		try {
			ps = con.prepareStatement(
					"SELECT reply_comment,reply_id,num,reply_time FROM reply where title=? and content=? ORDER BY num ASC");
			ps.setString(1, boardDto.getTitle());
			ps.setString(2, boardDto.getContent());
			rs = ps.executeQuery();
			while (rs.next()) {
				replyDTO dto = new replyDTO();
				dto.setReply(rs.getString("reply_comment"));
				dto.setWriter(rs.getString("reply_id"));
				dto.setNum(rs.getInt("num"));
				dto.setWriteTime(rs.getString("reply_time"));

				list.add(dto);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 좋아요 눌렀을 떄
	public void updateLikes(BoardDTO dto, int count) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE free SET likes=? WHERE num=?");
			ps.setInt(1, count);
			ps.setInt(2, dto.getNum());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
