package com.newsp.vo;

import lombok.Data;

@Data
public class UsersVO {
	private int idx;
	private String id;
	private String password;
	private String nickname;
	private String email;
	private int level;
	private String level_image;
	private String signup_date;
	private String auth_key;
	private int auth_status;
	private int user_status;
	private int warning;
	private String level_name;
}
