package kh.init.messages;

import java.sql.Timestamp;

public class MessageDTO {
	private int msg_seq;
	private String from_id;
	private String to_id;
	private String contents;
	private Timestamp write_date;
	private String receive_read;
	
	public MessageDTO() {}
	public MessageDTO(int msg_seq, String from_id, String to_id, String contents, Timestamp write_date,
			String receive_read) {
		super();
		this.msg_seq = msg_seq;
		this.from_id = from_id;
		this.to_id = to_id;
		this.contents = contents;
		this.write_date = write_date;
		this.receive_read = receive_read;
	}
	
	public int getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(int msg_seq) {
		this.msg_seq = msg_seq;
	}
	public String getFrom_id() {
		return from_id;
	}
	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}
	public String getTo_id() {
		return to_id;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Timestamp write_date) {
		this.write_date = write_date;
	}
	public String getReceive_read() {
		return receive_read;
	}
	public void setReceive_read(String receive_read) {
		this.receive_read = receive_read;
	}
	
}
