package com.huho.android.sharelocation.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IAsynTaskDelegate {
	public void didSuccessWithMessage(String message);
	public void didFailWithMessage(String message);
	public void didSuccessWithJsonArray(JSONArray jsonArray);

}
