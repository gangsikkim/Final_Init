package kh.init.friends;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.init.alarm.AlarmDAO;
import kh.init.members.MemberDAO;
import kh.init.members.MemberDTO;

@Service
public class FriendService {
   
	@Autowired
	private FriendDAO dao;
	
	@Autowired
	private MemberDAO mdao;
	
	@Autowired
	private AlarmDAO aldao;
	
	@Transactional("txManager") // 친구요청 리스트 가져오기
	public List<MemberDTO> getRequestMemList(String id) throws Exception{
		List<MemberDTO> realList = new ArrayList<>();
		List<FriendRequestDTO> list = dao.getFndRequestList(id);
		System.out.println("svc listsize : "+list.size());
		
		for(int i=0; i<list.size();i++) {
			MemberDTO mlist = mdao.getMyInfo(list.get(i).getFrom_id());
			realList.add(mlist);
			
		}
		System.out.println("realList :"+realList.size());
	
		return realList;
	}
	@Transactional("txManager")  //검색된 친구요청 멤버 리스트
	public List<MemberDTO> searchRequestMemList(String id,String search) throws Exception{
		List<MemberDTO> realList = new ArrayList<>();
		List<FriendRequestDTO> list = dao.getFndRequestList(id,search);
		System.out.println("svc listsize : "+list.size());
		
		for(int i=0; i<list.size();i++) {
			MemberDTO mlist = mdao.getMyInfo(list.get(i).getFrom_id());
			realList.add(mlist);
			
		}
		System.out.println("realList :"+realList.size());
	
		return realList;
	}
	@Transactional("txManager") // 친구 요청 받아주기
	public int acceptFriendRequest(String my_id,String yr_id,String my_relation) throws Exception{
		FriendRequestDTO dto = dao.getFndRequest2(my_id,yr_id);
		int result = dao.insertFriendship(my_id,yr_id,"1");
		int result2 = dao.insertFriendship(yr_id,my_id,dto.getRelation());
		int result3 = dao.deleteRequest(yr_id);
	    return result;
	}
	
	@Transactional("txManager")
	public int cutFriendship(String my_id, String fr_id) throws Exception{
		System.out.println("친구 끊기 svc 도착");
		int result =dao.deleteRelation(my_id, fr_id);
		int result2 =dao.deleteRelation(fr_id, my_id);
		return result;
	}
	
	@Transactional("txManager") // 친구 리스트 가져오기
	public List<MemberDTO> getFriendsListService(String id) throws Exception{
		List<MemberDTO> list = new ArrayList<>();
		List<FriendDTO> flist = dao.getFriendsList(id);
		System.out.println("flist : " + flist.size());
		for(int i=0;flist.size()>i;i++) {
			String fr_id = flist.get(i).getFr_id();
			list.add(mdao.getMyInfoByFriend(fr_id));
		}
		
			return list;
		
		
	}
	@Transactional("txManager") //검색된 친구 리스트 가져오기
	public List<MemberDTO> searchFriendsListService(String email,String search) throws Exception{
		List<MemberDTO> list = new ArrayList<>();
		System.out.println("넘어온 이메일은 "+email);
		List<FriendDTO> flist = dao.getFriendsList(email,search);
		System.out.println("검색 친구리스트 사이즈: "+flist.size());
		
		for(int i=0;flist.size()>i;i++) {
			String fr_id = flist.get(i).getFr_id();
			list.add(mdao.getMyInfoByFriend(fr_id));
		}
		
			return list;
		
		
	}
	
	@Transactional("txManager") //친구 요청하기
	public int friendRequestService(FriendRequestDTO dto,String id) throws Exception {
		System.out.println("친구요청 서비스 도착");
		List<FriendDTO> list = dao.getFriendsList(id);
		System.out.println("리스트 사이즈 : " + list.size());
		 int result = 0;
		 //기존 나의 친구 리스트 사이즈가 0일 때 바로 친구 요청
		 if(list.size() == 0 ) {
			 int seq = dao.selectByReqSeq(); //시퀀스 프롬 듀얼
			 System.out.println("seq : " + seq);
			 
			 result = dao.insertFndRequest(dto,id,seq); //친구 요청
			 aldao.alarmFriend(seq, dto);	// 친구 요청 알림에 담기
			System.out.println("결과값은 " + result);
		 }else {
			 //이미 친구인지 확인하는 코드
			 for(int i=0; i<list.size();i++) {
				 if(list.get(i).getFr_id() == dto.getTo_id()) {
				  System.out.println("이미 친구 관계입니다.");
				   return result; //친구가 존재하면 바로 리턴
				 }
			 }
			 int seq = dao.selectByReqSeq(); //시퀀스 프롬 듀얼
			 System.out.println("seq : " + seq);
			 
			 result = dao.insertFndRequest(dto,id,seq); //친구 요청
			 aldao.alarmFriend(seq, dto);	// 친구 요청 알림에 담기
			System.out.println("결과값은 " + result);
		 }
		
			 
		return result;
				
				
			   
		}
}