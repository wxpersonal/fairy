package me.weix.fairy.shiro.session;/*
package me.weix.fairy.shiro.session;


import com.wx.util.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

*/
/**
 * 基于redis的sessionDao，缓存共享session
 * Created by shuzheng on 2017/2/23.
 *//*

public class CustomSessionDao extends EnterpriseCacheSessionDAO {

    private static Logger log = LoggerFactory.getLogger(CustomSessionDao.class);
    // 会话key
    private final static String WHATEVER_SESSION_ID = "fairy-session-id";

    */
/**
     * 创建session，缓存到redis
     *
     * @param session
     * @return
     *//*

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        RedisUtil.set(WHATEVER_SESSION_ID + "_" + sessionId, session, 5, TimeUnit.MINUTES);
        log.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    */
/**
     * 从redis获取session
     *
     * @param sessionId
     * @return
     *//*

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        if(session == null) {
            session = (Session) RedisUtil.getCachedObject(WHATEVER_SESSION_ID + "_" + sessionId);
        }
        log.debug("doReadSession >>>>> sessionId={}", sessionId);
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        RedisUtil.set(WHATEVER_SESSION_ID + "_" + session.getId(), session, 5, TimeUnit.MINUTES);
        log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {

        RedisUtil.deleteObjectByKey(WHATEVER_SESSION_ID + "_" + session.getId());
        log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

}
*/
