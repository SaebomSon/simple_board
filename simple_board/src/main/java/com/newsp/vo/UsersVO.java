package com.newsp.vo;

import lombok.Data;

@Data
public class UsersVO {
	private int idx;
	private String id;
	private String password;
	private String nickname;
	private String email;
	private String signup_date;
	private int level;
	private String level_image;
	private String auth_key;
	private int auth_status;
}
