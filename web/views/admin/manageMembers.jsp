<%@ page language="java" contentType="text/html;charset=UTF-8" 
	pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.member.model.vo.Member" %>
<% 
	List<Member> members = (ArrayList)request.getAttribute("members");
	for(Member m:members){
		for(int i=0; i<members.size(); i++){
			System.out.println(m.getUserId());
			System.out.println(m.getUsername());
			System.out.println(m.getGender());
			System.out.println(m.getAge());
			System.out.println(m.getEmail());
			System.out.println(m.getPhone());
			System.out.println(m.getHobby());
			System.out.println(m.getEnrollDate());
		}
	}
%>