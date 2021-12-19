package com.newsp.vo;

import lombok.Data;

@Data
public class GradeVO {
	private int idx;
	private int user_idx;
	private int type;
	private int update_level;
	private String written_date;
	private String approval_date;
	private int status;
	
}
