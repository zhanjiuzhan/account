package org.account.cl.impl;

import org.account.cl.*;
import org.account.cl.condition.UserQuery;
import org.account.cl.exception.exception.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final int USERNAME_LEN = 32;

    @Value("${security.my.private-key}")
    private String privateKey;

    @Value("${security.my.public-key}")
    private String publicKey;

    @Autowired
    @Qualifier("userDaoCacheImpl")
    private UserDao userDaoCacheImpl;

    @Autowired
    @Qualifier("defaultPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    {
        this.privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsFC8TxOaiqiNwi+PFUWMU5YOqllzThHyQg2j27Viw0BNDCBUA2vVxTR/tgGjjluSCaqjMzHraA7ZtQinvtD0jR7wwqmIUqtnlgkDAi62pTF3AANnuLRmRGdXX/Qy8x7T+1S6zm4215R+i3oMwM5FNJkr+z0If1nVtuEsoxyGnjAgMBAAECgYBfHle8K7Tg07YKsR8VuDl40FagliZMNxAgnx3UCR7f5cO5tlbzZcDQr+bbdxjZ/0xYo5p6p6qHAtYQY94tBrlifTYn5HbmQacaVQ2eHLYi4IhrzomH6CerSYL6KJPM8uccwOsPmTjYbrYO1Q5ewmmNau20WGYBb2PGwe9OARPMMQJBANF+hnpBceUi/dboFZCARleJlpMrz/T8Nf/jKTNuZqKapq5WZRTVgGPg6G2KumwV4TjD3iqzixddILpSYwi+VTkCQQCp4Xsp2lzIwcEtC2gAhxsbX+x3RThIcA7P33W3MXMtROjOMDNotVFXy8HavcGmskctK4gjBwsHtAOQ0UYKNLP7AkAqCPc2xLbzwSep3XumOPbkTak28o2RnKHBLHVx6m8RpXJYfOFfYs+WOuSoRjPNyD4ew75qVwhLsdYFTM6uTHKRAkEAlmdKawCoWxmn1SCfILB9YFwp+GLxdLi5dHNsPhfq2C6FS1/DdDXr4aZFaEvOcq6uc6Bx9EkdC+HlskaGEy0cFwJAA8LXayt346misCrkG3ZORUEFHoTCwTdFmqArzBBDiHmiUXddZaDmQsMyX3yVaYBCltl/n3tu7H5Gffht242oqA==";
        this.publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLBQvE8TmoqojcIvjxVFjFOWDqpZc04R8kINo9u1YsNATQwgVANr1cU0f7YBo45bkgmqozMx62gO2bUIp77Q9I0e8MKpiFKrZ5YJAwIutqUxdwADZ7i0ZkRnV1/0MvMe0/tUus5uNteUfot6DMDORTSZK/s9CH9Z1bbhLKMchp4wIDAQAB";
    }

    @Override
    public boolean addUser(User user) {
        // 将用户的密码进行解码
        String original = getDecodePassword(user.getPassword());
        // 将用户密码进行编码
        user.setPassword(getBCryptPassword(original));
        // 凭证未过期
        user.setCredentialsExpired(0);
        // 用户可用
        user.setIsEnable(1);
        // 用户未过期
        user.setExpired(0);
        // 用户未锁定
        user.setLocked(0);
        return userDaoCacheImpl.addUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null || username.trim().length() >= USERNAME_LEN) {
            return null;
        }
        return userDaoCacheImpl.getUserByUsername(username);
    }

    /**
     * 解码前端密码为明文密码
     * @param encodePassword
     * @return
     */
    @Override
    public String getDecodePassword(String encodePassword) {
        try {
            // password 需要进行Base64解码
            byte[] pwd = Base64.getDecoder().decode(encodePassword);
            // 进行RSA校验
            return JcSecurityUtils.decryptByPrivate(new String(pwd), JcSecurityUtils.getPrivateKey(privateKey));
        } catch (Exception e) {
            logger.warn("密码解码失败: " + e.getMessage() + " 未按照规则上传密码!");
            throw ExceptionEnum.INVALID_PARAMETER2.newException("密码格式有误");
        }
    }

    @Override
    public long loginNum(String username, UserDao.USER_OP op) {
        return userDaoCacheImpl.loginNum(username, op);
    }

    @Override
    public List<User> gets() {
        return userDaoCacheImpl.gets();
    }

    @Override
    public int getsByConditionCount(UserQuery query) {
        return userDaoCacheImpl.getsByConditionCount(query);
    }

    @Override
    public List<User> getsByCondition(UserQuery query) {
        return userDaoCacheImpl.getsByCondition(query);
    }

    @Override
    public boolean updateUser(String username, UserQuery query) {
        if (username == null || username.trim().length() >= USERNAME_LEN) {
            return false;
        }

        if (JcStringUtils.isNotBlank(query.getPassword())) {
            // 将用户的密码进行解码
            String original = getDecodePassword(query.getPassword());
            // 将用户密码进行编码
            query.setPassword(getBCryptPassword(original));
        }
        return userDaoCacheImpl.updateUser(username, query);
    }

    @Override
    public boolean deleteUser(String username) {
        if (username == null || username.trim().length() >= USERNAME_LEN) {
            return true;
        }
        User user = getUserByUsername(username);
        if (user == null) {
            return true;
        } else {
            // 用户已经不可用了 删除了
            if (user.getIsEnable() == 0) {
                return userDaoCacheImpl.deleteUser(username);
            } else {
                // 修改其状态不可用
                return userDaoCacheImpl.updateUser(username, new UserQuery().setEnable(false));
            }
        }
    }

    @Override
    public boolean isOneUser(String username, String password) {
        // 对密码进行解密
        String original = getDecodePassword(password);
        // 从数据库中取得user信息
        User user = getUserByUsername(username);
        // 进行密码校验
        return user != null && passwordEncoder.matches(original, user.getPassword());
    }

    @Override
    public boolean isValidUser(String username) {
        // 从数据库中取得user信息
        User user = getUserByUsername(username);
        return user != null && user.getIsEnable() == 1 && user.getExpired() == 0 && user.getLocked() == 0 && user.getCredentialsExpired() == 0;
    }

    /**
     * 将密码变成数据库中存储的形式
     * @param password
     * @return
     */
    private String getBCryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 将密码变成前端发送过来的形式 WARNING 测试用 正式无用
     * @param password
     * @return
     */
    private String getWebPassword(String password) {
        password = JcSecurityUtils.encryptByPublic(password.getBytes(), JcSecurityUtils.getPublicKey(publicKey));
        password = Base64.getEncoder().encodeToString(password.getBytes());
        return password;
    }

    public static void main(String[] args) throws Exception {
        UserServiceImpl obj = new UserServiceImpl();
        String pwd = "123456";
        String publicPassword = obj.getWebPassword(pwd);
        System.out.println("前端加密后是: " + publicPassword);

        String pt = new String(Base64.getDecoder().decode(publicPassword));
        String password = JcSecurityUtils.decryptByPrivate(pt, JcSecurityUtils.getPrivateKey(obj.privateKey));
        System.out.println("原密码是: " + password);

        if (password.equals(pwd)) {
            String dbPassowrd = new BCryptPasswordEncoder().encode(password);
            System.out.println("保存在数据库中的是: " + dbPassowrd);
        }

        System.out.println(obj.getWebPassword("acc1232"));
    }
}
