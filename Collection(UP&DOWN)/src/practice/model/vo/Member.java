package practice.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private String memberId; // 아이디
	private String memberPw; // 비밀번호
	private String memberName; // 이름
	private int highScore; // 최고 점수
	
	// 생성자
	public Member() { }

	
	public Member(String memberId, String memberPw, String memberName) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
	}

	public Member(String memberId, String memberPw, String memberName, int highScore) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.highScore = highScore;
	}


	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberPw=" + memberPw + ", memberName=" + memberName + ", highScore=" + highScore
				+ "]";
	}

	
	
	
}
