package kh.init.feeds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAO {

	@Autowired
	private SqlSessionTemplate jdbc;
	
	public int registerReply(ReplyDTO dto)throws Exception{
		return jdbc.insert("Feed.registerReply", dto);
	}
	public int deleteReply(String col,int val)throws Exception{
		Map<String,Object> param = new HashMap<>();
		param.put("col", col);
		param.put("val", val);
		return jdbc.delete("Feed.deleteReply",param);
	}
	public List<ReplyDTO> viewAllReply(int feed_seq, int parent)throws Exception{
		Map<String,Object> param = new HashMap<>();
		param.put("feed_seq", feed_seq);
		param.put("parent", parent);
		return jdbc.selectList("Feed.viewAllReply",param);
	}
	public int replyNextSeq()throws Exception{
		return jdbc.selectOne("Feed.replyNextSeq");
	}
	public int updateReply(ReplyDTO dto)throws Exception{
		return jdbc.update("Feed.updateReply",dto);
	}
}
