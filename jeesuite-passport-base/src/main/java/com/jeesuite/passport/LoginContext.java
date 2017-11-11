package com.jeesuite.passport;

import com.jeesuite.passport.exception.UnauthorizedException;
import com.jeesuite.passport.model.LoginSession;
import com.jeesuite.springweb.RequestContextHelper;

public class LoginContext {
	
	private final static ThreadLocal<LoginSession> holder = new ThreadLocal<>();
	

	public static LoginSession getLoginSession(){
		LoginSession loginSession =holder.get();
		if(loginSession == null){
			String headerString = RequestContextHelper.getRequest().getHeader(PassportConstants.HEADER_AUTH_USER);
			loginSession = LoginSession.decode(headerString);
//			if(loginSession != null){
//				holder.set(loginSession);
//			}
		}
		return loginSession;
	}
	
	public static void setLoginSession(LoginSession loginSession){
		if(loginSession == null){
			holder.remove();
		}else{			
			holder.set(loginSession);
		}
	}
	
	public static void resetLoginSessionHolder(){
		holder.remove();
	}
	
	/**
	 * 获取登录信息，未登录抛出异常
	 * @return
	 */
	public static LoginSession getRequireLoginSession(){
		LoginSession loginInfo = getLoginSession();
		if(loginInfo == null)throw new UnauthorizedException();
		return loginInfo;
	}
	
}
