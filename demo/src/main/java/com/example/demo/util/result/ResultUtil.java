package com.example.demo.util.result;

import com.example.demo.model.result.Result;

/**
 * 结果集 格式化
 * @author caocc
 *
 */
public class ResultUtil {

	public static Result success(Object o) {
		Result result = new Result();
		result.setCode(200);
		result.setMsg("success");
		result.setDate(o);
		return result;
	}
	
	public static Result success() {
		return success(null);
	}
	
	public static Result error(Integer code,String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
}
