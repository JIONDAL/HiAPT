package login;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class replyDTO {
	private SimpleIntegerProperty num;
	private SimpleStringProperty title;//글의 제목(댓글을 작성한 글의 제목)
	private SimpleStringProperty content;//글의 내용(댓글을 작성한 글의 내용)
	private SimpleStringProperty writeTime;//댓글 작성시간
	private SimpleStringProperty reply;//댓글 내용
	private SimpleStringProperty writer;//댓글 작성자
	
	
	public int getNum() {
		return num.get();
	}

	public void setNum(int num) {
		this.num = new SimpleIntegerProperty(num);
	}
	
	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title = new SimpleStringProperty(title);
	}

	public String getContent() {
		return content.get();
	}

	public void setContent(String content) {
		this.content = new SimpleStringProperty(content);
	}
	
	public String getWriteTime() {
		return writeTime.get();
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = new SimpleStringProperty(writeTime);
	}
	
	public String getReply() {
		return reply.get();
	}
	
	public void setReply(String reply) {
		this.reply = new SimpleStringProperty(reply);
	}
	
	public String getWriter() {
		return writer.get();
	}

	public void setWriter(String writer) {
		this.writer = new SimpleStringProperty(writer);
	}
	
	
}
