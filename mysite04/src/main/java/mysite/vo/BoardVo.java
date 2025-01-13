package mysite.vo;

public class BoardVo {
	private long id;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int groupNo;
	private int orderNo;
	private int depth;
	private int userId;
	private String writerName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	
	@Override
	public String toString() {
		return "BoardVo [id=" + id + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDAte="
				+ regDate + ", groupNo=" + groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", userId=" + userId
				+ ", writerName=" + writerName + "]";
	}
	
}
