package kh.init.friends;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kh.init.members.MemberDTO;
import kh.init.members.MemberService;



@RequestMapping("/friend")
@Controller
public class FriendController {


	@Autowired
	private FriendService service;
	@Autowired
	private MemberService mservice;
	@Autowired
	private HttpSession session;
    
	@RequestMapping("/friendRequest") //친구 요청 하기
	@ResponseBody
	public String friendRequest(FriendRequestDTO dto,Model model) {
		try { 
			MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		int result = service.friendRequestService(dto,mDto.getEmail());
		System.out.println("친구요청 결과 : "+result);
		if(!(dto.getTo_id().equalsIgnoreCase(mDto.getEmail()))) {
            int frResult = service.friendIsOkService(dto.getTo_id(), mDto.getEmail());
            model.addAttribute("frResult", frResult);
            }
//		MemberDTO ydto = mservice.getMyPageService(dto.getTo_id());
//		model.addAttribute("mvo", ydto);
		 if(result == 1) {
			 return "complete";
		 }else if(result == 3){
			 return "alreadyFriend";
		 }else if(result == 4){
			 return "alreadyApply";
		 }else{
			 return "errors";
		 }
		
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	@RequestMapping("/rejectFndRequest") //친구요청 거부하기~
	@ResponseBody
	public String rejectFndRequest(String yr_id) {
		System.out.println("친구 요청 거부 CON 도착"); 
		System.out.println(yr_id);
		MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		try {
			int result = service.rejectFriendRequestService(mDto.getEmail(), yr_id);
			
			return "ok";
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	@RequestMapping("/redoFndRequest") //친구요청 취소하기~
	@ResponseBody
	public String redoFndRequest(String yr_id) {
		System.out.println("친구 요청 취소 CON 도착"); 
		System.out.println(yr_id);
		MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		try {
			int result = service.rejectFriendRequestService( yr_id,mDto.getEmail());
			
			return "ok";
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	@RequestMapping("/changeRelation") //친구 관계 변경하기~
	@ResponseBody
	public String changeRelation(String yr_id,String relation) {
		System.out.println("친구 관계 변경 CON 도착"); 
		System.out.println(yr_id);
		System.out.println("관계는 "+relation);
		MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		System.out.println("myid는 "+mDto.getEmail());
		try {
			int result = service.changeRelation( mDto.getEmail(),yr_id,relation);
			System.out.println("관계설정 결과는  "+result);
			return "ok";
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	@RequestMapping("/acceptFndRequest") //친구 받아주기~
	@ResponseBody
	public String acceptFndRequest(String yr_id,String relation) {
		System.out.println("친구 수락 CON 도착"); 
		System.out.println(yr_id);
		try {
			MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
			int result = service.acceptFriendRequest(mDto.getEmail(), yr_id,relation);
			return "ok";
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	@RequestMapping("/cutFndRelation") //친구 끊기
	@ResponseBody
	public String cutFndRelation(String yr_id) {
		System.out.println("친구끊기 CON 도착.");
		MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		try {
		int result = service.cutFriendship(mDto.getEmail(), yr_id);
		return "ok";
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	@RequestMapping(value ="/selectFndList" ,produces ="application/text; charset=utf-8")
	@ResponseBody   //친구 목록 조회~
	public String selectFndList(Model model) {
		MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		System.out.println("친구목록 조회 CON 도착.");
		System.out.println("세션값 : "+mDto.getEmail());
		try {
			List<MemberDTO> list = service.getFriendsListService(mDto.getEmail());
			List<MemberDTO> waitlist = service.getRequestMemList(mDto.getEmail());
			List<FriendDTO> frlist =service.getFriendsRelationService(mDto.getEmail());
			System.out.println("waitlist size ="+waitlist.size());
			
			System.out.println(list.size());
			System.out.println(frlist);
			System.out.println(frlist.size());
			Gson g = new Gson();
			
			g.toJson(waitlist);
			JsonObject obj = new JsonObject();
		    if(waitlist.size() != 0) {
            obj.addProperty("waitlist", g.toJson(waitlist));
		    }
		    if(list.size() != 0) {
            obj.addProperty("list", g.toJson(list));
            obj.addProperty("frlist", g.toJson(frlist));
		    }
            System.out.println(obj.toString());
			return obj.toString();
			}catch(Exception e) {
				e.printStackTrace();
				return "error";
			}
		
	}
	
	@RequestMapping(value ="/searchFndList" ,produces ="application/text; charset=utf-8")
	@ResponseBody  //친구 검색해보자~
	public String searchFndList(Model model,String search) {
		System.out.println("넘어온 서치값은 "+search);
		System.out.println("검색 친구목록 조회 CON 도착.");
		MemberDTO mDto = (MemberDTO)session.getAttribute("loginInfo");
		try {
			List<MemberDTO> list = service.searchFriendsListService(mDto.getEmail(),search);
			List<MemberDTO> waitlist = service.searchRequestMemList(mDto.getEmail(),search);
			List<FriendDTO> frlist =service.searchFriendsRelationService(mDto.getEmail(), search);
			System.out.println("waitlist size ="+waitlist.size());
			
			System.out.println(list.size());
			System.out.println(frlist.size());
			Gson g = new Gson();
			
			g.toJson(waitlist);
			JsonObject obj = new JsonObject();
		    if(waitlist.size() != 0) {
            obj.addProperty("waitlist", g.toJson(waitlist));
		    }
		    if(list.size() != 0) {
            obj.addProperty("list", g.toJson(list));
            obj.addProperty("frlist", g.toJson(frlist));
		    }
            System.out.println(obj.toString());
			return obj.toString();
			}catch(Exception e) {
				e.printStackTrace();
				return "error";
			}
		
	}
}

