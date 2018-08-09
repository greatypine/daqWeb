package com.cnpc.pms.personal.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cnpc.pms.base.message.SMSStatusCode;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.SortFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.dynamic.common.SSLClient;
import com.cnpc.pms.personal.dto.CommentDto;
import com.cnpc.pms.personal.entity.HumanVacation;
import com.cnpc.pms.personal.manager.HumanReContentManager;
import com.cnpc.pms.personal.manager.HumanVacationManager;
import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;

@SuppressWarnings("all")
public class HumanReContentManagerImpl extends BizBaseCommonManager implements HumanReContentManager {
    
	
    
    
    
}
