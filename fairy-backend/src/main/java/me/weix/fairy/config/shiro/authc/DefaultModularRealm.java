package me.weix.fairy.config.shiro.authc;

import me.weix.fairy.common.CONST;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
public class DefaultModularRealm extends ModularRealmAuthenticator {

    private Map<String, Object> definedRealms;


    /**
     * 调用单个realm执行操作
     */
    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm, AuthenticationToken token) {

        if (!realm.supports(token)) {
            throw new ShiroException("token错误!");
        }
        AuthenticationInfo info = realm.getAuthenticationInfo(token);

        if (info == null) {
            throw new ShiroException("token不存在!");
        }
        return info;
    }

    /**
     * 多个realm实现
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        return super.doMultiRealmAuthentication(realms, token);
    }

    /**
     * 判断登录类型执行操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {

        this.assertRealmsConfigured();
        Realm realm = null;
        CustomUsernamepasswordToken token = (CustomUsernamepasswordToken) authenticationToken;

        if (token.getLoginType() == CONST.LOGINTYPE.EMAIN) {//邮箱登录
            realm = (Realm) this.definedRealms.get("emailRealm");
        }
        if (token.getLoginType() == CONST.LOGINTYPE.MOBILE) {//手机登录
            realm = (Realm) this.definedRealms.get("mobileRealm");
        }
        if(token.getLoginType() == CONST.LOGINTYPE.USERNAME) {
            realm = (Realm) this.definedRealms.get("usernameRealm");
        }
        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }

    /**
     * 判断realm是否为空
     */
    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        this.definedRealms = this.getDefinedRealms();
        if (this.definedRealms == null || this.definedRealms.isEmpty()) {
            throw new ShiroException("值传递错误!");
        }
    }

    public Map<String, Object> getDefinedRealms() {
        return this.definedRealms;
    }

    public void setDefinedRealms(Map<String, Object> definedRealms) {
        this.definedRealms = definedRealms;
    }
}
